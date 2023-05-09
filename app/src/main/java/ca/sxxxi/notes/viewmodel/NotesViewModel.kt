package ca.sxxxi.notes.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import ca.sxxxi.notes.MainActivity
import ca.sxxxi.notes.TestApplication
import ca.sxxxi.notes.repository.NotesRepository
import ca.sxxxi.notes.room.entity.Note
import ca.sxxxi.notes.states.NoteEditUiState
import ca.sxxxi.notes.states.NoteListUiState
import ca.sxxxi.notes.states.NotesUiState
import ca.sxxxi.notes.states.SortType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

// inject room repository (not dao)
class NotesViewModel(
    private val savedStateHandle: SavedStateHandle,     // This is good for SPA. I think
    private val notesRepository: NotesRepository
): ViewModel() {
    var noteListUiState by mutableStateOf(NoteListUiState())
        private set
    var noteEditUiState by mutableStateOf(NoteEditUiState())
        private set

    init {
        viewModelScope.launch {
            fetchNotes()
        }
    }

    private suspend fun fetchNotes() {
        notesRepository.notes.distinctUntilChanged().collectLatest {
            noteListUiState.notes.value = it
        }
        sortNotes()
    }

    fun sortNotes(sortType: SortType = noteListUiState.sortType.value) {
        noteListUiState.sortType.value = sortType
        noteListUiState.notes.value = noteListUiState.notes.value.sortedBy {
            when(noteListUiState.sortType.value) {
                SortType.LATEST -> it.lastUpdate
                else -> it.title
            }
        }
    }

    fun createNote() {
        noteEditUiState = NoteEditUiState()
    }

    fun editNote(note: Note) {
        // Initialize state for editing
        noteEditUiState = NoteEditUiState(backup = note)
    }

    fun addNote() = viewModelScope.launch {
        noteEditUiState.editable.value.lastUpdate = LocalDateTime.now().toString()
        notesRepository.saveNote(noteEditUiState.editable.value)
        noteEditUiState = NoteEditUiState()
    }

    fun updateNote() = viewModelScope.launch {
        // Only update `lastUpdated` field when modified
        if (noteEditUiState.backup != noteEditUiState.editable.value) {
            noteEditUiState.editable.value.lastUpdate = LocalDateTime.now().toString()
        }
        notesRepository.updateNote(noteEditUiState.editable.value)
        noteEditUiState = NoteEditUiState()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return NotesViewModel(
                    savedStateHandle = savedStateHandle,
                    notesRepository = (application as TestApplication).notesRepository) as T
            }
        }
    }
}

