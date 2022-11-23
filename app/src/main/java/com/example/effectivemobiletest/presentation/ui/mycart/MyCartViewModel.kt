package com.example.effectivemobiletest.presentation.ui.mycart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.Cart
import com.example.domain.model.MyCart

class MyCartViewModel : ViewModel() {
    //данные корзины
    private val dataMyCart: MutableLiveData<MyCart> = MutableLiveData()
    val dataCartLive: LiveData<MyCart> = dataMyCart

    //обновление данных корзины
    init {
        dataMyCart.value = Cart.getCart().value
    }

}