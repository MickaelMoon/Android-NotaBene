package com.example.notabene.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.viewmodel.NoteModifyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ModifyNoteActivity: AppCompatActivity() {

    private val NoteModifyViewModel: NoteModifyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.modify_note_layout)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@ModifyNoteActivity)

        val editTextTitle = findViewById<EditText>(R.id.editTitle)
        val editTextDescription = findViewById<EditText>(R.id.editDescription)
        val buttonSaveChanges = findViewById<Button>(R.id.btnUpdate)
//        val buttonEdit = findViewById<Button>(R.id.btnEdit)

        // Set up UI elements and listeners for modifying note attributes
        // ...

//        buttonEdit.setOnClickListener {
//            // Assuming you have a `selectedNote` variable,
//            // you can pass it to the dialog function
//            val selectedNote = // logic to get the selected note
//                showNoteModifyDialog(selectedNote)
//        }
    }

    private fun saveChanges() {
        // Implement the logic to save the changes made to the note
        // (e.g., update the note in your data source)
        // ...

        // Close the activity or perform any other necessary action
        finish()
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
        dateEditText.hint = "New Date"
        inputLayout.addView(dateEditText)

        val descriptionEditText = EditText(this)
        descriptionEditText.hint = "New Description"
        inputLayout.addView(descriptionEditText)

        val categoryEditText = EditText(this)
        categoryEditText.hint = "New Category"
        inputLayout.addView(categoryEditText)

        builder.setView(inputLayout)

        builder.setPositiveButton("Update") { _, _ ->
            val newTitle = titleEditText.text.toString()
            val newDate = dateEditText.text.toString()
            val newDescription = descriptionEditText.text.toString()
            val newCategory = categoryEditText.text.toString()

            // Update the note using the NoteModifyViewModel
            NoteModifyViewModel.updateSelectedNote(newTitle, newDate, newDescription, newCategory)
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}