package com.example.locmart.domain.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.data.api.product.dto.HomeResponse
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.domain.model.ProductQuery
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getHome(): HomeResponse
    suspend fun getCategories(): List<Category>
    fun getProducts(query: ProductQuery): Flow<PagingData<Product>>

    fun getRecents(): Flow<List<String>>

    suspend fun clearRecents()

    suspend fun addRecent(search:String)
}