package com.gravity.billeasy.data_layer

import com.gravity.billeasy.domain_layer.ProductEntity
import kotlinx.coroutines.flow.Flow


class ProductRepository(private val dao: ProductDao) {

    suspend fun addProduct(productEntity: ProductEntity) = dao.addProduct(productEntity)

//    suspend fun addCategory(categoryEntity: CategoryEntity) = dao.addCategory(categoryEntity)
//
//    suspend fun addUnit(unitEntity: UnitEntity) = dao.addUnit(unitEntity)

    suspend fun updateProduct(productEntity: ProductEntity) = dao.updateProduct(productEntity)

    suspend fun deleteProduct(productEntity: ProductEntity) = dao.deleteProduct(productEntity)

    fun checkIsGivenIdExists(id: Long): Boolean = dao.checkIsGivenIdExists(id)

//    fun getUnitFromId(unitId: Long): Flow<String> { return dao.getUnitFromId(unitId) }

//    fun getUnitId(unit: String): Flow<Long> { return dao.getUnitId(unit) }

//    fun getCategoryId(category: String): Flow<Long> { return dao.getCategoryId(category) }

    fun getAllProducts(): Flow<List<ProductEntity>> { return dao.getAllProducts() }

//    fun getCategoryFromId(categoryId: Long): Flow<String> {
//        return dao.getCategoryFromId(categoryId)
//    }
}
