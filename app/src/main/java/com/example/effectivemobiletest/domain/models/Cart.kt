package com.example.effectivemobiletest.domain.models

import androidx.lifecycle.MutableLiveData
import com.example.effectivemobiletest.ItemCart
import com.example.effectivemobiletest.MyCart

//операции с корзиной
object Cart {
    var data: MutableLiveData<MyCart> = MutableLiveData()

    //добавить товар
    fun add(item: ItemCart?) {}

    //удалить товар
    fun delete(item: ItemCart?) {}

    //посчитать общую стоимость
    fun totalSumm(items: List<ItemCart>?) {}

    //назначить стоимость доставки
    fun setDelivery() {}
}