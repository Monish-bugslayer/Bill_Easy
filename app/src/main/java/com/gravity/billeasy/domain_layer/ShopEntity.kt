package com.gravity.billeasy.domain_layer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShopEntity(
    @PrimaryKey(autoGenerate = true)
    val shopId: Long,
    val shopName: String,
    val shopAddress: String,
    val shopMobileNumber: String,
    val GSTNumber: String,
    val TINNumber: String
)
