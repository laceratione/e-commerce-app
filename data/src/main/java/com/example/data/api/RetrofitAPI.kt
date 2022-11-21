package com.example.data.api

import com.example.domain.model.MyCart
import com.example.domain.model.BaseProduct
import com.example.domain.model.Product
import com.example.domain.model.ProductDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//API сервера
interface RetrofitAPI {
    //возвращает товары категорий Hot Sales, Best Seller
    @GET("/v3/654bd15e-b121-49ba-a588-960956b15175")
    fun getObjectsHotSales(): Call<Product>

    //возвращает детальную информацию о товаре
    @GET("/v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    fun getProductDetails(): Call<ProductDetails>

    //возвращает корзину
    @GET("/v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    fun getMyCart(): Call<MyCart>

    @GET("/v3/products")
    fun getProducts(): List<BaseProduct>

    @GET("/v3/all-products?")
    fun getProducts(@Query("query") text: String): List<BaseProduct>

    @GET("/v3/all-products?")
    fun getProducts(
        @Query("brand") brand: String,
        @Query("price") price: String,
        @Query("size") size: String): List<BaseProduct>

}