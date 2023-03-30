package com.example.ftkshoppinglist

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = { MyTopBar("Profile", navController) },
        content = {
            Text("This is the profile screen")
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
        },
        bottomBar = { BottomAppBar { Text(text = "FTK corporation") } }
    )
}