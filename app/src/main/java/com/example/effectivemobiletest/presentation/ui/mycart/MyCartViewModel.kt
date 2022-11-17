package com.example.effectivemobiletest.presentation.ui.mycart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.effectivemobiletest.domain.models.Cart
import com.example.effectivemobiletest.MyCart

class MyCartViewModel : ViewModel() {
    //данные корзины
    val dataMyCart: MutableLiveData<MyCart> = MutableLiveData()

    //обновление данных корзины
    init {
        dataMyCart.value = Cart.data.value
    }

}