package com.dev.plnoteapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dev.plnoteapp.feature_note.note_presentation.add_edit_note.AddEditNoteScreen
import com.dev.plnoteapp.feature_note.note_presentation.navigation.Screen
import com.dev.plnoteapp.feature_note.note_presentation.notes.NotesScreen

@Composable
fun Note(
    appState: AppState = rememberAppState()
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = Screen.NotesScreen.route
    ) {
        composable(Screen.NotesScreen.route) {
            NotesScreen(
                onAddOrItemClick = { noteId, noteColor ->
                    navController.navigate(
                        Screen.AddEditNoteScreen.route
                        + "?noteId=$noteId&noteColor=$noteColor"
                    )
                }
            )
        }

        composable(
            route = Screen.AddEditNoteScreen.route
                    + "?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "noteColor"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val color = it.arguments?.getInt("noteColor") ?: -1

            AddEditNoteScreen(
                noteColor = color,
                onSaveClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}