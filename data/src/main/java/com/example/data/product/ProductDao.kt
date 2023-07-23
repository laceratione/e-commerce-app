package com.example.data.product

import androidx.room.*
import com.example.domain.model.BaseProduct

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: BaseProduct)

    @Update
    suspend fun updateProduct(product: BaseProduct)

    @Delete
    suspend fun deleteProduct(product: BaseProduct)

    @Query("select * from product_table")
    suspend fun getProducts(): List<BaseProduct>
}