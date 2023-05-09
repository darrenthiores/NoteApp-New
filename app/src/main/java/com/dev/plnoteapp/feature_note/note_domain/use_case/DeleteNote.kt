package com.dev.plnoteapp.feature_note.note_domain.use_case

import com.dev.plnoteapp.feature_note.note_domain.model.Note
import com.dev.plnoteapp.feature_note.note_domain.repository.NoteRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DeleteNote @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}