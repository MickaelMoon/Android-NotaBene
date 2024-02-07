package com.example.notabene.model.note_model

import java.util.Date

data class NoteData (
    val noteId: Int,
    var title: String,
    var description: String,
    val userId: Int,
    val flagsup: Int,
    val date: Date,
    val categoryId: Int,
    val category: String
)

