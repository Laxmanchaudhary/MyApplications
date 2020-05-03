package com.example.noteapproom.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class],version = 1)

abstract class NoteDatabase:RoomDatabase(){

    abstract fun getNoteDao():NoteDao

    companion object{
         @Volatile private var instance:NoteDatabase?=null
       private val lock=Any()

        operator fun invoke(context:Context)= instance?: synchronized(lock) {
            instance?: builddatabase(context).also {
                instance=it
            }
        }
        private fun builddatabase(context:Context)=Room.databaseBuilder(context.applicationContext,
            NoteDatabase::class.java,"MyNoteDatabase").build()
    }


}