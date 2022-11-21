package com.example.data.repository

import com.example.data.api.RetrofitAPI
import com.example.domain.model.BaseProduct
import com.example.domain.model.MyCart
import com.example.domain.model.Product
import com.example.domain.model.ProductDetails
import com.example.domain.repository.CloudRepository
import retrofit2.Call

class CloudRepositoryImpl(private val retrofitAPI: RetrofitAPI): CloudRepository {
    override fun getProducts(): List<BaseProduct> {
        return retrofitAPI.getProducts()
    }

    override fun getProducts(query: String): List<BaseProduct> {
        return retrofitAPI.getProducts(query)
    }

    override fun getProducts(brand: String, price: String, size: String): List<BaseProduct> {
        return retrofitAPI.getProducts(brand, price, size)
    }

    override fun getProducts(id: Int): Call<ProductDetails> {
        return retrofitAPI.getProductDetails()
    }

    override fun getProductsHomeAct(): Call<Product> {
        return retrofitAPI.getObjectsHotSales()
    }

    override fun getProductsCart(): Call<MyCart> {
        return retrofitAPI.getMyCart()
    }
}