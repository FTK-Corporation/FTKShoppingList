package com.example.ftkshoppinglist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FilterLogic(productsViewModel: ProductsViewModel = viewModel()) {
    LaunchedEffect(null) {
        productsViewModel.fetchProducts()
    }
    
}