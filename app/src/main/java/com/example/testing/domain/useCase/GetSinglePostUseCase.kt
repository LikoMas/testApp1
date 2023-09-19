package com.example.testing.domain.useCase

import com.example.testing.domain.model.Post
import com.example.testing.domain.repository.Repository
import com.example.testing.util.ResultOf
import com.example.testing.util.asFlowResultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSinglePostUseCase @Inject constructor(val repository: Repository) {
    operator fun invoke(id: Int): Flow<ResultOf<Post?>> {
        return flow { emit(repository.getPostsListItem(id)) }.asFlowResultOf()
            .flowOn(Dispatchers.IO)
    }
}