package com.example.notesapp.domain

import com.example.notesapp.data.remote.dto.NotesResponse
import com.example.notesapp.domain.models.Note
import com.example.notesapp.domain.models.NoteColor
import kotlinx.coroutines.flow.Flow


interface NoteRepository {

    suspend fun getAllNotes(): List<Note>

    suspend fun  deleteNote(id: Int): Result<String>

    suspend fun updateNote(note: Note): Result<String>

    suspend fun getNote(id: Int) : Note?

    suspend fun createNote(note: Note): Result<String>

    suspend fun getNotesByColor(color: NoteColor): Flow<List<Note>>

}