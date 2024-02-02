package com.example.notabene.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.NoteDto
import com.example.notabene.repositories.NotesRepository
import com.example.notabene.view.adapters.NotesListAdapter
import com.example.notabene.viewmodel.NotesViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val notesRepo: NotesRepository by inject();
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@MainActivity)

        manager = LinearLayoutManager(this)
        getNotes()
    }

    private fun getNotes() {
        this.notesRepo.getNotesByUserId("1").subscribe({ notes ->
            Log.d("NotesViewModel", notes.toString())
            this.setUpNotesUserList(notes)
        }, { error ->
            println(error)
        })
    }

    private fun setUpNotesUserList(notes: List<NoteData>): List<NoteData> {
        recyclerView = findViewById<RecyclerView>(R.id.note_recycler_view).apply {
            layoutManager = manager
            myAdapter = NotesListAdapter(notes)
            adapter = myAdapter
        }
        return notes
    }


//    private val usersViewModel: NotesViewModel by viewModel();
//    private lateinit var notesListRv: RecyclerView
////    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout;
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//

//
//        this.notesListRv = findViewById(R.id.note_recycler_view)
//        this.usersViewModel.getNotesByUserId("1");
//        this.observeNotesLiveData()
//    }
//
//    private fun setUpNotesUserList(notes: List<NoteDto>) {
//        val notesAdapter = NotesListAdapter(notes);
//        notesListRv.adapter = notesAdapter;
//    }
//    private fun observeNotesLiveData() {
//        usersViewModel.completeNotesList.observe(this@MainActivity) {
//            setUpNotesUserList(it)
//        }
//    }
}