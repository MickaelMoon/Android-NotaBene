package com.example.notabene.repositories

import com.example.notabene.model.note_model.CreateNoteResponse
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.createNoteBody
import com.example.notabene.model.user_model.ApiResponse
import com.example.notabene.model.user_model.LoginData
import com.example.notabene.network.services.NoteApiService
import io.reactivex.rxjava3.core.Flowable

class NotesRepository(
    private val noteService: NoteApiService
) {

    fun getNotesByUserId(userId: String) : Flowable<List<NoteData>> {
        return noteService.getNotesByUserId(
            userId,
        ).map {
            it
        }
    }

    suspend fun createNote(noteDate: createNoteBody): CreateNoteResponse {
        return this.noteService.createNote(noteDate)
    }
}