package com.gravity.billeasy.data.model

data class Product(
    val productName: String,
    val productCategory: String,
    val unit: String,
    val availableStock: Int,
    val quantity: Int,
    val buyingPrice: Float,
    val retailPrice: Float,
    val wholeSalePrice: Float
)