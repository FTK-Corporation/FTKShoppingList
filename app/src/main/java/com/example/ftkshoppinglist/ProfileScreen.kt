package com.example.ftkshoppinglist

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController, authViewModel: FirebaseAuthViewModel) {
    Column() {
        Text("This is the profile screen")
        Text(text = authViewModel.user.value?.uid.toString())
    }
    if (authViewModel.checkAdmin()) AddProductButton(navController)
    if (!authViewModel.checkUser()) LogInAndRegisterButtons(navController)
}

@Composable
fun AddProductButton(navController: NavController) {
    Button(onClick = { navController.navigate("AddProduct") }) {
        Text(text = "Add Product")
    }
}

@Composable
fun LogInAndRegisterButtons(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
}