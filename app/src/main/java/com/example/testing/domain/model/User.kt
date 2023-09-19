package com.example.testing.domain.model

data class User(
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val email: String,
    val password: String
)