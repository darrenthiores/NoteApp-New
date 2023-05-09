package com.dev.plnoteapp.di

import com.dev.plnoteapp.feature_note.note_data.repository.NoteRepositoryImpl
import com.dev.plnoteapp.feature_note.note_domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteRepositoryTestModule {

    @Binds
    abstract fun provideNoteRepository(
        repository: NoteRepositoryImpl
    ): NoteRepository
    
}