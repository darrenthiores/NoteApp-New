package com.dev.plnoteapp.feature_note.note_data.repository

import com.dev.plnoteapp.feature_note.note_data.local.LocalDataSource
import com.dev.plnoteapp.feature_note.note_data.mapper.toNote
import com.dev.plnoteapp.feature_note.note_data.mapper.toNoteEntity
import com.dev.plnoteapp.feature_note.note_domain.model.Note
import com.dev.plnoteapp.feature_note.note_domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): NoteRepository {
    override fun getNotes(): Flow<List<Note>> =
        localDataSource.getNotes()
            .map { entities ->
                entities.map { entity ->
                    entity.toNote()
                }
            }

    override suspend fun getNoteById(id: Int): Note? =
        localDataSource.getNoteById(id)
            ?.toNote()

    override suspend fun insertNote(note: Note) {
        localDataSource.insertNote(
            note = note.toNoteEntity()
        )
    }

    override suspend fun deleteNote(note: Note) {
        localDataSource.deleteNote(
            note = note.toNoteEntity()
        )
    }
}