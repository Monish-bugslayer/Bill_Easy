package com.gravity.billeasy.data_layer.repository

import com.gravity.billeasy.data_layer.dao.BillDao
import com.gravity.billeasy.domain_layer.entities.BillEntity
import kotlinx.coroutines.flow.Flow

class BillRepository(private val billDao: BillDao) {

    suspend fun addBill(billEntity: BillEntity) = billDao.addBill(billEntity)

    suspend fun getAllBills(): Flow<List<BillEntity>> = billDao.getAllBills()
}