package com.gravity.billeasy.ui_layer.navigationsetup

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.gravity.billeasy.ui_layer.IsNeedButton
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.Home
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.MyProducts
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.EditShopDetails
import com.gravity.billeasy.ui_layer.app_screens.base_screens.sales.Sales
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.CreateAccountScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.LoginScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.OTPVerificationScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.Otp
import com.gravity.billeasy.ui_layer.viewmodel.ProductsViewModel
import com.gravity.billeasy.ui_layer.viewmodel.ShopViewModel

class NavigationSetup(
    private val navHostController: NavHostController,
    private val navigationControllerImpl: AppNavigationControllerImpl
) {

    @Composable
    fun SetupNavigation(
        innerPadding: PaddingValues,
        productsViewModel: ProductsViewModel,
        shopViewModel: ShopViewModel,
        startDestination: String = BillEasyScreens.HOME.name
    ) {
        NavHost(
            navController = navHostController,
            startDestination = startDestination,
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {

            composable(route = BillEasyScreens.LOGIN.name) {
                LoginScreen(onLoginButtonClicked = { mobileNumber, fromScreen ->
                    navigationControllerImpl.navigateToOTPVerificationScreen(
                        mobileNumber,
                        fromScreen
                    )
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
                    navigationControllerImpl.navigateToOTPVerificationScreen(
                        mobileNumber,
                        fromScreen
                    )
                })
            }

            composable(route = BillEasyScreens.HOME.name) { Home(shopViewModel = shopViewModel) }

            composable(route = BillEasyScreens.CREATE_SHOP.name) {
                EditShopDetails (
                    shopViewModel,
                    innerPadding = innerPadding,
                    isNeedButton = IsNeedButton.Yes(
                        onButtonClick = { navigationControllerImpl.navigateToHomeScreen() },
                        buttonText = "Next"
                    )
                )
            }

            composable(route = BillEasyScreens.MY_PRODUCTS.name) {
                MyProducts(productsViewModel = productsViewModel)
            }

            composable(route = BillEasyScreens.BILLS.name) { Sales() }

            composable(route = BillEasyScreens.GENERATE_BILL.name) {}
        }
    }
}