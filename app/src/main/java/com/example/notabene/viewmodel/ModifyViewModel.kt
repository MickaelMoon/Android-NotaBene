package com.example.notabene.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.createNoteBody
import com.example.notabene.repositories.ModifyRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject

class ModifyViewModel(
    private val modifyRepo: ModifyRepository
): ViewModel() {
    val disposeBag = CompositeDisposable()

    val noteData: BehaviorSubject<List<NoteData>> = BehaviorSubject.createDefault(listOf())
    val completeNotesList: MutableLiveData<List<NoteData>> = MutableLiveData()

    fun getNoteById(noteId: Int) {
        this.modifyRepo.getNoteById(noteId).subscribe({ note ->
            this.noteData.onNext(note)
            this.completeNotesList.postValue(List<NoteData>(note.size) {
                NoteData(
                    note[it].noteId,
                    note[it].title,
                    note[it].description,
                    note[it].userId,
                    note[it].flagsup,
                    note[it].date,
                    note[it].categoryId,
                    note[it].category
                )
            })
        }, { error ->
            println("Error in function getNoteById: ${error.message ?: "Default message error"}")
        }).addTo(disposeBag)
    }

    suspend fun updateNote(createNoteBody: createNoteBody, noteId: Int) {
        this.modifyRepo.updateNote(createNoteBody, noteId)
    }

}