package com.example.notesapp.presentation.notes.create

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import com.example.notesapp.domain.Screen
import com.example.notesapp.domain.models.Note
import com.example.notesapp.domain.models.NoteColor
import com.example.notesapp.presentation.composables.SimpleNote
import com.example.notesapp.presentation.notes.composables.EditableNote
import com.example.notesapp.presentation.notes.composables.Fab
import com.example.notesapp.presentation.notes.edit.EditEvent
import kotlinx.coroutines.launch

@Composable
fun CreateScreen(
        navController: NavController,
        viewModel: CreateViewModel = hiltViewModel<CreateViewModel>()
) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()





//    val currentTitle by viewModel.stateTitle.collectAsState()
//    val currentContent by viewModel.stateContent.collectAsState()

    Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
    ) {
        EditableNote(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(NoteColor.BlueColor.color),
                title = state.title,
                onTitleValueChange = viewModel.updateTitle(),
                content = state.content,
                onContentValueChange = viewModel.updateTitle(),
                showTitleHint = true,
                showContentHint = true,
                onColorClick = {

                }
        )

        /*Box(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(state.color.color),
            contentAlignment = Alignment.CenterEnd,
            ){
            Column(
                    Modifier.padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
            ) {
                BasicTextField(
                        modifier = Modifier.align(Alignment.End),
                        value = currentNote.title,
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

        }*/
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
                navController.navigate(Screen.NotesScreen.route)
            }) {
                Icon(
                        Icons.Default.Check,
                        contentDescription = "Update"
                )
            }
        }

    }
}
