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

    override suspend fun deleteNote(id: Int): Result<String> {
        val result: Boolean = api.deleteNote(id)
        return if(result)Result.success("Se Elimino Exitosamente la Nota")
        else Result.failure(
                throw Exception("No se pudo Eliminar la Nota :(")
        )
    }

    override suspend fun updateNote(note: Note): Result<String> {
        Log.d("ASA","Llegue 1")
        val result: Boolean = api.updateNote(note)
        Log.d("ASA","Llegue 2")
        Log.d("ASA",result.toString())

        return try{
            Result.success("Se Guardo Exitosamente la Nota")
        } catch (e: Exception){
            e.printStackTrace()
            Result.failure(
                    throw Exception("No se pudo guardar la Nota :(")
            )
        }
    }

    override suspend fun getNote(id: Int): Note {
        val noteResponse: NotesResponse = api.getById(id)
        val note: Note = noteResponse.toNote()
        return note
    }

    override suspend fun createNote(note: Note): Result<String> {
        val apiResult: Boolean = api.createNote(note)
        return if(apiResult)Result.success("Se Guardo Exitosamente la Nota")
            else Result.failure(
                throw Exception("No se pudo guardar la Nota :(")
        )


        /*return try{
            val apiResult: Boolean = api.createNote(note)
            Result.success("Se Guardo Exitosamente la Nota")
        } catch(e: Exception){
            e.printStackTrace()
            Result.failure(
                    throw Exception("No se pudo guardar la Nota :("))
        }*/

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