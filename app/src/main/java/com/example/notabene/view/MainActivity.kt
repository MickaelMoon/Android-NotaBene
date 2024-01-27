package com.example.notabene.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.notabene.R
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.view.adapters.NotesListAdapter
import com.example.notabene.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var notesListRv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun setUpNotesUserList(notes: List<NoteData>) {
        val notesAdapter = NotesListAdapter(notes);
        notesListRv.adapter = notesAdapter;
    }
}