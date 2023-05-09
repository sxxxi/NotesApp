package ca.sxxxi.notes.repository

import ca.sxxxi.notes.room.dao.NoteDao
import ca.sxxxi.notes.room.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NotesRepository(private val notesDao: NoteDao) {
    /**
     * if signedIn, Update outdated local notes
     * Get notes
      */
    val notes: Flow<List<Note>> = notesDao.getAnonymousNotes()

    suspend fun saveNote(note: Note) {
        withContext(Dispatchers.IO) { notesDao.insertNote(note) }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) { notesDao.updateNote(note) }
    }
}