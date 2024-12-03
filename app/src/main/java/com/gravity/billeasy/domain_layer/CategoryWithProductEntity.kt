package com.gravity.billeasy.domain_layer

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithProduct(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "productCategoryId"
    )
    val products: List<ProductEntity>
)