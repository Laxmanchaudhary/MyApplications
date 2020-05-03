package com.example.noteapproom.Db

import androidx.room.*

@Dao
interface NoteDao {


    @Insert
    suspend fun addNote(note:Note)

//ORDER BY id DESC    -> this will display latest saved notes first
    @Query("SELECT * from note ORDER BY id DESC")
    suspend fun getAllNote():List<Note>


    @Insert
    suspend fun addMultipleNotes(vararg note:Note)

    @Update
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deletenote(note:Note)
}