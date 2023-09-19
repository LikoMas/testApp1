package com.example.testing.data

import com.example.testing.data.api.API
import com.example.testing.data.db.UsersDao
import com.example.testing.domain.model.Post
import com.example.testing.domain.model.User
import com.example.testing.domain.repository.Repository
import com.example.testing.util.mappers.asEntity
import com.example.testing.util.mappers.asModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val api: API,
    val dao: UsersDao
) : Repository {

    override fun addUser(user: User): Boolean {
        return dao.insertUser(user.asEntity()) > -1
    }

    override fun checkUser(email: String, password: String): Boolean {
        return dao.getUser(email, password) != null
    }

    override fun checkUser(email: String): Boolean {
        return dao.getUser(email) != null
    }

    override fun getPostsList(): List<Post> {
        val response = api.getPosts().execute()
        return response.body()?.map { it.asModel() } ?: emptyList()
    }

    override fun getPostsListItem(id: Int): Post? {
        val response = api.getSinglePost(id).execute()
        return if (response.isSuccessful)
            response.body()?.asModel()
        else null
    }
}