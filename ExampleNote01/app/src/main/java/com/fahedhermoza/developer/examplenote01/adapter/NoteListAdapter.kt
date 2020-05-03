package com.fahedhermoza.developer.examplenote01.Adapter

import android.content.Context
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fahedhermoza.developer.examplenote01.R
import com.fahedhermoza.developer.examplenote01.Models.Note
import com.fahedhermoza.developer.examplenote01.utils.inflate
import java.util.HashSet

class NoteListAdapter internal constructor(context: Context) : RecyclerView.Adapter<NoteListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>() // Cached copy of words
    val selectedNotes = HashSet<Note>()

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val checkboxDelete: CheckBox = itemView.findViewById(R.id.checkboxDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = notes[position]
        holder.textViewTitle.text = current.title

        lateinit var description: String
        if(current.description.length<30)
            description = current.description
        else
            description = current.description.substring(0,30).plus("...")
        holder.textViewDescription.text = description

        holder.checkboxDelete.setOnClickListener {
            val adapterPosition = position
            if (!selectedNotes.contains(notes[adapterPosition])) {
                holder.checkboxDelete.isChecked = true
                selectedNotes.add(notes[adapterPosition])
            } else {
                holder.checkboxDelete.isChecked = false
                selectedNotes.add(notes[adapterPosition])
            }
        }
    }

    internal fun setNotes(listNotes: List<Note>) {
        this.notes = listNotes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}