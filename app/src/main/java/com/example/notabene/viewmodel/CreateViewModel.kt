package com.example.notabene.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notabene.model.note_model.ChangeNoteResponse
import com.example.notabene.model.note_model.createNoteBody
import com.example.notabene.repositories.CreateRepository
import java.text.SimpleDateFormat
import java.util.Date

class CreateViewModel(
    private val createRepo: CreateRepository
): ViewModel() {
    suspend fun createNote(createNoteBody: createNoteBody): ChangeNoteResponse {
        return this.createRepo.createNote(createNoteBody)
    }
}