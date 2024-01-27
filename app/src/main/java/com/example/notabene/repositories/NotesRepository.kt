package com.example.notabene.repositories

import com.example.notabene.network.services.NoteApiService
import io.reactivex.rxjava3.core.Flowable

class NotesRepository(
    private val noteService: NoteApiService
) {

    fun getRandomListOfNotes(size: Int): Flowable<List<NoteData>> {
        return noteService.getOneRandomNoteData(
            "fr_FR",
            size,
            "uuid",
            "counter",
            "name",
            "lastName",
            "date",
            "text",
            "phone",
            "boolean"
        ).map {
            it.data
        }
    }
}