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
//    @Query("SELECT * FROM note")
//    fun getAll(): MutableStateFlow<Iterable<Note>>;

//    @Query("SELECT * FROM note WHERE author = NULL")
//    fun getPublicNotes(): Flow<List<Note>>

    // For when the user ins not logged in
    @Query("SELECT * FROM note WHERE author IS NULL")
    fun getAnonymousNotes(): Flow<List<Note>>


    // For when the user is logged in
    @Query("SELECT * FROM note WHERE author = :id")
    fun getNotesByAuthor(id: Long): Flow<List<Note>>

//    @Query("SELECT * FROM UserInfo WHERE id = :id")
//    fun getUserWithNotes(id: Long): MutableStateFlow<UserWithNotes>
//
//    @Query("SELECT * FROM userinfo JOIN note ON userinfo.id = note.author WHERE userinfo.id = :id")
//    fun getUserNotes(id: Long): Map<UserInfo, List<Note>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(note: Note)

    @Insert
    fun insertNote(note: Note)
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateAll(notes: Iterable<Note>)

    @Delete
    fun deleteNoteById(note: Note)

    @Delete
    fun deleteAllById(notes: Iterable<Note>)


}