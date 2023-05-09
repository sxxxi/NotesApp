package ca.sxxxi.notes.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ca.sxxxi.notes.room.entity.Note
import kotlinx.coroutines.flow.StateFlow

data class NoteListUiState(
    val loggedIn: MutableState<Boolean> = mutableStateOf(false),
    var sortType: MutableState<SortType> = mutableStateOf(SortType.LATEST),
    var notes: MutableState<List<Note>> = mutableStateOf(listOf()),
)

enum class SortType {
    AUTHOR, LATEST, ALPHABETICAL
}