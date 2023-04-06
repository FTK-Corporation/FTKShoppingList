package com.example.ftkshoppinglist.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ftkshoppinglist.*

@Composable
fun navCon(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable(route = "Home") {
            WelcomeScreen(navController)
        }
        composable(route = "Preset") {
            PresetScreen(navController)
        }
        composable(route = "Profile") {
            ProfileScreen(navController)
        }
        composable(route = "Shoppinglist") {
            ShoppingListScreen(navController)
        }
        composable(route = "Readylist") {
            ReadyListScreen(navController)
        }
        composable(route = "ShopSelect") {
            ShopSelectionScreen(navController)
        }
        composable(route = "Login") {
            LoginScreen(navController)
        }
        composable(route = "SignUp") {
            SignUpScreen(navController)
        }
    }
}