package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("home_store") val hotProducts: List<HotProduct>,
    @SerializedName("best_seller") val bestProducts: List<BestProduct>
)

