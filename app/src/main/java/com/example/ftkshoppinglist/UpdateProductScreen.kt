package com.example.ftkshoppinglist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UpdateProductScreen(productsViewModel: ProductsViewModel = viewModel()) {
    var searchParam by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        productsViewModel.fetchProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = searchParam,
            onValueChange = { searchParam = it.lowercase() },
            label = { Text(text = "Search Product") },
            modifier = Modifier.fillMaxWidth()
        )
        if (searchParam == "") {
            LazyColumn(){
                items(productsViewModel.products) { product ->
                    ProductCard(name = product.name, id = product.id)
                }
            }
        }
        if (searchParam.all { char -> char.isDigit() }) {
            LazyColumn() {
                items(productsViewModel.products) { product ->
                    if (product.id.lowercase().contains(searchParam))
                        ProductCard(name = product.name, id = product.id)
                }
            }
        } else {
            LazyColumn() {
                items(productsViewModel.products) { product ->
                    if (product.name.lowercase().contains(searchParam))
                        ProductCard(name = product.name, id = product.id)
                }
            }
        }
    }

}

@Composable
fun ProductCard(name: String, id: String) {
    Card(
        elevation = 10.dp, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(text = "$name    $id", modifier = Modifier.padding(10.dp))
    }


}