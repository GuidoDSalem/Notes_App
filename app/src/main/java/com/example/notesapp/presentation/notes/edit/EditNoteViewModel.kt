package com.example.notesapp.presentation.notes.edit

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.notesapp.domain.NoteRepository
import com.example.notesapp.domain.Screen
import com.example.notesapp.domain.models.Note
import com.example.notesapp.domain.models.NoteColor
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

    fun updateColor(color: NoteColor){
        _stateFlow.value = stateFlow.value.copy(
                color = color
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
        viewModelScope.launch(Dispatchers.IO){
            val result: Result<String>  = noteRepository.updateNote(newNote)
            withContext(Dispatchers.Main){
                result.onSuccess { message ->
                    navController.navigate(Screen.NotesScreen.route)
                    Log.d("ASA3","Deveria haber navegado")
                    _editEvent.send(EditEvent.SuccessEdit(message))
                }
                result.onFailure {
                    val message: String = it?.message!!
                    _editEvent.send(EditEvent.SuccessEdit(message))
                }
            }

        }
    }

    fun loadNote(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val note: Note =
                withContext(Dispatchers.Default) { noteRepository.getNote(id)!! }

            Log.d("ASA-4",note.content)

            withContext(Dispatchers.Main){
                _stateFlow.value = stateFlow.value.copy(
                        title = note.title,
                        content = note.content,
                        color = note.color
                )
            }
            Log.d("ASA-2",stateFlow.value.content)
        }

    }
}



