package com.example.notabene.model.note_model

data class ChangeNoteResponse(
    val changes: Int,
    val lastInsertRowid: Int
)
