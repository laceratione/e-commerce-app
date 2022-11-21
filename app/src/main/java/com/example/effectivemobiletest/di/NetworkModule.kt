package com.example.effectivemobiletest.di

import com.example.data.api.RetrofitAPI
import com.example.data.api.ServerAPI
import com.example.data.repository.CloudRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideRetrofitAPI(): RetrofitAPI {
        return ServerAPI.getInstance().create(RetrofitAPI::class.java)
    }
}

@Module
class RepositoryModule{
    @Provides
    fun provideCloudRepositoryImpl(retrofitAPI: RetrofitAPI): CloudRepositoryImpl {
        return CloudRepositoryImpl(retrofitAPI)
    }
}