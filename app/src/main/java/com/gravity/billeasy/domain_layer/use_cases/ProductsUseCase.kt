package com.gravity.billeasy.domain_layer.use_cases

import com.gravity.billeasy.data_layer.repository.ProductRepository
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.domain_layer.entities.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


// TODO need to remove all use cases this is unwanted isolation cause boiler plate code
class ProductsUseCase(private val productRepository: ProductRepository) {

    suspend fun addProduct(product: Product) = productRepository.addProduct(
        ProductEntity(
            productName = product.productName,
            availableStock = product.availableStock,
            buyingPrice = product.buyingPrice,
            retailPrice = product.retailPrice,
            wholeSalePrice = product.wholeSalePrice,
            quantity = product.quantity,
            category = product.category,
            unit = product.unit,
            shopId = product.shopId
        )
    )

    suspend fun updateProduct(product: Product) = productRepository.updateProduct(
        ProductEntity(
            productId = product.productId,
            productName = product.productName,
            availableStock = product.availableStock,
            buyingPrice = product.buyingPrice,
            retailPrice = product.retailPrice,
            wholeSalePrice = product.wholeSalePrice,
            quantity = product.quantity,
            category = product.category,
            unit = product.unit,
            shopId = product.shopId
        )
    )

    suspend fun deleteProduct(product: Product) = productRepository.deleteProduct(
        ProductEntity(
            productId = product.productId,
            productName = product.productName,
            availableStock = product.availableStock,
            buyingPrice = product.buyingPrice,
            retailPrice = product.retailPrice,
            wholeSalePrice = product.wholeSalePrice,
            quantity = product.quantity,
            category = product.category,
            unit = product.unit,
            shopId = product.shopId
        )
    )

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