package com.example.ftkshoppinglist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FilterLogic(productsViewModel: ProductsViewModel = viewModel()) {
    LaunchedEffect(null) {
        productsViewModel.fetchProducts()
    }

}