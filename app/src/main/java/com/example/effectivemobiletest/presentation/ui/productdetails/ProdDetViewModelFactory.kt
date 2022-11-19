package com.example.effectivemobiletest.presentation.ui.productdetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.effectivemobiletest.presentation.ui.homepage.HomeViewModel

class ProdDetViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)){
            return ProductDetailsViewModel(application) as T
        }
        throw IllegalArgumentException("Class not found")
    }

}