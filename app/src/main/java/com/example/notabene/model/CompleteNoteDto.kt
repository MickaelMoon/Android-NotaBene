package com.example.notabene.model

import com.example.notabene.model.note_model.NoteData;
import com.example.notabene.model.user_model.UserData;



data class CompleteNoteDto(
    val notes: List<NoteData>
) {

}
