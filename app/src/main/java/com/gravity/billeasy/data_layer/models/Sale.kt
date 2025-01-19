package com.gravity.billeasy.data_layer.models

import androidx.compose.runtime.Stable

@Stable
data class Sale (
    val billId: Long,
    val customerName: String,
    val billingDate: String,
    val productName: String,
    val productId: Long,
    val productCategory: String,
    val orderedQuantity: Int,
    val finalizedPerUnitPrice: Double,
    val totalPrice: Double,
    val paymentType: String,
    val shopId: Long
)
