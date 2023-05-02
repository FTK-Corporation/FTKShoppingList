package com.example.ftkshoppinglist.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ftkshoppinglist.FirebaseAuthViewModel
import com.example.ftkshoppinglist.ProductData
import com.example.ftkshoppinglist.ProductsViewModel

@Composable
fun ReadyListScreen(
    navController: NavController,
    productsViewModel: ProductsViewModel,
    authViewModel: FirebaseAuthViewModel
) {

    Column(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            value = authViewModel.presetNameInput,
            onValueChange = { authViewModel.presetNameInput = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Shopping List Name") }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { productsViewModel.isFinishButtonClicked = true },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(text = "Finish Shopping!")
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(productsViewModel.list) { list ->
                    TextButton(
                        onClick = { productsViewModel.popupControl = list },
                        modifier = Modifier
                            .padding(10.dp)
                            .background(if (productsViewModel.isButtonClicked) MaterialTheme.colors.primary else MaterialTheme.colors.secondary)
                    ) {
                        AsyncImage(
                            model = list.imageUri,
                            contentDescription = "Image",
                            modifier = Modifier
                                .height(150.dp)
                                .width(150.dp)
                                .padding(4.dp)
                        )

                        if (productsViewModel.popupControl == list) {
                            Popup(
                                popupPositionProvider = WindowCenterOffsetPositionProvider(),
                                onDismissRequest = { productsViewModel.popupControl = null },
                            ) {
                                Surface(
                                    border = BorderStroke(3.dp, MaterialTheme.colors.primary),
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colors.secondary,
                                ) {
                                    Box(
                                        modifier = Modifier.padding(100.dp),
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,

                                            ) {
                                            AsyncImage(
                                                model = list.imageUri,
                                                contentDescription = "Image"
                                            )
                                            Text(text = list.name)
                                            Text(text = list.description)
                                            Spacer(modifier = Modifier.height(32.dp))
                                            Button(
                                                onClick = { productsViewModel.popupControl = null },
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(text = "Return to selection")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }




    if (productsViewModel.isFinishButtonClicked) {
        Popup(
            popupPositionProvider = WindowCenterOffsetPositionProvider(),
            onDismissRequest = { productsViewModel.isFinishButtonClicked = false },
        ) {
            Surface(
                border = BorderStroke(3.dp, MaterialTheme.colors.primary),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colors.surface,
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Button(
                        onClick = { productsViewModel.isFinishButtonClicked = false }
                    ) {
                        Text(text = "Return to list")
                    }
                    Text(
                        text = "Would you like to save this list into the presets?",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )

                    if (authViewModel.presetNameInput == "Shopping List") {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Remember you can change the list name!",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                productsViewModel.list = mutableListOf<ProductData>()
                                productsViewModel.isFinishButtonClicked = false
                            }
                        ) {
                            Text(text = "Don't save it")
                        }
                        Button(
                            onClick = {
                                if (authViewModel.checkUser()) {
                                    authViewModel.savePreset(productsViewModel.list)
                                    productsViewModel.list = mutableListOf<ProductData>()
                                    authViewModel.presetNameInput = ""
                                    productsViewModel.isFinishButtonClicked = false
                                } else {
                                    navController.navigate("Login")
                                }
                            }
                        ) {
                            Text(text = "Save it!")
                        }
                    }
                }

            }
        }
    }
//    if (authViewModel.presetSavePopupControl) PresetSavePopup(
//        authViewModel = authViewModel,
//        productsViewModel = productsViewModel
//    )
}

//@Composable
//fun PresetSavePopup(authViewModel: FirebaseAuthViewModel, productsViewModel: ProductsViewModel) {
//    Popup(
//        popupPositionProvider = WindowCenterOffsetPositionProvider(),
//        onDismissRequest = { authViewModel.presetSavePopupControl = false },
//    ) {
//        Surface(
//            border = BorderStroke(3.dp, MaterialTheme.colors.primary),
//            shape = RoundedCornerShape(8.dp),
//            color = MaterialTheme.colors.secondary,
//            modifier = Modifier.padding(18.dp)
//        ) {
//            Column {
//                OutlinedTextField(
//                    value = authViewModel.presetNameInput,
//                    onValueChange = { authViewModel.presetNameInput = it },
//                    label = { Text(text = "Preset Name") })
//                Button(onClick = {
//
//                    authViewModel.presetSavePopupControl = false
//                }) {
//                    Text(text = "Save")
//                }
//            }
//        }
//    }
//
//}

class WindowCenterOffsetPositionProvider(
    private val x: Int = 0,
    private val y: Int = 0
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        return IntOffset(
            (windowSize.width - popupContentSize.width) / 2 + x,
            (windowSize.height - popupContentSize.height) / 2 + y
        )
    }
}
