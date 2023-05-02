package com.example.ftkshoppinglist.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ftkshoppinglist.*

@Composable
fun navCon(navController: NavHostController) {
    var authViewModel: FirebaseAuthViewModel = viewModel()
    var productsViewModel: ProductsViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable(route = "Home") {
            WelcomeScreen(navController)
        }
        composable(route = "Preset") {
            PresetScreen(authViewModel)
        }
        composable(route = "Profile") {
            ProfileScreen(navController, authViewModel)
        }
        composable(route = "Shoppinglist") {
            ShoppingListScreen(navController, productsViewModel)
        }
        composable(route = "Readylist") {
            ReadyListScreen(navController, productsViewModel, authViewModel)
        }
        composable(route = "ShopSelect") {
            ShopSelectionScreen(navController)
        }
        composable(route = "Login") {
            LoginScreen(navController, authViewModel)
        }
        composable(route = "SignUp") {
            SignUpScreen(navController, authViewModel)
        }
        composable(route = "AddProduct") {
            AddProductScreen()
        }
        composable(route = "UpdateProduct") {
            UpdateProductScreen()
        }
    }
}