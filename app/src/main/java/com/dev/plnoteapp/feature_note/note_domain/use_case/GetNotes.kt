package com.dev.plnoteapp.feature_note.note_domain.use_case

import com.dev.plnoteapp.feature_note.note_domain.model.Note
import com.dev.plnoteapp.feature_note.note_domain.repository.NoteRepository
import com.dev.plnoteapp.feature_note.note_domain.util.NoteOrder
import com.dev.plnoteapp.feature_note.note_domain.util.OrderType
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class GetNotes @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository
            .getNotes()
            .map { notes ->
                when(noteOrder.orderType) {
                    OrderType.Ascending -> {
                        when(noteOrder) {
                            is NoteOrder.Color -> notes.sortedBy { it.color }
                            is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                            is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        }
                    }
                    OrderType.Descending -> {
                        when(noteOrder) {
                            is NoteOrder.Color -> notes.sortedByDescending { it.color }
                            is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                            is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        }
                    }
                }
            }
    }
}