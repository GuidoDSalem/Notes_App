package com.example.notesapp.domain

import com.example.notesapp.domain.models.Note
import com.example.notesapp.domain.models.NoteColor

sealed class Screen(val route:String){
    //object SplashScreen:Screen("splash")
    object MainScreen: Screen("main")
    object NotesScreen: Screen("notes")
    object CreateNoteScreen : Screen("notes/create/")
    object EditNoteScreen: Screen("notes/edit/{noteId}/"){
        fun withArg(note: Note): String{
            return "notes/edit/${note.id?.toString()}/"
        }
    }

    //object NScreen: Screen("n")


    fun withArgs(vararg args:String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}