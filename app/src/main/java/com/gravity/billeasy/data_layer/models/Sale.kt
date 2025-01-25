package com.gravity.billeasy.data_layer.models

import androidx.compose.runtime.Stable

enum class PaymentMethod { CREDIT_CARD, DEBIT_CARD, CASH, UPI, CHEQUE }

enum class BillType { CREDIT, NON_CREDIT }

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
    val paymentMethod: String,
    val billType: String,
    val shopId: Long
) {
    constructor(): this(
        billId = 0L,
        customerName = "",
        billingDate = "",
        productName = "",
        productId = 0L,
        productCategory = "",
        orderedQuantity = 0,
        finalizedPerUnitPrice = 0.0,
        totalPrice = 0.0,
        paymentMethod = "",
        billType = "",
        shopId = 0L
    )
}

data class SaleValidationState(
    val customerNameError: String? = null,
    val billingDateError: String? = null,
    val paymentMethodError: String? = null,
    val billTypeError: String? = null
)
