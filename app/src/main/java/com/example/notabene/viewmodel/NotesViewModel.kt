package com.example.notabene.viewmodel


import android.annotation.SuppressLint
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notabene.model.CompleteNoteDto
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.NoteDto
import com.example.notabene.repositories.NotesRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit



class NotesViewModel(
    private val notesRepo: NotesRepository,
): ViewModel() {
    private val disposeBag = CompositeDisposable()

//    private val notesData = PublishSubject.create<List<NoteData>>()

    // Observables used by the view model to get the users infos only
    private val notesData: BehaviorSubject<List<NoteData>> = BehaviorSubject.createDefault(listOf())

    val completeNotesList: MutableLiveData<List<NoteData>> = MutableLiveData()

    init {
        this.getNotesByUserId("1")
    }

    fun getNotesByUserId(userId: String) {
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

    @SuppressLint("SimpleDateFormat")
    fun createNote(userId: String) {
        val date: Date = SimpleDateFormat("yyyy-MM-dd").parse("14-02-2024")
        val noteData = NoteData(400, "Nico", "c nico qui a fait ca", userId.toInt(), 0, date)
        this.notesRepo.createNote(noteData)
    }
}