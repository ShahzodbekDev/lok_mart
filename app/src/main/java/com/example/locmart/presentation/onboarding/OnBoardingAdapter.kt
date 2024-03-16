package com.example.locmart.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.locmart.R
import com.example.locmart.databinding.ItemOnboardingBinding

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {

    private val items = listOf(
        Triple(
            R.drawable.onboarding_image_0,
            R.string.onboarding_title_0,
            R.string.onboarding_description_0
        ),
        Triple(
            R.drawable.onboarding_image_1,
            R.string.onboarding_title_1,
            R.string.onboarding_description_1
        ),
    )

    class ViewHolder(private val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(page: Triple<Int, Int, Int>) = with(binding) {
            Glide.with(root.context).load(page.first).into(image)
            title.text = root.context.getString(page.second)
            description.text = root.context.getText(page.third)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOnboardingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

    }
}