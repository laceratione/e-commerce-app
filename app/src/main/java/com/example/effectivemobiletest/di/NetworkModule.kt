package com.example.effectivemobiletest.di

import com.example.effectivemobiletest.data.RetrofitAPI
import com.example.effectivemobiletest.data.ServerAPI
import com.example.effectivemobiletest.domain.repository.CloudProductRepository
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideRetrofitAPI(): RetrofitAPI{
        return ServerAPI.getInstance().create(RetrofitAPI::class.java)
    }
}

@Module
class RepositoryModule{
    @Provides
    fun provideProductRepository(retrofitAPI: RetrofitAPI): CloudProductRepository{
        return CloudProductRepository(retrofitAPI)
    }
}