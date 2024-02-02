package com.example.notabene.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notabene.model.CompleteNoteDto
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.NoteDto
import com.example.notabene.repositories.NotesRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject


class NotesViewModel(
    private val notesRepo: NotesRepository,
): ViewModel() {
    private val disposeBag = CompositeDisposable()

    private val notesData = PublishSubject.create<List<NoteData>>()
    val completeNotesList: MutableLiveData<List<NoteData>> = MutableLiveData()

    init {
        this.getNotesByUserId("1")
    }

    private fun getNotesByUserId(userId: String) {
        Log.d("NotesViewModel", "getNotesByUserId")
        this.notesRepo.getNotesByUserId(userId).subscribe({ notes ->
            this.notesData.onNext(notes)
            Log.d("NotesViewModel", notes.toString())
            this.completeNotesList.postValue(List<NoteData>(notes.size) {
                NoteData(
                    notes[it].noteId,
                    notes[it].title,
                    notes[it].description,
                    notes[it].userId,
                    notes[it].flagsup,
                    notes[it].date
                )
            })
        }, { error ->
            Log.d("Error in function getNotesByUserId",
                error.message ?: "Default message error"
            )
        }).addTo(disposeBag)
    }

}