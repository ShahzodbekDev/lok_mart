package com.example.locmart.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.locmart.R
import com.example.locmart.data.api.product.dto.Banner
import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.data.api.product.dto.Section
import com.example.locmart.databinding.FragmentHomeBinding
import com.example.locmart.presentation.home.adapters.BannerAdapter
import com.example.locmart.presentation.home.adapters.HomeCategoryAdapter
import com.example.locmart.presentation.home.adapters.SectionAdapter
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()
        initUI()
    }

    private fun initUI() = with(binding) {
        retry.setOnClickListener {
            viewModel.getHome()
        }

        indicator.apply {
            val normalColor = ContextCompat.getColor(requireContext(), R.color.indicator_unchacked)
            val checkedColor = ContextCompat.getColor(requireContext(), R.color.indicator_chacked)
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(resources.getDimension(R.dimen.dp_20))
            setSliderHeight(resources.getDimension(R.dimen.dp_4))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            notifyDataChanged()
        }
    }

    private fun subscribeToLiveData() = with(binding) {

        viewModel.loading.observe(viewLifecycleOwner) {
            progress.isVisible = it
        }
        viewModel.erorr.observe(viewLifecycleOwner) {
            erorr.isVisible = it
        }
        viewModel.home.observe(viewLifecycleOwner) {
            home.isVisible = it != null
            it ?: return@observe

            val name = it.user.firstName ?: it.user.username
            greeting.text = getString(R.string.fragment_home_greeting, name)
            Glide.with(root).load(it.user.avatar).into(avatar)

            banners.adapter = BannerAdapter(it.banners, this@HomeFragment::onBannerClick)
            indicator.setupWithViewPager(banners)
            indicator.apply {
                setPageSize(it.banners.size)
                notifyDataChanged()
            }

            categories.adapter =
                HomeCategoryAdapter(it.categories, this@HomeFragment::onCategoryClick)

            sections.adapter = SectionAdapter(
                it.sections,
                this@HomeFragment::showAll,
                this@HomeFragment::onClickProduct,
                this@HomeFragment::like
            )
        }


    }

    private fun onBannerClick(banner: Banner) {

    }

    private fun onCategoryClick(category: Category) {

    }

    private fun showAll(section: Section) {

    }

    private fun onClickProduct(product: Product) {

    }

    private fun like(product: Product) {

    }
}