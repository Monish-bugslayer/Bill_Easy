package com.gravity.billeasy.ui_layer.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravity.billeasy.data_layer.models.OrderedProduct
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.data_layer.models.Sale
import com.gravity.billeasy.domain_layer.use_cases.SalesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Stable
class SalesViewModel(private val salesUseCase: SalesUseCase): ViewModel() {

    val cumulativeTotal = mutableIntStateOf(0)
    val orderedProducts: State<List<OrderedProduct>> = mutableStateOf(emptyList())
    val allSales: State<List<Sale>> get() = _allSales
    private val _allSales = mutableStateOf(emptyList<Sale>())
    var allProducts: State<List<Product>> = mutableStateOf(emptyList())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            salesUseCase.getAllSales().collectLatest { saleEntity ->
                _allSales.value = saleEntity.map {
                    Sale(
                        billId = it.billId,
                        customerName = it.customerName,
                        billingDate = it.billingDate,
                        productName = it.productName,
                        productId = it.productId,
                        productCategory = it.productCategory,
                        orderedQuantity = it.orderedQuantity,
                        finalizedPerUnitPrice = it.finalizedPerUnitPrice,
                        totalPrice = it.totalPrice,
                        paymentType = it.paymentType,
                        shopId = it.shopId
                    )
                }
            }
        }
    }
}