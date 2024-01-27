package com.example.notabene.network.services

import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface NoteApiService {
    @GET("custom")
    fun getOneNoteData(
        @Query("_locale") randomDataLang: String,
        @Query("_quantity") responseLength: Int,
        @Query("id") id: String,
        @Query("profilePicture") profilePicture: String,
        @Query("name") userName: String,
        @Query("lastName") userLastName: String,
        @Query("lastSeenDate") userLastSeenDate: String,
        @Query("statusDescription") status: String,
        @Query("phoneNumber") phoneNumber: String,
        @Query("isPremium") isPremium: String
    ): Flowable<NoteDto>
}