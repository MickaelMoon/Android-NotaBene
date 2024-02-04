package com.example.notabene.view

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.view.adapters.NotesListAdapter
import com.example.notabene.viewmodel.NoteModifyViewModel
import com.example.notabene.viewmodel.NotesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val NotesViewModel: NotesViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout
    private lateinit var buttonEditNote: AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@MainActivity)

        this.recyclerView = findViewById(R.id.note_recycler_view)
        this.swipeToRefreshLayout = findViewById(R.id.swipe_to_refresh_layout)


        this.swipeToRefreshLayout.setOnRefreshListener {
            this.NotesViewModel.getNotesByUserId("1")
        }

        val editNodeButton = findViewById<AppCompatImageButton>(R.id.button_edit_node)

        editNodeButton.setOnClickListener {
            val intent = Intent(this, ModifyNoteActivity::class.java)
            startActivity(intent)
        }
//        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
//
//        radioGroup.setOnCheckedChangeListener { group, checkedId ->
//            val selectedRadioButton: RadioButton = findViewById(checkedId)
//
//            buttonEditNote = findViewById(R.id.button_edit_node)
//            buttonEditNote.setOnClickListener {
//
//                val selectedNote = selectedRadioButton.isSelected;
//                val intent = Intent(this, ModifyNoteActivity::class.java)
//                intent.putExtra("selectedNote", selectedNote)
//                startActivity(intent)
//            }
//        }

        this.observeNoteLiveData()
    }

    private fun observeNoteLiveData() {
        NotesViewModel.completeNotesList.observe(this@MainActivity) { notesCompleteData ->
            setUpNotesUserList(notesCompleteData)
        }
    }

    private fun setUpNotesUserList(conversations: List<NoteData>) {
        val noteAdapter = NotesListAdapter(conversations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter
    }

    private fun showNoteModifyDialog(note: NoteData) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Modify note")

        val inputLayout = LinearLayout(this)
        inputLayout.orientation = LinearLayout.VERTICAL

        val titleEditText = EditText(this)
        titleEditText.hint = "New Title"
        inputLayout.addView(titleEditText)

        val dateEditText = EditText(this)
        dateEditText.hint
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