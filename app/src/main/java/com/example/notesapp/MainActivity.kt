package com.example.notesapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.domain.Screen
import com.example.notesapp.presentation.notes.NoteScreen
import com.example.notesapp.presentation.notes.create.CreateScreen
import com.example.notesapp.presentation.notes.edit.EditScreen
import com.example.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.NotesScreen.route){
                        
                        // MAIN SCREEN
                        composable(
                            route = Screen.MainScreen.route
                        ){
                            Greeting("Android")
                        }
                        
                        // NOTES SCREEN
                        composable(
                            route = Screen.NotesScreen.route
                        ){
                            NoteScreen(navController = navController)
                        }

                        // CREATE NOTE SCREEN
                        composable(
                                route = Screen.CreateNoteScreen.route
                        ){
                            it.arguments
                            CreateScreen(navController = navController)
                        }
                        
                        // EDIT NOTE SCREEN
                        composable(
                                route = Screen.EditNoteScreen.route,
                                arguments = listOf(
                                        navArgument("noteId"){
                                            type = NavType.IntType
                                            defaultValue = -1
                                            nullable = false
                                        }
                                )// ListOf
                        ){
                            EditScreen(
                                    navController = navController,
                                    noteId = it.arguments?.getInt("noteId")!!
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesAppTheme {
        Greeting("Android")
    }
}