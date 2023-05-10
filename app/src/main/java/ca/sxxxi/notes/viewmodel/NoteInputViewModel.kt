package ca.sxxxi.notes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import ca.sxxxi.notes.TestApplication
import ca.sxxxi.notes.repository.NotesRepository
import ca.sxxxi.notes.room.entity.Note
import ca.sxxxi.notes.states.NoteInputUiState
import kotlinx.coroutines.launch

class NoteInputViewModel(private val notesRepository: NotesRepository): ViewModel() {
    var inputUiState by mutableStateOf(NoteInputUiState())
        private set

    fun modify(action: (Note) -> Unit) {
        val copy = inputUiState.newState.copy()
        action(copy)
        inputUiState.newState = copy
    }

    fun setModifiable(note: Note) {
        // Initialize state for editing
        inputUiState = NoteInputUiState(note)
    }

    fun addNote() = viewModelScope.launch {
        // Only notes with null ids are allowed to be INSERTED
        // TODO: Handle error if user tries to insert note with non-null id

        inputUiState.verify()
        notesRepository.saveNote(inputUiState.newState)
        inputUiState = NoteInputUiState()
    }

    fun updateNote() = viewModelScope.launch {
        // Only notes with non-null ids are allowed to be UPDATED
        // TODO: Handle error if user tries to update note with null id

        inputUiState.verify()
        notesRepository.updateNote(inputUiState.newState)
        inputUiState = NoteInputUiState()
    }

    fun deleteNote() = viewModelScope.launch {
        notesRepository.deleteNotes(inputUiState.newState)
        inputUiState = NoteInputUiState()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return NoteInputViewModel(
                    notesRepository = (application as TestApplication).notesRepository) as T
            }
        }
    }
}