package com.example.notesapp.data.remote

import com.example.notesapp.data.remote.dto.NotesResponse
import com.example.notesapp.domain.models.Note

interface NotesApi {

    suspend fun getAll(): List<NotesResponse>

    suspend fun getByColor(color: Long): List<NotesResponse>

    suspend fun getById(id: Int) : NotesResponse

    suspend fun deleteNote(id: Int): Boolean

    suspend fun updateNote(note: Note): Boolean

    suspend fun createNote(note: Note): Boolean

}