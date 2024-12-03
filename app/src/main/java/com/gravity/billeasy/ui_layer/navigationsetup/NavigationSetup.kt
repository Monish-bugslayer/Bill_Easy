package com.gravity.billeasy.ui_layer.navigationsetup

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.CreateAccountScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.LoginScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.OTPVerificationScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.Otp
import com.gravity.billeasy.ui_layer.app_screens.AddProduct
import com.gravity.billeasy.ui_layer.app_screens.base_screens.Home
import com.gravity.billeasy.ui_layer.app_screens.base_screens.MyProducts
import com.gravity.billeasy.ui_layer.app_screens.base_screens.Sales

class NavigationSetup(
    private val navHostController: NavHostController,
    private val navigationControllerImpl: AppNavigationControllerImpl
) {

    @Composable
    fun SetupNavigation(innerPadding: PaddingValues) {
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
            composable(route = BillEasyScreens.HOME.name) { Home() }
            composable(route = BillEasyScreens.ALL_PRODUCTS.name) { MyProducts() }
            composable(route = BillEasyScreens.ADD_PRODUCT.name) { AddProduct() }
            composable(route = BillEasyScreens.BILLS.name) { Sales() }
            composable(route = BillEasyScreens.GENERATE_BILL.name) {}
            composable(route = BillEasyScreens.EDIT_PRODUCT.name) {}
        }
    }
}