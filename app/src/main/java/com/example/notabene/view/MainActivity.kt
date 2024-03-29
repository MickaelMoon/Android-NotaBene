package com.example.notabene.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
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
    private lateinit var searchView: SearchView
    private var userId:Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@MainActivity)

        this.userId = intent?.getIntExtra("userId", -1)!!
        Log.d("userId", userId.toString())
        if(userId == -1) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        this.recyclerView = findViewById(R.id.note_recycler_view)
        this.swipeToRefreshLayout = findViewById(R.id.swipe_to_refresh_layout)
        this.searchView = findViewById(R.id.search_view)

        this.swipeToRefreshLayout.setOnRefreshListener {
            this.notesViewModel.getNotesByUserId(userId.toString())
            this.swipeToRefreshLayout.isRefreshing = false
        }

        this.observeNoteLiveData()

        val buttonModify = findViewById<AppCompatImageButton>(R.id.button_edit_node)
        buttonModify.setOnClickListener {
            val node = noteAdapter.getSelectedNote()
            if(node != null) {
                val intent = Intent(this, ModifyActivity::class.java)
                intent.putExtra("noteId", node.noteId)
                intent.putExtra("userId", userId)
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
                        notesViewModel.deleteNote(node.noteId)

                        // Refresh the list of notes
                        notesViewModel.getNotesByUserId(userId.toString())
                        Toast.makeText(this@MainActivity, "Note deleted successfully!", Toast.LENGTH_LONG).show()

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
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        val historyButton = findViewById<AppCompatImageButton>(R.id.button_historical_node)
        historyButton.setOnClickListener {
            val intent = Intent(this, HistoricalActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        this.notesViewModel.getNotesByUserId(userId.toString())
    }

    private fun filterList(query: String?){
        if(query != null){
            val filteredList = ArrayList<NoteData>()
            val mList: MutableList<NoteData> = mutableListOf()
            this.notesViewModel.completeNotesList.observe(this, Observer {data ->
                mList.addAll(data)
                for(item in data) {
//                    Log.d("item", item.toString());
                }
            })
            Log.d("mList", mList.toString())
            for(i in mList){
                if(i.category.lowercase().contains(query) || i.category.contains(query)){
                    filteredList.add(i)
                }
            }
            Log.d("filteredList", "$filteredList")
            if(filteredList.isEmpty()){
                Toast.makeText(this, "No Data found", Toast.LENGTH_LONG).show()
            }else{
                noteAdapter.setFilteredList(filteredList)
            }
        }
    }


    private fun observeNoteLiveData() {
        notesViewModel.completeNotesList.observe(this@MainActivity) { notesCompleteData ->
            setUpNotesUserList(notesCompleteData)
            this.swipeToRefreshLayout.isRefreshing = false
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