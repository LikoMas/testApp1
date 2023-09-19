package com.example.testing.domain.useCase

import com.example.testing.domain.repository.Repository
import com.example.testing.util.ResultOf
import com.example.testing.util.asFlowResultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CheckUserEmailUseCase @Inject constructor(val repository: Repository) {
    operator fun invoke(email: String): Flow<ResultOf<Boolean>> {
        return flow {
            emit(
                repository.checkUser(
                    email = email
                )
            )
        }.asFlowResultOf().flowOn(Dispatchers.IO)
    }
}