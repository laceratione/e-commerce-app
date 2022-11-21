package com.example.domain.usecase

import com.example.domain.model.MyCart
import com.example.domain.repository.CloudRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

//получение товаров из корзины пользователя
class GetDataCart(
    private val cloudRepository: CloudRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke():
            Call<MyCart> = withContext(defaultDispatcher) {
        val items = cloudRepository.getProductsCart()
        items
    }
}