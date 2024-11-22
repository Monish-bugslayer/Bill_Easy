package com.gravity.billeasy.presentation_layer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.gravity.billeasy.R

@Composable
fun Home() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.pink_light))){

    }
}