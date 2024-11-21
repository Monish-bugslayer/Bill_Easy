package com.gravity.billeasy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gravity.billeasy.loginscreens.LoginScreen
import com.gravity.billeasy.presentation_layer.AppNavigationControllerImpl
import com.gravity.billeasy.presentation_layer.NavigationSetup
import com.gravity.billeasy.ui.theme.BillEasyTheme

class MainActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val navHostController: NavHostController = rememberNavController()
            val appNavigationImpl = AppNavigationControllerImpl(navHostController)
            BillEasyTheme {
                Scaffold { innerPadding ->
                    val navigationSetup = NavigationSetup(navHostController, appNavigationImpl)
                    navigationSetup.setupNavgation(innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BillEasyTheme {
        Greeting("Android")
    }
}