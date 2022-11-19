package com.example.effectivemobiletest.domain.interactor

import com.example.effectivemobiletest.domain.models.BaseProduct
import com.example.effectivemobiletest.domain.repository.CloudProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//получение данных через строку поиска
class GetDataSearch(
    private val productRepository: CloudProductRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(query: String):
            List<BaseProduct> = withContext(defaultDispatcher) {
        val items = productRepository.getProducts(query)
        items
    }
}