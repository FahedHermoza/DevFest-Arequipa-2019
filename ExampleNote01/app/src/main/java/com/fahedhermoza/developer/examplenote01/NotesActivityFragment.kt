package com.fahedhermoza.developer.examplenote01

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahedhermoza.developer.examplenote01.Adapter.NoteListAdapter
import com.fahedhermoza.developer.examplenote01.Models.LocalDataSource
import com.fahedhermoza.developer.examplenote01.Models.Note
import com.fahedhermoza.developer.examplenote01.Models.NoteRoomDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_notes.*
import java.util.concurrent.Executors

/**
 * A placeholder fragment containing a simple view.
 */
class NotesActivityFragment : Fragment() {
    private lateinit var adapter :NoteListAdapter

    private lateinit var dataSource: LocalDataSource
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Insertar data
        //insertDataDefaultEXECUTORS()
        //adapter = NoteListAdapter(context!!)
        //updateDataEXECUTORS()
        //recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(context)

        //Insertar data
        dataSource = LocalDataSource(activity!!.application)
        //insertDataDefaultRXJAVA()

        fab.setOnClickListener {
            navigationToDetailNotes()
        }
    }

    private fun insertDataDefaultEXECUTORS(){

        Executors.newSingleThreadExecutor().execute {
            val noteDao = NoteRoomDatabase.getDatabase(activity!!.application).noteDao()
            var note = Note("Deudas","2500$ Carlos, 3000$ Bernie, 6000$ ")
            noteDao.insert(note)
            note = Note("Compras Black Friday","Libros Raywerlinch, Hacking tools Hack 5, etc")
            noteDao.insert(note)
        }

    }

    private fun updateDataEXECUTORS(){
        Executors.newSingleThreadExecutor().execute {
            val noteDao = NoteRoomDatabase.getDatabase(activity!!.application).noteDao()
            adapter.setNotes(noteDao.getAlphabetizedNotes())
        }
    }

    /*** Example with Observable of RxJava ***/

    override fun onStart() {
        super.onStart()
        //dataSource = LocalDataSource(activity!!.application)
        getMyNotesList()

    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun getMyNotesList() {
        val myNotesDisposable = myNotesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(myNotesDisposable)
    }

    private val myNotesObservable: Observable<List<Note>>
        get() = dataSource.allNotes


    private val observer: DisposableObserver<List<Note>>
        get() = object : DisposableObserver<List<Note>>() {

            override fun onNext(noteList: List<Note>) {
                displayNotes(noteList)
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d("TAG", "Error$e")
                e.printStackTrace()
                //displayError("Error fetching movie list")
            }

            override fun onComplete() {
                Log.d("TAG", "Completed")
            }
        }

    fun displayNotes(noteList: List<Note>?) {
        if (noteList == null || noteList.isEmpty()) {
            Log.d("TAG", "No notes to display")
            recyclerView.visibility = View.INVISIBLE
        } else {
            adapter = NoteListAdapter(context!!)
            adapter.setNotes(noteList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun insertDataDefaultRXJAVA() {
        var note = Note("Musica","Rock N Rool, Matalos a Todos, Ritmo de Black Eyes Pleace")
        dataSource.insert(note)
        note = Note("Monedas","Soles, Libras, Pokemonedas, Dolar, Europe")
        dataSource.insert(note)
    }

    fun navigationToDetailNotes(){
        val intent = Intent(context, DetailNotesActivity::class.java)
        startActivity(intent)
    }
}
