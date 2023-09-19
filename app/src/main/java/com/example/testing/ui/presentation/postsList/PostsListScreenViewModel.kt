package com.example.testing.ui.presentation.postsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing.domain.model.Post
import com.example.testing.domain.useCase.GetPostsListUseCase
import com.example.testing.util.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsListScreenViewModel @Inject constructor(val getPostsListUseCase: GetPostsListUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(PostsListScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun loadPostsList() {
        viewModelScope.launch {
            getPostsListUseCase().catch { tr ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = tr.message
                )
            }.collect {
                when (it) {
                    is ResultOf.Error -> _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = it.throwable.message
                    )

                    ResultOf.Loading -> _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        errorMessage = null
                    )

                    is ResultOf.Success -> _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        postsList = it.value,
                        errorMessage = null
                    )
                }
            }
        }
    }


    init {
        loadPostsList()
    }
}

data class PostsListScreenUIState(
    val postsList: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)