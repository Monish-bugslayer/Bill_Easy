package com.gravity.billeasy.ui_layer.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravity.billeasy.DatabaseTablePreferences
import com.gravity.billeasy.domain_layer.use_cases.ProductsUseCase
import com.gravity.billeasy.data_layer.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Stable
class ProductsViewModel(
    private val appProductsUseCase: ProductsUseCase
): ViewModel() {

    val allProducts: MutableState<List<Product>> get() = _allProducts
    private val _allProducts: MutableState<List<Product>> = mutableStateOf(emptyList())
    private val _isDataLoadingCompleted = mutableStateOf(false)
    private var isFreshLoading = false
    val isDataLoadingCompleted = snapshotFlow { _isDataLoadingCompleted.value }
        .stateIn(
            scope = viewModelScope,
            initialValue = false,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    init {
        isFreshLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            appProductsUseCase.getAllProducts().collectLatest { productEntity ->
                if(isFreshLoading) {
                    _isDataLoadingCompleted.value = false
                    delay(2000) // when converting to online this will help
                    _allProducts.value = productEntity.map {
                        Product(
                            productId = it.productId,
                            productName = it.productName,
                            category = it.category,
                            unit = it.unit,
                            availableStock = it.availableStock,
                            quantity = it.quantity,
                            buyingPrice = it.buyingPrice,
                            wholeSalePrice = it.wholeSalePrice,
                            retailPrice = it.retailPrice,
                            shopId = it.shopId
                        )
                    }
                    _isDataLoadingCompleted.value = true
                    isFreshLoading = false
                } else {
                    _allProducts.value = productEntity.map {
                        Product(
                            productId = it.productId,
                            productName = it.productName,
                            category = it.category,
                            unit = it.unit,
                            availableStock = it.availableStock,
                            quantity = it.quantity,
                            buyingPrice = it.buyingPrice,
                            wholeSalePrice = it.wholeSalePrice,
                            retailPrice = it.retailPrice,
                            shopId = it.shopId
                        )
                    }
                }
            }
        }
    }

    var searchQuery by mutableStateOf("")
        private set
    val searchResults: StateFlow<List<Product>> =
        snapshotFlow { searchQuery }
            .combine(snapshotFlow { _allProducts.value }) { searchQuery, products ->
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

    fun deleteProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        appProductsUseCase.deleteProduct(product)
    }

    fun editProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        appProductsUseCase.updateProduct(product)
    }

    fun addProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        // TODO  Need to get shop id from from db and add it while adding product
        appProductsUseCase.addProduct(product)
    }

    fun checkIsGivenIdExistsAndAddProduct(importedProducts: List<Product>) = viewModelScope.launch(Dispatchers.IO) {
        importedProducts.forEach { println("In viewm model imported products $it") }
        appProductsUseCase.checkIsGivenIdExistsAndAddProduct(importedProducts)
    }

    fun onSearchQueryChange(newQuery: String) { searchQuery = newQuery }
}
