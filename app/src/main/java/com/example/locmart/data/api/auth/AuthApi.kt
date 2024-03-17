package com.example.locmart.data.api.auth

import com.example.locmart.data.api.auth.dto.SignInRequest
import com.example.locmart.data.api.auth.dto.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/sign-in")
    suspend fun signIn(@Body request : SignInRequest) : SignInResponse
}