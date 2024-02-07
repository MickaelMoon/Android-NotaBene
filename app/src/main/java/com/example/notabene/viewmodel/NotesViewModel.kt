package com.example.notabene.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notabene.model.note_model.ChangeNoteResponse
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.repositories.NotesRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject


class NotesViewModel(
    private val notesRepo: NotesRepository,
): ViewModel() {
    private val disposeBag = CompositeDisposable()

    // Observables used by the view model to get the users infos only
    val notesData: BehaviorSubject<List<NoteData>> = BehaviorSubject.createDefault(listOf())

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
                    notes[it].date,
                    notes[it].categoryId,
                    notes[it].category
                )
            })
        }, { error ->
            Log.d("Error in function getNotesByUserId",
                error.message ?: "Default message error"
            )
        }).addTo(disposeBag)
    }

    suspend fun deleteNote(noteId: Int): ChangeNoteResponse {
        return this.notesRepo.deleteNote(noteId)
    }

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
            Log.d("Error in function getDeletedNotesByUserId",
                error.message ?: "Default message error"
            )
        }).addTo(disposeBag)
    }
}
