package com.dev.plnoteapp.feature_note.note_domain.di

import com.dev.plnoteapp.feature_note.note_domain.repository.NoteRepository
import com.dev.plnoteapp.feature_note.note_domain.use_case.AddNote
import com.dev.plnoteapp.feature_note.note_domain.use_case.DeleteNote
import com.dev.plnoteapp.feature_note.note_domain.use_case.GetNotes
import com.dev.plnoteapp.feature_note.note_domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object NoteUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideNoteUseCases(
        repository: NoteRepository
    ): NoteUseCases = NoteUseCases(
        getNotes = GetNotes(repository),
        deleteNote = DeleteNote(repository),
        addNote = AddNote(repository)
    )
}