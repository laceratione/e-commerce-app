package com.example.effectivemobiletest.presentation.ui.mycart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.Cart
import com.example.domain.model.MyCart

class MyCartViewModel : ViewModel() {
    //данные корзины
    private val _dataMyCart: MutableLiveData<MyCart> = MutableLiveData()
    val dataMyCart: LiveData<MyCart> = _dataMyCart

    //обновление данных корзины
    init {
        _dataMyCart.value = Cart.getCart().value
    }

}