package com.example.effectivemobiletest.domain.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: BaseProduct)

    @Update
    suspend fun updateProduct(product: BaseProduct)

    @Delete
    suspend fun deleteProduct(product: BaseProduct)

    @Query("select * from product_table")
    suspend fun getProducts(): LiveData<List<BaseProduct>>
}