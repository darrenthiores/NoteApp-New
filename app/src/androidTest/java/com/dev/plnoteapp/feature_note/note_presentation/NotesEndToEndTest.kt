package com.dev.plnoteapp.feature_note.note_presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dev.plnoteapp.MainActivity
import com.dev.plnoteapp.core.util.TestTags
import com.dev.plnoteapp.feature_note.note_data.di.NoteDatabaseModule
import com.dev.plnoteapp.feature_note.note_data.di.NoteRepositoryModule
import com.dev.plnoteapp.feature_note.note_domain.di.NoteUseCaseModule
import com.dev.plnoteapp.feature_note.note_presentation.add_edit_note.AddEditNoteScreen
import com.dev.plnoteapp.feature_note.note_presentation.navigation.Screen
import com.dev.plnoteapp.feature_note.note_presentation.notes.NotesScreen
import com.dev.plnoteapp.ui.theme.PLNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(
    NoteDatabaseModule::class, NoteRepositoryModule::class,
    NoteUseCaseModule::class
)
class NotesEndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        composeRule.activity.setContent {
            val navController = rememberNavController()

            PLNoteAppTheme {
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
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {

        // Click on FAB to get to add note screen
        composeRule
            .onNodeWithContentDescription("Add Note")
            .performClick()

        // enter texts in title and content text fields
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")
        // save the note
        composeRule
            .onNodeWithContentDescription("Save Note")
            .performClick()

        // make sure there is a note in the list with our title then click
        composeRule
            .onNodeWithText("test-title")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("test-title")
            .performClick()

        // make sure title and content text fields contain note title and content
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .assertTextEquals("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .assertTextEquals("test-content")
        // update title then save
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("2")
        composeRule
            .onNodeWithContentDescription("Save Note")
            .performClick()

        // make sure title is updated
        composeRule
            .onNodeWithText("test-title2")
            .assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for(i in 1..3) {
            // Click on FAB to get to add note screen
            composeRule
                .onNodeWithContentDescription("Add Note")
                .performClick()

            // enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput("test-title-$i")
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput("test-content-$i")
            // save the note
            composeRule
                .onNodeWithContentDescription("Save Note")
                .performClick()
        }

        composeRule
            .onNodeWithText("test-title-1")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("test-title-2")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("test-title-3")
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Title")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Descending")
            .performClick()

         composeRule
            .onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextContains("test-title-3")
        composeRule
            .onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertTextContains("test-title-2")
        composeRule
            .onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertTextContains("test-title-1")
    }
}