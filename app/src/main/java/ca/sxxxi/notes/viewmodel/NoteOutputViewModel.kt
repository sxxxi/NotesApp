package ca.sxxxi.notes.viewmodel

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import ca.sxxxi.notes.repository.NotesRepository
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import ca.sxxxi.notes.TestApplication
import ca.sxxxi.notes.room.entity.Note
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NoteOutputViewModel(private val notesRepository: NotesRepository): ViewModel() {
    var notes by mutableStateOf(listOf<Note>())
        private set

    init {
        viewModelScope.launch {
            notesRepository.notes
                .collectLatest { notes = it }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return NoteOutputViewModel(
                    notesRepository = (application as TestApplication).notesRepository) as T
            }
        }
    }

}
