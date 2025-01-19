package com.gravity.billeasy.data_layer.repository

import com.gravity.billeasy.data_layer.dao.ProductDao
import com.gravity.billeasy.domain_layer.entities.ProductEntity
import kotlinx.coroutines.flow.Flow


class ProductRepository(private val dao: ProductDao) {

    suspend fun addProduct(productEntity: ProductEntity) = dao.addProduct(productEntity)

    suspend fun updateProduct(productEntity: ProductEntity) = dao.updateProduct(productEntity)

    suspend fun deleteProduct(productEntity: ProductEntity) = dao.deleteProduct(productEntity)

    fun checkIsGivenIdExists(id: Long): Boolean = dao.checkIsGivenIdExists(id)

    fun getAllProducts(): Flow<List<ProductEntity>> { return dao.getAllProducts() }

}
