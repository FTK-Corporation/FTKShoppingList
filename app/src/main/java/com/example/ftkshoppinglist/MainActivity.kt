package com.example.ftkshoppinglist

import android.content.ClipData.Item
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.ftkshoppinglist.ui.MyTopBar
import com.example.ftkshoppinglist.ui.navCon
import com.example.ftkshoppinglist.ui.theme.FTKShoppingListTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FTKShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    // Subscribe to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // State of topBar, set state to false, if current page route is "car_details"
    var topBarTitle by rememberSaveable { (mutableStateOf("")) }
    var topBarHideIcon by rememberSaveable { (mutableStateOf(true)) }

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        "Home" -> {
            // Show BottomBar and TopBar
            topBarTitle = "ShoppingList App"
            topBarHideIcon = true
        }
        "Preset" -> {
            topBarTitle = "Presets"
            topBarHideIcon = false
        }
        "Profile" -> {
            topBarTitle = "Profile"
            topBarHideIcon = false
        }
        "Shoppinglist" -> {
            topBarTitle = "Create a list"
            topBarHideIcon = false
        }
        "Readylist" -> {
            topBarTitle = "Your current list"
            topBarHideIcon = false
        }
        "ShopSelect" -> {
            topBarTitle = "Choose the shop"
            topBarHideIcon = false
        }
        "Login" -> {
            topBarTitle = "Log In"
            topBarHideIcon = false
        }
        "SignUp" -> {
            topBarTitle = "Sign Up"
            topBarHideIcon = false
        }
        "AddProduct" -> {
            topBarTitle = "Add a Product to Database"
            topBarHideIcon = false
        }
        else -> {
            topBarTitle = "Default"
            topBarHideIcon = false
        }
    }
    Scaffold(
        topBar = { MyTopBar(topBarTitle, navController, topBarHideIcon) },
        content = { navCon(navController) },
        bottomBar = { BottomAppBar { Text(text = "FTK corporation") } }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingListScreen(navController: NavController, productsViewModel: ProductsViewModel = viewModel()) {

    LaunchedEffect(Unit){
        productsViewModel.fetchProducts()
    }
    var text by remember { mutableStateOf(TextFieldValue(""))
    }
    var expanded by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text(text = "Search for items...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding( 20.dp)
        ) {
            Button(
                onClick = { expanded=!expanded },
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
            ) {
                Text(text = "Filter")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                DropdownMenuItem(onClick = { }) {
                    Text("Food and drinks")
                }
                DropdownMenuItem(onClick = { }) {
                    Text("Cooking utensils")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text("Home repair")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text("Small electronics")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text("Pet equipment")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text("Toys")
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text("Clothing")
                }
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
            ) {
                Text(text = "Search")
            }
            Button(
                onClick = { navController.navigate("Readylist") },
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
            ) {
                Text(text = "Finish")
            }

        }
        var popupControl by remember { mutableStateOf<ProductData?>(null) }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp,
            ),
            content = {
                items(productsViewModel.products){ product ->
                    TextButton(
                        onClick = { popupControl = product },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        AsyncImage(
                            model = product.imageUri,
                            contentDescription = "images",
                            modifier = Modifier
                                .height(150.dp)
                                .width(150.dp)
                                .padding(4.dp)
                        )
                        if (popupControl == product) {
                            Popup(
                                popupPositionProvider = WindowCenterOffsetPositionProvider(),
                                onDismissRequest = { popupControl = null },
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
                                                model = product.imageUri,
                                                contentDescription = "Image")
                                            Text(text = product.name)
                                            Text(text = product.description)
                                            Spacer(modifier = Modifier.height(32.dp))
                                            Button(
                                                onClick = { popupControl = null },
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(text = "Return to selection")
                                            }
                                            Button(
                                                onClick = { /*TODO*/}
                                            ){
                                                Text(text = "Add to list!")
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
}


@Composable
fun FilterLogic(productsViewModel: ProductsViewModel = viewModel()) {
    LaunchedEffect(null) {
        productsViewModel.fetchProducts()
    }
}

@Composable
fun SelectionButtons(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate("ShopSelect") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(60.dp)

        ) {
            Text(text = "Create your Shopping List!")
        }
    }
}


class WindowCenterOffsetPositionProvider(
    private val x: Int = 0,
    private val y: Int = 0)
    : PopupPositionProvider {
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
