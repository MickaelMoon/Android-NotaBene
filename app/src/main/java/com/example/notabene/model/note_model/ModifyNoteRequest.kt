package com.example.notabene.model.note_model

data class ModifyNoteRequest(
    val title: String,
    val description: String,
    val date: String,
    val category: String
)