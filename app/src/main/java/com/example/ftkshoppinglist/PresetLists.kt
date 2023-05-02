package com.example.ftkshoppinglist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.ftkshoppinglist.FirebaseAuthViewModel

@Composable
fun PresetScreen(authViewModel: FirebaseAuthViewModel) {
    LaunchedEffect(Unit) {
        authViewModel.fetchPresets()
    }

    Column() {
        LazyColumn() {
            items(authViewModel.presets) { preset ->
                Text(text = preset.name)
            }
        }
    }
}

