package com.fahedhermoza.developer.examplenote01.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fahedhermoza.developer.examplenote01.R
import com.fahedhermoza.developer.examplenote01.Models.Note

class NoteListAdapter internal constructor(
context: Context
) : RecyclerView.Adapter<NoteListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = notes[position]
        holder.textViewTitle.text = current.title
        holder.textViewDescription.text = current.description
    }

    internal fun setNotes(words: List<Note>) {
        this.notes = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}