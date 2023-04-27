package com.example.ftkshoppinglist

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UpdateProductScreen(
    productsViewModel: ProductsViewModel = viewModel(),
    storageViewModel: StorageViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        productsViewModel.fetchProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (!storageViewModel.popupControl) SearchProductScreen(
            productsViewModel = productsViewModel,
            storageViewModel = storageViewModel
        )
        else UpdateProductPopup(
            product = storageViewModel.selectedProduct!!,
            storageViewModel = storageViewModel
        )
    }
}

@Composable
fun SearchProductScreen(productsViewModel: ProductsViewModel, storageViewModel: StorageViewModel) {
    var searchParam by remember { mutableStateOf("") }

    TextField(
        value = searchParam,
        onValueChange = { searchParam = it.lowercase() },
        label = { Text(text = "Search Product") },
        modifier = Modifier.fillMaxWidth()
    )
    LazyColumn {
        items(productsViewModel.products) { product ->
            if (product.id.lowercase().contains(searchParam) || product.name.lowercase()
                    .contains(searchParam) || product.description.lowercase()
                    .contains(searchParam)
            ) ProductCard(product, storageViewModel)
        }
    }
}

@Composable
fun ProductCard(product: ProductData, storageViewModel: StorageViewModel) {
    Card(
        elevation = 10.dp, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                storageViewModel.popupControl = true
                storageViewModel.selectedProduct = product
            }
    ) {
        Text(text = "${product.name}    ${product.id}", modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun UpdateProductPopup(product: ProductData, storageViewModel: StorageViewModel) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        storageViewModel.imageUri = it
    }

    LaunchedEffect(storageViewModel.popupControl) {
        storageViewModel.idInput = product.id
        storageViewModel.changeName(product.name)
        storageViewModel.changeDescription(product.description)
        storageViewModel.imageUri
        product.aisle?.let { storageViewModel.changeAisle(it) }
        product.category?.let { storageViewModel.changeCategory(it) }
        product.subCategory?.let { storageViewModel.changeSubCategory(it) }
        product.price?.let { storageViewModel.priceInput = it.toString() }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { storageViewModel.popupControl = false },

                ) {
                Text(text = "Go Back")
            }
        }
        OutlinedTextField(
            value = storageViewModel.idInput,
            onValueChange = { storageViewModel.idInput = it },
            label = { Text(text = "ID") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = storageViewModel.name,
            onValueChange = { storageViewModel.changeName(it) },
            label = { Text(text = "Product Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = storageViewModel.description,
            onValueChange = { storageViewModel.changeDescription(it) },
            label = { Text(text = "Product Description") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = storageViewModel.aisle,
            onValueChange = { storageViewModel.changeAisle(it) },
            label = { Text(text = "Product Aisle") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = storageViewModel.category,
            onValueChange = { storageViewModel.changeCategory(it) },
            label = { Text(text = "Category") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = storageViewModel.subCategory,
            onValueChange = { storageViewModel.changeSubCategory(it) },
            label = { Text(text = "Sub Category") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = storageViewModel.priceInput,
            onValueChange = { storageViewModel.priceInput = it },
            label = { Text(text = "Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { launcher.launch("image/*") },

                ) {
                Text(text = "Select Image")
            }
            if (storageViewModel.imageUri != null) Text(text = storageViewModel.imageUri.toString())
        }
        Button(onClick = {
            storageViewModel.updateProduct()
        }) {
            Text(text = "Update Product")
        }
    }


}