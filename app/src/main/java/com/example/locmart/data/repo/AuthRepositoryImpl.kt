package com.example.locmart.data.repo

import com.example.locmart.data.api.auth.AuthApi
import com.example.locmart.data.api.auth.dto.SignInRequest
import com.example.locmart.data.api.auth.dto.SignUpRequest
import com.example.locmart.data.store.TokenStore
import com.example.locmart.data.store.UserStore
import com.example.locmart.domain.model.User
import com.example.locmart.domain.repo.AuthRepository
import javax.inject.Inject
import com.example.locmart.data.api.auth.dto.SignInResponse as AuthRespons


class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore,
    private val userStore: UserStore

) : AuthRepository {
    override suspend fun signIn(username: String, password: String){

        val request = SignInRequest(username, password)
        val response = authApi.signIn(request)
      saveUserInfo(response)

    }

    override suspend fun signUp(username: String, email: String, password: String) {

        val request = SignUpRequest(username, email, password)
        val response = authApi.signUp(request)
       saveUserInfo(response)
    }

    private suspend fun saveUserInfo(response: AuthRespons) {
        tokenStore.set(response.token)
        userStore.set(response.user)
    }
}