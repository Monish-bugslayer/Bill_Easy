package com.gravity.billeasy.ui_layer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.ProductCategory
import com.gravity.billeasy.ui_layer.QuantityUnit
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class SearchViewModel: ViewModel() {
    // this means this searchQuery is publicly visible but can be modified within the class
    var searchQuery by mutableStateOf("")
        private set

    val searchResults: StateFlow<List<Product>> =
        snapshotFlow { searchQuery }
            .combine(generateProductList()) { searchQuery, products ->
                when {
                    searchQuery.isNotEmpty() -> products.filter { product ->
                        product.productName.contains(searchQuery, ignoreCase = true)
                    }
                    else -> products
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }


    private fun generateProductList() = flowOf(
            listOf(
                Product(
                    productId = 0,
                    productName = "Milk biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 9.65,
                    retailPrice = 10.0,
                    unit = QuantityUnit.PIECE.name,
                    availableStock = 1000,
                    quantity = 1,
                    buyingPrice = 9.50
                ),
                Product(
                    productId = 1,
                    productName = "Marie biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 9.65,
                    retailPrice = 10.0,
                    unit = QuantityUnit.PIECE.name,
                    availableStock = 1000,
                    quantity = 1,
                    buyingPrice = 9.50
                ),
                Product(
                    productId = 2,
                    productName = "Good day biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 9.65,
                    retailPrice = 10.0,
                    unit = QuantityUnit.PIECE.name,
                    availableStock = 1000,
                    quantity = 1,
                    buyingPrice = 9.50
                ),
                Product(
                    productId = 3,
                    productName = "Nutrichoice biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 9.65,
                    retailPrice = 10.0,
                    unit = QuantityUnit.PIECE.name,
                    availableStock = 1000,
                    quantity = 1,
                    buyingPrice = 9.50
                ),
                Product(
                    productId = 4,
                    productName = "50-50 biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 9.65,
                    retailPrice = 10.0,
                    unit = QuantityUnit.PIECE.name,
                    availableStock = 1000,
                    quantity = 1,
                    buyingPrice = 9.50
                ),
                Product(
                    productId = 5,
                    productName = "Oreo biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 9.65,
                    retailPrice = 10.0,
                    unit = QuantityUnit.PIECE.name,
                    availableStock = 1000,
                    quantity = 1,
                    buyingPrice = 9.50
                ),
                Product(
                    productId = 6,
                    productName = "Bounce biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 9.65,
                    retailPrice = 10.0,
                    unit = QuantityUnit.PIECE.name,
                    availableStock = 1000,
                    quantity = 1,
                    buyingPrice = 9.50
                ),
                Product(
                    productId = 7,
                    productName = "Parle-G biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 9.65,
                    retailPrice = 10.0,
                    unit = QuantityUnit.PIECE.name,
                    availableStock = 1000,
                    quantity = 1,
                    buyingPrice = 9.50
                ),
                Product(
                    productId = 8,
                    productName = "Bourborn biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 9.65,
                    retailPrice = 10.0,
                    unit = QuantityUnit.PIECE.name,
                    availableStock = 1000,
                    quantity = 1,
                    buyingPrice = 9.50
                ),
                Product(
                    productId = 9,
                    productName = "Bourborn biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 1065.0,
                    retailPrice = 1100.0,
                    unit = QuantityUnit.BOX.name,
                    availableStock = 10,
                    quantity = 1,
                    buyingPrice = 1000.0
                ),
                Product(
                    productId = 10,
                    productName = "Milk biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 110.0,
                    retailPrice = 120.0,
                    unit = QuantityUnit.DOZEN.name,
                    availableStock = 1000,
                    quantity = 12,
                    buyingPrice = 108.0
                ),
                Product(
                    productId = 11,
                    productName = "Marie biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 110.0,
                    retailPrice = 120.0,
                    unit = QuantityUnit.DOZEN.name,
                    availableStock = 1000,
                    quantity = 12,
                    buyingPrice = 108.0
                ),
                Product(
                    productId = 12,
                    productName = "Oreo biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 110.0,
                    retailPrice = 120.0,
                    unit = QuantityUnit.DOZEN.name,
                    availableStock = 1000,
                    quantity = 12,
                    buyingPrice = 108.0
                ),
                Product(
                    productId = 13,
                    productName = "Parle-G biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 110.0,
                    retailPrice = 120.0,
                    unit = QuantityUnit.DOZEN.name,
                    availableStock = 1000,
                    quantity = 12,
                    buyingPrice = 108.0
                ),
                Product(
                    productId = 14,
                    productName = "Happy Happy biscate",
                    productCategory = ProductCategory.BISCATES.name,
                    wholeSalePrice = 110.0,
                    retailPrice = 120.0,
                    unit = QuantityUnit.DOZEN.name,
                    availableStock = 1000,
                    quantity = 12,
                    buyingPrice = 108.0
                ),
            )
    )
}