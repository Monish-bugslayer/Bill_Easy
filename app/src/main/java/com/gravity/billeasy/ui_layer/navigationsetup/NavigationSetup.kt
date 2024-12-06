package com.gravity.billeasy.ui_layer.navigationsetup

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.util.fastCbrt
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.gravity.billeasy.appdatastore.databasePreferenceDataStore
import com.gravity.billeasy.data_layer.DatabaseInstance
import com.gravity.billeasy.data_layer.Repository
import com.gravity.billeasy.domain_layer.UseCase
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.CreateAccountScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.LoginScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.OTPVerificationScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.Otp
import com.gravity.billeasy.ui_layer.app_screens.ProductAddOrEditScreen
import com.gravity.billeasy.ui_layer.app_screens.ProductAddOrEdit
import com.gravity.billeasy.ui_layer.app_screens.base_screens.Home
import com.gravity.billeasy.ui_layer.app_screens.base_screens.MyProducts
import com.gravity.billeasy.ui_layer.app_screens.base_screens.Sales
import com.gravity.billeasy.ui_layer.viewmodel.ProductViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val EDIT_SCREEN_TITLE = "Edit your product"

class NavigationSetup(
    private val navHostController: NavHostController,
    private val navigationControllerImpl: AppNavigationControllerImpl
) {

    private lateinit var productViewModel: ProductViewModel

    @Composable
    fun InitViewModel(){
        if(!::productViewModel.isInitialized) {
            val context = LocalContext.current
            val database = DatabaseInstance.getDatabase(context)
            val productDao = database.productDao()
            val repository = Repository(productDao)
            val useCase = UseCase(repository)
            productViewModel = ProductViewModel(useCase, context.databasePreferenceDataStore)
        }
    }

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

            composable(route = BillEasyScreens.MY_PRODUCTS.name) {
                MyProducts(
                    viewModel = productViewModel,
                    onEditProduct = {
                        val productJson = Uri.encode(Json.encodeToString(it))
                        navigationControllerImpl.navigateToAddProductScreen(
                            screenTitle = EDIT_SCREEN_TITLE,
                            productJson = productJson,
                            isForAdd = false
                    ) }
            ) }

            composable<ProductAddOrEdit> { backstackEntry ->
                val productAddOrEdit = backstackEntry.toRoute() as ProductAddOrEdit
                // TODO when adding Product model in param, an crash is occuring, need to find the root cause and fix it.
                ProductAddOrEditScreen(
                    isForAdd = productAddOrEdit.isForAdd,
                    screenTitle = productAddOrEdit.screenTitle,
                    product = productAddOrEdit.product,
                    viewModel = productViewModel,
                    navigateBackAfterAddOrEditProduct = {
                        if(productAddOrEdit.isForAdd) {
                            navigationControllerImpl.navigateToMyProducts()
                        } else {
                            navHostController.navigateUp()
                        }
                    }
                )
            }
            composable(route = BillEasyScreens.BILLS.name) { Sales() }

            composable(route = BillEasyScreens.GENERATE_BILL.name) {}

            composable(route = BillEasyScreens.EDIT_PRODUCT.name) {}
        }
    }
}