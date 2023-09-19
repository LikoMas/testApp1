package com.example.testing.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testing.data.db.entity.UserEntity

@Dao
interface UsersDao {
    @Query("Select * From users_table Where email Like :email And password Like :password")
    fun getUser(email: String, password: String): UserEntity?

    @Query("Select * From users_table Where email Like :email")
    fun getUser(email: String): UserEntity?

    @Insert
    fun insertUser(user: UserEntity): Long
}