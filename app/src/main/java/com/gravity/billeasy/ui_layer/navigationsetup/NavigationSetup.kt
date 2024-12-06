package com.gravity.billeasy.ui_layer.navigationsetup

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.gravity.billeasy.appdatastore.databasePreferenceDataStore
import com.gravity.billeasy.data_layer.DatabaseInstance
import com.gravity.billeasy.data_layer.Repository
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.domain_layer.UseCase
import com.gravity.billeasy.ui_layer.app_screens.ProductAddOrEditScreen
import com.gravity.billeasy.ui_layer.app_screens.base_screens.Home
import com.gravity.billeasy.ui_layer.app_screens.base_screens.MyProducts
import com.gravity.billeasy.ui_layer.app_screens.base_screens.Sales
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.CreateAccountScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.LoginScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.OTPVerificationScreen
import com.gravity.billeasy.ui_layer.app_screens.loginscreens.Otp
import com.gravity.billeasy.ui_layer.viewmodel.ProductViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val EDIT_SCREEN_TITLE = "Edit your product"
const val ADD_SCREEN_TITLE = "Add your product"

class NavigationSetup(
    private val navHostController: NavHostController,
    private val navigationControllerImpl: AppNavigationControllerImpl
) {

    private lateinit var productViewModel: ProductViewModel

    @Composable
    fun InitViewModel() {
        if (!::productViewModel.isInitialized) {
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

            composable(route = BillEasyScreens.HOME.name) { Home() }

            composable(route = BillEasyScreens.MY_PRODUCTS.name) {
                MyProducts(viewModel = productViewModel, onEditProduct = {
                    val productJson = Uri.encode(Json.encodeToString(it))
                    navigationControllerImpl.navigateToAddProductScreen(productJson = productJson)
                })
            }

            composable(route = "${BillEasyScreens.PRODUCT_ADD_OR_EDIT.name}?product={product}",
                arguments = listOf(navArgument("product") {
                    // Here is the problem of the bottom navigation navigation back to home screen after clicking add product
                    type = ProductNavType()
                    nullable = true
                })) { backstackEntry ->

                val productArg = backstackEntry.arguments?.getParcelable<Product>("product")
                ProductAddOrEditScreen(
                    isForAdd = productArg == null,
                    screenTitle = if (productArg == null) ADD_SCREEN_TITLE else EDIT_SCREEN_TITLE,
                    product = productArg,
                    viewModel = productViewModel,
                    navigateBackAfterAddOrEditProduct = {
                        if (productArg == null) {
                            navigationControllerImpl.navigateToMyProducts()
                        } else {
                            navHostController.navigateUp()
                        }
                    })
            }

            composable(route = BillEasyScreens.BILLS.name) { Sales() }

            composable(route = BillEasyScreens.GENERATE_BILL.name) {}
        }
    }
}