package com.example.notabene.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.notabene.repositories.NotesRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable


class NotesViewModel(
    private val notesRepo: NotesRepository,
): ViewModel() {
    private val disposeBag = CompositeDisposable()

    private fun getNotesByUserId(userId: String) {
        this.notesRepo.getNotesByUserId(userId).subscribe({ notes ->
            Log.d("NotesViewModel", "Notes: $notes")
        }, { error ->
            Log.d("NotesViewModel", "Error: ${error.message}")
        }).addTo(disposeBag)
    }




}