package com.gravity.billeasy.data_layer.models

data class Shop(
    val shopId: Long = 0,
    val shopName: String,
    val shopAddress: String,
    val shopMobileNumber: String,
    val GSTNumber: String,
    val TINNumber: String
)