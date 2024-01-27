package com.example.notabene.viewmodel


import android.database.Observable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notabene.model.CompleteNoteDto
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.repositories.NotesRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.kotlin.addTo


class NotesViewModel(
    private val notesRepo: NotesRepository,
): ViewModel() {
    private val disposeBag = CompositeDisposable()

    private val notesData: BehaviorSubject<List<NoteData>> = BehaviorSubject.createDefault(listOf());
    val completeNotesList: MutableLiveData<List<CompleteNoteDto>> = MutableLiveData()

    private fun getNotesByUserId(userId: String, callBack: () -> Unit) {
        this.notesRepo.getNotesByUserId(userId).subscribe({ notes ->
        this.notesData.onNext(notes);
            Log.d("NotesViewModel", notes.toString())
        }, { error ->
            Log.d("Error in function getNotesByUserId while fetching data from Fake API",
                error.message ?: "Default message error"
            )
        }).addTo(disposeBag)

    }

}