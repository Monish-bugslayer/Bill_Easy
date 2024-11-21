package com.gravity.billeasy.presentation_layer

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gravity.billeasy.loginscreens.CreateAccountScreen
import com.gravity.billeasy.loginscreens.LoginScreen
import com.gravity.billeasy.loginscreens.OTPVerificationScreen

const val OTP_SENT_TOAST = "Otp sent to the provided mobile number"
class NavigationSetup(
    private val navHostController: NavHostController,
    private val navigationControllerImpl: AppNavigationControllerImpl
) {

    @Composable
    fun SetupNavgation(innerPadding: PaddingValues) {
        val context = LocalContext.current
        NavHost(
            navController = navHostController,
            startDestination = BillEasyScreens.LOGIN.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BillEasyScreens.LOGIN.name) {
                LoginScreen(onLoginButtonClicked = {
                    navigationControllerImpl.navigateFromLoginScreenToOTPVerificationScreen()
                }, onCreateAccountButtonClicked = {
                    navigationControllerImpl.navigateFromLoginScreenToCreateAccountScreen()
                })
            }
            composable(route = BillEasyScreens.OTP_VERIFICATION.name) {
                OTPVerificationScreen()
            }
            composable(route = BillEasyScreens.CREATE_ACCOUNT.name) {
                CreateAccountScreen(onClickProceed = {
                    Toast.makeText(context, OTP_SENT_TOAST, Toast.LENGTH_LONG).show()
                    navigationControllerImpl.navigateFromLoginScreenToOTPVerificationScreen()
                })
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
}