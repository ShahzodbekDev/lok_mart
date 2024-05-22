package com.example.locmart.presentation.products

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.domain.model.ProductQuery
import com.example.locmart.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository,

    ) : ViewModel() {
    val loading = MutableLiveData(false)
    val erorr = MutableLiveData(false)
    val products = MediatorLiveData<PagingData<Product>>()
    val category = MutableLiveData<Category>()

    fun setCategory(category: Category) {
        this.category.postValue(category)
        getProducts()
    }

    fun getProducts() {
        val query = ProductQuery(category = category.value)
        val products = productRepository.getProducts(query)
        this.products.addSource(products) {
            this.products.postValue(it)
        }
    }

    fun setLoadState(state : CombinedLoadStates){
        val loading = state.source.refresh is LoadState.Loading
        this.loading.postValue(loading)
    }


}