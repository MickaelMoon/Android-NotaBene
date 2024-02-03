package com.example.notabene.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notabene.R
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.NoteDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

const val EXPIRED: String = "EXPIRED"
const val WILL_EXPIRE: String = "EXPIRE IN"
const val DEFAULT_STATUS: String = "EXPIRE TODAY"
class NotesListAdapter(
    private val data: List<NoteData>,
): RecyclerView.Adapter<NotesListAdapter.MyNoteViewHolder>() {

    class MyNoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        private fun getDateStatusFormatted(date: Date): String {
            val noteDate = date
            val currentDate = getCurrentDate()
            var status: String = DEFAULT_STATUS
            Log.d("NoteDateBefore", noteDate.toString())
            Log.d("NoteDateAfter", noteDate.after(currentDate).toString())
            Log.d("NoteDateEquals", noteDate.equals(currentDate).toString())
            if(noteDate.equals(currentDate)){ status = DEFAULT_STATUS }
            if(noteDate.before(currentDate)){ status = EXPIRED }
            if(noteDate.after(currentDate)){
                val diffInMillies = noteDate.time - currentDate.time
                val diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
                status = "$WILL_EXPIRE $diffInDays days"
            }
            return status
        }

        private fun getCurrentDate(): Date {
            return Date()
        }
        fun bind(note: NoteData) {
            val noteDescription = view.findViewById<TextView>(R.id.description_note)
            noteDescription.text = note.description
            val noteDate = view.findViewById<TextView>(R.id.date_note)
            noteDate.text = this.getDateStatusFormatted(note.date)
        }
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



}

