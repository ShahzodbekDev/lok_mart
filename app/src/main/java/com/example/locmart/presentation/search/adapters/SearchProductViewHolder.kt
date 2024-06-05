package com.example.locmart.presentation.search.adapters

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.locmart.R
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.databinding.ItemProductSearchBinding

class SearchProductViewHolder(private val binding: ItemProductSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        onClick: (product: Product) -> Unit,
        liked: (product: Product) -> Unit
    ) = with(binding) {
        Glide.with(root).load(product.image).into(image)

        name.text = product.title

        rating.text = String.format("%.1f", product.rating)

        ratingCaunt.text =
            root.context.getString(R.string.item_product_ratings_count, product.ratingCount)

        val current = product.price - (product.discount ?: 0.0)
        price.text = root.context.getString(R.string.price, current)
        old.text = root.context.getString(R.string.price_striked, product.price)
        old.isVisible = product.discount != null

        root.setOnClickListener {
            onClick(product)
        }

    }
}