package com.example.notabene.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notabene.R
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.model.note_model.NoteDto


class NotesListAdapter(
    private val data: List<NoteData>,
): RecyclerView.Adapter<NotesListAdapter.MyNoteViewHolder>() {

    class MyNoteViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(note: NoteData) {
            val noteDescription = view.findViewById<TextView>(R.id.description_note)
            noteDescription.text = note.description
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





//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteCellViewHolder {
//        val NoteCellView = LayoutInflater.from(parent.context).inflate(R.layout.note_cell_layout, parent, false)
//        return NoteCellViewHolder(NoteCellView)
//    }
//    override fun getItemCount(): Int {
//        return notes.size
//    }
//    override fun onBindViewHolder(holder: NoteCellViewHolder, position: Int) {
//        val note = this.notes[position]
//        holder.noteDescription.text = notes.toString();
//    }
//    inner class NoteCellViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        var noteDescription: TextView
//        init {
//            noteDescription = itemView.findViewById(R.id.description_note)
//        }
//    }
}

