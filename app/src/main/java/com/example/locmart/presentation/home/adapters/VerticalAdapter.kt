package com.example.locmart.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.databinding.ItemProductBinding
import com.example.locmart.presentation.products.ProductViewHolder

class VerticalAdapter(
    private val product: List<Product>,
    private val onClick: (product: Product) -> Unit,
    private val liked: (product: Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = product.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.bind(product[position], onClick, liked)

    }

}
