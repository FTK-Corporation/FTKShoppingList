package com.example.ftkshoppinglist

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ftkshoppinglist.elements.CenteredProgressCircle

@Composable
fun LoginScreen(navController: NavController, authViewModel: FirebaseAuthViewModel) {

    LogInForm(navController, authViewModel)

}

@Composable
fun LogInForm(navController: NavController, authViewModel: FirebaseAuthViewModel) {
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
            onClick = { authViewModel.signInUser() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Log In")
        }
    }
    when(authViewModel.logInState) {
        "LOADING" -> {
            CenteredProgressCircle()
        }
        "SUCCESS" -> {
            navController.navigateUp()
            authViewModel.logInState = ""
        }
        "FAILURE" -> {
            Toast.makeText(LocalContext.current, authViewModel.errorMessage, Toast.LENGTH_LONG).show()
            authViewModel.logInState = ""
        }
    }
}