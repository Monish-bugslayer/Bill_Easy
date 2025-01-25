package com.gravity.billeasy.data_layer.models

data class Shop(
    val id: Long = 0,
    val name: String,
    val address: String,
    val mobileNumber: String,
    val emailAddress: String,
    val gstNumber: String,
    val tinNumber: String,
    val ownerName: String,
    val ownerAddress: String,
    val ownerMobileNumber: String
) {
    constructor() : this (
        id = 0,
        name = "",
        address = "",
        mobileNumber = "",
        emailAddress = "",
        gstNumber = "",
        tinNumber = "",
        ownerName = "",
        ownerAddress = "",
        ownerMobileNumber = ""
    )
}

data class ShopValidationState (
    val nameError: String? = null,
    val addressError: String? = null,
    val emailError: String? = null,
    val mobileError: String? = null,
    val gstError: String? = null,
    val tinError: String? = null,
    val ownerNameError: String? = null,
    val ownerAddressError: String? = null,
    val ownerMobileError: String? = null
)