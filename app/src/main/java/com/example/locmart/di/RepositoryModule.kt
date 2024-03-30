package com.example.locmart.di

import com.example.locmart.data.repo.AuthRepositoryImpl
import com.example.locmart.data.repo.ProductRepositoryImpl
import com.example.locmart.domain.repo.AuthRepository
import com.example.locmart.domain.repo.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
    @Binds
    abstract fun bindProductRepository(
        authRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}