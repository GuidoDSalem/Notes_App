package com.example.notesapp.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.notesapp.data.mapper.toNote
import com.example.notesapp.data.remote.NotesApi
import com.example.notesapp.data.remote.dto.NotesResponse
import com.example.notesapp.domain.NoteRepository
import com.example.notesapp.domain.models.Note
import com.example.notesapp.domain.models.NoteColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(
    private val api: NotesApi
): NoteRepository {

    override suspend fun getAllNotes(): List<Note> {
        val result = api.getAll()
        return result.map {
            it.toNote()
        }
    }

    override suspend fun deleteNote(id: Int) {
        val result = api.deleteNote(id)
        if(result){
            println("Se elimino Exitosamente la Nota")
        }else{
            println("No se pudo borrar la Nota :(")
        }
    }

    override suspend fun updateNote(note: Note): Result<String> {
        Log.d("ASA","Llegue 1")
        val result: Boolean = api.updateNote(note)
        Log.d("ASA","Llegue 2")
        Log.d("ASA",result.toString())

        if(result){

            return  Result.success("Se Guardo Exitosamente la Nota")
        }else{
            return Result.failure(
                    throw Exception("No se pudo guardar la Nota :(")
            )

        }
    }

    override suspend fun getNote(id: Int): Note {
        val result: NotesResponse = api.getById(id)
        return result.toNote()
    }

    override suspend fun createNote(note: Note) {
        val result: Boolean = api.createNote(note)
        if(result){
            println("Se creo exitosamente la Nota")
        }else{
            println("No se pudo crear la Nota :(")
        }

    }

    override suspend fun getNotesByColor(color: NoteColor): Flow<List<Note>> {
        val result: List<NotesResponse> = api.getByColor(NoteColor.getLong(color))
        val notes = result.map {
            it.toNote()
        }
        return flow {
            emit(notes)
        }
    }
}