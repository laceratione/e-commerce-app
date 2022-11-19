package com.example.effectivemobiletest.domain.interactor

import com.example.effectivemobiletest.domain.models.Product
import com.example.effectivemobiletest.domain.repository.CloudProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

//получение товаров категорий главного экрана
class GetDataHomeAct(
    private val productRepository: CloudProductRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke():
            Call<Product> = withContext(defaultDispatcher) {
        val items = productRepository.getProductsHomeAct()
        items
    }
}