package com.gravity.billeasy.loginscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gravity.billeasy.R

const val VERIFICATION = "Verification"
const val VERIFIY_OTP = "Verify otp"

@Composable
fun OTPVerificationScreen() {

    val otp = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(color = colorResource(id = R.color.sandle))) {
        Text(
            text = VERIFICATION,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 15.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = otp.value,
            onValueChange = { otp.value = it },
            modifier = Modifier
                .padding(top = 100.dp)
                .align(Alignment.CenterHorizontally)
        )
        ElevatedButton(onClick = { /*TODO*/ }, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 10.dp)) {
            Text(text = VERIFIY_OTP)
        }
    }

}