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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Text(text = "Create Note", style = MaterialTheme.typography.h1, /*fontWeight = FontWeight(4)*/)
        EditableNote(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                title = state.title,
                onTitleValueChange = { newTitle ->
                    viewModel.updateTitle(newTitle) },
                content = state.content,
                onContentValueChange = { newContent ->
                    viewModel.updateContent(newContent) },
                color = state.color,
                onColorClick = viewModel::updateColor
        )

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
                viewModel.createNote(navController)
            }) {
                Icon(
                        Icons.Default.Check,
                        contentDescription = "Update"
                )
            }
        }

    }
}
