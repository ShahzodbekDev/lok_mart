package com.example.locmart.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.locmart.R
import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.databinding.ItemCategoryHomeBinding

class HomeCategoryAdapter(
    private val categoriys: List<Category>,
    private val onClick: (category: Category) -> Unit
) : RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCategoryHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) = with(binding) {

            Glide.with(root).load(category.image).into(image)
            name.text = category.title
           items.text = root.context.getString(R.string.item_category_count, category.count)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        ItemCategoryHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = categoriys.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoriys[position])
    }

}