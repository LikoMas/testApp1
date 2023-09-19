package com.example.testing.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface ResultOf<out T> {
    object Loading : ResultOf<Nothing>
    data class Error(val throwable: Throwable) : ResultOf<Nothing>
    data class Success<T>(val value: T) : ResultOf<T>
}

fun <T> Flow<T>.asFlowResultOf(): Flow<ResultOf<T>> {
    return this.map<T, ResultOf<T>> { ResultOf.Success(it) }
        .onStart { emit(ResultOf.Loading) }
        .catch { e -> emit(ResultOf.Error(e)) }
}