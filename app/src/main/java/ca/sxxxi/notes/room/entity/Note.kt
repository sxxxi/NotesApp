package ca.sxxxi.notes.room.entity

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(foreignKeys = [ForeignKey(
    entity = UserInfo::class,
    childColumns = ["author"],
    parentColumns = ["id"]
)])
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo var title: String? = null,
    @ColumnInfo var content: String? = null,
    @ColumnInfo val author: Long? = null,
    @ColumnInfo var lastUpdate: String = LocalDateTime.now().toString()
): Comparable<Note> {
    override fun compareTo(other: Note): Int {
        // Compare date modifiable fields when id is the same
        Log.d("Boo", other.id.toString() + "  " + id.toString())
        if (other.id == this.id) {
            val titleModified = other.title != title
            val contentModified = other.content != content
            val authorChanged = other.author != author
            val modified = titleModified || contentModified || authorChanged

            return if (modified) -1 else 0
        }

        // Compare date time when not the same id
        val otherLastUpdate = LocalDateTime.parse(other.lastUpdate)
        val thisLastUpdate = LocalDateTime.parse(lastUpdate)
        return thisLastUpdate.compareTo(otherLastUpdate)
    }

}
