package com.dev.plnoteapp.feature_note.note_domain.use_case

import com.dev.plnoteapp.feature_note.data.repository.FakeNoteRepository
import com.dev.plnoteapp.feature_note.note_domain.model.InvalidNoteException
import com.dev.plnoteapp.feature_note.note_domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteTest {
    private lateinit var addNote: AddNote
    private lateinit var getNotes: GetNotes
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        addNote = AddNote(
            repository = fakeRepository
        )
        getNotes = GetNotes(
            repository = fakeRepository
        )
    }

    @Test
    fun `insert note with blank title should return exception`() = runBlocking {
        val result = kotlin.runCatching {
            addNote(
                Note(
                    title = "",
                    content = "test",
                    timestamp = 0L,
                    color = 0
                )
            )
        }.onFailure {
            assertThat(it).isInstanceOf(InvalidNoteException::class.java)
        }

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message)
            .isEqualTo("The title of the note can't be empty")
    }

    @Test
    fun `insert note with blank content should return exception`() = runBlocking {
        val result = kotlin.runCatching {
            addNote(
                Note(
                    title = "test",
                    content = "",
                    timestamp = 0L,
                    color = 0
                )
            )
        }.onFailure {
            assertThat(it).isInstanceOf(InvalidNoteException::class.java)
        }

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message)
            .isEqualTo("The content of the note can't be empty")
    }

    @Test
    fun `insert note then check if note inserted`() = runBlocking {
        val note = Note(
            title = "test",
            content = "test",
            timestamp = 0L,
            color = 0
        )

        addNote(note)

        val notes = getNotes().first()

        assertThat(notes[0]).isEqualTo(note)
    }
}