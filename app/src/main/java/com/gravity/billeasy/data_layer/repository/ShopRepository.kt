package com.gravity.billeasy.data_layer.repository

import androidx.compose.runtime.Stable
import com.gravity.billeasy.data_layer.dao.ShopDao
import com.gravity.billeasy.domain_layer.entities.ShopEntity
import kotlinx.coroutines.flow.Flow

@Stable
class ShopRepository(private val shopDao: ShopDao) {
    fun getCurrentShopDetails(): Flow<ShopEntity?> = shopDao.getCurrentShopDetails()
    fun updateShop(shopEntity: ShopEntity) { shopDao.updateShop(shopEntity) }
}