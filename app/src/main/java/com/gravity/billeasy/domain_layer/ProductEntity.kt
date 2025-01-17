package com.gravity.billeasy.domain_layer

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0,
    val productName: String,
    val availableStock: Long,
    val buyingPrice: Double,
    val retailPrice: Double,
    val wholeSalePrice: Double,
    val quantity: Long,
    val category: String,
    val unit: String
)