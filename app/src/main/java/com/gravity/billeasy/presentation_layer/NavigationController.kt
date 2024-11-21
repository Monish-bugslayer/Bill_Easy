package com.gravity.billeasy.presentation_layer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gravity.billeasy.loginscreens.CREATE_ACCOUNT
import com.gravity.billeasy.loginscreens.CreateAccountScreen
import com.gravity.billeasy.loginscreens.LoginScreen
import com.gravity.billeasy.loginscreens.OTPVerificationScreen

enum class BillEasyScreens {
    LOGIN, CREATE_ACCOUNT, OTP_VERIFICATION, HOME, ALL_PRODUCTS, GENERATE_BILL, ADD_PRODUCT, EDIT_PRODUCT
}

interface AppNavigationController {

    fun navigateFromLoginScreenToCreateAccountScreen()
    fun navigateFromLoginScreenToOTPVerificationScreen()
    fun navigateFromCreateAccountToOTPVerificationScreen()
}

class AppNavigationControllerImpl(private val navHostController: NavHostController) :
    AppNavigationController {

    override fun navigateFromLoginScreenToCreateAccountScreen() {
        navHostController.navigate(BillEasyScreens.CREATE_ACCOUNT.name)
    }

    override fun navigateFromLoginScreenToOTPVerificationScreen() {
        navHostController.navigate(BillEasyScreens.OTP_VERIFICATION.name)
    }

    override fun navigateFromCreateAccountToOTPVerificationScreen() {
        TODO("Not yet implemented")
    }
}