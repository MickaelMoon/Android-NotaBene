package com.example.notabene.network.services

import com.example.notabene.model.note_model.ModifyNoteRequest
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.NoteDto

import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteApiService {
    @GET("/note/getNotesByUser/{userId}")
    fun getNotesByUserId(
        @Path("userId") userId: String,
    ): Flowable<List<NoteData>>

    // Modify Note by Note ID
    @PUT("/note/modifyNote/{noteId}")
    fun modifyNote(
        @Path("noteId") noteId: String,
        @Body request: ModifyNoteRequest
    ): Flowable<List<NoteData>>
}