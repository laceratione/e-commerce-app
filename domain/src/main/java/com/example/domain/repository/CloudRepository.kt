package com.example.domain.repository

import com.example.domain.model.BaseProduct
import com.example.domain.model.MyCart
import com.example.domain.model.Product
import com.example.domain.model.ProductDetails
import retrofit2.Call

//интерфейс облачного хранилища
interface CloudRepository {
    fun getProducts(): List<BaseProduct>

    fun getProducts(query: String): List<BaseProduct>

    fun getProducts(brand: String, price: String, size: String):
            List<BaseProduct>

    fun getProductsHomeAct(): Call<Product>

    fun getProductsCart(): Call<MyCart>

    fun getProducts(id: Int): Call<ProductDetails>
}