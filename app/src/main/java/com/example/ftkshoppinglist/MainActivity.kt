package com.example.ftkshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ftkshoppinglist.ui.theme.FTKShoppingListTheme

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
                    WelcomeScreen()
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    Scaffold(
        topBar = { MyTopBar()},
        content={
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SelectionButtons()
            }

                },
        bottomBar = { BottomAppBar{ Text(text = "FTK corporation")}}
    )

}
@Composable
fun MyTopBar(title:String, navController: NavController){
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title={Text(title)},
        actions={
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded=false }) {
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
fun SelectionButtons(){
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(60.dp)

        ) {
            Text(text = "Create your Shopping List!")
        }
    }
}