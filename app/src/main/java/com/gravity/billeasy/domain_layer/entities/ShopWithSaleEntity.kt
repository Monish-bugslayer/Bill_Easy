package com.gravity.billeasy.domain_layer.entities

import androidx.room.Embedded
import androidx.room.Relation


data class ShopWithSaleEntity(
    @Embedded val shop: ShopEntity,
    @Relation(
        parentColumn = "shopId",
        entityColumn = "shopId"
    )
    val products: List<BillEntity>
)
