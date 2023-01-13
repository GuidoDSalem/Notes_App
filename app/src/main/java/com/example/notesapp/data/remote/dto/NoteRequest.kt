package com.example.notesapp.data.remote.dto

@kotlinx.serialization.Serializable
data class NoteRequest(
    val color: Long,
    val content: String,
    val title: String,
    val time: Long
)
