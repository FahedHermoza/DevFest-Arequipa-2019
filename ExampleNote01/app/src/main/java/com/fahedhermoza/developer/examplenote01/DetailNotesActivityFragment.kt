package com.fahedhermoza.developer.examplenote01

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fahedhermoza.developer.examplenote01.Models.LocalDataSource
import com.fahedhermoza.developer.examplenote01.Models.Note
import kotlinx.android.synthetic.main.fragment_detail_notes.*

/**
 * A placeholder fragment containing a simple view.
 */
class DetailNotesActivityFragment : Fragment() {

    private lateinit var dataSource: LocalDataSource
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSave.setOnClickListener {
            if (TextUtils.isEmpty(editTextTitle.text) || TextUtils.isEmpty(editTextDescription.text)) {
                Toast.makeText(activity, "Ingrese titulo y descripción", Toast.LENGTH_LONG).show()
            } else {
                insertMoreDataRXJAVA(editTextTitle.text.toString(),editTextDescription.text.toString())
                activity?.finish()
            }

        }
    }

    private fun insertMoreDataRXJAVA(title: String, description: String) {
        dataSource = LocalDataSource(activity!!.application)
        var note = Note(title,description)
        dataSource.insert(note)
    }
}
