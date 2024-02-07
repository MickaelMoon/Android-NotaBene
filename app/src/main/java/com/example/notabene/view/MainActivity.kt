package com.example.notabene.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
    private lateinit var noteAdapter: NotesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@MainActivity)


        this.recyclerView = findViewById(R.id.note_recycler_view)
        this.swipeToRefreshLayout = findViewById(R.id.swipe_to_refresh_layout)

        this.swipeToRefreshLayout.setOnRefreshListener {
            this.notesViewModel.getNotesByUserId("1")
            this.swipeToRefreshLayout.isRefreshing = false
        }

        this.observeNoteLiveData()

        val button = findViewById<Button>(R.id.button_login)
        button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val buttonModify = findViewById<AppCompatImageButton>(R.id.button_edit_node)
        buttonModify.setOnClickListener {
            val node = noteAdapter.getSelectedNote()
            if(node != null) {
                val intent = Intent(this, ModifyActivity::class.java)
                intent.putExtra("noteId", node.noteId)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select a note to modify", Toast.LENGTH_LONG).show()
            }
        }

        val deleteButton = findViewById<AppCompatImageButton>(R.id.button_delete_node)
        deleteButton.setOnClickListener{
            val node = noteAdapter.getSelectedNote()
            if(node != null) {
                lifecycleScope.launch {
                    try {
                        val response = notesViewModel.deleteNote(node.noteId)
                        // Refresh the list of notes
                        notesViewModel.getNotesByUserId("1")
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Deletion failed", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please select a note to delete", Toast.LENGTH_LONG).show()
            }
        }

        val createButton = findViewById<AppCompatImageButton>(R.id.button_create_node)
        createButton.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

    }

    private fun observeNoteLiveData() {
        notesViewModel.completeNotesList.observe(this@MainActivity) { notesCompleteData ->
            setUpNotesUserList(notesCompleteData)
//            Log.d("MainActivity", notesCompleteData.toString())

        }
    }

    private fun setUpNotesUserList(notes: List<NoteData>) {
        noteAdapter =  NotesListAdapter(notes) { selectedNote ->
            Log.d("MainActivity", "Selected noteId ${selectedNote.noteId}")
        }
        recyclerView.layoutManager = LinearLayoutManager( this)
        recyclerView.adapter = noteAdapter
    }
}