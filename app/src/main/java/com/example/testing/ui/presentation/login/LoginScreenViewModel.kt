package com.example.testing.ui.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing.domain.useCase.CheckUserUseCase
import com.example.testing.util.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val checkUserUseCase: CheckUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun clearUserMatches(){
        _uiState.value = _uiState.value.copy(userMatches = null)
    }

    fun checkUser(email: String, password: String) {
        viewModelScope.launch {
            checkUserUseCase(email = email, password = password).catch { tr ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    userMatches = null,
                    errorMessage = tr.message
                )
            }.collect {
                when (it) {
                    is ResultOf.Error -> _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        userMatches = null,
                        errorMessage = it.throwable.message
                    )

                    ResultOf.Loading -> _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        userMatches = null,
                        errorMessage = null
                    )

                    is ResultOf.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            userMatches = it.value,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }


}

data class LoginScreenUIState(
    val userMatches: Boolean? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)