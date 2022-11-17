package com.example.effectivemobiletest

import android.graphics.Bitmap
import com.example.effectivemobiletest.domain.models.BaseProduct
import com.google.gson.annotations.SerializedName

//объект Корзина
data class MyCart(
    @SerializedName("delivery") val delivery: String,
    @SerializedName("id") val id: String,
    @SerializedName("total") val total: Double,
    @SerializedName("basket") val basket: List<ItemCart>,
)

//элемент корзины
class ItemCart(
    id: Int,
    @SerializedName("images") override val picture: String,
    @SerializedName("price") val price: Double,
    title: String,
    bitmap: Bitmap?
): BaseProduct(id, title, picture, bitmap)
