package com.example.notabene.model.note_model

data class CreateNoteResponse(
    val changes: Int,
    val lastInsertRowid: Int
)
