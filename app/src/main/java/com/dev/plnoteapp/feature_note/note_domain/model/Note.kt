package com.dev.plnoteapp.feature_note.note_domain.model

import com.dev.plnoteapp.feature_note.note_domain.util.*

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val noteColors = listOf(
            RedOrange,
            RedPink,
            BabyBlue,
            Violet,
            LightGreen
        )
    }
}

class InvalidNoteException(message: String): Exception(message)