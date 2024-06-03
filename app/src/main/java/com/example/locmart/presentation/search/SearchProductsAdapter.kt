package com.example.locmart.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.databinding.ItemProductBinding
import com.example.locmart.databinding.ItemProductSearchBinding

class SearchProductsAdapter(
    private val onClick: (product: Product) -> Unit,
    private val liked: (product: Product) -> Unit

) : PagingDataAdapter<Product, SearchProductViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return, onClick, liked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchProductViewHolder(
        ItemProductSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}