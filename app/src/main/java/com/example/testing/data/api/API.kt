package com.example.testing.data.api

import com.example.testing.data.api.model.PostDto
import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("/posts")
    fun getPosts(): Call<List<PostDto>>

    @GET("/posts/{id}")
    fun getSinglePost(id: Int): Call<PostDto>
}