package com.example.domain.usecase

import com.example.domain.model.ProductDetails
import com.example.domain.repository.CloudRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class GetDataDetails(
    private val cloudRepository: CloudRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke():
            Call<ProductDetails> = withContext(defaultDispatcher) {
        val items = cloudRepository.getProducts(1)
        items
    }
}