package com.example.effectivemobiletest.domain.interactor

import com.example.effectivemobiletest.domain.models.ProductDetails
import com.example.effectivemobiletest.domain.repository.CloudProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class GetDataDetails(
    private val productRepository: CloudProductRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke():
            Call<ProductDetails> = withContext(defaultDispatcher) {
        val items = productRepository.getProducts(1)
        items
    }
}