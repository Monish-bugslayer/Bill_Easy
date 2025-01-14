package com.gravity.billeasy.domain_layer

import androidx.room.Embedded
import androidx.room.Relation

data class OwnerWithShopEntity (
    @Embedded val shop: ShopEntity,
    @Relation (
        parentColumn = "shopId",
        entityColumn = "shopId"
    )
    val shopList: List<ShopEntity>
)
