package ca.sxxxi.notes.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.sxxxi.notes.viewmodel.NotesViewModel

@Composable
fun Navigation(notesViewModel: NotesViewModel = viewModel(factory = NotesViewModel.Factory)) {
    val nav = rememberNavController()
    NavHost(
        navController = nav, 
        startDestination = "noteList"
    ) {
        composable("noteList") {
            NoteList(noteViewModel = notesViewModel, onNavigateToNoteEdit = { nav.navigate("noteEdit") })
        }
        composable("noteEdit") {
           NoteEdit(notesViewModel = notesViewModel, onNavigateToNoteList = {
               nav.popBackStack("noteList", inclusive = false, saveState = true)
           })
        }

    }
}