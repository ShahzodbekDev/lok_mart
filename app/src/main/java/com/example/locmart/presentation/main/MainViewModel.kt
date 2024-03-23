package com.example.locmart.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locmart.domain.model.Destination
import com.example.locmart.domain.repo.AuthRepository
import com.example.locmart.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    val events = SingleLiveEvent<Event>()
    init {
        getDestination()
    }

    fun getDestination() = viewModelScope.launch(Dispatchers.IO) {
        authRepository.designInFlow().collectLatest {
            events.postValue(Event.NavigateTo(it))
        }
    }

    sealed class Event {
        data class NavigateTo(val destination: Destination) :Event()
    }




}