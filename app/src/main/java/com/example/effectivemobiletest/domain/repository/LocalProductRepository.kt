package com.example.effectivemobiletest.domain.repository

import androidx.lifecycle.LiveData
import com.example.effectivemobiletest.domain.models.BaseProduct
import com.example.effectivemobiletest.domain.models.ProductDao

//локальное хранилище
class LocalProductRepository(private val productDao: ProductDao) {
    suspend fun insertProduct(product: BaseProduct) = productDao.insertProduct(product)

    suspend fun updateProduct(product: BaseProduct) = productDao.updateProduct(product)

    suspend fun deleteProduct(product: BaseProduct) = productDao.deleteProduct(product)

    suspend fun getProducts(): LiveData<List<BaseProduct>> = productDao.getProducts()
}