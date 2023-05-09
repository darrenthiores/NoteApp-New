package com.dev.plnoteapp.feature_note.note_domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
