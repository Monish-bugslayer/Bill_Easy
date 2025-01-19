package com.gravity.billeasy.domain_layer.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [ForeignKey(
        parentColumns = ["shopId"],
        entity = ShopEntity::class,
        childColumns = ["shopId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0,
    val shopId: Long,
    val productName: String,
    val availableStock: Long,
    val buyingPrice: Double,
    val retailPrice: Double,
    val wholeSalePrice: Double,
    val quantity: Long,
    val category: String,
    val unit: String
)