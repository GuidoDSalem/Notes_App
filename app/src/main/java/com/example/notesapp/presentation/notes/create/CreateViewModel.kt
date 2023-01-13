package com.example.notesapp.presentation.notes.create

import androidx.lifecycle.ViewModel
import com.example.notesapp.data.remote.dto.NoteRequest
import com.example.notesapp.domain.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
        private val noteRepository: NoteRepository
): ViewModel() {

    private val _state = MutableStateFlow(CreateState())
    val state = _state.asStateFlow()



}