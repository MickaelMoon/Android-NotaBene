package com.example.notabene.view

import android.app.AlertDialog
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
import com.example.notabene.model.note_model.createNoteBody
import com.example.notabene.viewmodel.CreateViewModel
import com.example.notabene.viewmodel.NotesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Objects

class CreateActivity() : AppCompatActivity() {
    private val createViewModel: CreateViewModel by viewModel()
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_layout)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@CreateActivity)

        userId = intent?.getIntExtra("userId", -1)!!
        // get reference to all views
        val newCategory = findViewById<EditText>(R.id.createCategory)
        val newTitle = findViewById<EditText>(R.id.createTitle).text
        val newDescription = findViewById<EditText>(R.id.createDescription).text
//        val date = findViewById<EditText>(R.id.editDate).text
        val calendarView = findViewById<CalendarView>(R.id.createDate)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            view.date = calendar.timeInMillis
        }
        val createButton = findViewById<Button>(R.id.btnCreate)

        // set on-click listener
        createButton.setOnClickListener {

            val title = newTitle.toString()
            val description = newDescription.toString()
            val date = Date(calendarView.date)
            val category = newCategory.text.toString()
            Log.d("date", date.toString())
            lifecycleScope.launch {
                try {
                    Log.d("date", date.toString())
                    createViewModel.createNote(
                        createNoteBody(
                            title,
                            description,
                            userId,
                            date,
                            category
                        )
                    )
                    Toast.makeText(this@CreateActivity, "Note created successfully!", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Log.d("CreateNote", e.message.toString())
                    Toast.makeText(this@CreateActivity, "Creation failed", Toast.LENGTH_LONG).show()
                }
            }
            finish()
        }
    }
}