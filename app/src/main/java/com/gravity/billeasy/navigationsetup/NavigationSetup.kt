package com.gravity.billeasy.navigationsetup

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.gravity.billeasy.loginscreens.CreateAccountScreen
import com.gravity.billeasy.loginscreens.LoginScreen
import com.gravity.billeasy.loginscreens.OTPVerificationScreen
import com.gravity.billeasy.loginscreens.Otp
import com.gravity.billeasy.presentation_layer.Home

class NavigationSetup(
    private val navHostController: NavHostController,
    private val navigationControllerImpl: AppNavigationControllerImpl
) {

    @Composable
    fun SetupNavgation(innerPadding: PaddingValues) {
        NavHost(
            navController = navHostController,
            startDestination = BillEasyScreens.HOME.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BillEasyScreens.LOGIN.name) {
                LoginScreen(onLoginButtonClicked = { mobileNumber, fromScreen ->
                    navigationControllerImpl.navigateToOTPVerificationScreen(mobileNumber, fromScreen)
                }, onCreateAccountButtonClicked = {
                    navigationControllerImpl.navigateToCreateAccountScreen()
                })
            }
            composable<Otp> { backstackEntry ->
                val otp = backstackEntry.toRoute() as Otp
                OTPVerificationScreen(otp.mobileNumber, otp.dataFrom)
            }
            composable(route = BillEasyScreens.CREATE_ACCOUNT.name) {
                CreateAccountScreen(onClickProceed = { mobileNumber, fromScreen ->
                    navigationControllerImpl.navigateToOTPVerificationScreen(mobileNumber, fromScreen)
                })
            }
            composable(route = BillEasyScreens.HOME.name) {
                Home()
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
}