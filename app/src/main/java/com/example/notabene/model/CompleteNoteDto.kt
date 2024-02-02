package com.example.notabene.model

import com.example.notabene.model.note_model.NoteData;


data class CompleteNoteDto(
    // notes is an array of NoteData
    val notes: List<NoteData>,
) {

}
