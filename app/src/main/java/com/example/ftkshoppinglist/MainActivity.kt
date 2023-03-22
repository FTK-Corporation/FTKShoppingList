package com.example.ftkshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        content={ Text(text = "This is the content")},
        bottomBar = { BottomAppBar{ Text(text = "FTK corporation")}}
    )

}
@Composable
fun MyTopBar(){
    TopAppBar(
        title={Text("Shoppinglist Extraordinaire")},
        navigationIcon={
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Menu,contentDescription = null)
            }
        },
        actions={
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
        }
    )
}