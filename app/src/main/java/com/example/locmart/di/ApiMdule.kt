package com.example.locmart.di

import com.example.locmart.data.api.auth.AuthApi
import com.example.locmart.data.api.product.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiMdule {

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit) = retrofit.create(AuthApi::class.java)
    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit) = retrofit.create(ProductApi::class.java)
}