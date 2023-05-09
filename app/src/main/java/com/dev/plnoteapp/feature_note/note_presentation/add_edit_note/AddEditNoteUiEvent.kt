package com.dev.plnoteapp.feature_note.note_presentation.add_edit_note

sealed class AddEditNoteUiEvent {
    data class ShowSnackBar(val message: String): AddEditNoteUiEvent()
    object SaveNote: AddEditNoteUiEvent()
}
