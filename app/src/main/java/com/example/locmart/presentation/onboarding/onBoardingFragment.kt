package com.example.locmart.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.locmart.R
import com.example.locmart.databinding.FragmentOnboardingBinding
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class onBoardingFragment: Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val viewModel by viewModels<OnboardingViewModel>()
    private val adapter = OnBoardingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() = with(binding) {

        pager.adapter = adapter

        indicator.apply {
            val normalColor = ContextCompat.getColor(requireContext(), R.color.indicator_unchacked)
            val checkedColor = ContextCompat.getColor(requireContext(), R.color.indicator_chacked)
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(resources.getDimension(R.dimen.dp_15))
            setSliderHeight(resources.getDimension(R.dimen.dp_8))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setPageSize(adapter.itemCount)
            notifyDataChanged()
        }

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.onPageSelected(position)
                next.text=if(position == adapter.itemCount-1){
                    getString(R.string.fragment_onboarding_get_started)
                }else{
                    getString(R.string.fragment_onboarding_next)
                }
            }
        })

        next.setOnClickListener {
            if(pager.currentItem == adapter.itemCount-1){
                viewModel.onboarded()
                findNavController().navigate(onBoardingFragmentDirections.toSignInFragment())

            }else{
                pager.setCurrentItem(pager.currentItem+1, true)
            }

        }
    }


}