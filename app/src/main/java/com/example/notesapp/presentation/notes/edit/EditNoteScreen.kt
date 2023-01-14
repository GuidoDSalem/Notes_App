package com.example.notesapp.presentation.notes.edit

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.notesapp.domain.Screen
import com.example.notesapp.presentation.notes.composables.EditableNote
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@Composable
fun EditScreen(
        navController: NavController,
        viewModel: EditNoteViewModel = hiltViewModel<EditNoteViewModel>(),
        noteId: Int
) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val state by viewModel.stateFlow.collectAsState()

    LaunchedEffect(key1 = true){
        Log.d("ASA1",state.title)
        async { viewModel.loadNote(noteId) }.await()
        Log.d("ASA1",state.title)
    }


    Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
                text = "Edit Screen",
                style = MaterialTheme.typography.h1
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                viewModel.loadNote(noteId)
            },
            contentAlignment = Alignment.CenterEnd,


            ){
            Column(
                    Modifier.padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
            ) {

                EditableNote(
                        modifier = Modifier.fillMaxWidth(),
                        title = state.title,
                        onTitleValueChange = { title ->
                            viewModel.updateTitle(title)
                        },
                        content = state.content,
                        onContentValueChange = { content ->
                            viewModel.updateContent(content)
                        },
                        color = state.color,
                        onColorClick =  { color ->
                            viewModel.updateColor(color)
                        }
                )
            }

        }
        Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
                ){
            IconButton(onClick = { navController.navigate(Screen.NotesScreen.route) }) {
                Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back"
                )
            }
            
            Spacer(Modifier.weight(1f))

            IconButton(onClick = {
                viewModel.updateNote(noteId,navController,scaffoldState)
                scope.launch {
                    viewModel.editEventFlow.collect { event ->
                        when(event){
                            is EditEvent.SuccessEdit -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                        event.message,
                                        duration = SnackbarDuration.Long
                                )
                                navController.navigate(Screen.NotesScreen.route)
                            }
                            is EditEvent.FailureEdit -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                        event.message,
                                        duration = SnackbarDuration.Long
                                )
                            }
                        }

                    }
                }
            }) {
                Icon(
                        Icons.Default.Check,
                        contentDescription = "Update"
                )
            }
        }

    }
}


