package com.dev.plnoteapp.feature_note.note_domain.use_case

import com.dev.plnoteapp.feature_note.note_domain.model.InvalidNoteException
import com.dev.plnoteapp.feature_note.note_domain.model.Note
import com.dev.plnoteapp.feature_note.note_domain.repository.NoteRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AddNote @Inject constructor(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty")
        }

        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty")
        }

        repository.insertNote(note)
    }
}