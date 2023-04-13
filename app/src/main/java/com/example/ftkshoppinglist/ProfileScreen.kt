package com.example.ftkshoppinglist

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController, authViewModel: FirebaseAuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "User ID: " + authViewModel.user.value?.uid.toString())
        if (authViewModel.checkAdmin()) {
            AddProductButton(navController)
            UpdateProductButton(navController = navController)
        }
        Spacer(
            modifier = Modifier
                .height(32.dp)
        )
        if (!authViewModel.checkUser()) LogInAndRegisterButtons(navController)
        else LogOutButton(navController = navController, authViewModel = authViewModel)
    }

}

@Composable
fun AddProductButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("AddProduct") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(40.dp)
    ) {
        Text(text = "Add Product")
    }
}

@Composable
fun UpdateProductButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("UpdateProduct") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(40.dp)
    ) {
        Text(text = "Update Product")
    }
}

@Composable
fun LogInAndRegisterButtons(navController: NavController) {
    Button(
        onClick = { navController.navigate("SignUp") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(40.dp)
    ) {
        Text(text = "Create an account")
    }
    Button(
        onClick = { navController.navigate("Login") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(40.dp)
    ) {
        Text(text = "Login")
    }

}

@Composable
fun LogOutButton(navController: NavController, authViewModel: FirebaseAuthViewModel) {
    Button(onClick = {
        authViewModel.logOut()
        navController.navigate("Home")
    }) {
        Text(text = "Log Out")
    }
}