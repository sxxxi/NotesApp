package ca.sxxxi.notes.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.sxxxi.notes.room.entity.UserInfo

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun saveUserInfo(user: UserInfo)

    @Query("SELECT * FROM userinfo WHERE username = :username")
    fun getUserInfoByUsername(username: String): UserInfo?
}