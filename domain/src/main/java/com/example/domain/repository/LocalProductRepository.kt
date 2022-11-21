package com.example.domain.repository

import com.example.domain.model.BaseProduct
import com.example.domain.model.ProductDao

//локальное хранилище
class LocalProductRepository(private val productDao: ProductDao) {
    suspend fun insertProduct(product: BaseProduct) = productDao.insertProduct(product)

    suspend fun updateProduct(product: BaseProduct) = productDao.updateProduct(product)

    suspend fun deleteProduct(product: BaseProduct) = productDao.deleteProduct(product)

    suspend fun getProducts(): List<BaseProduct> = productDao.getProducts()
}