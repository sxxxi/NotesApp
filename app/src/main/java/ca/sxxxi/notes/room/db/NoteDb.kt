package ca.sxxxi.notes.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ca.sxxxi.notes.room.dao.NoteDao
import ca.sxxxi.notes.room.entity.Note
import ca.sxxxi.notes.room.entity.UserInfo

@Database(entities = [Note::class, UserInfo::class], version = 1)
abstract class NoteDb : RoomDatabase() {
    abstract fun dao(): NoteDao

    companion object {
        fun dao(ctx: Context): NoteDao {
            return Room.databaseBuilder(
                ctx,
                NoteDb::class.java,
                "NoteApiDb"
            ).build().dao()
        }
    }
}