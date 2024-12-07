package com.gravity.billeasy

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.app.Activity
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui.theme.BillEasyTheme
import com.gravity.billeasy.ui_layer.app_screens.ProductAddOrEditBottomSheet
import com.gravity.billeasy.ui_layer.navigationsetup.AppNavigationControllerImpl
import com.gravity.billeasy.ui_layer.navigationsetup.BillEasyScreens
import com.gravity.billeasy.ui_layer.navigationsetup.NavigationSetup
import com.gravity.billeasy.ui_layer.viewmodel.ProductViewModel

inline val appColorInt get() = R.color.orange_light
inline val appColor @Composable get() = Color(LocalContext.current.resources.getColor(R.color.orange_light))

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val window = (context as Activity).window
            window.statusBarColor = context.resources.getColor(R.color.white)
            val navHostController: NavHostController = rememberNavController()
            val appNavigationImpl = AppNavigationControllerImpl(navHostController)
            val showBottomBar = remember { mutableStateOf(true) }
            val isNeedToShowBottomSheet = remember { mutableStateOf(false) }
            BillEasyTheme {
                Scaffold(bottomBar = {
                    AnimatedVisibility(
                        visible = showBottomBar.value, enter = slideInVertically(
                            initialOffsetY = { it }, animationSpec = tween(durationMillis = 150)
                        ), exit = slideOutVertically(
                            targetOffsetY = { it }, animationSpec = tween(durationMillis = 150)
                        )
                    ) {
                        appNavigationImpl.getCurrentRoute()?.let {
                            BottomNavigationBar(
                                window = window,
                                currentRoot = it,
                                navigateToHomeScreen = { appNavigationImpl.navigateToHomeScreen() },
                                navigateToMyProductsScreen = { appNavigationImpl.navigateToMyProducts() },
                                navigateToBillScreen = { appNavigationImpl.navigateToSales() })
                        }
                    }
                }, floatingActionButton = {
                    AddProductFab(onClick = {
                        isNeedToShowBottomSheet.value = true
//                        appNavigationImpl.navigateToAddProductScreen(productJson = null)
//                        showBottomBar.value = false
                    })
                }) { innerPadding ->
                    val navigationSetup = NavigationSetup(navHostController, appNavigationImpl)
                    val productViewModel = navigationSetup.initViewModel()
                    navigationSetup.SetupNavigation(innerPadding = innerPadding)
                    ShowOrHideBottomSheet(
                        isNeedToShowBottomSheet = isNeedToShowBottomSheet,
                        isForAdd = true,
                        productViewModel = productViewModel,
                        product = null
                    )
                }
            }
        }
    }
}

@Composable
fun ShowOrHideBottomSheet(
    isNeedToShowBottomSheet: MutableState<Boolean>,
    productViewModel: ProductViewModel,
    isForAdd: Boolean,
    product: Product?,
) {
    if (isNeedToShowBottomSheet.value) {
        ProductAddOrEditBottomSheet(
            isForAdd = isForAdd,
            viewModel = productViewModel,
            product = product
        ) { isNeedToShowBottomSheet.value = false }
    }
}

@Composable
fun AddProductFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() }, containerColor = appColor
    ) {
        Icon(Icons.Filled.Add, contentDescription = "add product")
    }
}

@Stable
@Composable
fun BottomNavigationBar(
    window: Window,
    currentRoot: String,
    navigateToHomeScreen: () -> Unit,
    navigateToMyProductsScreen: () -> Unit,
    navigateToBillScreen: () -> Unit,
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
    BottomNavigation(backgroundColor = appColor) {
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
                        modifier = Modifier.size(25.dp)
                    )
                },
                label = { Text(text = it.label, fontWeight = FontWeight.Medium, fontSize = 12.sp) },
                onClick = {
                    when (it.name) {
                        BillEasyScreens.HOME.name -> {
                            navigateToHomeScreen()
                        }

                        BillEasyScreens.MY_PRODUCTS.name -> {
                            navigateToMyProductsScreen()
                        }

                        BillEasyScreens.BILLS.name -> {
                            navigateToBillScreen()
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