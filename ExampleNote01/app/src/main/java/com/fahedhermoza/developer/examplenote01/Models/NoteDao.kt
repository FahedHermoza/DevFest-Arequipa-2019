package com.fahedhermoza.developer.examplenote01.Models

import androidx.room.*
import io.reactivex.Observable

@Dao
interface NoteDao {
    @Query("SELECT * from note_table ORDER BY title ASC")
    fun getAllNotes(): Observable<List<Note>>

    @Query("SELECT * from note_table ORDER BY title ASC")
    fun getAlphabetizedNotes(): List<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Query("DELETE FROM note_table WHERE title = :title")
    fun delete(title: String?)

    @Query("DELETE FROM note_table")
    fun deleteAll()

    @Update
    fun update(note: Note)
}


