package com.example.locmart.data.api.product

import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.data.api.product.dto.HomeResponse
import com.example.locmart.data.api.product.dto.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("home")
    suspend fun getHome() : HomeResponse


    @GET("categories")
    suspend fun getCategories() : List<Category>

    @GET("products")
    suspend fun getProducts(
        @Query("category_id") categoryId: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : List<Product>
}