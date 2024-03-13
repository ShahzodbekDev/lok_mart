package com.example.locmart.domain.repo

import com.example.locmart.domain.model.User

interface AuthRepository {

    suspend fun signIn( username : String, password : String) : User
}