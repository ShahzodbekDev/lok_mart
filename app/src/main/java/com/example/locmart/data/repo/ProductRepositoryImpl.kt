package com.example.locmart.data.repo

import com.example.locmart.data.api.product.ProductApi
import com.example.locmart.data.api.product.dto.HomeResponse
import com.example.locmart.data.store.UserStore
import com.example.locmart.domain.repo.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val userStore: UserStore
) : ProductRepository {
    override suspend fun getHome(): HomeResponse {
       val response = productApi.getHome()
        userStore.set(response.user)
        return response
    }
}