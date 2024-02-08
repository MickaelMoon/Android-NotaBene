package com.example.notabene.view.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notabene.R
import com.example.notabene.model.note_model.NoteData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

const val EXPIRED: String = "EXPIRÉE"
const val WILL_EXPIRE: String = "EXPIRE DANS"
const val DEFAULT_STATUS: String = "EXPIRE AUJOURD'HUI"

class NotesListAdapter(
    private var data: List<NoteData>,
    private val onNoteSelected: (NoteData) -> Unit
): RecyclerView.Adapter<NotesListAdapter.MyNoteViewHolder>() {

    private var selectedPosition = -1

    inner class MyNoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val radioButton: RadioButton = itemView.findViewById(R.id.radio_note)

        init {
            radioButton.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
                onNoteSelected(data[selectedPosition])
            }
        }

        private fun getCategoryNameFormatted(categoryName: String): String {
            return "@$categoryName"
        }
        private fun getDateStatusFormatted(date: Date): String {
            val currentDate = getCurrentDate()
            val status: String = when {
                date == currentDate -> DEFAULT_STATUS
                date.before(currentDate) -> EXPIRED
                date.after(currentDate) -> {
                    val diffInMillies = date.time - currentDate.time
                    val diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
                    "$WILL_EXPIRE ${if (diffInDays.toInt() == 1) "$diffInDays JOUR" else "$diffInDays JOURS"}"
                }
                else -> DEFAULT_STATUS
            }

            return status
        }

        private fun getCurrentDate(): Date {
            val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val today = Date()
            return formatter.parse(formatter.format(today))
        }

        fun bind(note: NoteData) {
            val noteTitle = view.findViewById<TextView>(R.id.title_note)
            noteTitle.text = note.title
            val noteDate = view.findViewById<TextView>(R.id.date_note)
            noteDate.text = this.getDateStatusFormatted(note.date)
            val noteCategory = view.findViewById<TextView>(R.id.category_note)
            noteCategory.text = this.getCategoryNameFormatted(note.category)

            // Check the RadioButton if this item is the selected item
            radioButton.isChecked = (selectedPosition == adapterPosition)
        }
    }

    // ...

    fun getSelectedNote(): NoteData? {
        return if (selectedPosition != -1) data[selectedPosition] else null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNoteViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_cell_layout, parent, false)
        return MyNoteViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyNoteViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setFilteredList(filteredList: ArrayList<NoteData>) {
        this.data = filteredList
        notifyDataSetChanged()
    }
}





//class NotesListAdapter(
//    private val data: List<NoteData>
//): RecyclerView.Adapter<NotesListAdapter.MyNoteViewHolder>() {
//    private var lastChecked: RadioButton? = null
//    inner class MyNoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
//        private val radioButton: RadioButton = itemView.findViewById(R.id.radio_note)
//
//        init {
//            radioButton.setOnClickListener {
//                lastChecked?.apply { isChecked = false }
//                radioButton.isChecked = true
//                lastChecked = radioButton
//            }
//        }
//
//        private fun getCategoryNameFormatted(categoryName: String): String {
//            return "@$categoryName"
//        }
//
//        private fun getDateStatusFormatted(date: Date): String {
//            val currentDate = getCurrentDate()
//            val status: String = when {
//                date == currentDate -> DEFAULT_STATUS
//                date.before(currentDate) -> EXPIRED
//                date.after(currentDate) -> {
//                    val diffInMillies = date.time - currentDate.time
//                    val diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
//                    "$WILL_EXPIRE ${if (diffInDays.toInt() == 1) "$diffInDays DAY" else "$diffInDays DAYS"}"
//                }
//                else -> DEFAULT_STATUS
//            }
//
//            return status
//        }
//
//        private fun getCurrentDate(): Date {
//            val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
//            val today = Date()
//            return formatter.parse(formatter.format(today))
//        }
//
//        fun bind(note: NoteData) {
//            val noteDescription = view.findViewById<TextView>(R.id.description_note)
//            noteDescription.text = note.description
//            val noteDate = view.findViewById<TextView>(R.id.date_note)
//            noteDate.text = this.getDateStatusFormatted(note.date)
//            val noteCategory = view.findViewById<TextView>(R.id.category_note)
//            noteCategory.text = this.getCategoryNameFormatted(note.category)
//
//            // Reset the checked state when binding a new item
//            radioButton.isChecked = false
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNoteViewHolder {
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_cell_layout, parent, false)
//        return MyNoteViewHolder(v)
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    override fun onBindViewHolder(holder: MyNoteViewHolder, position: Int) {
//        holder.bind(data[position])
//        Log.d("NotesListAdapter", "noteId ${data[position].noteId}")
//    }
//}

