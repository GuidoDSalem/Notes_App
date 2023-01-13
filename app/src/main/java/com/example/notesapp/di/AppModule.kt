package com.example.notesapp.di

import com.example.notesapp.data.repository.NoteRepositoryImpl
import com.example.notesapp.data.remote.NotesApi
import com.example.notesapp.data.remote.NotesApiImpl
import com.example.notesapp.domain.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(Android){
            install(ContentNegotiation){
                json(Json{
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging){
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

        }
    }

    @Provides
    @Singleton
    fun provideNotesApi(client: HttpClient): NotesApi{
        return NotesApiImpl(client)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(api: NotesApi): NoteRepository{
        return NoteRepositoryImpl(api)
    }
}