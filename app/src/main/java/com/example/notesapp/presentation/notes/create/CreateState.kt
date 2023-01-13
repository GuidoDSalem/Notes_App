package com.example.notesapp.presentation.notes.create

import com.example.notesapp.domain.models.NoteColor

data class CreateState(
        val title: String = "",
        val isTitleHint: Boolean = true,
        val content: String = "",
        val isContentHint: Boolean = true,
        val color: NoteColor = NoteColor.BlueColor
)
