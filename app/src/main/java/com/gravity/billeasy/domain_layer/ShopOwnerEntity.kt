package com.gravity.billeasy.domain_layer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShopOwnerEntity(
    @PrimaryKey(autoGenerate = true)
    val ownerId: Long,
    val ownerName: String,
    val ownerAddress: String,
    val ownerMobileNumber: String,
    val shopId: Long = 0
)
