package com.example.notesapp.presentation.notes

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.mapper.toNote
import com.example.notesapp.data.remote.dto.NotesResponse
import com.example.notesapp.domain.NoteRepository
import com.example.notesapp.domain.models.Note
import com.example.notesapp.domain.models.NoteColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {

    private var _noteList = MutableStateFlow(emptyList<Note>())
    var notesStateFlow = _noteList.asStateFlow()

    private val simpleNote_ = Channel<Note>()
    val simpleNote = simpleNote_.receiveAsFlow()

    init {

        viewModelScope.launch {
            println("MAIN VIEWMODEL")
            getAllNotes()
        }
    }

    fun getAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            val allNotes = noteRepository.getAllNotes()
            withContext(Dispatchers.Main){
                _noteList.emit(
                    value = allNotes
                )
            }
        }
    }

    fun getNote(note_: Note){
        val note = viewModelScope.async(Dispatchers.IO) {
            val note  =  async { noteRepository.getNote(note_.id!!) }.await()
            simpleNote.collect{ note ->
                note
            }
        }
    }

    fun deleteNote(id: Int){
        viewModelScope.launch {
            val result: Result<String> = noteRepository.deleteNote(id)
            Log.d("ASA-dalete","${result.isSuccess}")
            result.onSuccess {
                getAllNotes()
            }
            result.onFailure {
                it.printStackTrace()

            }
        }
    }

}