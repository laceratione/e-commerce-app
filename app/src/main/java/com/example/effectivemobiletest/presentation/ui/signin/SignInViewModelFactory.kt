package com.example.effectivemobiletest.presentation.ui.signin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignInViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignInViewModel::class.java)){
            return SignInViewModel(application) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}