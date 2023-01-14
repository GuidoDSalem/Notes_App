package com.example.notesapp.presentation.notes.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.notesapp.data.remote.dto.NoteRequest
import com.example.notesapp.domain.NoteRepository
import com.example.notesapp.domain.Screen
import com.example.notesapp.domain.models.Note
import com.example.notesapp.domain.models.NoteColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
        private val noteRepository: NoteRepository
): ViewModel() {

    private val _state = MutableStateFlow(CreateState())
    val state = _state.asStateFlow()

    fun updateTitle(newTitle: String){
        viewModelScope.launch {
            _state.value = state.value.copy(
                    title = newTitle
            )
        }
    }

    fun updateContent(newContent: String){
        viewModelScope.launch {
            _state.value =
                    state.value.copy(
                            content = newContent
                    )


        }
    }

    fun updateColor(newColor: NoteColor){
        Log.d("ASA","LLegue color")
        Log.d("ASA",newColor.hex.toString())

        viewModelScope.launch {
            _state.value = state.value.copy(
                    color = newColor,
            )
        }
        Log.d("ASA", state.value.color.hex.toString())
        println(state.value.color.toString())
    }

    fun createNote(navController: NavController){
        val newNote = Note(
                title = state.value.title,
                content = state.value.content,
                color = state.value.color,
                id = null,
                date =  ZonedDateTime.now()
        )
        viewModelScope.launch {
            val result: Result<String> =  async { noteRepository.createNote(note = newNote) }.await()

            result.onSuccess {
                navController.navigate(Screen.NotesScreen.route)
            }
        }
    }



}