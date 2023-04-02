package com.example.ftkshoppinglist

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {

    LogInForm(navController)

}

@Composable
fun LogInForm(navController: NavController) {
    var authViewModel: FirebaseAuthViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome!")
        OutlinedTextField(
            value = authViewModel.emailInput,
            onValueChange = { authViewModel.emailInput = it },
            label = { Text(text = "E-mail") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = authViewModel.pwdInput,
            onValueChange = { authViewModel.pwdInput = it },
            label = { Text(text = "Password") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(
            onClick = { handleLogin(navController, authViewModel) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Log In")
        }
    }
}

private fun handleLogin(navController: NavController, authViewModel: FirebaseAuthViewModel) {
    authViewModel.signInUser()
    navController.navigate("Profile")
}