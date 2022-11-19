package com.example.effectivemobiletest.di

import com.example.effectivemobiletest.presentation.ui.homepage.HomeViewModel
import com.example.effectivemobiletest.presentation.ui.productdetails.ProductDetailsViewModel
import dagger.Component

@Component(modules = [FilterModule::class, NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    fun injectHomeViewModel(homeViewModel: HomeViewModel)

    fun injectProductDetailsViewModel(productDetailsViewModel: ProductDetailsViewModel)
}