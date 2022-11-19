package com.example.effectivemobiletest.domain.repository

import com.example.effectivemobiletest.MyCart
import com.example.effectivemobiletest.data.RetrofitAPI
import com.example.effectivemobiletest.domain.models.BaseProduct
import com.example.effectivemobiletest.domain.models.Product
import com.example.effectivemobiletest.domain.models.ProductDetails
import retrofit2.Call

//облачное хранилище
class CloudProductRepository(private val retrofitAPI: RetrofitAPI) {
    //все товары
    fun getProducts(): List<BaseProduct> = retrofitAPI.getProducts()

    //по ключевому слову
    fun getProducts(query: String): List<BaseProduct> = retrofitAPI.getProducts(query)

    //по параметрам фильтра
    fun getProducts(brand: String, price: String, size: String):
            List<BaseProduct> = retrofitAPI.getProducts(brand, price, size)

    //главный экран
    fun getProductsHomeAct(): Call<Product> = retrofitAPI.getObjectsHotSales()

    //товары из корзины
    fun getProductsCart(): Call<MyCart> = retrofitAPI.getMyCart()

    //детальная информация товара
    fun getProducts(id: Int): Call<ProductDetails> = retrofitAPI.getProductDetails()
}