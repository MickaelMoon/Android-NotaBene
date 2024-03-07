package com.example.notabene.repositories

import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.createNoteBody
import com.example.notabene.network.services.NoteApiService
import io.reactivex.rxjava3.core.Flowable

class ModifyRepository(
    private val noteService: NoteApiService
) {
    fun getNoteById(noteId: Int): Flowable<List<NoteData>> {
        return noteService.getNoteById(
            noteId,
        )
    }

    suspend fun updateNote(data: createNoteBody, noteId: Int) {
        noteService.updateNote(
            noteId,
            data
        )
    }
}