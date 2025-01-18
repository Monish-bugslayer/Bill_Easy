package com.gravity.billeasy.data_layer.models

data class Shop(
    val shopId: Long = 0,
    val shopName: String,
    val shopAddress: String,
    val shopMobileNumber: String,
    val shopEmailAddress: String,
    val gstNumber: String,
    val tinNumber: String,
    val ownerName: String,
    val ownerAddress: String,
    val ownerMobileNumber: String
)