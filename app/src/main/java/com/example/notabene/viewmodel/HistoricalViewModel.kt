package com.example.notabene.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.repositories.HistoricalRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject

class HistoricalViewModel(
    private val notesRepo: HistoricalRepository,
) : ViewModel() {
    private val disposeBag = CompositeDisposable()

    val notesData: BehaviorSubject<List<NoteData>> = BehaviorSubject.createDefault(listOf())

    val completeNotesList: MutableLiveData<List<NoteData>> = MutableLiveData()

    fun getDeletedNotesByUserId(userId: String) {
        this.notesRepo.getDeletedNotesByUserId(userId).subscribe({ notes ->
            this.notesData.onNext(notes)
            Log.d("Deleted notes", notes.toString())
            this.completeNotesList.postValue(List<NoteData>(notes.size) {
                NoteData(
                    notes[it].noteId,
                    notes[it].title,
                    notes[it].description,
                    notes[it].userId,
                    notes[it].flagsup,
                    notes[it].date,
                    notes[it].categoryId,
                    notes[it].category
                )
            })
        }, { error ->
            Log.d(
                "Error in function getDeletedNotesByUserId",
                error.message ?: "Default message error"
            )
        }).addTo(disposeBag)
    }
}