package com.gravity.billeasy.ui_layer.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravity.billeasy.data_layer.models.OrderedProduct
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.data_layer.models.Sale
import com.gravity.billeasy.data_layer.repository.ProductRepository
import com.gravity.billeasy.domain_layer.use_cases.SalesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Stable
class SalesViewModel(
    private val salesUseCase: SalesUseCase,
    private val productsRepo: ProductRepository
): ViewModel() {

    private val _allProducts: MutableState<List<Product>> = mutableStateOf(emptyList())
    private val _allSales = mutableStateOf(emptyList<Sale>())
    val cumulativeTotal = mutableIntStateOf(0)
    var orderedProducts: List<OrderedProduct> = emptyList()
        private set
    val allSales: State<List<Sale>> get() = _allSales
    val allProducts: MutableState<List<Product>> get() = _allProducts

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


    init {
        viewModelScope.launch(Dispatchers.IO) {
            salesUseCase.getAllSales().collectLatest { saleEntity ->
                _allSales.value = saleEntity.map {
                    Sale(
                        billId = it.billId,
                        customerName = it.customerName,
                        billingDate = it.billingDate,
                        orderedProducts = it.orderedProducts,
                        productId = it.productId,
                        productCategory = it.productCategory,
                        orderedQuantity = it.orderedQuantity,
                        finalizedPerUnitPrice = it.finalizedPerUnitPrice,
                        totalPrice = it.totalPrice,
                        paymentMethod = it.paymentMethod,
                        billType = it.billType,
                        shopId = it.shopId
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            productsRepo.getAllProducts().collectLatest { productEntity ->
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

    fun onSearchQueryChange(newQuery: String) { searchQuery = newQuery }

    fun updateOrderedProducts(newOrderedProduct: MutableMap<Long, OrderedProduct>) = {
        orderedProducts = newOrderedProduct.values.toList()
    }

}