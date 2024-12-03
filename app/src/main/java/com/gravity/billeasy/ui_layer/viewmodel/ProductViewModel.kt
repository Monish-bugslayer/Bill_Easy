package com.gravity.billeasy.ui_layer.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravity.billeasy.domain_layer.UseCase
import com.gravity.billeasy.data_layer.models.Product
import kotlinx.coroutines.launch

class ProductViewModel(private val appUseCase: UseCase): ViewModel() {

    init {
        viewModelScope.launch {
            addUnit()
            addCategory()
            getAllProducts()
        }
    }

    val allProducts: MutableState<List<Product>> get() = _allProducts
    private var _allProducts: MutableState<List<Product>> = mutableStateOf(emptyList())

    suspend fun getUnitId(unitName: String): Long { return appUseCase.getUnitId(unitName) }

    suspend fun getCategoryId(categoryName: String): Long {
        return appUseCase.getCategoryId(categoryName)
    }

    suspend fun getCategoryFromId(id: Long): String { return appUseCase.getCategoryFromId(id) }

    suspend fun getUnitFromId(id: Long): String { return appUseCase.getUnitFromId(id) }

    suspend fun getAllProducts() { _allProducts.value = appUseCase.getAllProducts() }

    fun addProduct(product: Product) = viewModelScope.launch { appUseCase.addProduct(product) }

    fun addCategory() = viewModelScope.launch { appUseCase.addCategory() }

    fun addUnit() = viewModelScope.launch { appUseCase.addUnit() }
}
