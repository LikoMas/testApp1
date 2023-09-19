package com.example.testing.domain.useCase

import com.example.testing.domain.model.User
import com.example.testing.domain.repository.Repository
import com.example.testing.util.ResultOf
import com.example.testing.util.asFlowResultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddUserUseCase @Inject constructor(val repository: Repository) {
    operator fun invoke(user: User): Flow<ResultOf<Boolean>> {
        return flow { emit(repository.addUser(user)) }.asFlowResultOf().flowOn(Dispatchers.IO)
    }
}