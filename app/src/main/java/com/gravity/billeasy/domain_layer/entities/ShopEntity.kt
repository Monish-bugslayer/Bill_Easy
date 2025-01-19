package com.gravity.billeasy.domain_layer.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop")
data class ShopEntity(
    @PrimaryKey(autoGenerate = true)
    val shopId: Long = 0,
    val shopName: String,
    val shopAddress: String,
    val shopEmailAddress: String,
    val shopMobileNumber: String,
    val gstNumber: String,
    val tinNumber: String,
    val ownerName: String,
    val ownerAddress: String,
    val ownerMobileNumber: String
)
