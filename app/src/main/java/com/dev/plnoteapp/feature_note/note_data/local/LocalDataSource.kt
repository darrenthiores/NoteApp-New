package com.dev.plnoteapp.feature_note.note_data.local

import com.dev.plnoteapp.feature_note.note_data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    noteDb: NoteDatabase
) {
    private val noteDao = noteDb.noteDao()

    fun getNotes(): Flow<List<NoteEntity>> =
        noteDao.getNotes()

    suspend fun getNoteById(id: Int): NoteEntity? =
        noteDao.getNoteById(id)

    suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }
}