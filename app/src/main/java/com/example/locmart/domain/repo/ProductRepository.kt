package com.example.locmart.domain.repo

import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.data.api.product.dto.HomeResponse

interface ProductRepository {

    suspend fun getHome() : HomeResponse
    suspend fun getCategories() : List<Category>
}