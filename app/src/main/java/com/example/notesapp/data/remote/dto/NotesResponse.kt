package com.example.notesapp.data.remote.dto


@kotlinx.serialization.Serializable
data class NotesResponse(
    val color: Long,
    val content: String,
    val id: Int,
    val time: Long,
    val title: String,

)