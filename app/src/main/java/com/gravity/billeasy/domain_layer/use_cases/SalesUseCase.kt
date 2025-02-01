package com.gravity.billeasy.domain_layer.use_cases

import com.gravity.billeasy.data_layer.models.Sale
import com.gravity.billeasy.data_layer.repository.BillRepository
import com.gravity.billeasy.domain_layer.entities.BillEntity
import kotlinx.coroutines.flow.Flow

class SalesUseCase(private val salesRepo: BillRepository) {

    suspend fun addSale(sale: Sale) = salesRepo.addSale(
        BillEntity(
            id = sale.billId,
            customerName = sale.customerName,
            billingDate = sale.billingDate,
            paymentMethod = sale.paymentMethod,
            billType = sale.billType,
            shopId = sale.shopId
        )
    )

    suspend fun getAllSales(): Flow<List<BillEntity>> = salesRepo.getAllSales()
}