package com.example.testing.domain.repository

import com.example.testing.domain.model.Post
import com.example.testing.domain.model.User

interface Repository {
    fun addUser(user: User): Boolean
    fun checkUser(email: String, password: String): Boolean
    fun checkUser(email: String): Boolean

    fun getPostsList(): List<Post>
    fun getPostsListItem(id: Int): Post?
}