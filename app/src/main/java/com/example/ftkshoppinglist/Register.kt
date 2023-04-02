package com.example.ftkshoppinglist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SignUpScreen(navController: NavController) {
    SignUpForm(navController)
}

@Composable
fun SignUpForm(navController: NavController) {
    var authViewModel: FirebaseAuthViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create an account for even more content!")
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
            value = authViewModel.usernameInput,
            onValueChange = { authViewModel.usernameInput = it },
            label = { Text(text = "Username") },
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
//        OutlinedTextField(
//            value = "",
//            onValueChange = {},
//            label = { Text(text = "Repeat Password") },
//            singleLine = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        )
        Button(
            onClick = { handleRegister(navController, authViewModel) },
            enabled = (authViewModel.emailInput.isNotEmpty() && authViewModel.pwdInput.isNotEmpty()),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            Text(text = "Sign Up")
        }
    }
}

private fun handleRegister(navController: NavController, authViewModel: FirebaseAuthViewModel) {
    authViewModel.registerUser()
    navController.navigate("Profile")
}


