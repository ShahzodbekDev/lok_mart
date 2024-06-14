package com.example.locmart.presentation.filter

import android.media.metrics.Event
import android.service.autofill.FillEventHistory
import android.util.EventLog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locmart.data.api.product.dto.Category
import com.example.locmart.domain.repo.ProductRepository
import com.example.locmart.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    val categories = MutableLiveData<List<Category>>()
    val events = SingleLiveEvent<Event>()

    init {
        getCategories()
    }

    fun getCategories() = viewModelScope.launch {
        try {
            val result = productRepository.getCategories()
            categories.postValue(result)
        } catch (e: Exception) {
            events.postValue(Event.CategoriesError)
        }

    }

    sealed class Event {
        object CategoriesError : Event()
    }
}