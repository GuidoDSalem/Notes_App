package com.example.notesapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SimpleRespond<T>(
        val success: Boolean,
        val data: T
)
