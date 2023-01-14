package com.example.notesapp.presentation.notes

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesapp.data.remote.dto.NotesResponse
import com.example.notesapp.domain.Screen
import com.example.notesapp.domain.models.Note
import com.example.notesapp.presentation.composables.SimpleNote
import com.example.notesapp.presentation.notes.composables.Fab

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel<NoteViewModel>()
) {
    var notes = viewModel.notesStateFlow.collectAsState()
    
    Scaffold(
            floatingActionButton = {
                Fab(navController)
            }
    ) {
        Column(
                modifier = Modifier.fillMaxSize()
        ){
            Text(text = "Notes", style = MaterialTheme.typography.h1,)
            LazyColumn{
                items(notes.value){ note: Note ->
                    SimpleNote(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                            ,
                            title = note.title,
                            content = note.content,
                            noteColor = note.color,//LocalNoteColor.current.BlueColor//Color(0)
                            // note
                            // .color//LocalNoteColor.current.BlueColor
                            time = note.date,
                            onDelete = {
                                viewModel.deleteNote(note.id!!)
                            },
                            onClick = {
                                navController.navigate(Screen.EditNoteScreen.withArg(note))
                            }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

    }
    
}