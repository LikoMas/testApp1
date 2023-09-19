package com.example.testing.data.db

import androidx.room.RoomDatabase
import com.example.testing.data.db.entity.UserEntity

@androidx.room.Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun returnUsersDao(): UsersDao
}