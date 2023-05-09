package com.dev.plnoteapp.feature_note.note_presentation.notes.state

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NotesScreenState(
    val scaffoldState: ScaffoldState,
    val coroutineScope: CoroutineScope
) {
    fun showSnackBar(
        message: String,
        onUndo: () -> Unit
    ) {
        coroutineScope.launch {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "Undo"
            )

            when(result) {
                SnackbarResult.Dismissed -> Unit
                SnackbarResult.ActionPerformed -> onUndo()
            }
        }
    }
}

@Composable
fun rememberNotesScreenState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): NotesScreenState = remember(scaffoldState, coroutineScope) {
    NotesScreenState(
        scaffoldState,
        coroutineScope
    )
}