package com.gravity.billeasy

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gravity.billeasy.appdatastore.appPreferenceDataStore
import com.gravity.billeasy.data_layer.DatabaseInstance
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.data_layer.repository.ProductRepository
import com.gravity.billeasy.data_layer.repository.BillRepository
import com.gravity.billeasy.data_layer.repository.ShopRepository
import com.gravity.billeasy.domain_layer.use_cases.ShopUseCase
import com.gravity.billeasy.ui.theme.BillEasyTheme
import com.gravity.billeasy.ui_layer.ScrollState
import com.gravity.billeasy.ui_layer.app_screens.base_screens.sales.AddSaleBottomSheet
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.ProductAddOrEditBottomSheet
import com.gravity.billeasy.ui_layer.navigationsetup.AppNavigationControllerImpl
import com.gravity.billeasy.ui_layer.navigationsetup.BillEasyScreens
import com.gravity.billeasy.ui_layer.navigationsetup.NavigationSetup
import com.gravity.billeasy.ui_layer.viewmodel.ProductsViewModel
import com.gravity.billeasy.ui_layer.viewmodel.BillViewModel
import com.gravity.billeasy.ui_layer.viewmodel.ShopViewModel

inline val appColorInt get() = R.color.orange_light
inline val appColor @Composable get() = Color(LocalContext.current.resources.getColor(R.color.orange_light))

class MainActivity : ComponentActivity() {
    lateinit var productsViewModel: ProductsViewModel
        private set
    lateinit var shopViewModel: ShopViewModel
        private set
    lateinit var billViewModel: BillViewModel
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        initViewModels(this)
        setContent {
            val context = LocalContext.current
            val window = (context as Activity).window
            // Todo status bar color is not changing when dark mode or light mode changes.
            //  When app start in dark mode the status bar color is black. And when app is alive
            //  and mode changes to light the status bar color is not changing
            window.statusBarColor = resources.getColor(R.color.white)
            val navHostController: NavHostController = rememberNavController()
            val appNavigationImpl = AppNavigationControllerImpl(navHostController)
            val isNeedToShowAddProductBottomSheet = remember { mutableStateOf(false) }
            val isNeedToShowAddBillBottomSheet = remember { mutableStateOf(false) }
            val currentRoot = appNavigationImpl.getCurrentRoute()
            val scrollState = rememberScrollAwareState()
            BillEasyTheme {
                Scaffold(modifier = Modifier.nestedScroll(scrollState.nestedScrollConnection), bottomBar = {
                    AnimatedVisibility(
                        visible = shopViewModel.isNeedToShowCreateShopScreen.value.not(), enter = slideInVertically(
                            initialOffsetY = { it }, animationSpec = tween(durationMillis = 150)
                        ), exit = slideOutVertically(
                            targetOffsetY = { it }, animationSpec = tween(durationMillis = 150)
                        )
                    ) {
                        appNavigationImpl.getCurrentRoute()?.let {
                            BottomNavigationBar(
                                window = window,
                                currentRoot = it,
                                scrollState = scrollState,
                                navigateToHomeScreen = { appNavigationImpl.navigateToHomeScreen() },
                                navigateToMyProductsScreen = { appNavigationImpl.navigateToMyProducts() },
                                navigateToBillScreen = { appNavigationImpl.navigateToSales() })
                        }
                    }
                }, floatingActionButton = {
                    if(shopViewModel.isNeedToShowCreateShopScreen.value.not()) {
                        AnimatedVisibility(
                            visible = scrollState.isNeedToShowFab.value, enter = slideInHorizontally(
                                initialOffsetX = { it }, animationSpec = tween(durationMillis = 150)
                            ), exit = slideOutHorizontally(
                                targetOffsetX = { it }, animationSpec = tween(durationMillis = 150)
                            )
                        ){
                            AddProductAndSaleFab(
                                if(currentRoot in setOf(
                                        BillEasyScreens.HOME.name,
                                        BillEasyScreens.MY_PRODUCTS.name
                                    )) "Add Product"
                                else "Add Sale"
                            ) {
                                currentRoot?.let {
                                    when(it) {
                                        BillEasyScreens.HOME.name,
                                        BillEasyScreens.MY_PRODUCTS.name -> {
                                            isNeedToShowAddProductBottomSheet.value = true
                                        }
                                        else -> isNeedToShowAddBillBottomSheet.value = true
                                    }
                                }
                            }
                        }
                    }
                }) { innerPadding ->
                    val navigationSetup = remember {
                        NavigationSetup(navHostController, appNavigationImpl)
                    }
                    navigationSetup.SetupNavigation(
                        innerPadding = innerPadding,
                        productsViewModel,
                        shopViewModel,
                        if(shopViewModel.isNeedToShowCreateShopScreen.value)
                            BillEasyScreens.CREATE_SHOP.name else BillEasyScreens.HOME.name
                    )
                    if(isNeedToShowAddProductBottomSheet.value) {
                        ProductAddOrEditBottomSheet(
                            isForAdd = true,
                            productsViewModel = productsViewModel,
                            product = null
                        ) { isNeedToShowAddProductBottomSheet.value = false }
                    } else if(isNeedToShowAddBillBottomSheet.value) {
                        AddSaleBottomSheet(
                            billViewModel = billViewModel
                        ) { isNeedToShowAddBillBottomSheet.value = false }
                    }
                }
            }
        }
    }

    fun initViewModels(context: Context) {
        val database = DatabaseInstance.getDatabase(context)
        val productDao = database.productDao()
        val productRepository = ProductRepository(productDao)
        if( !::shopViewModel.isInitialized ) {
            val shopDao = database.shopDao()
            val shopRepository = ShopRepository(shopDao)
            val shopUseCase = ShopUseCase(shopRepository)
            shopViewModel = ShopViewModel(shopUseCase, context.appPreferenceDataStore)
        }
        if( !::productsViewModel.isInitialized ) {
            productsViewModel = ProductsViewModel(productRepository)
        }
        if( !::billViewModel.isInitialized ) {
            val billDao = database.billDao()
            val billRepo = BillRepository(billDao)
            billViewModel = BillViewModel(billRepo, productRepository)
        }
    }
}

