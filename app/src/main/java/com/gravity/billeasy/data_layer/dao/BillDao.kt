package com.gravity.billeasy.data_layer.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gravity.billeasy.domain_layer.entities.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Insert
    suspend fun addBill(billEntity: BillEntity)

    @Query("SELECT * from sales")
    fun getAllBills(): Flow<List<BillEntity>>
}