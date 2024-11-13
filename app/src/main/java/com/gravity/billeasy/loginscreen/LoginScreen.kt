package com.gravity.billeasy.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gravity.billeasy.R


fun checkIsValidMobileNumber(number: String): Boolean = number.length == 10

@Composable
fun keyboardAsState(): State<Boolean> {
    val keyboardState = remember { mutableStateOf(false) }
    val view = LocalView.current
    LaunchedEffect(view) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            keyboardState.value = insets.isVisible(WindowInsetsCompat.Type.ime())
            insets
        }
    }
    return keyboardState
}

@Composable
fun LoginScreen() {

    val mobileNumber = remember { mutableStateOf("") }
    val isNeedToEnableLogin by remember {
        derivedStateOf {
            checkIsValidMobileNumber(mobileNumber.value)
        }
    }
    val scrollState = rememberScrollState()

//    val isKeyboardVisible = keyboardAsState().value
//    LaunchedEffect(isKeyboardVisible) {
//        if(isKeyboardVisible){
//            scrollState.animateScrollBy(200f)
//            println(scrollState.value)
//        }
//    }

    Scaffold { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color("#FBDFC9".toColorInt()))
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .imePadding()
        ) {
            Text(
                text = "Welcome to Bill Easy",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier
                    .padding(top = 25.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Image(painter = painterResource(id = R.drawable.login_image), "")

            OutlinedTextField(
                value = mobileNumber.value,
                onValueChange = { mobileNumber.value = it },
                label = { Text(text = "Phone number") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            ElevatedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
                    .alpha(if (isNeedToEnableLogin) 1f else 0.5f)
            ) {
                Text(text = "Login")
            }

            ElevatedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 5.dp)
            ) {
                Text(text = "Create account")
            }


        }
    }
}