package com.example.locmart.data.repo

import com.example.locmart.data.api.auth.AuthApi
import com.example.locmart.data.api.auth.dto.SignInRequest
import com.example.locmart.data.api.auth.dto.SignUpRequest
import com.example.locmart.data.store.OnboardedStore
import com.example.locmart.data.store.TokenStore
import com.example.locmart.data.store.UserStore
import com.example.locmart.domain.model.Destination
import com.example.locmart.domain.repo.AuthRepository
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.locmart.data.api.auth.dto.SignInResponse as AuthRespons


class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore,
    private val userStore: UserStore,
    private val onboardedStore: OnboardedStore

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

    override fun designInFlow() = channelFlow {

       suspend fun sendDestination() {
            when{
                tokenStore.get(true) !=null -> send(Destination.Home)
                onboardedStore.get(true) == true -> send(Destination.Auth)
                else -> send(Destination.OnBoarding)
            }
        }

        launch {
            tokenStore.getFlow().collectLatest {
                sendDestination()
            }
        }

        launch {
            onboardedStore.getFlow().collectLatest {
                sendDestination()
            }
        }
    }

    override suspend fun onboarded() = onboardedStore.set(true)


    private suspend fun saveUserInfo(response: AuthRespons) {
        tokenStore.set(response.token)
        userStore.set(response.user)
    }
}