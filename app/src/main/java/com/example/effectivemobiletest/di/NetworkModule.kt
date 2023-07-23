package com.example.effectivemobiletest.di

import com.example.data.api.RetrofitAPI
import com.example.data.api.ServerAPI
import com.example.data.repository.CloudRepositoryImpl
import com.example.data.repository.LocalUserRepositoryImpl
import com.example.data.AppDataBase
import com.example.domain.repository.CloudRepository
import com.example.domain.repository.LocalUserRepository
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
    fun provideCloudRepositoryImpl(retrofitAPI: RetrofitAPI): CloudRepository {
        return CloudRepositoryImpl(retrofitAPI)
    }

    @Provides
    fun provideLocalUserRepositoryImpl(database: AppDataBase): LocalUserRepository{
        return LocalUserRepositoryImpl(database)
    }
}