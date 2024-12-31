package com.gravity.billeasy.ui_layer.app_screens.base_screens.sales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gravity.billeasy.R

@Composable
fun Sales() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.background(color = colorResource(R.color.white)),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "No sales yet, click the plus button below and create a sale",
                textAlign = TextAlign.Center,
                color = colorResource(R.color.black),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}