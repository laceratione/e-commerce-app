package com.example.effectivemobiletest.di

import com.example.effectivemobiletest.presentation.ui.homepage.HomeViewModel
import com.example.effectivemobiletest.presentation.ui.login.LoginViewModel
import com.example.effectivemobiletest.presentation.ui.productdetails.ProductDetailsViewModel
import com.example.effectivemobiletest.presentation.ui.signin.SignInViewModel
import dagger.Component

@Component(modules = [FilterModule::class, NetworkModule::class,
    RepositoryModule::class, UseCaseModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(homeViewModel: HomeViewModel)
    fun inject(productDetailsViewModel: ProductDetailsViewModel)
    fun inject(signInViewModel: SignInViewModel)
    fun inject(loginViewModel: LoginViewModel)
}