package com.gravity.billeasy.domain_layer.use_cases

import com.gravity.billeasy.data_layer.models.Sale
import com.gravity.billeasy.data_layer.repository.SalesRepository
import com.gravity.billeasy.domain_layer.entities.SaleEntity
import kotlinx.coroutines.flow.Flow

class SalesUseCase(private val salesRepo: SalesRepository) {

    suspend fun addSale(sale: Sale) = salesRepo.addSale(
        SaleEntity(
            billId = sale.billId,
            customerName = sale.customerName,
            billingDate = sale.billingDate,
            productName = sale.productName,
            productId = sale.productId,
            orderedQuantity = sale.orderedQuantity,
            productCategory = sale.productCategory,
            finalizedPerUnitPrice = sale.finalizedPerUnitPrice,
            totalPrice = sale.totalPrice,
            paymentMethod = sale.paymentMethod,
            billType = sale.billType,
            shopId = sale.shopId
        )
    )

    suspend fun getAllSales(): Flow<List<SaleEntity>> = salesRepo.getAllSales()
}