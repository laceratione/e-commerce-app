package com.example.domain.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

//товар категории Best Seller
class BestProduct(
    id: Int,
    @SerializedName("is_favorites") val isFavorites: Boolean,
    title: String,
    @SerializedName("price_without_discount") val priceWithoutDiscount: Int,
    @SerializedName("discount_price") val discountPrice: Int,
    picture: String,
    bitmap: Bitmap?
) : BaseProduct(id, title, picture, bitmap)
