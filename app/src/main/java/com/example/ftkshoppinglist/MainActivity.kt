package com.example.ftkshoppinglist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
fun navCon(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable(route = "Home") {
            WelcomeScreen(navController)
        }
        composable(route = "Preset") {
            PresetScreen(navController)
        }
        composable(route = "Profile") {
            ProfileScreen(navController)
        }
        composable(route = "Shoppinglist") {
            ShoppingListScreen(navController)
        }
        composable(route = "Readylist") {
            ReadyListScreen(navController)
        }
        composable(route = "ShopSelect") {
            ShopSelectionScreen(navController)
        }
        composable(route = "Login") {
            LoginScreen(navController)
        }
        composable(route = "SignUp") {
            SignUpScreen(navController)
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

@Composable
fun MyTopBar(title: String, navController: NavController, hideArrow: Boolean? = false) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (hideArrow != true) IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = { navController.navigate("preset") }) {
                    Text("Go to preset lists")
                }
                DropdownMenuItem(onClick = { navController.navigate("profile") }) {
                    Text("Profile")
                }
            }


        }
    )
}

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectionButtons(navController)
    }
}

@Composable
fun ShopSelectionScreen(navController: NavController) {
    SelectShop(navController)
}

@Composable
fun SelectShop(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center,
        ) {
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(60.dp)
        ) {
            Text(text = "Click here to see the shops")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DropdownMenuItem(onClick = { navController.navigate("Shoppinglist") }) {
                Text("Choose shop1")
            }
            DropdownMenuItem(onClick = { navController.navigate("Shoppinglist") }) {
                Text("Choose shop2")
            }
        }
    }
}

@Composable
fun PresetScreen(navController: NavController) {
    Text("This is the presets screen")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingListScreen(navController: NavController) {

    var fireStore = Firebase.firestore;
    val list = (1..10).map { it.toString() }
    var text by remember { mutableStateOf(TextFieldValue(""))
    }
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
                .padding(20.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
            ) {
                Text(text = "Filter")
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp,
            ),
            content = {
                items(list.size) {
                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .height(150.dp)
                            .width(150.dp)
                            .padding(4.dp)
                    ) {
                        Text(text= "bababooe")
                    }

                }
            }
        )

    }
}


@Composable
fun ReadyListScreen(navController: NavController) {
    Column(

    ) {


        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

        }
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