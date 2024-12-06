package com.gravity.billeasy.ui_layer.app_screens.base_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gravity.billeasy.R

@Composable
fun Sales() {
    Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.background(Color.White), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Image(
                modifier = Modifier.alpha(0.5f).align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.sales_feature),
                contentDescription = "Sales feature"
            )
            Text(
                text = "This feature yet to be implemented",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}