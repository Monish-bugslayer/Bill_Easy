package com.gravity.billeasy.domain_layer

import com.gravity.billeasy.data_layer.ProductRepository
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.ProductCategory
import com.gravity.billeasy.ui_layer.QuantityUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class UseCase(private val productRepository: ProductRepository) {

    private val categoriesList get() = ProductCategory.entries.toList()
    private val unitsList get() = QuantityUnit.entries.toList()

    suspend fun addProduct(product: Product) = productRepository.addProduct(ProductEntity(
            productName = product.productName,
            availableStock = product.availableStock,
            buyingPrice = product.buyingPrice,
            retailPrice = product.retailPrice,
            wholeSalePrice = product.wholeSalePrice,
            quantity = product.quantity,
            productCategoryId = getCategoryId(product.productCategory),
            productUnitId = getUnitId(product.unit)
        ))

    suspend fun updateProduct(product: Product) = productRepository.updateProduct(ProductEntity(
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

    suspend fun deleteProduct(product: Product) = productRepository.deleteProduct(ProductEntity(
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
            productRepository.addCategory(CategoryEntity(category = it.name))
        }
    }

    suspend fun addUnit() {
        unitsList.forEach {
            productRepository.addUnit(UnitEntity(unit = it.name))
        }
    }

    suspend fun getCategoryFromId(categoryId: Long): String {
        return productRepository.getCategoryFromId(categoryId).first()
    }

    suspend fun getUnitFromId(unitId: Long): String {
        return productRepository.getUnitFromId(unitId).first()
    }

    suspend fun getUnitId(unit: String): Long {
        return productRepository.getUnitId(unit).first()
    }

    suspend fun getCategoryId(category: String): Long {
        return productRepository.getCategoryId(category).first()
    }

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productRepository.getAllProducts()
    }

    suspend fun checkIsGivenIdExistsAndAddProduct(importedProducts: List<Product>){
        withContext(Dispatchers.IO) {
            importedProducts.forEach { it ->
                if(productRepository.checkIsGivenIdExists(it.productId).not()) {
                    addProduct(it)
                }
            }
        }
    }
}