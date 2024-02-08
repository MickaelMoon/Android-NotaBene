package com.example.notabene.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notabene.R
import com.example.notabene.model.note_model.NoteData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

class HistoricalNotesListAdapter(
    private var data: List<NoteData>,
): RecyclerView.Adapter<HistoricalNotesListAdapter.MyNoteViewHolder>() {

    inner class MyNoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
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
            Log.d("NoteData", note.toString())
            val noteTitle = view.findViewById<TextView>(R.id.title_note_delete)
            noteTitle.text = note.title
            val noteDate = view.findViewById<TextView>(R.id.date_note_delete)
            noteDate.text = this.getDateStatusFormatted(note.date)
            val noteCategory = view.findViewById<TextView>(R.id.category_note_delete)
            noteCategory.text = this.getCategoryNameFormatted(note.category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNoteViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.historical_note_cell_layout, parent, false)
        return MyNoteViewHolder(v)    }

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