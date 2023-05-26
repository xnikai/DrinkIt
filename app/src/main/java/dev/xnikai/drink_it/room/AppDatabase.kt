package dev.xnikai.drink_it.room

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import dev.xnikai.drink_it.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    @Dao
    interface UserDao {
        @Query("SELECT * FROM userentity")
        suspend fun getUser(): UserEntity

        @Insert(onConflict = REPLACE)
        suspend fun createUser(userEntity: UserEntity)

        @Delete
        suspend fun deleteUser(userEntity: UserEntity)

        @Update
        suspend fun updateUser(userEntity: UserEntity)
    }
}