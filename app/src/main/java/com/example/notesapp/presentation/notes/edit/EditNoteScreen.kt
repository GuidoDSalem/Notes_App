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
import com.google.android.material.snackbar.Snackbar
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
    val currentNote by viewModel.stateFlow.collectAsState()

    LaunchedEffect(key1 = true){
        viewModel.setNote(noteId)
    }


//    val currentTitle by viewModel.stateTitle.collectAsState()
//    val currentContent by viewModel.stateContent.collectAsState()

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
            .background(currentNote.color.color)
            .clickable {
                viewModel.setNote(noteId)
            },
            contentAlignment = Alignment.CenterEnd,


            ){
            Column(
                    Modifier.padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
            ) {
                BasicTextField(
                        modifier = Modifier.align(Alignment.End),
                        value = currentNote.title.trim(),
                        textStyle =  MaterialTheme.typography.h3,
                        onValueChange = { title ->
                            viewModel.updateTitle(title)
                        }
                )

                Spacer(modifier = Modifier.height(30.dp))

                BasicTextField(
                        modifier = Modifier.align(Alignment.End),
                        value = currentNote.content,
                        textStyle =  MaterialTheme.typography.h5,
                        onValueChange = { content ->
                            viewModel.updateContent(content)
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
                Log.d("ASA","Llegue 3")
                scope.launch {
                    viewModel.editEventFlow.collect { event ->
                        Log.d("ASA","Llegue 4")

                        when(event){
                            is EditEvent.SuccessEdit -> {
                                Log.d("ASA","Llegue 5")

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


