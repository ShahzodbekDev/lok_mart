package com.example.locmart.domain.repo

import com.example.locmart.domain.model.Destination
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signIn(username: String, password: String)
    suspend fun signUp(username: String, email: String, password: String)

    fun designInFlow(): Flow<Destination>
    suspend fun onboarded()
}