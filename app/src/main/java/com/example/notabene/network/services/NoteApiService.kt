package com.example.notabene.network.services

import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface NoteApiService {
    @GET("custom")
    fun getOneNoteData(
        @Query("id") id: String,
        @Query("name") userName: String,
    ): Flowable<NoteDto>

    @GET("custom")
    fun getNotesByUserId(
        @Query("_locale") randomDataLang: String,
        @Query("userId") userId: String,
    ): Flowable<NoteDto>
}