package com.example.locmart.presentation.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(

    private val productRepository: ProductRepository

): ViewModel(){

    val loading = MutableLiveData(false)

    val erorr = MutableLiveData(false)

    val categories = MutableLiveData<List<Category>>()

    init {
        getCategories()
    }

    fun getCategories() = viewModelScope.launch {
        loading.postValue(true)
        erorr.postValue(false)
        try {
            val response = productRepository.getCategories()
            categories.postValue(response)
        } catch (e:Exception) {
            erorr.postValue(true)
        }
        finally {
            loading.postValue(false)
        }
    }




}