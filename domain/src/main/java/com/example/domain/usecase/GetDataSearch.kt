package com.example.domain.usecase

import com.example.domain.model.BaseProduct
import com.example.domain.repository.CloudRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//получение данных через строку поиска
class GetDataSearch(
    private val cloudRepository: CloudRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(query: String):
            List<BaseProduct> = withContext(defaultDispatcher) {
        val items = cloudRepository.getProducts(query)
        items
    }
}