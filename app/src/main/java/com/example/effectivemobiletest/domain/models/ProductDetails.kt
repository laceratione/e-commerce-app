package com.example.effectivemobiletest.domain.models

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

//объект, содержащий подробную информацию о товаре
data class ProductDetails(
    @SerializedName("CPU") val cpu: String,
    @SerializedName("camera") val camera: String,
    @SerializedName("capacity") val capacity: List<String>,
    @SerializedName("color") val color: List<String>,
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("isFavorites") val isFavorites: Boolean,
    @SerializedName("price") val price: Int,
    @SerializedName("rating") val rating: Float,
    @SerializedName("sd") val sd: String,
    @SerializedName("ssd") val ssd: String,
    @SerializedName("title") val title: String,
    var bitmaps: List<Bitmap>?
)
