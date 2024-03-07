package com.example.notabene.repositories

import com.example.notabene.model.note_model.NoteData
import com.example.notabene.network.services.NoteApiService
import io.reactivex.rxjava3.core.Flowable

class HistoricalRepository(
    private val noteService: NoteApiService) {

    fun getDeletedNotesByUserId(userId: String): Flowable<List<NoteData>> {
        return noteService.getDeletedNotesByUserId(
            userId,
        ).map {
            it
        }
    }
}