package com.example.locmart.presentation.search

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
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
):ViewModel(){

    val loading = MutableLiveData(false)
    val products = MediatorLiveData<PagingData<Product>>()
    val query = MutableLiveData(ProductQuery())
    val searches = MutableLiveData<List<String>>()

    init{
        getRecentSearches()
    }


    private fun getProducts(){
        val products = productRepository.getProducts(query.value!!)
        this.products.addSource(products){
           this.products.value = it
            
        }
    }

   fun setCategory(category: Category){
       query.postValue(query.value!!.copy(category = category))
       getProducts()
   }

    fun setSearch(search:String){
        query.postValue(query.value!!.copy(search = search))
        getProducts()
    }

    fun setLoadState(states: CombinedLoadStates) {
        val loading = states.source.refresh is LoadState.Loading
        this.loading.postValue(loading)
    }

    private fun getRecentSearches() = viewModelScope.launch {
        productRepository.getRecentSearchs().collectLatest {
            searches.postValue(it)

        }
    }


}