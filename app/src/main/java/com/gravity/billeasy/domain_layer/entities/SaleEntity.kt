package com.gravity.billeasy.domain_layer.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "sales",
    foreignKeys = [ForeignKey(
        parentColumns = ["shopId"],
        childColumns = ["shopId"],
        entity = ShopEntity::class,
        onDelete = ForeignKey.CASCADE
    )])
data class SaleEntity(
    @PrimaryKey(autoGenerate = true)
    val billId: Long,
    val customerName: String,
    val billingDate: String,
    val orderedProducts: String,
    val paymentMethod: String,
    val billType: String,
    val shopId: Long
)
