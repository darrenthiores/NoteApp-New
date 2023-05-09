package com.dev.plnoteapp.feature_note.note_data.mapper

import com.dev.plnoteapp.feature_note.note_data.local.entity.NoteEntity
import com.dev.plnoteapp.feature_note.note_domain.model.Note

fun NoteEntity.toNote(): Note =
    Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )

fun Note.toNoteEntity(): NoteEntity =
    NoteEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )