package com.example.locmart.data.api.auth

import com.example.locmart.data.api.auth.dto.SignInRequest
import com.example.locmart.data.api.auth.dto.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.locmart.data.api.auth.dto.SignInResponse as AuthRespons

interface AuthApi {

    @POST("auth/sign-in")
    suspend fun signIn(@Body request : SignInRequest) : AuthRespons

    @POST("auth/sign-up")
    suspend fun signUp(@Body request : SignUpRequest) : AuthRespons
}