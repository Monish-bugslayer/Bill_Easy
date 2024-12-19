package com.gravity.billeasy.domain_layer

import com.gravity.billeasy.data_layer.Repository
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.ProductCategory
import com.gravity.billeasy.ui_layer.QuantityUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UseCase(private val repository: Repository) {

    private val categoriesList get() = ProductCategory.entries.toList()
    private val unitsList get() = QuantityUnit.entries.toList()

    suspend fun addProduct(product: Product) = repository.addProduct(ProductEntity(
            productName = product.productName,
            availableStock = product.availableStock,
            buyingPrice = product.buyingPrice,
            retailPrice = product.retailPrice,
            wholeSalePrice = product.wholeSalePrice,
            quantity = product.quantity,
            productCategoryId = getCategoryId(product.productCategory),
            productUnitId = getUnitId(product.unit)
        ))

    suspend fun updateProduct(product: Product) = repository.updateProduct(ProductEntity(
            productId = product.productId,
            productName = product.productName,
            availableStock = product.availableStock,
            buyingPrice = product.buyingPrice,
            retailPrice = product.retailPrice,
            wholeSalePrice = product.wholeSalePrice,
            quantity = product.quantity,
            productCategoryId = getCategoryId(product.productCategory),
            productUnitId = getUnitId(product.unit)
        ))

    suspend fun deleteProduct(product: Product) = repository.deleteProduct(ProductEntity(
        productId = product.productId,
        productName = product.productName,
        availableStock = product.availableStock,
        buyingPrice = product.buyingPrice,
        retailPrice = product.retailPrice,
        wholeSalePrice = product.wholeSalePrice,
        quantity = product.quantity,
        productCategoryId = getCategoryId(product.productCategory),
        productUnitId = getUnitId(product.unit)
    ))

    suspend fun addCategory() {
        categoriesList.forEach {
            repository.addCategory(CategoryEntity(category = it.name))
        }
    }

    suspend fun addUnit() {
        unitsList.forEach {
            repository.addUnit(UnitEntity(unit = it.name))
        }
    }

    suspend fun getCategoryFromId(categoryId: Long): String {
        return repository.getCategoryFromId(categoryId).first()
    }

    suspend fun getUnitFromId(unitId: Long): String {
        return repository.getUnitFromId(unitId).first()
    }

    suspend fun getUnitId(unit: String): Long {
        return repository.getUnitId(unit).first()
    }

    suspend fun getCategoryId(category: String): Long {
        return repository.getCategoryId(category).first()
    }

    suspend fun getAllProducts(): Flow<List<ProductEntity>> {
        return repository.getAllProducts()
    }
}