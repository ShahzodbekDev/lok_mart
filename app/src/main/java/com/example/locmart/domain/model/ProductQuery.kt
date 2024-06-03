package com.example.locmart.domain.model

import com.example.locmart.data.api.product.dto.Category

data class ProductQuery(
    val category: Category? = null,
    val search : String? = null
)

