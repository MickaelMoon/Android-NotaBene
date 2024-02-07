package com.example.notabene.view

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.createNoteBody
import com.example.notabene.viewmodel.ModifyViewModel
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ModifyActivity(): AppCompatActivity() {
    private val modifyViewModel: ModifyViewModel by viewModel()
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_layout)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@ModifyActivity)

        val noteId = intent?.getIntExtra("noteId", -1)
        userId = intent?.getIntExtra("userId", -1)!!
        val editTitle: EditText = findViewById(R.id.editTitle)
        val editDescription: EditText = findViewById(R.id.editDescription)
        val editCategory: EditText = findViewById(R.id.editCategory)
        val calendarView: CalendarView = findViewById(R.id.editDate)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            view.date = calendar.timeInMillis
        }

        if(noteId != null && noteId != -1) {
            this.modifyViewModel.getNoteById(noteId)
            this.modifyViewModel.noteData.subscribe { note ->
                if (note.isNotEmpty()) {
                    val myNote = note[0]
                    editTitle.setText(myNote.title)
                    editDescription.setText(myNote.description)
                    editCategory.setText(myNote.category)
                    calendarView.date = myNote.date.time
                }
            }.addTo(this.modifyViewModel.disposeBag)
        } else {
            editTitle.setText("")
        }

        val updateButton = findViewById<Button>(R.id.btnUpdate)
        updateButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val title = editTitle.text.toString()
                    val description = editDescription.text.toString()
                    val date = Date(calendarView.date)
                    val category = editCategory.text.toString()
                    Log.d("date", Date(calendarView.date).toString())
                    modifyViewModel.updateNote(
                        createNoteBody(
                            title,
                            description,
                            userId,
                            date,
                            category
                        ),
                        noteId!!
                    )
                    Toast.makeText(this@ModifyActivity, "Note updated", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this@ModifyActivity, "Error updating note", Toast.LENGTH_SHORT).show()
                }
            }
            finish()
        }
    }
}