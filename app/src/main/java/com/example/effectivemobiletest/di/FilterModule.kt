package com.example.effectivemobiletest.di

import com.example.effectivemobiletest.presentation.ui.homepage.FilterSettings
import dagger.Module
import dagger.Provides

@Module
class FilterModule {
    @Provides
    fun provideFilterSettings(): FilterSettings{
        return FilterSettings()
    }
}