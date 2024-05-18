package com.example.locmart.data.api.product

import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.data.api.product.dto.HomeResponse
import retrofit2.http.GET

interface ProductApi {

    @GET("home")
    suspend fun getHome() : HomeResponse


    @GET("categories")
    suspend fun getCategories() : List<Category>
}