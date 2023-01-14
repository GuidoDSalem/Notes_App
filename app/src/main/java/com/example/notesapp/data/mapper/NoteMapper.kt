package com.example.notesapp.data.mapper


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.notesapp.data.remote.dto.NotesResponse
import com.example.notesapp.domain.models.Note
import com.example.notesapp.domain.models.NoteColor
import io.ktor.server.util.*
import io.ktor.util.date.*
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date





fun NotesResponse.toNote(): Note {
    return Note(
        title = title,
        content = content,
        color = NoteColor.fromLong(color),
        id = id,
        date = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(time),
                ZoneId.systemDefault()
        )
    )
}