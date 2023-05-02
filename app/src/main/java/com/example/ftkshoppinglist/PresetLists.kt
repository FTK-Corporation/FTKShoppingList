package com.example.ftkshoppinglist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ftkshoppinglist.FirebaseAuthViewModel
import com.example.ftkshoppinglist.PresetData
import com.example.ftkshoppinglist.ProductData
import com.example.ftkshoppinglist.StorageViewModel

@Composable
fun PresetScreen(authViewModel: FirebaseAuthViewModel) {
    LaunchedEffect(Unit) {
        authViewModel.fetchPresets()
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        if (!authViewModel.presetPopupControl) {
            PresetListScreen(authViewModel = authViewModel)
        } else {
            SelectedPresetScreen(authViewModel = authViewModel)
        }
    }
}

@Composable
fun PresetListScreen(authViewModel: FirebaseAuthViewModel) {
    LazyColumn() {
        items(authViewModel.presets) { preset ->
            PresetCard(preset = preset, authViewModel = authViewModel)
        }
    }
}

@Composable
fun SelectedPresetScreen(authViewModel: FirebaseAuthViewModel) {
    authViewModel.selectedPreset?.let { selectedPreset ->
        Box(modifier = Modifier.fillMaxHeight()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { authViewModel.presetPopupControl = false },

                        ) {
                        Text(text = "Go Back")
                    }
                }
                Text(text = selectedPreset.name, fontSize = 24.sp)
//                LazyColumn() {
//                    items(selectedPreset.products) {
//                        Text(text = it.name)
//                    }
//                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        top = 16.dp,
                        end = 12.dp,
                        bottom = 16.dp,
                    ),
                    content = {
                        items(selectedPreset.products) { product ->
                            AsyncImage(
                                model = product.imageUri,
                                contentDescription = "Image",
                                modifier = Modifier
                                    .height(150.dp)
                                    .width(150.dp)
                                    .padding(4.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PresetCard(preset: PresetData, authViewModel: FirebaseAuthViewModel) {
    Card(
        elevation = 10.dp, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                authViewModel.presetPopupControl = true
                authViewModel.selectedPreset = preset
            }
    ) {
        Text(text = "${preset.name}    ${preset.products.size}", modifier = Modifier.padding(10.dp))
    }
}

