package com.gravity.billeasy.data_layer.models

import androidx.compose.runtime.Stable

@Stable
data class Bill (
    val billId: Long,
    val customerName: String,
    val billingDate: String,
    val paymentMethod: String,
    val billType: String,
    val shopId: Long
) {
    constructor(): this(
        billId = 0L,
        customerName = "",
        billingDate = "",
        paymentMethod = "",
        billType = "",
        shopId = 0L
    )
}

data class BillValidationState(
    val customerNameError: String? = null,
    val billingDateError: String? = null,
    val paymentMethodError: String? = null,
    val billTypeError: String? = null
)
