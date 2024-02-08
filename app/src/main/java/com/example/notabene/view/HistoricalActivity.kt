package com.example.notabene.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.view.adapters.HistoricalNotesListAdapter
import com.example.notabene.viewmodel.HistoricalViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoricalActivity : AppCompatActivity() {
    private val historicalViewModel: HistoricalViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout
    private lateinit var historicalNotesListAdapter: HistoricalNotesListAdapter
    private lateinit var searchView: SearchView
    private var userId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.historical_layout)

        this.userId = intent?.getIntExtra("userId", -1)!!

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@HistoricalActivity)

        Log.d("HistoryActivity : userId", userId.toString())
        this.searchView = findViewById(R.id.search_view)
        this.recyclerView = findViewById(R.id.historical_note_recycler_view)

        this.swipeToRefreshLayout = findViewById(R.id.swipe_to_refresh_layout)

        this.swipeToRefreshLayout.setOnRefreshListener {
            this.historicalViewModel.getDeletedNotesByUserId(userId.toString())
            this.swipeToRefreshLayout.isRefreshing = false
        }
        this.observeNoteLiveData()

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        val quitView = findViewById<AppCompatImageButton>(R.id.quit_view)
        quitView.setOnClickListener{
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        this.historicalViewModel.getDeletedNotesByUserId(userId.toString())
    }

    private fun filterList(query: String?){
        if(query != null){
            val filteredList = ArrayList<NoteData>()
            val notes: MutableList<NoteData> = mutableListOf()
            this.historicalViewModel.completeNotesList.observe(this, Observer {data ->
                notes.addAll(data)
//              for(item in data) {
//                    Log.d("item", item.toString());
//              }
            })
            Log.d("mList", notes.toString())
            for(note in notes){
                if(note.category.lowercase().contains(query) || note.category.contains(query)){
                    filteredList.add(note)
                }
            }
            Log.d("filteredList", "$filteredList")
            if(filteredList.isEmpty()){
                Toast.makeText(this, "No Data found", Toast.LENGTH_LONG).show()
            }else{
                historicalNotesListAdapter.setFilteredList(filteredList)
            }
        }
    }

    private fun observeNoteLiveData() {
        historicalViewModel.completeNotesList.observe(this@HistoricalActivity) { notesCompleteData ->
            setUpNotesUserList(notesCompleteData)
            this.swipeToRefreshLayout.isRefreshing = false
            Log.d("HistoryActivity", notesCompleteData.toString())

        }
    }

    private fun setUpNotesUserList(notes: List<NoteData>) {
        historicalNotesListAdapter =  HistoricalNotesListAdapter(notes)
        recyclerView.layoutManager = LinearLayoutManager( this)
        recyclerView.adapter = historicalNotesListAdapter
    }
}