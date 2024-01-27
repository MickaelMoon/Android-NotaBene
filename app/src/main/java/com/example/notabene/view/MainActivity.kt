package com.example.notabene.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules
import com.example.notabene.model.CompleteNoteDto
import com.example.notabene.view.adapters.NotesListAdapter
import com.example.notabene.viewmodel.NotesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val usersViewModel: NotesViewModel by viewModel();
    private lateinit var notesListRv: RecyclerView
//    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@MainActivity)

        this.notesListRv = findViewById(R.id.note_recycler_view)
        this.usersViewModel.getNotesByUserId("1");
        this.observeNotesLiveData()
    }

    private fun setUpNotesUserList(notes: List<CompleteNoteDto>) {
        val notesAdapter = NotesListAdapter(notes);
        notesListRv.adapter = notesAdapter;
    }
    private fun observeNotesLiveData() {
        usersViewModel.completeUsersList.observe(this@MainActivity) {
            setUpNotesUserList(it)
        }
    }
}