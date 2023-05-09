package com.dev.plnoteapp.feature_note.note_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev.plnoteapp.feature_note.note_data.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao
}