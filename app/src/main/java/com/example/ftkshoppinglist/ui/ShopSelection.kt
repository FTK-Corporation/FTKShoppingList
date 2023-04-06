package com.example.ftkshoppinglist.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ShopSelectionScreen(navController: NavController) {
    SelectShop(navController)
}

@Composable
fun SelectShop(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(60.dp)
        ) {
            Text(text = "Click here to see the shops")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DropdownMenuItem(onClick = { navController.navigate("Shoppinglist") }) {
                Text("Choose shop1")
            }
            DropdownMenuItem(onClick = { navController.navigate("Shoppinglist") }) {
                Text("Choose shop2")
            }
        }
    }
}
