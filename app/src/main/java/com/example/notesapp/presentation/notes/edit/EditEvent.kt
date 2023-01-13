package com.example.notesapp.presentation.notes.edit

sealed class EditEvent {
    data class FailureEdit(val message: String): EditEvent()
    data class SuccessEdit(val message: String): EditEvent()
}
