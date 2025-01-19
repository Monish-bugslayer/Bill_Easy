package com.gravity.billeasy.domain_layer.use_cases

import androidx.compose.runtime.Stable
import com.gravity.billeasy.data_layer.models.Shop
import com.gravity.billeasy.data_layer.repository.ShopRepository
import com.gravity.billeasy.domain_layer.entities.ShopEntity
import kotlinx.coroutines.flow.Flow


@Stable
class ShopUseCase(private val shopRepo: ShopRepository) {
    fun getCurrentShopDetails(): Flow<ShopEntity> = shopRepo.getCurrentShopDetails()
    fun updateShop(shop: Shop) {
        shopRepo.updateShop(
            ShopEntity(
                shopId = shop.shopId,
                shopName = shop.shopName,
                shopEmailAddress = shop.shopEmailAddress,
                shopAddress = shop.shopAddress,
                shopMobileNumber = shop.shopMobileNumber,
                gstNumber = shop.gstNumber,
                tinNumber = shop.tinNumber,
                ownerAddress = shop.ownerAddress,
                ownerMobileNumber = shop.ownerMobileNumber,
                ownerName = shop.ownerName
            )
        )
    }
}