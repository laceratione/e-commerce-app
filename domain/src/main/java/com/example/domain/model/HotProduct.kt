package com.example.domain.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

//объект категории Hot Sales
class HotProduct(
    id: Int,
    @SerializedName("is_new") val isNew: Boolean,
    title: String,
    @SerializedName("subtitle") val subtitle: String,
    picture: String,
    @SerializedName("is_buy") val isBuy: Boolean,
    bitmap: Bitmap?
) : BaseProduct(id, title, picture, bitmap)
