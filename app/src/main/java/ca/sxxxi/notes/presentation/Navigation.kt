package ca.sxxxi.notes.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.sxxxi.notes.viewmodel.NoteInputViewModel
import ca.sxxxi.notes.viewmodel.NoteOutputViewModel

@Composable
fun Navigation(
    noteOutputViewModel: NoteOutputViewModel = viewModel(factory = NoteOutputViewModel.Factory),
    noteInputViewModel: NoteInputViewModel = viewModel(factory = NoteInputViewModel.Factory),
) {
    val nav = rememberNavController()

    NavHost(
        navController = nav, 
        startDestination = "noteList"
    ) {
        composable("noteList") {
            NoteList(noteViewModel = noteOutputViewModel, onNavigateToNoteEdit = {
                Log.d("FOO", it.toString())
                noteInputViewModel.setModifiable(it)
                nav.navigate("noteEdit")
            })
        }
        composable("noteEdit") {
           NoteEdit(
               noteInputViewModel = noteInputViewModel,
               onNavigateToNoteList = { nav.popBackStack("noteList", inclusive = false, saveState = true) }
           )
        }

    }
}