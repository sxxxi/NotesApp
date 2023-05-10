package ca.sxxxi.notes.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.sxxxi.notes.room.entity.Note
import ca.sxxxi.notes.viewmodel.NoteOutputViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    noteViewModel: NoteOutputViewModel,
    onNavigateToNoteEdit: (Note) -> Unit = {},
) {
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                shape = RoundedCornerShape(8.dp),
                onClick = {
//                    noteViewModel.createNote()
                    onNavigateToNoteEdit(Note())
                }
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add note")
                Text(text = "New note") 
            }
        }
    ) {
        Column(modifier.padding(it)) {
            val noteListState = noteViewModel.notes

            NoteList(notes = noteViewModel.notes) { editable ->
                onNavigateToNoteEdit(editable)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoteList(modifier: Modifier = Modifier, notes: List<Note>, onNoteSelect: (Note) -> Unit) {
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
                onNoteSelect = onNoteSelect
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
                style = MaterialTheme.typography.titleMedium,
                text = note.title ?: "New Note"
            )
            Text(
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = note.content ?: "..."
            )

            Column (
                modifier =  modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline,
                    text = "C: ${Note.displayDateTime(note.dateCreated)}"
                )
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline,
                    text = "U: ${Note.displayDateTime(note.lastUpdate)}"
                )
            }
        }
    }
}

@Preview
@Composable
fun NoteListPreview() {
    MaterialTheme {
        val notes = mutableListOf<Note>(
            Note(id = 1, title = "Bad day", content = "I lost my pants. ".repeat(10), author = null),
            Note(id = 2, title = "Bad day", content = "I lost my pants. ".repeat(10), author = null),
            Note(id = 3, title = "Bad day", content = "I lost my pants. ".repeat(10), author = null),
            Note(id = 4, title = "Bad day", content = "I lost my pants. ".repeat(10), author = null),
        )
        NoteList(notes = notes, onNoteSelect = {})
    }
}