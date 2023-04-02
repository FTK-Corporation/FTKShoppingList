package com.example.ftkshoppinglist

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddProductScreen() {
    PickImage()
}

@Composable
fun PickImage() {
    var storageViewModel: StorageViewModel = viewModel()

    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        storageViewModel.imageUri = it
    }

    Column() {
        OutlinedTextField(
            value = storageViewModel.idInput,
            onValueChange = { storageViewModel.idInput = it },
            label = { Text(text = "ID") })
        OutlinedTextField(
            value = storageViewModel.name,
            onValueChange = { storageViewModel.changeName(it) },
            label = { Text(text = "Product Name") })
        OutlinedTextField(
            value = storageViewModel.description,
            onValueChange = { storageViewModel.changeDescription(it) },
            label = { Text(text = "Product Description") })
        OutlinedTextField(
            value = storageViewModel.aisle,
            onValueChange = { storageViewModel.changeAisle(it) },
            label = { Text(text = "Product Aisle") })
        OutlinedTextField(
            value = storageViewModel.category,
            onValueChange = { storageViewModel.changeCategory(it) },
            label = { Text(text = "Category") })
        OutlinedTextField(
            value = storageViewModel.subCategory,
            onValueChange = { storageViewModel.changeSubCategory(it) },
            label = { Text(text = "Sub Category") })
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Select Image")
        }

        if (storageViewModel.productExists)
            Text(text = "Product exists!")

        Button(onClick = { storageViewModel.addProduct() }) {
            Text(text = "Add Product")
        }
    }
}

