package com.dev.plnoteapp.di

import android.content.Context
import androidx.room.Room
import com.dev.plnoteapp.feature_note.note_data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteDatabaseTestModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase =
        Room
            .inMemoryDatabaseBuilder(
                context,
                NoteDatabase::class.java
            )
            .fallbackToDestructiveMigration()
            .build()
}