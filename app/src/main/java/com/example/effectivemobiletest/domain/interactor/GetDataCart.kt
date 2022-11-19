package com.example.effectivemobiletest.domain.interactor

import com.example.effectivemobiletest.MyCart
import com.example.effectivemobiletest.domain.repository.CloudProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

//получение товаров из корзины пользователя
class GetDataCart(
    private val productRepository: CloudProductRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke():
            Call<MyCart> = withContext(defaultDispatcher) {
        val items = productRepository.getProductsCart()
        items
    }
}