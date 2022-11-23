package com.example.domain.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//операции с корзиной
object Cart {
    private var data: MutableLiveData<MyCart> = MutableLiveData()
    val dataLive: LiveData<MyCart> = data

    //обновить корзину
    fun updateCart(myCart: MyCart){
        data.postValue(myCart)
    }

    fun getCart(): MutableLiveData<MyCart> = data

    //добавить товар
    fun add(item: ItemCart?) {}

    //удалить товар
    fun delete(item: ItemCart?) {}

    //посчитать общую стоимость
    fun totalSumm(items: List<ItemCart>?) {}

    //назначить стоимость доставки
    fun setDelivery() {}
}