package com.example.notabene.model.note_model

import java.util.Date

data class createNoteBody(
    val title: String,
    val description: String,
    val userId: Int,
    val date: Date,
    val categoryId: Int
)
