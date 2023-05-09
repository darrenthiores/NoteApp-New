package com.dev.plnoteapp.feature_note.note_presentation.add_edit_note.state

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddEditNoteScreenState(
    val scaffoldState: ScaffoldState,
    val coroutineScope: CoroutineScope
) {
    fun showSnackBar(message: String) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message
            )
        }
    }
}

@Composable
fun rememberAddEditNoteScreenState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AddEditNoteScreenState = remember(scaffoldState, coroutineScope) {
    AddEditNoteScreenState(
        scaffoldState,
        coroutineScope
    )
}