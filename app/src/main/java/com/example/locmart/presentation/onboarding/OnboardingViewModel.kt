package com.example.locmart.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locmart.domain.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){

    fun onboarded() = viewModelScope.launch {
        authRepository.onboarded()
    }


}