package ca.sxxxi.notes.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ca.sxxxi.notes.room.entity.Note

data class NoteEditUiState(
    var modified: Boolean = false,
    val backup: Note = Note(),
    var editable: MutableState<Note> = mutableStateOf(backup.copy())
)
