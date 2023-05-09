package ca.sxxxi.notes.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val username: String,
    @ColumnInfo val firstName: String,
    @ColumnInfo val lastName: String,
)