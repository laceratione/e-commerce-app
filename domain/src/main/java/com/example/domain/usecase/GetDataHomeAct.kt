package com.example.domain.usecase

import com.example.domain.model.Product
import com.example.domain.repository.CloudRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

//получение товаров категорий главного экрана
class GetDataHomeAct(
    private val cloudRepository: CloudRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke():
            Call<Product> = withContext(defaultDispatcher) {
        val items = cloudRepository.getProductsHomeAct()
        items
    }
}