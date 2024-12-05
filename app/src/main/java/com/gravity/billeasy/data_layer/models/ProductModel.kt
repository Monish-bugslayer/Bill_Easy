package com.gravity.billeasy.data_layer.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val productId: Long = 0,
    val productName: String,
    val productCategory: String,
    val unit: String,
    val availableStock: Long,
    val quantity: Long,
    val buyingPrice: Double,
    val retailPrice: Double,
    val wholeSalePrice: Double
)