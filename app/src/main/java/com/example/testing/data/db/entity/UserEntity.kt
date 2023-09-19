package com.example.testing.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users_table",
    indices = [Index(value = ["email"], unique = true)]
)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int?,

    @ColumnInfo("first_name") val firstName: String,
    @ColumnInfo("last_name") val lastName: String,
    @ColumnInfo("patronymic") val patronymic: String,

    @ColumnInfo("email") val email: String,
    @ColumnInfo("password") val password: String
)