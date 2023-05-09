package ca.sxxxi.notes.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import ca.sxxxi.notes.room.entity.Note
import ca.sxxxi.notes.viewmodel.NotesViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEdit(
    modifier: Modifier = Modifier,
    notesViewModel: NotesViewModel,
    onNavigateToNoteList: () -> Unit = {}
) {
    var uiNote by remember { notesViewModel.noteEditUiState.editable }
    Column {
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleLarge,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.onBackground
            ),
            value = uiNote.title ?: "",
            onValueChange = {
                uiNote = uiNote.copy(title = it)
            }
        )

        TextField(
            modifier = modifier
                .weight(1F)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.onBackground
            ),
            value = uiNote.content ?: "",
            onValueChange = {
                uiNote = uiNote.copy(content = it)
            }
        )

        Button(onClick = {
            // New notes has null ids, old notes already has one
            uiNote.id?.let {
                notesViewModel.updateNote()
            } ?: notesViewModel.addNote()

            onNavigateToNoteList()
        }) {
           Text(text = "Save")
        }
    }
}