package com.example.notesapp.data.remote

object HttpRoutes {

    private const val BASE_PATH= "http://10.0.2.2:8080"

    const val NOTES = "$BASE_PATH/notes"

    fun <T>noteByIdPath(id:T): String{
        return NOTES + "/$id"
    }


}