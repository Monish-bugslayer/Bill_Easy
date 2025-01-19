package com.gravity.billeasy.data_layer.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gravity.billeasy.domain_layer.entities.SaleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SalesDao {
    @Insert
    suspend fun addSale(saleEntity: SaleEntity)

    @Query("SELECT * from sales")
    fun getAllSales(): Flow<List<SaleEntity>>
}