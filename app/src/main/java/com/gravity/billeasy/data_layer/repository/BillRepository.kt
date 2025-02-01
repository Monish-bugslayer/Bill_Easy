package com.gravity.billeasy.data_layer.repository

import com.gravity.billeasy.data_layer.dao.BillDao
import com.gravity.billeasy.domain_layer.entities.BillEntity
import kotlinx.coroutines.flow.Flow

class BillRepository(private val billDao: BillDao) {

    suspend fun addSale(billEntity: BillEntity) = billDao.addSale(billEntity)

    suspend fun getAllSales(): Flow<List<BillEntity>> = billDao.getAllSales()
}