package com.dev.plnoteapp.feature_note.note_presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.plnoteapp.core.util.TestTags
import com.dev.plnoteapp.feature_note.note_presentation.notes.component.NoteItem
import com.dev.plnoteapp.feature_note.note_presentation.notes.component.OrderSection
import com.dev.plnoteapp.feature_note.note_presentation.notes.state.NotesScreenState
import com.dev.plnoteapp.feature_note.note_presentation.notes.state.rememberNotesScreenState

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    screenState: NotesScreenState = rememberNotesScreenState(),
    onAddOrItemClick: (Int, Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier,
        scaffoldState = screenState.scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    actionColor = MaterialTheme.colors.primary,
                    snackbarData = data
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddOrItemClick(-1, -1)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your note",
                    style = MaterialTheme.typography.h4
                )

                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .testTag(TestTags.ORDER_SECTION),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(
                    items = state.notes,
                    key = { note -> note.id ?: note.title }
                ) { note ->
                    NoteItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onAddOrItemClick(
                                    note.id ?: -1,
                                    note.color
                                )
                            },
                        note = note,
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            screenState.showSnackBar(
                                message = "Note deleted",
                                onUndo = {
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}