package com.example.locmart.presentation.sign_in


import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locmart.domain.repo.AuthRepository
import com.example.locmart.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor (private val authRepository: AuthRepository) : ViewModel() {

    val loading = MutableLiveData(/* value = */ false)
    val events = SingleLiveEvent<Event>()


    fun signIn(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {

        loading.postValue(true)
        try {
            authRepository.signIn(username, password)
            events.postValue(Event.NavigateToHome)
        } catch (e: Exception) {
            when{
                e is HttpException && e.code() == 404 -> events.postValue(Event.InvalidCredentials)
                e is IOException -> events.postValue(Event.ConnectionErorr)
                else -> events.postValue(Event.Erorr)
            }

        }finally {
            loading.postValue(false)
        }


    }

    sealed class Event {
        object InvalidCredentials : Event()
        object ConnectionErorr : Event()
        object Erorr : Event()
        object NavigateToHome : Event()

    }
}