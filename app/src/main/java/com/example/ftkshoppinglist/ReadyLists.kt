package com.example.ftkshoppinglist.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ftkshoppinglist.ProductData
import com.example.ftkshoppinglist.ProductsViewModel

@Composable
fun ReadyListScreen(navController: NavController, productsViewModel: ProductsViewModel) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp),
        content = {
            items(productsViewModel.list){ list ->
                TextButton(
                    onClick = { "" },
                    modifier = Modifier.padding(10.dp)
                ){
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
                            popupPositionProvider = productsViewModel.posProvider,
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
