package com.dev.plnoteapp.feature_note.note_presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.plnoteapp.core.util.TestTags
import com.dev.plnoteapp.feature_note.note_domain.model.Note
import com.dev.plnoteapp.feature_note.note_presentation.add_edit_note.component.TransparentHintTextField
import com.dev.plnoteapp.feature_note.note_presentation.add_edit_note.state.AddEditNoteScreenState
import com.dev.plnoteapp.feature_note.note_presentation.add_edit_note.state.rememberAddEditNoteScreenState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    screenState: AddEditNoteScreenState = rememberAddEditNoteScreenState(),
    noteColor: Int,
    onSaveClick: () -> Unit
) {
    val titleState by viewModel.noteTitle
    val contentState by viewModel.noteContent
    val backgroundColor by viewModel.noteColor

    val noteBackground = remember {
        Animatable(
            Color(
                if(noteColor!=-1) noteColor else backgroundColor
            )
        )
    }

    val scope = screenState.coroutineScope

    LaunchedEffect(true) {
        viewModel.eventFlow.collect {
            when(it) {
                AddEditNoteUiEvent.SaveNote -> onSaveClick()
                is AddEditNoteUiEvent.ShowSnackBar -> screenState.showSnackBar(it.message)
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        scaffoldState = screenState.scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save Note"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackground.value)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .shadow(12.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 2.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackground.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }

                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TransparentHintTextField(
                modifier = Modifier,
                text = titleState.text,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                hint = titleState.hint,
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
                testTag = TestTags.TITLE_TEXT_FIELD
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                modifier = Modifier
                    .fillMaxHeight(),
                text = contentState.text,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                hint = contentState.hint,
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                testTag = TestTags.CONTENT_TEXT_FIELD
            )
        }
    }
}