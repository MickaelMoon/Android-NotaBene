package com.example.notabene.network.services

import com.example.notabene.model.note_model.NoteDto
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NoteApiService {
    @GET("/note/getNotesByUser/{userId}")
    fun getNotesByUserId(
        @Path("userId") userId: String,
    ): Flowable<NoteDto>
}