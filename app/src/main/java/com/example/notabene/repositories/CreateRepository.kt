package com.example.notabene.repositories

import com.example.notabene.model.note_model.ChangeNoteResponse
import com.example.notabene.model.note_model.createNoteBody
import com.example.notabene.network.services.NoteApiService

class CreateRepository(
    private val noteService: NoteApiService
) {
    suspend fun createNote(noteDate: createNoteBody): ChangeNoteResponse {
        return this.noteService.createNote(noteDate)
    }
}