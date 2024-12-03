package com.gravity.billeasy

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.app.Activity
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
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
import com.gravity.billeasy.ui_layer.navigationsetup.AppNavigationControllerImpl
import com.gravity.billeasy.ui_layer.navigationsetup.BillEasyScreens
import com.gravity.billeasy.ui_layer.navigationsetup.NavigationSetup
import com.gravity.billeasy.ui.theme.BillEasyTheme

inline val appColorInt get() = R.color.orange_light
inline val appColor @Composable
get() = Color(LocalContext.current.resources.getColor(R.color.orange_light))

class MainActivity : ComponentActivity() {

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

            BillEasyTheme {
                Scaffold(bottomBar = {
                    if (appNavigationImpl.getCurrentRoute() != BillEasyScreens.ADD_PRODUCT.name) BottomNavigationBar(
                        appNavigationImpl, window
                    )
                    else window.navigationBarColor = context.resources.getColor(R.color.white)
                }, floatingActionButton = {
                    when (appNavigationImpl.getCurrentRoute()) {
                        BillEasyScreens.HOME.name -> AddProductFab(appNavigationImpl)
                        BillEasyScreens.ALL_PRODUCTS.name -> AddProductFab(appNavigationImpl)
                        BillEasyScreens.BILLS.name -> AddProductFab(appNavigationImpl)
                    }
                }) { innerPadding ->
                    val navigationSetup = NavigationSetup(navHostController, appNavigationImpl)
                    navigationSetup.SetupNavigation(innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun AddProductFab(appNavigationControllerImpl: AppNavigationControllerImpl) {
    FloatingActionButton(
        onClick = { appNavigationControllerImpl.navigateToAddProductScreen() },
        containerColor = appColor
    ) {
        Icon(Icons.Filled.Add, contentDescription = "add product")
    }
}

@Stable
@Composable
fun BottomNavigationBar(
    appNavigationControllerImpl: AppNavigationControllerImpl, window: Window
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
            name = BillEasyScreens.ALL_PRODUCTS.name,
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
                selected = appNavigationControllerImpl.getCurrentRoute() == it.name,
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
                            appNavigationControllerImpl.navigateToHomeScreen()
                        }

                        BillEasyScreens.ALL_PRODUCTS.name -> {
                            appNavigationControllerImpl.navigateToMyProducts()
                        }

                        BillEasyScreens.BILLS.name -> {
                            appNavigationControllerImpl.navigateToSales()
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