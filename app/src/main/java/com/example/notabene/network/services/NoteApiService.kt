package com.example.notabene.network.services

import com.example.notabene.model.note_model.ChangeNoteResponse
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.createNoteBody
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteApiService {
    @GET("/note/getNotesByUser/{userId}")
    fun getNotesByUserId(
        @Path("userId") userId: String,
    ): Flowable<List<NoteData>>

    @GET("/note/getById/{noteId}")
    fun getNoteById(
        @Path("noteId") noteId: Int,
    ): Flowable<List<NoteData>>

    @POST("/note/update/{noteId}")
    suspend fun updateNote(
        @Path("noteId") noteId: Int,
        @Body createNoteBody: createNoteBody,
    ): ChangeNoteResponse

    @POST("/note/create")
    suspend fun createNote(@Body createNoteBody: createNoteBody): ChangeNoteResponse

    @DELETE("/note/delete/{noteId}")
    suspend fun deleteNote(
        @Path("noteId") noteId: Int,
    ): ChangeNoteResponse

    @GET("/note/getNotesDeletedByUser/{userId}")
    fun getDeletedNotesByUserId(
        @Path("userId") userId: String,
    ): Flowable<List<NoteData>>
}