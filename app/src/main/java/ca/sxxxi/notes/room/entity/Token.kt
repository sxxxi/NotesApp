package ca.sxxxi.notes.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.time.LocalDateTime

@Entity(foreignKeys = [ForeignKey(
    entity = UserInfo::class,
    parentColumns = ["id"],
    childColumns = ["owner"],
    onDelete = ForeignKey.CASCADE
)])
data class Token(
    @ColumnInfo val owner: Long,
    @ColumnInfo val jws: String,
    @ColumnInfo val expiryDate: LocalDateTime
)