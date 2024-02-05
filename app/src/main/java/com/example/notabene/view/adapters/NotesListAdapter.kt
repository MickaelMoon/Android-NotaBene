package com.example.notabene.view.adapters

import android.annotation.SuppressLint
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

const val EXPIRED: String = "EXPIRED"
const val WILL_EXPIRE: String = "EXPIRE IN"
const val DEFAULT_STATUS: String = "EXPIRE TODAY"
class NotesListAdapter(
    private val data: List<NoteData>
): RecyclerView.Adapter<NotesListAdapter.MyNoteViewHolder>() {

    class MyNoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        private fun getCategoryNameFormatted(categoryName: String): String {
            return "@$categoryName"
        }

        //        private fun getDateStatusFormatted(date: Date): String {
//            val currentDate = getCurrentDate()
//            var status: String = DEFAULT_STATUS
//            if(date == currentDate){
//                status = DEFAULT_STATUS
//            }else if (date.before(currentDate)) {
//                status = EXPIRED
//            } else if (date.after(currentDate)) {
//                val diffInMillies = date.time - currentDate.time
//                val diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
//                status = if(diffInDays.toInt() == 1){
//                    "$WILL_EXPIRE $diffInDays DAY"
//                } else{
//                    "$WILL_EXPIRE $diffInDays DAYS"
//                }
//            }
//            return status
//        }
        private fun getDateStatusFormatted(date: Date): String {
            val currentDate = getCurrentDate()
            val status: String = when {
                date == currentDate -> DEFAULT_STATUS
                date.before(currentDate) -> EXPIRED
                date.after(currentDate) -> {
                    val diffInMillies = date.time - currentDate.time
                    val diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
                    "$WILL_EXPIRE ${if (diffInDays.toInt() == 1) "$diffInDays DAY" else "$diffInDays DAYS"}"
                }
                else -> DEFAULT_STATUS
            }

            return status
        }

        @SuppressLint("SimpleDateFormat")
        private fun getCurrentDate(): Date {
            val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val today = Date()
            return formatter.parse(formatter.format(today))
        }

        fun bind(note: NoteData) {
            val noteDescription = view.findViewById<TextView>(R.id.description_note)
            noteDescription.text = note.description
            val noteDate = view.findViewById<TextView>(R.id.date_note)
            noteDate.text = this.getDateStatusFormatted(note.date)
            val noteCategory = view.findViewById<TextView>(R.id.category_note)
            noteCategory.text = this.getCategoryNameFormatted(note.category)
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

