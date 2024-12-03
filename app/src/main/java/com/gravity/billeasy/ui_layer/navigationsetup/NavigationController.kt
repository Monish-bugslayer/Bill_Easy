package com.gravity.billeasy.ui_layer.navigationsetup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gravity.billeasy.ui_layer.loginscreens.Otp

enum class BillEasyScreens {
    LOGIN, CREATE_ACCOUNT, OTP_VERIFICATION, HOME, ALL_PRODUCTS, GENERATE_BILL, ADD_PRODUCT, EDIT_PRODUCT, BILLS
}

interface AppNavigationController {

    fun navigateToCreateAccountScreen()
    fun navigateToOTPVerificationScreen(mobileNumber: String, fromScreen: String)
    fun navigateToAddProductScreen()
    fun navigateToMyProducts()
    fun navigateToSales()
    fun navigateToHomeScreen()
}

@Stable
class AppNavigationControllerImpl(private val navHostController: NavHostController) :
    AppNavigationController {

    override fun navigateToCreateAccountScreen() {
        navHostController.navigate(route = BillEasyScreens.CREATE_ACCOUNT.name)
    }

    override fun navigateToOTPVerificationScreen(mobileNumber: String, fromScreen: String) {
        navHostController.navigate(route = Otp(mobileNumber, fromScreen))
    }

    override fun navigateToAddProductScreen() {
        navHostController.navigate(route = BillEasyScreens.ADD_PRODUCT.name)
    }

    override fun navigateToMyProducts() {
        navHostController.navigate(route = BillEasyScreens.ALL_PRODUCTS.name) {
            applyNavigationConfiguration(navHostController)
        }
    }

    override fun navigateToSales() {
        navHostController.navigate(route = BillEasyScreens.BILLS.name) {
            applyNavigationConfiguration(navHostController)
        }
    }

    override fun navigateToHomeScreen() {
        navHostController.navigate(route = BillEasyScreens.HOME.name) {
            applyNavigationConfiguration(navHostController)
        }
    }

    @Composable
    fun getCurrentRoute(): String? {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        return currentRoute
    }

    private fun NavOptionsBuilder.applyNavigationConfiguration(navHostController: NavHostController) {
        popUpTo(navHostController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}