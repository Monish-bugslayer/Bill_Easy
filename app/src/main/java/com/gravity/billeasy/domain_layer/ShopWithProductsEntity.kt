package com.gravity.billeasy.domain_layer

import androidx.room.Embedded
import androidx.room.Relation

data class ShopWithProductsEntity(
    @Embedded val shop: ShopEntity,
    @Relation(
        parentColumn = "shopId",
        entityColumn = "shopId"
    )
    val products: List<ProductEntity>
)
