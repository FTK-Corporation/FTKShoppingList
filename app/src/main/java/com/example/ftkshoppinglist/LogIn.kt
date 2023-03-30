package com.example.ftkshoppinglist

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController){
    Scaffold(
        topBar = {MyTopBar("Login",navController)},
        content={
            LogInForm(navController)
        },
        bottomBar = { BottomAppBar{ Text(text = "FTK corporation") } }

    )
}
@Composable
fun LogInForm(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome!")
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label={ Text(text = "Username/E-mail") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label={ Text(text = "Password") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(
            onClick = { navController.navigate("Home") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Log In")
        }
    }
}
