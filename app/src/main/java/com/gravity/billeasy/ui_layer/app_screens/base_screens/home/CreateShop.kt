package com.gravity.billeasy.ui_layer.app_screens.base_screens.home

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CreateShop (
    paddingValues: PaddingValues,
    content: @Composable () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.white)).padding(top = paddingValues.calculateTopPadding())) {
        Column(Modifier.fillMaxSize().background(color = colorResource(R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text (
                modifier = Modifier.fillMaxWidth(),
                text = "Create your shop",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            content()
        }
    }
}