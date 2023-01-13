package com.example.notesapp.presentation.notes.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.notesapp.domain.Screen

@Composable
fun Fab(
        navController: NavController
) {
    FloatingActionButton(onClick = { navController.navigate(Screen.CreateNoteScreen.route) }) {
        Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "New Note"
        )
    }

}

//@Preview
//@Composable
//fun fabPreviw() {
//    Fab()
//}