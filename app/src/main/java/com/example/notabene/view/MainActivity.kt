package com.example.notabene.view

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.view.adapters.NotesListAdapter
import com.example.notabene.viewmodel.NotesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val notesViewModel: NotesViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout
    private lateinit var createButton: AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.createButton = findViewById(R.id.button_create_node)
        createButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val response = notesViewModel.createNote(1)
                    Log.d("CreateNote", response.toString())
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@MainActivity)


        this.recyclerView = findViewById(R.id.note_recycler_view)
        this.swipeToRefreshLayout = findViewById(R.id.swipe_to_refresh_layout)

        this.swipeToRefreshLayout.setOnRefreshListener {
            this.notesViewModel.getNotesByUserId("1")
        }

        this.observeNoteLiveData()

        val button = findViewById<Button>(R.id.button_login)
        button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeNoteLiveData() {
        notesViewModel.completeNotesList.observe(this@MainActivity) { notesCompleteData ->
            setUpNotesUserList(notesCompleteData)
            Log.d("MainActivity", notesCompleteData.toString())
        }
    }

    private fun setUpNotesUserList(notes: List<NoteData>) {
        val noteAdapter =  NotesListAdapter(notes)
        recyclerView.layoutManager = LinearLayoutManager( this)
        recyclerView.adapter = noteAdapter
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