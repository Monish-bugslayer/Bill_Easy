package com.gravity.billeasy.data_layer.repository

import com.gravity.billeasy.data_layer.dao.SalesDao
import com.gravity.billeasy.domain_layer.entities.SaleEntity
import kotlinx.coroutines.flow.Flow

class SalesRepository(private val salesDao: SalesDao) {

    suspend fun addSale(saleEntity: SaleEntity) = salesDao.addSale(saleEntity)

    suspend fun getAllSales(): Flow<List<SaleEntity>> = salesDao.getAllSales()
}