package com.example.ftkshoppinglist

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Select Image")
        }

        OutlinedTextField(
            value = storageViewModel.idInput,
            onValueChange = { storageViewModel.idInput = it })
        OutlinedTextField(
            value = storageViewModel.name,
            onValueChange = { storageViewModel.changeName(it) })
        OutlinedTextField(
            value = storageViewModel.description,
            onValueChange = { storageViewModel.changeDescription(it) })
        OutlinedTextField(
            value = storageViewModel.aisle,
            onValueChange = { storageViewModel.changeAisle(it) })
        OutlinedTextField(
            value = storageViewModel.category,
            onValueChange = { storageViewModel.changeCategory(it) })
        OutlinedTextField(
            value = storageViewModel.subCategory,
            onValueChange = { storageViewModel.changeSubCategory(it) })

        if (storageViewModel.productExists) 
            Text(text = "Product exists!")
        
        Button(onClick = { storageViewModel.addProduct() }) {
            Text(text = "Add Product")
        }
    }
}

