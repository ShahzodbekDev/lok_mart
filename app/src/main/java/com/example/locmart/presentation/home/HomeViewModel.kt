package com.example.locmart.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locmart.data.api.product.dto.HomeResponse
import com.example.locmart.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository

): ViewModel() {

    val loading = MutableLiveData(false)
    val erorr = MutableLiveData(false)
    val home = MutableLiveData<HomeResponse?>(null)
    init {
        getHome()
    }



    fun getHome()= viewModelScope.launch {
        loading.postValue(true)
        erorr.postValue(false)
        try {
            val response = productRepository.getHome()
            home.postValue(response)


        } catch (e:Exception) {
            erorr.postValue(true)
        }
       finally {
           loading.postValue(false)
       }
    }
    sealed class Event {

    }
}