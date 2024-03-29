package com.example.locmart.data.api.auth.dto

import com.google.gson.annotations.SerializedName

data class SignInRequest(

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)
