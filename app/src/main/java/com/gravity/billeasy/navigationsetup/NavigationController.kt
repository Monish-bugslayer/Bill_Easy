package com.gravity.billeasy.navigationsetup

import androidx.navigation.NavHostController
import com.gravity.billeasy.loginscreens.Otp

enum class BillEasyScreens {
    LOGIN, CREATE_ACCOUNT, OTP_VERIFICATION, HOME, ALL_PRODUCTS, GENERATE_BILL, ADD_PRODUCT, EDIT_PRODUCT
}

interface AppNavigationController {

    fun navigateToCreateAccountScreen()
    fun navigateToOTPVerificationScreen(mobileNumber: String, fromScreen: String)
}

class AppNavigationControllerImpl(private val navHostController: NavHostController) :
    AppNavigationController {

    override fun navigateToCreateAccountScreen() {
        navHostController.navigate(route = BillEasyScreens.CREATE_ACCOUNT.name)
    }

    override fun navigateToOTPVerificationScreen(mobileNumber: String, fromScreen: String) {
        navHostController.navigate(route = Otp(mobileNumber, fromScreen))
    }
}