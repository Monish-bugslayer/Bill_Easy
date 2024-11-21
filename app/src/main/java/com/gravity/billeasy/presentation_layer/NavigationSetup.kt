package com.gravity.billeasy.presentation_layer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gravity.billeasy.loginscreens.CreateAccountScreen
import com.gravity.billeasy.loginscreens.LoginScreen
import com.gravity.billeasy.loginscreens.OTPVerificationScreen

class NavigationSetup(
    private val navHostController: NavHostController,
    private val navigationControllerImpl: AppNavigationControllerImpl
) {

    @Composable
    fun setupNavgation(innerPadding: PaddingValues) = NavHost(
        navController = navHostController,
        startDestination = BillEasyScreens.LOGIN.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = BillEasyScreens.LOGIN.name) {
            LoginScreen(onLoginButtonClicked = {
                navigationControllerImpl.navigateFromLoginScreenToOTPVerificationScreen() },
                onCreateAccountButtonClicked = {
                    navigationControllerImpl.navigateFromLoginScreenToCreateAccountScreen()
                })
        }
        composable(route = BillEasyScreens.OTP_VERIFICATION.name) {
            OTPVerificationScreen()
        }
        composable(route = BillEasyScreens.CREATE_ACCOUNT.name) {
            CreateAccountScreen()

        }
        composable(route = BillEasyScreens.HOME.name) {

        }
        composable(route = BillEasyScreens.ALL_PRODUCTS.name) {

        }
        composable(route = BillEasyScreens.ADD_PRODUCT.name) {

        }
        composable(route = BillEasyScreens.GENERATE_BILL.name) {

        }
        composable(route = BillEasyScreens.EDIT_PRODUCT.name) {

        }

    }
}