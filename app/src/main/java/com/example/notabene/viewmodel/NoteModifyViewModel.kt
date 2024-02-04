package com.example.notabene.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notabene.model.note_model.NoteData

class NoteModifyViewModel : ViewModel() {

    private val _currentNote = MutableLiveData<NoteData>();
    val currentNote: LiveData<NoteData> get() = _currentNote;

    fun setNote(note: NoteData) {
        _currentNote.value = note;
    }

    fun updateSelectedNote(newTitle: String, newDate: String, newDescription: String, newCategory: String) {

        val currentNoteValue = _currentNote.value;
        currentNoteValue?.let {
            _currentNote.value = it.copy(
                title = newTitle,
                date = newDate,
                description = newDescription
//                category = newCategory
            )
        }

    }
}