package com.example.testing.util.mappers

import com.example.testing.data.api.model.PostDto
import com.example.testing.domain.model.Post

fun PostDto.asModel(): Post {
    return Post(
        body = this.body,
        id = this.id,
        title = this.title,
        userId = this.userId
    )
}