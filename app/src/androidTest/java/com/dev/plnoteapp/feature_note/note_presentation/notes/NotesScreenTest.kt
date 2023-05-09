package com.dev.plnoteapp.feature_note.note_presentation.notes

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dev.plnoteapp.MainActivity
import com.dev.plnoteapp.core.util.TestTags
import com.dev.plnoteapp.feature_note.note_data.di.NoteDatabaseModule
import com.dev.plnoteapp.feature_note.note_data.di.NoteRepositoryModule
import com.dev.plnoteapp.feature_note.note_domain.di.NoteUseCaseModule
import com.dev.plnoteapp.feature_note.note_presentation.navigation.Screen
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
class NotesScreenTest {

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
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        // to get context
        // val context = ApplicationProvider.getApplicationContext<Context>()

        composeRule
            .onNodeWithTag(TestTags.ORDER_SECTION)
            .assertDoesNotExist()

        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()

        composeRule
            .onNodeWithTag(TestTags.ORDER_SECTION)
            .assertIsDisplayed()
    }
}