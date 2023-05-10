package ca.sxxxi.notes.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ca.sxxxi.notes.viewmodel.NoteInputViewModel

@Composable
fun NoteEdit(
    modifier: Modifier = Modifier,
    noteInputViewModel: NoteInputViewModel,
    onNavigateToNoteList: () -> Unit = {}
) {
    val value = noteInputViewModel.inputUiState.newState
    Column(
        modifier = modifier
            .padding(20.dp)
    ) {
        Column {
            CustomTextField(
                value = value.title,
                textStyle = MaterialTheme.typography.headlineLarge,
                onValueChange = { newVal ->
                    noteInputViewModel.modify {
                        it.title = newVal
                    }
                }
            )
        }

        CustomTextField(
            modifier = modifier
                .weight(1F)
                .fillMaxSize(),
            textStyle = MaterialTheme.typography.bodyLarge,
            value = value.content,
            onValueChange = { newVal ->
                noteInputViewModel.modify {
                    it.content = newVal
                }
            }
        )

        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            FilledTonalIconButton(
                onClick = {
                    noteInputViewModel.deleteNote()
                    onNavigateToNoteList()
                }
            ) {
                Icon(Icons.Rounded.Delete, contentDescription = "Delete icon")
            }

            FilledIconButton(
                onClick = {
                    // New notes has null ids, old notes already has one
                    value.id?.let {
                        noteInputViewModel.updateNote()
                    } ?: noteInputViewModel.addNote()

                    onNavigateToNoteList()
                }
            ) {
                Icon(Icons.Rounded.Done, contentDescription = "Insert")
            }

        }
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    value: String,
    singleLine: Boolean = false,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .padding(8.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = textStyle.fontSize),
    )
}