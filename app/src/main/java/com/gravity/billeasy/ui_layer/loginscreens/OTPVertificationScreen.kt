package com.gravity.billeasy.ui_layer.loginscreens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.gravity.billeasy.R
import java.util.concurrent.TimeUnit

const val VERIFICATION = "Verification"
const val VERIFIY_OTP = "Verify otp"
const val AUTH_COMPLETED = "Authentication success"
const val AUTH_FAILED = "Authentication failed"
const val OTP_SENT = "Otp sent to the provided mobile number"

fun checkIsValidOtp(number: String): Boolean = number.length == 6


@Composable
fun OTPVerificationScreen(mobileNumber: String, fromScreen: String) {

    val otp = remember { mutableStateOf("") }
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    var storedVerificationId: String? = null
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    val isNeedToEnableLogin by remember {
        derivedStateOf {
            checkIsValidOtp(otp.value)
        }
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Toast.makeText(context, AUTH_COMPLETED, Toast.LENGTH_LONG).show()
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            println(p0)
            Toast.makeText(context, AUTH_FAILED, Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId, token)
            Toast.makeText(context, OTP_SENT, Toast.LENGTH_LONG).show()
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    val onSignInCompleteListener = OnCompleteListener<AuthResult> {
        if(it.isSuccessful){
            Toast.makeText(context, "OTP verification success", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(context, "OTP verification failed", Toast.LENGTH_LONG).show()
        }
    }

    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber("+91$mobileNumber")
        .setTimeout(60L, TimeUnit.SECONDS)
        .setActivity(LocalContext.current as Activity)
        .setCallbacks(callbacks)
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)


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
        ElevatedButton(onClick = {
            if(isNeedToEnableLogin) {
                val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, otp.value)
                auth.signInWithCredential(credential).addOnCompleteListener(onSignInCompleteListener)
            } },
            modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 10.dp).alpha(if( isNeedToEnableLogin ) 1f else 0.5f)
        ) {
            Text(text = VERIFIY_OTP)
        }
    }

}