package com.example.locmart.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.locmart.R
import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.databinding.FragmentFilterBinding
import com.example.locmart.databinding.ItemRadioGroupBinding
import com.example.locmart.domain.model.ProductQuery
import com.example.locmart.domain.model.Sort
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private val viewModel by viewModels<FilterViewModel>()
    private val args by navArgs<FilterFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding) {

        viewModel.categories.observe(viewLifecycleOwner) { categories ->

            listOf(categoriesTitle, categoriesGroup, othersDivider).forEach {
                it.isVisible = true

                categories.forEach {
                    val radioBinding = ItemRadioGroupBinding.inflate(layoutInflater)
                    radioBinding.root.text = it.title
                    radioBinding.root.tag = it
                    radioBinding.root.isChecked = args.filter.category?.id == it.id
                    categoriesGroup.addView(radioBinding.root)
                }
            }
        }
        viewModel.events.observe(viewLifecycleOwner) {

            when (it) {
                FilterViewModel.Event.CategoriesError -> {
                    val sneckBar = Snackbar.make(
                        binding.root,
                        R.string.fragment_filter_categories_error,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    sneckBar.setAction(R.string.retry) {
                        sneckBar.dismiss()
                        viewModel.getCategories()
                    }
                    sneckBar.show()
                }

            }
        }
    }

    private fun initUI() = with(binding) {

        close.setOnClickListener {
            findNavController().popBackStack()
        }

        val filter = args.filter
        filter.range?.let { priceSlider.values = it.toList() }
        filter.rating?.let {
            (ratingGroup.getChildAt(it) as RadioButton).isChecked = true
        }

        filter.discount?.let {
            (discountsGroup.getChildAt(it) as RadioButton).isChecked = true
        }

        discountSort.isChecked = filter.sort.contains(Sort.discount)
        shippingSort.isChecked = filter.sort.contains(Sort.shipping)
        voucherSort.isChecked = filter.sort.contains(Sort.voucher)
        deliverySort.isChecked = filter.sort.contains(Sort.delivery)

        apply.setOnClickListener {
            val sort = mutableListOf<Sort>()
            if (discountSort.isChecked) sort.add(Sort.discount)
            if (shippingSort.isChecked) sort.add(Sort.shipping)
            if (voucherSort.isChecked) sort.add(Sort.voucher)
            if (deliverySort.isChecked) sort.add(Sort.delivery)
            val query = ProductQuery(
                category = categoriesGroup.children.map { it as RadioButton }
                    .firstOrNull() { it.isChecked }?.tag as? Category,
                search = args.filter.search,
                range = priceSlider.values[0] to priceSlider.values[1],
                rating = ratingGroup.children
                    .mapIndexed { index, view -> index to (view as RadioButton).isChecked }
                    .firstOrNull { it.second }?.first,
                discount = discountsGroup.children
                    .mapIndexed { index, view -> index to (view as RadioButton).isChecked }
                    .firstOrNull { it.second }?.first,
              sort = sort

            )
            val result = bundleOf(RESULT_KEY to query)
            setFragmentResult(REQUEST_KEY, result)
            findNavController().popBackStack()

        }

    }

    companion object {
        const val REQUEST_KEY = "filter_request_key"
        const val RESULT_KEY = "filter_result_key"
    }

}
