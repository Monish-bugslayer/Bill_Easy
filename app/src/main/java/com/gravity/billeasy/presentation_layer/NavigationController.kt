package com.gravity.billeasy.presentation_layer

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gravity.billeasy.Utils.NavigationConstants
import com.gravity.billeasy.loginscreen.LoginScreen

interface NavigationController {

    fun navigateFromMainActivityToLoginScreen()
    fun navigateFromLoginScreenToMainActivity()

//    val navController: NavHostController = rememberNavController()
//    NavHost(navController = navController, startDestination = NavigationConstants.LOGIN_SCREEN) {
//        composable(route = NavigationConstants.LOGIN_SCREEN) {
//            LoginScreen()
//        }
//    }
}