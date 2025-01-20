package com.gravity.billeasy.ui_layer.app_screens.base_screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gravity.billeasy.ui_layer.viewmodel.ShopViewModel

@Composable
fun CreateShop(shopViewModel: ShopViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Create shop")
    }
}