package com.example.notesapp.domain.models

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime


data class Note(
    val title: String,
    val content: String,
    val color: NoteColor,
    val id: Int? = null,
    val date: ZonedDateTime
)
