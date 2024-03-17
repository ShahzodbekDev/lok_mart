package com.example.locmart.domain.repo

interface AuthRepository {

    suspend fun signIn( username : String, password : String)
}