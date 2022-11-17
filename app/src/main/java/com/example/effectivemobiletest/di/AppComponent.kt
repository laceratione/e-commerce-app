package com.example.effectivemobiletest.di

import com.example.effectivemobiletest.presentation.ui.homepage.HomeViewModel
import dagger.Component

@Component(modules = [FilterModule::class])
interface AppComponent {
    fun injectHomeViewModel(homeViewModel: HomeViewModel)
}