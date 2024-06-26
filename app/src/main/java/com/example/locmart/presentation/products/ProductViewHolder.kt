package com.example.locmart.presentation.products

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.locmart.R
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.databinding.ItemProductBinding
import kotlin.math.roundToInt

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        product: Product,
        onClick: (product: Product) -> Unit,
        liked: (product: Product) -> Unit
    ) = with(binding) {
        Glide.with(root).load(product.image).into(image)
        discount.isVisible = product.discount != null

        product.discount?.let {
            val discount = ((product.discount / product.price) * 100).roundToInt()
            binding.discount.text =
                root.context.getString(R.string.fragment_item_product_discount, discount)
        }


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

        fun setLike() {
            val liked =
                if (product.favorite) R.drawable.ic_heart_chacked else R.drawable.ic_heart_unchacked
            binding.like.setImageResource(liked)
        }
        setLike()

        binding.like.setOnClickListener {
            product.favorite = product.favorite.not()
            setLike()
            liked(product)
        }

    }
}