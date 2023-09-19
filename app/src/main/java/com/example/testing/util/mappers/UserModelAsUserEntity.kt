package com.example.testing.util.mappers

import com.example.testing.data.db.entity.UserEntity
import com.example.testing.domain.model.User

fun User.asEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        patronymic = this.patronymic,
        email = this.email,
        password = this.password
    )
}