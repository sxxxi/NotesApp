package ca.sxxxi.notes.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ca.sxxxi.notes.room.entity.Note
import java.io.Serializable
import java.time.LocalDateTime

data class NoteInputUiState(private val initialState: Note = Note()): Serializable {
    var newState by mutableStateOf(initialState.copy())

    fun verify() {
        if (newState != initialState) {
            newState.lastUpdate = LocalDateTime.now().toString()
        }
    }
}