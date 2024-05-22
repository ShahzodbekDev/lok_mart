package com.example.locmart.presentation.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment: Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel by viewModels<CategoriesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() = with(binding) {

       viewModel.loading.observe(viewLifecycleOwner) {
           loading.root.isVisible = it
       }
       viewModel.erorr.observe(viewLifecycleOwner) {
           error.root.isVisible = it
       }

        viewModel.categories.observe(viewLifecycleOwner) {
            categories.adapter = CategoriesAdapter(it, this@CategoriesFragment::onCategoryClick)
        }
    }

    private fun initUI() = with(binding) {
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        error.retry.setOnClickListener {
            viewModel.getCategories()
        }
    }

    private fun onCategoryClick(category: Category) {
    findNavController().navigate(CategoriesFragmentDirections.toProductsFragment(category))
    }
}