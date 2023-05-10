package ca.sxxxi.notes.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(foreignKeys = [ForeignKey(
    entity = UserInfo::class,
    childColumns = ["author"],
    parentColumns = ["id"]
)])
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo var title: String = "New Note",
    @ColumnInfo var content: String = "",
    @ColumnInfo val author: Long? = null,
    @ColumnInfo var lastUpdate: String = LocalDateTime.now().toString(),
    @ColumnInfo val dateCreated: String = LocalDateTime.now().toString()
): Comparable<Note> {
    /**
     * Latest version of note is greater.
     */
    override fun compareTo(other: Note): Int {
        val otherLastUpdate = LocalDateTime.parse(other.lastUpdate)
        val thisLastUpdate = LocalDateTime.parse(lastUpdate)
        return thisLastUpdate.compareTo(otherLastUpdate)
    }

    /**
     * A note is only equals to other if other is
     * an instance of a Note, has the same ID,
     * and is not modified.
     */
    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + lastUpdate.hashCode()
        return result
    }

    companion object {
        private var format: String = "MM/dd HH:mm"
        private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(format)

        fun displayDateTime(t: String = LocalDateTime.now().toString()): String {
            // TODO: This can throw an error when string is not valid.
            return formatter.format(LocalDateTime.parse(t))
        }
    }
}
