package com.example.notabene.model.note_model;

data class NoteData (
    val noteId: Int,
    val title: String,
    val description: String,
    val userId: Int,
    val flagsup: Int,
)

