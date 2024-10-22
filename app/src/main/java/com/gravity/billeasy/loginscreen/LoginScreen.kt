package com.gravity.billeasy.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gravity.billeasy.R


@Composable
fun LoginScreen() {
    var isPlaying by remember {
        mutableStateOf(true)
    }
    var speed by remember {
        mutableFloatStateOf(1f)
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.login_animation)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false
    )

    val linearGradient = Brush.linearGradient(
        0.0f to Color.White,
        0.5f to colorResource(R.color.light_pink),
        1.0f to Color.White
    )

    Column(Modifier.fillMaxSize().background(linearGradient)) {
        Text(
            text = "Welcome to Bill Easy",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier
                .padding(top = 45.dp)
                .align(Alignment.CenterHorizontally)
        )

        Image(imageVector = ImageVector.vectorResource(R.drawable.shop), "")

    }
}