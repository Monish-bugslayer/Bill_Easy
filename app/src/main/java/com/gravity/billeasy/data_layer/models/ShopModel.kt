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
) {
    constructor() : this(
        shopId = 0,
        shopName = "Default Shop Name",
        shopAddress = "Default Address",
        shopMobileNumber = "0000000000",
        shopEmailAddress = "default@example.com",
        gstNumber = "DEFAULTGST123",
        tinNumber = "DEFAULTTIN456",
        ownerName = "Default Owner",
        ownerAddress = "Default Owner Address",
        ownerMobileNumber = "9999999999"
    )
}