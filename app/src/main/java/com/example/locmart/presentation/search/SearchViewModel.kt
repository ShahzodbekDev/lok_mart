package com.example.locmart.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.data.api.product.dto.Product
import com.example.locmart.domain.model.ProductQuery
import com.example.locmart.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {


    val loading = MutableLiveData(false)
    val products = MutableLiveData<PagingData<Product>>()
    val query = MutableLiveData(ProductQuery())
    val recents = MutableLiveData<List<String>>()


    init {
        getRecents()
    }


    private fun getProducts() = viewModelScope.launch {


        productRepository.getProducts(query.value!!).cachedIn(viewModelScope).collectLatest {

            products.postValue(it)

        }
    }

    fun setCategory(category: Category) {
        query.postValue(query.value!!.copy(category = category))
        getProducts()
    }

    fun setSearch(search: String) {
        query.postValue(query.value!!.copy(search = search))
        addRecent(search)
        getProducts()
    }

    fun setLoadState(states: CombinedLoadStates) {
        val loading = states.source.refresh is LoadState.Loading
        this.loading.postValue(loading)
    }

    private fun getRecents() = viewModelScope.launch {
        productRepository.getRecents().collectLatest {
            recents.postValue(it)

        }
    }

    fun clearRecents() = viewModelScope.launch {
        productRepository.clearRecents()
    }

    private fun addRecent(search: String) = viewModelScope.launch {
        productRepository.addRecent(search)
    }

    fun setQusery(query: ProductQuery){
        this.query.value = query
        getProducts()
    }


}