@Composable
fun rememberScrollAwareState() = remember { ScrollState() }

@Composable
fun ShowProductsBottomSheet (
    isNeedToShowAddProductBottomSheet: MutableState<Boolean>,
    productsViewModel: ProductsViewModel,
    isForAdd: Boolean,
    product: Product?
) {
    ProductAddOrEditBottomSheet(
        isForAdd = isForAdd,
        productsViewModel = productsViewModel,
        product = product
    ) { isNeedToShowAddProductBottomSheet.value = false }
}

@Composable
fun AddProductAndSaleFab(
    fabText: String,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = { onClick() }, containerColor = appColor,
        icon = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "add product or add sale",
                tint = Color.Black
            )
        },
        text = {
            Text(
                text = fabText
            )
        }
    )
}

@Composable
fun BottomNavigationBar(
    window: Window,
    currentRoot: String,
    scrollState: ScrollState,
    navigateToHomeScreen: () -> Unit,
    navigateToMyProductsScreen: () -> Unit,
    navigateToBillScreen: () -> Unit
) {
    val context = LocalContext.current
    window.navigationBarColor = context.resources.getColor(appColorInt)
    val topLevelRoutes = listOf(
        BottomNavigationScreens(
            name = BillEasyScreens.HOME.name,
            icon = R.drawable.home,
            contentDescription = "Home",
            "Home"
        ), BottomNavigationScreens(
            name = BillEasyScreens.MY_PRODUCTS.name,
            icon = R.drawable.products,
            "My products",
            "My Products"
        ), BottomNavigationScreens(
            name = BillEasyScreens.BILLS.name,
            icon = R.drawable.invoice,
            contentDescription = "Sales",
            label = "Sales"
        )
    )
    NavigationBar(containerColor = appColor) {
        topLevelRoutes.forEach {
            BottomNavigationItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                selected = currentRoot == it.name,
                icon = {
                    Icon(
                        painter = painterResource(it.icon),
                        contentDescription = it.contentDescription,
                        modifier = Modifier.size(25.dp),
                        tint = Color.Black
                    )
                },
                label = {
                    Text(
                        text = it.label,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                },
                onClick = {
                    if(currentRoot != it.name) {
                        when (it.name) {
                            BillEasyScreens.HOME.name -> {
                                scrollState.resetScrollState()
                                navigateToHomeScreen()
                            }

                            BillEasyScreens.MY_PRODUCTS.name -> {
                                scrollState.resetScrollState()
                                navigateToMyProductsScreen()
                            }

                            BillEasyScreens.BILLS.name -> {
                                scrollState.resetScrollState()
                                navigateToBillScreen()
                            }
                        }
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}

data class BottomNavigationScreens(
    val name: String, val icon: Int, val contentDescription: String, val label: String
)