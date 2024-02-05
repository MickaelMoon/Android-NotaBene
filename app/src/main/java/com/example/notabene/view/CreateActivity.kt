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
import com.example.notabene.viewmodel.NotesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date

class CreateActivity(): AppCompatActivity() {
    private val notesViewModel: NotesViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_layout)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@CreateActivity)

        // get reference to all views
        val title = findViewById<EditText>(R.id.editTitle).text
        val description = findViewById<EditText>(R.id.editDescription).text
//        val date = findViewById<EditText>(R.id.editDate).text
        val date = findViewById<CalendarView>(R.id.editDate).date.toString()
        val formatedDate: Date = SimpleDateFormat("dd/MM/yyyy").parse(date)
        val categoryId = findViewById<EditText>(R.id.editCategory).text

        val createButton = findViewById<Button>(R.id.btnUpdate)

        // set on-click listener
        createButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    Log.d("date", formatedDate.toString())
                    val response = notesViewModel.createNote(title.toString(), description.toString(), 1, formatedDate.toString(), 1)
                    Log.d("CreateNote", response.toString())
                } catch (e: Exception) {
                    Log.d("CreateNote", e.message.toString())
                    Toast.makeText(this@CreateActivity, "Creation failed", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}