package com.example.notabene.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
    private val deletedNotesViewModel: HistoricalViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout
    private lateinit var historicalNotesListAdapter: HistoricalNotesListAdapter
    private var userId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.historical_layout)

        this.userId = intent?.getIntExtra("userId", -1)!!

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@HistoricalActivity)

        Log.d("HistoryActivity : userId", userId.toString())

        this.recyclerView = findViewById(R.id.historical_note_recycler_view)
        this.swipeToRefreshLayout = findViewById(R.id.swipe_to_refresh_layout)

        this.swipeToRefreshLayout.setOnRefreshListener {
            this.deletedNotesViewModel.getDeletedNotesByUserId(userId.toString())
            this.swipeToRefreshLayout.isRefreshing = false
        }
        this.observeNoteLiveData()
    }

    override fun onResume() {
        super.onResume()
        this.deletedNotesViewModel.getDeletedNotesByUserId(userId.toString())
    }

    private fun observeNoteLiveData() {
        deletedNotesViewModel.completeNotesList.observe(this@HistoricalActivity) { notesCompleteData ->
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