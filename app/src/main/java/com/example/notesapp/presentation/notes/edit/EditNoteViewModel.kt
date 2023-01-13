package com.example.notesapp.presentation.notes.edit

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.notesapp.domain.NoteRepository
import com.example.notesapp.domain.Screen
import com.example.notesapp.domain.models.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
        private val noteRepository: NoteRepository,
) : ViewModel(){

    private var _stateFlow = MutableStateFlow(EditState())
    val stateFlow = _stateFlow.asStateFlow()

    private var _stateTitle = MutableStateFlow("")
    val stateTitle = _stateTitle.asStateFlow()

    private var _stateContent = MutableStateFlow("")
    val stateContent = _stateContent.asStateFlow()

    private val _editEvent = Channel<EditEvent>()
    val editEventFlow = _editEvent.receiveAsFlow()

    fun updateTitle(title: String){
        _stateFlow.value = stateFlow.value.copy(
                title = title
        )
    }

    fun updateContent(content: String){
        _stateFlow.value = stateFlow.value.copy(
                content = content
        )
    }

    /*private fun updateNote(id: Int): Result<String>{
        val newNote = Note(
                title = stateFlow.value.title,
                content =  stateFlow.value.content,
                color = stateFlow.value.color,
                id = id,
                date = ZonedDateTime.now()
        )
        viewModelScope.launch{
            val result: Result<String>  = noteRepository.updateNote(newNote)
            result.onSuccess { message ->
                _editEvent.send(EditEvent.SuccessEdit(message))
                navController.navigate(Screen.NotesScreen.route)
            }
            result.onFailure {
                val message: String = it?.message!!
                _editEvent.send(EditEvent.SuccessEdit(message))
            }
        }
    }*/

    fun updateNote(id: Int,navController: NavController,scaffoldState: ScaffoldState){
        val newNote = Note(
                title = stateFlow.value.title,
                content =  stateFlow.value.content,
                color = stateFlow.value.color,
                id = id,
                date = ZonedDateTime.now()
        )
        viewModelScope.launch{
            val result: Result<String>  = noteRepository.updateNote(newNote)
            result.onSuccess { message ->
                _editEvent.send(EditEvent.SuccessEdit(message))
                //scaffoldState.snackbarHostState.showSnackbar(message)
                navController.navigate(Screen.NotesScreen.route)
            }
            result.onFailure {
                val message: String = it?.message!!
                _editEvent.send(EditEvent.SuccessEdit(message))
            }
        }
    }

    fun setNote(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val note: Note = noteRepository.getNote(id)!!



            withContext(Dispatchers.Main){
                _stateFlow.emit(_stateFlow.value.copy(
                        title = note.title,
                        content = note.content,
                        color = note.color)
                )
                _stateTitle.value = note.title
                _stateContent.value = note.content
                Log.d("ASA",stateTitle.value)
                Log.d("ASA",stateContent.value)
            }
        }

    }
}



