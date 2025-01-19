package com.gravity.billeasy.data_layer.dao

import androidx.compose.runtime.Stable
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.gravity.billeasy.domain_layer.entities.ShopEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {
    // TODO in case in future if there is a support of adding multiple shops we need to get shop
    //  details by passing current logged in shop id
    @Query("SELECT * from shop")
    fun getCurrentShopDetails(): Flow<ShopEntity>

    @Update
    fun updateShop(shopEntity: ShopEntity)
}