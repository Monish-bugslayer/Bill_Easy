package com.gravity.billeasy.data_layer

import com.gravity.billeasy.domain_layer.CategoryEntity
import com.gravity.billeasy.domain_layer.ProductEntity
import com.gravity.billeasy.domain_layer.UnitEntity
import kotlinx.coroutines.flow.Flow


class Repository(private val dao: AppDao) {

    suspend fun addProduct(productEntity: ProductEntity) = dao.addProduct(productEntity)

    suspend fun addCategory(categoryEntity: CategoryEntity) = dao.addCategory(categoryEntity)

    suspend fun addUnit(unitEntity: UnitEntity) = dao.addUnit(unitEntity)

    fun getUnitFromId(unitId: Long): Flow<String> { return dao.getUnitFromId(unitId) }

    fun getUnitId(unit: String): Flow<Long> { return dao.getUnitId(unit) }

    fun getCategoryId(category: String): Flow<Long> { return dao.getCategoryId(category) }

    fun getAllProducts(): Flow<List<ProductEntity>> { return dao.getAllProducts() }

    fun getCategoryFromId(categoryId: Long): Flow<String> {
        return dao.getCategoryFromId(categoryId)
    }
}
