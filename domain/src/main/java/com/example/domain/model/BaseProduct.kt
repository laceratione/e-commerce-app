package com.example.domain.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

open class BaseProduct(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("picture") open val picture: String,
    var bitmap: Bitmap?
) {
    fun foo(){}
}