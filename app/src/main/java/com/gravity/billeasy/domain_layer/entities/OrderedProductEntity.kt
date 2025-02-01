package com.gravity.billeasy.domain_layer.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Ordered products",
    foreignKeys = [ForeignKey(
        entity = BillEntity::class,
        parentColumns = ["id"],
        childColumns = ["billId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class OrderedProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val productName: String,
    val productCategory: String,
    val orderedQuantity: Int,
    val pricePerUnit: Double,
    val orderTotal: Double,
    val billId: Long
)