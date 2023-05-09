package ca.sxxxi.notes.presentation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import ca.sxxxi.notes.room.entity.Note
import ca.sxxxi.notes.states.SortType
import ca.sxxxi.notes.viewmodel.NotesViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    noteViewModel: NotesViewModel,
    onNavigateToNoteEdit: () -> Unit = {}
) {
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    noteViewModel.createNote()
                    onNavigateToNoteEdit()
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add note")
                Text(text = "New note") 
            }
        }
    ) {
        Column(modifier.padding(it)) {
            val noteListState = noteViewModel.noteListUiState

            NoteList(notes = noteListState.notes.value) { editable ->
                noteViewModel.editNote(editable)
                onNavigateToNoteEdit()
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoteList(modifier: Modifier = Modifier, notes: List<Note>, onNoteSelected: (Note) -> Unit) {
    LazyVerticalGrid(modifier = modifier.fillMaxSize(), columns = GridCells.Fixed(2)) {
        /**
         * I used the `!!` operator because I know that room automatically
         * generates id for each note. Plus, the data being displayed is
         * retrieved directly from room through a StateFlow
         */
        items(notes, key = { it.id!! }) { note ->
            NoteListItem(
                modifier = modifier
                    .padding(4.dp)
                    .animateItemPlacement(),
                note = note,
                onNoteSelect = onNoteSelected
            )
        }
    }

}

@Composable
private fun NoteListItem(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteSelect: (Note) -> Unit
) {
    Surface(
        modifier = modifier
            .shadow(4.dp)
            .clickable { onNoteSelect(note) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = modifier
                .padding(8.dp)
        ) {
            Text(
                modifier = modifier,
                style = MaterialTheme.typography.titleLarge,
                text = note.title ?: "New Note"
            )
            Text(
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = note.content ?: "..."
            )
            Row {
                Spacer(modifier = modifier.weight(1F))
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    text = LocalDateTime.parse(note.lastUpdate)
                        .format(DateTimeFormatter.ofPattern("MM/dd HH:mm"))
                )
            }
        }
    }
}

@Preview
@Composable
fun NoteListPreview() {
    MaterialTheme {
        val notes = listOf<Note>(
            Note(title = "Bad day", content = "I shat my pants. ".repeat(10), author = null),
            Note(title = "Bad day", content = "I shat my pants. ".repeat(10), author = null),
            Note(title = "Bad day", content = "I shat my pants. ".repeat(10), author = null),
            Note(title = "Bad day", content = "I shat my pants. ".repeat(10), author = null),
        )
        NoteList(notes = notes, onNoteSelected = {})
    }
}