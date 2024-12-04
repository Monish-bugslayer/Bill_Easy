package com.gravity.billeasy.ui_layer.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravity.billeasy.DatabaseTablePreferences
import com.gravity.billeasy.domain_layer.UseCase
import com.gravity.billeasy.data_layer.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

class ProductViewModel(
    private val appUseCase: UseCase,
    private val dbPreferenceStore: DataStore<DatabaseTablePreferences>
): ViewModel() {

    init {
        viewModelScope.launch {
            dbPreferenceFlow.collectLatest {
                if(!it.tableCreated) {
                    addUnit()
                    addCategory()
                    dbPreferenceStore.updateData { preferences->
                        preferences.toBuilder().setTableCreated(true).build()
                    }
                }
            }
            getAllProducts()
        }
    }

    private val dbPreferenceFlow: Flow<DatabaseTablePreferences> = dbPreferenceStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(DatabaseTablePreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    val allProducts: MutableState<List<Product>> get() = _allProducts
    private var _allProducts: MutableState<List<Product>> = mutableStateOf(emptyList())
    // this means this searchQuery is publicly visible but can be modified within the class
    var searchQuery by mutableStateOf("")
        private set
    val searchResults: StateFlow<List<Product>> =
        snapshotFlow { searchQuery }
            .combine(snapshotFlow { allProducts.value }) { searchQuery, products ->
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

    private suspend fun getAllProducts() { _allProducts.value = appUseCase.getAllProducts() }

    private fun addCategory() = viewModelScope.launch { appUseCase.addCategory() }

    private fun addUnit() = viewModelScope.launch { appUseCase.addUnit() }

    suspend fun getUnitId(unitName: String): Long { return appUseCase.getUnitId(unitName) }

    suspend fun getCategoryFromId(id: Long): String { return appUseCase.getCategoryFromId(id) }

    suspend fun getUnitFromId(id: Long): String { return appUseCase.getUnitFromId(id) }

    fun addProduct(product: Product) = viewModelScope.launch { appUseCase.addProduct(product) }

    suspend fun getCategoryId(categoryName: String): Long {
        return appUseCase.getCategoryId(categoryName)
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }
}
