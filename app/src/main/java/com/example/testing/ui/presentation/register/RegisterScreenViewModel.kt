package com.example.testing.ui.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing.domain.model.User
import com.example.testing.domain.useCase.AddUserUseCase
import com.example.testing.domain.useCase.CheckUserEmailUseCase
import com.example.testing.util.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    val addUserUseCase: AddUserUseCase,
    val checkUserEmailUseCase: CheckUserEmailUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun checkUserEmail(email: String) {
        viewModelScope.launch {
            checkUserEmailUseCase(email).catch {tr ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    emailIsFree = null,
                    errorMessage = tr.message
                )
            }.collect{
                when(it){
                    is ResultOf.Error -> _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        emailIsFree = null,
                        errorMessage = it.throwable.message
                    )

                    ResultOf.Loading -> _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        emailIsFree = null,
                        errorMessage = null
                    )

                    is ResultOf.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            emailIsFree = !it.value,
                            errorMessage = null,
                        )
                    }
                }
            }
        }
    }

    fun clearEmailInUse(){
        _uiState.value =_uiState.value.copy(emailIsFree = null)
    }

    fun registerUser(user: User){
        viewModelScope.launch {
            addUserUseCase(user).catch {tr ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    registered = false,
                    errorMessage = tr.message
                )
            }.collect{
                when(it){
                    is ResultOf.Error -> _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        registered = false,
                        errorMessage = it.throwable.message
                    )

                    ResultOf.Loading -> _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        registered = false,
                        errorMessage = null
                    )

                    is ResultOf.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            registered = it.value,
                            errorMessage = null,
                        )
                    }
                }
            }
        }
    }
}

data class RegisterScreenUIState(
    val emailIsFree: Boolean? = null,
    val isLoading: Boolean = false,
    val registered: Boolean = false,
    val errorMessage: String? = null
)