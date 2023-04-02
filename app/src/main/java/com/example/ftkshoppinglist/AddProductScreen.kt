package com.example.ftkshoppinglist

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(8.dp)

    ) {
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


        if (storageViewModel.productExists)
            Text(text = "Product exists!")

        Button(onClick = { storageViewModel.addProduct() }) {
            Text(text = "Add Product")
        }
    }
}

