package ca.sxxxi.notes.states

import ca.sxxxi.notes.room.entity.Note
import java.io.Serializable

//data class NotesUiState (
//    var state: NotesAppState = NotesAppState.SELECT,
//    val isSignedIn: Boolean = false,
//)

sealed interface NotesUiState: Serializable {
    data class NotesSelect(var isLoggedIn: Boolean): NotesUiState
    data class EditNote(var editable: Note): NotesUiState
}