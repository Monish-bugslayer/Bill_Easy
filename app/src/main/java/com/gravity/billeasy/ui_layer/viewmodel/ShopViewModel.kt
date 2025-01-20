package com.gravity.billeasy.ui_layer.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gravity.billeasy.AppPreference
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.data_layer.models.Shop
import com.gravity.billeasy.domain_layer.use_cases.ShopUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Stable
class ShopViewModel(
    private val shopUseCase: ShopUseCase,
    private val appPreference: DataStore<AppPreference>
) : ViewModel() {

    val shop: MutableState<Shop> get() = _shop
    private val _shop: MutableState<Shop> = mutableStateOf(Shop())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            shopUseCase.getCurrentShopDetails().collectLatest {
                if(it != null) {
                    _shop.value = Shop (
                        shopId = it.shopId,
                        shopAddress = it.shopAddress,
                        shopName = it.shopName,
                        shopEmailAddress = it.shopEmailAddress,
                        shopMobileNumber = it.shopMobileNumber,
                        ownerName = it.ownerName,
                        ownerMobileNumber = it.ownerMobileNumber,
                        ownerAddress = it.ownerAddress,
                        gstNumber = it.gstNumber,
                        tinNumber = it.tinNumber
                    )
                    updateAppPreference()
                }
            }
        }
    }

    private fun updateAppPreference() {
        viewModelScope.launch(Dispatchers.IO) {
            appPreference.data.collectLatest {
                if(it.currentLoggedInShopId == "") {
                    appPreference.updateData { preference ->
                        preference.toBuilder().setCurrentLoggedInShopId(shop.value.shopId.toString()).build()
                    }
                }
            }
        }
    }

    fun updateShop(shop: Shop) = viewModelScope.launch(Dispatchers.IO) { shopUseCase.updateShop(shop) }

}