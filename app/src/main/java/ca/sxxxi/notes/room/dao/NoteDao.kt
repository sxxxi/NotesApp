package ca.sxxxi.notes.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.sxxxi.notes.room.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note WHERE author IS NULL")
    fun getAnonymousNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE author = :id")
    fun getNotesByAuthor(id: Long): Flow<List<Note>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(note: Note)

    @Insert
    fun insertNote(note: Note)

    @Delete
    fun deleteNoteById(note: Note)

    @Delete
    fun deleteAllById(notes: Iterable<Note>)


}