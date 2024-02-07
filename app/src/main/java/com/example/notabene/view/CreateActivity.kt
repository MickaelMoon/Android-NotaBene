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
import com.example.notabene.viewmodel.NotesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

class CreateActivity() : AppCompatActivity() {
    private val notesViewModel: NotesViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_layout)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@CreateActivity)

        // get reference to all views
        val newCategory = findViewById<EditText>(R.id.createCategory)
        val newTitle = findViewById<EditText>(R.id.createTitle).text
        val newDescription = findViewById<EditText>(R.id.createDescription).text
//        val date = findViewById<EditText>(R.id.editDate).text
        val calendarView = findViewById<CalendarView>(R.id.createDate)

        val createButton = findViewById<Button>(R.id.btnCreate)

        // set on-click listener
        createButton.setOnClickListener {

            val title = newTitle.toString()
            val description = newDescription.toString()
            val selectedDate = calendarView.date
            val categoryText = newCategory.text.toString()
            val categoryId = categoryText.toInt()
            val date = Date(selectedDate)
            lifecycleScope.launch {
                try {
                    if (!title.isEmpty() && !description.isEmpty()
                        && !categoryText.isEmpty()
                        && !Objects.isNull(selectedDate)) {
                        Log.d("date", date.toString())
                        val response = notesViewModel.createNote(
                            title,
                            description,
                            1,
                            date.toString(),
                            categoryId
                        )
                        Log.d("CreateNote", response.toString())
                        finish()
                    }
                } catch (e: Exception) {
                    Log.d("CreateNote", e.message.toString())
                    Toast.makeText(this@CreateActivity, "Creation failed", Toast.LENGTH_LONG).show()
                }

            }
        }
    }
}