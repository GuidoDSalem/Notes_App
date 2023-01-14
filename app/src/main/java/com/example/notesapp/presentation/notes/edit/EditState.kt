package com.example.notesapp.presentation.notes.edit

import android.graphics.Color
import com.example.notesapp.domain.models.NoteColor

data class EditState(
        val title: String = "",
        val content: String = "",
        val color: NoteColor = NoteColor.NullColor
)
