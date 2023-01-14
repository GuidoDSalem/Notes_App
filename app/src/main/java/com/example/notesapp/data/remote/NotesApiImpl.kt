package com.example.notesapp.data.remote

import android.util.Log
import com.example.notesapp.data.mapper.toNote
import com.example.notesapp.data.remote.dto.NoteRequest
import com.example.notesapp.data.remote.dto.NotesResponse
import com.example.notesapp.data.remote.dto.SimpleRespond
import com.example.notesapp.domain.models.Note
import com.example.notesapp.domain.models.NoteColor
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.time.ZonedDateTime


class NotesApiImpl(
    private val client: HttpClient
): NotesApi {
    override suspend fun getAll(): List<NotesResponse> {
        return catchApiCall(
                emptyList<NotesResponse>()
        ){
            val httpResponse: HttpResponse = client.get(HttpRoutes.NOTES)
            val notes: List<NotesResponse> = httpResponse.body()
            return notes
        }
    }

    override suspend fun getByColor(color: Long): List<NotesResponse> {
        val path: String = HttpRoutes.noteByIdPath(color)
        val httpResponse: HttpResponse = client.get(path)
        val response: SimpleRespond<List<NotesResponse>> = httpResponse.body()
        return response.data
        TODO("Implementar que pasa si algo salem Mal")

    }

    override suspend fun getById(id: Int): NotesResponse {
        return catchApiCall(
                dafaultNotesResponse
        ) {
            val path : String= HttpRoutes.noteByIdPath(id)
            val httpResponse: HttpResponse = client.get(path)
            val note: SimpleRespond<NotesResponse> = httpResponse.body()
            return note.data
            TODO("Implementar que pasa si algo sale mal")

            /*if(note.success){
                return note.data
            }
            else{
                return NotesResponse(
                        0L,
                        "",
                        -1,
                        System.currentTimeMillis(),
                        "Fail"
                )
            }*/

        }
    }

    override suspend fun deleteNote(id: Int): Boolean {
        return catchApiCall(false){
            val path: String = HttpRoutes.noteByIdPath(id)
            val httpResponse: HttpResponse = client.delete(path)
            val response: SimpleRespond<String> = httpResponse.body()
            return response.success
            TODO("Implementar que pasa si algo sale mal")
        }
    }

    override suspend fun updateNote(note: Note): Boolean {
        return catchApiCall(false){
            val path: String = HttpRoutes.noteByIdPath(note.id!!)
            val noteRequest = NoteRequest(
                    //id = note.id,
                    color = note.color.hex,
                    content =  note.content,
                    title =  note.title,
                    time = note.date.toEpochSecond() * 1000
            )
            val httpResponse: HttpResponse = client.put(path){
                contentType(ContentType.Application.Json)
                setBody(noteRequest)
            }
            val result: SimpleRespond<String> = httpResponse.body()
            Log.d("AZA",result.data)
            Log.d("AZA",result.success.toString())

            return result.success
            TODO("Implementar que pasa si algo sale mal")

        }
    }

    override suspend fun createNote(note: Note): Boolean {

        val newNote: NoteRequest = NoteRequest(
                //id =  -1,
                color = NoteColor.getLong(note.color),
                content = note.content,
                title = note.title,
                time = System.currentTimeMillis()
        )

        val path: String = HttpRoutes.NOTES
        val httpResponse: HttpResponse = client.post(path){
            contentType(ContentType.Application.Json)
            setBody(newNote)
        }
        val result: SimpleRespond<String> = httpResponse.body()
        return result.success

        TODO("Implementar que pasa si algo sale mal")

    }

    private val dafaultNotesResponse : NotesResponse = NotesResponse(
            color = 0,
            content = "",
            id = -1,
            time = 0,
            title = ""
    )
    private inline fun <T>catchApiCall(default:T ,action: ()->T) : T{
        return try{
            action()
        } catch(e: RedirectResponseException) {
            // 3XX - response
            println("Error 3XX: ${e.response.status.description}")
            default
        } catch(e: ClientRequestException) {
            // 4XX - response
            println("Error 4XX: ${e.response.status.description}")
            default
        } catch(e: ServerResponseException) {
            // 5XX - response
            println("Error 5XX: ${e.response.status.description}")
            default
        } catch(e: Exception){
            // General Exception
            println("Error : ${e.message}")
            default
        }
    }
}