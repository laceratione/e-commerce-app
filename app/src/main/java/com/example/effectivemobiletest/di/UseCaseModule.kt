package com.example.effectivemobiletest.di

import com.example.domain.repository.CloudRepository
import com.example.domain.usecase.GetDataCart
import com.example.domain.usecase.GetDataDetails
import com.example.domain.usecase.GetDataHomeAct
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetDataHomeAct(cloudRepository: CloudRepository): GetDataHomeAct{
        return GetDataHomeAct(cloudRepository)
    }

    @Provides
    fun provideGetDataCart(cloudRepository: CloudRepository): GetDataCart{
        return GetDataCart(cloudRepository)
    }

    @Provides
    fun provideGetDataDetails(cloudRepository: CloudRepository): GetDataDetails{
        return GetDataDetails(cloudRepository)
    }
}