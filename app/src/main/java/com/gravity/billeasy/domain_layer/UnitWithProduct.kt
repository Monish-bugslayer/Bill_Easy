package com.gravity.billeasy.domain_layer

import androidx.room.Embedded
import androidx.room.Relation

data class UnitWithProduct(
    @Embedded val unit: UnitEntity,
    @Relation(
        parentColumn = "unitId",
        entityColumn = "productUnitId"
    )
    val products: List<ProductEntity>
)