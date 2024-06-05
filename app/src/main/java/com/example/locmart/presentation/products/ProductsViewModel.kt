package com.example.locmart.presentation.products

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.domain.model.ProductQuery
import com.example.locmart.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository,

    ) : ViewModel() {
    val loading = MutableLiveData(false)
    val erorr = MutableLiveData(false)
    val products = MutableLiveData<PagingData<Product>>()
    val category = MutableLiveData<Category>()

    fun setCategory(category: Category) {
        this.category.postValue(category)
        getProducts()
    }

    fun getProducts() = viewModelScope.launch{
        val query = ProductQuery(category = category.value)
        productRepository.getProducts(query).collectLatest {
            products.postValue(it)
        }

    }

    fun setLoadState(states : CombinedLoadStates){
        val loading = states.source.refresh is LoadState.Loading
        this.loading.postValue(loading)
    }


}