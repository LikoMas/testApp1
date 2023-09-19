package com.example.testing.ui.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testing.domain.model.User
import com.example.testing.ui.components.OutlinedFormEmailField
import com.example.testing.ui.components.OutlinedFormPasswordField
import com.example.testing.ui.components.OutlinedFormTextField
import com.example.testing.ui.presentation.login.ErrorPopup

@Composable
fun RegisterScreen(
    navigateToLoginListScreen: () -> Unit,
    viewModel: RegisterScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showErrorDialog by remember { mutableStateOf(false) }


    var firstNameValue by remember { mutableStateOf("") }
    var secondNameValue by remember { mutableStateOf("") }
    var patronymicValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var confirmPasswordValue by remember { mutableStateOf("") }

    LaunchedEffect(uiState.emailIsFree) {
        if (uiState.emailIsFree == true) {
            viewModel.registerUser(
                User(
                    id = null,
                    firstName = firstNameValue,
                    lastName = secondNameValue,
                    patronymic = patronymicValue,
                    email = emailValue,
                    password = passwordValue
                )
            )
        } else if (uiState.emailIsFree == false) {
            showErrorDialog = true
        }
    }

    LaunchedEffect(key1 = uiState.registered) {
        if (uiState.registered) {
            navigateToLoginListScreen()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp).weight(1f),
                text = "Реєстрація",
                style = MaterialTheme.typography.headlineLarge
            )

            LazyColumn(userScrollEnabled = true, modifier = Modifier.fillMaxWidth().weight(4f)) {
                item {
                    OutlinedFormTextField(
                        onValueChange = { newValue ->
                            firstNameValue = newValue ?: ""
                        },
                        label = "Прізвище",
                        placeholderText = "Введіть своє прізвище",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        minLength = 5
                    )
                }
                item {
                    OutlinedFormTextField(
                        onValueChange = { newValue ->
                            secondNameValue = newValue ?: ""
                        },
                        label = "Ім'я",
                        placeholderText = "Введіть своє ім'я",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        minLength = 5
                    )
                }
                item {
                    OutlinedFormTextField(
                        onValueChange = { newValue ->
                            patronymicValue = newValue ?: ""
                        },
                        label = "По батькові",
                        placeholderText = "Введіть по батькові",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        minLength = 5
                    )
                }
                item {
                    OutlinedFormEmailField(
                        onValueChange = { newValue ->
                            emailValue = newValue ?: ""
                        },
                        label = "Email",
                        placeholderText = "Введіть ваш email",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    )
                }
                item {
                    OutlinedFormPasswordField(
                        onValueChange = { newValue ->
                            passwordValue = newValue ?: ""
                        },
                        label = "Пароль",
                        placeholderText = "Введіть ваш пароль",
                        minLength = 5,
                        maxLength = 15,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                }
                item {
                    OutlinedFormPasswordField(
                        onValueChange = { newValue ->
                            confirmPasswordValue = newValue ?: ""
                        },
                        label = "Підтвердження паролю",
                        placeholderText = "Повторіть ваш пароль",
                        minLength = 5,
                        maxLength = 15,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    )
                }
            }


            Column(modifier = Modifier.weight(2f)){
                Button(
                    onClick = {
                        viewModel.checkUserEmail(emailValue)
                    },
                    enabled = firstNameValue != ""
                            && secondNameValue != ""
                            && patronymicValue != ""
                            && emailValue != ""
                            && passwordValue != ""
                            && confirmPasswordValue != ""
                            && confirmPasswordValue == passwordValue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Text(text = "Продовжити", style = MaterialTheme.typography.bodyMedium)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Divider(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.Center) {
                        Text(text = "Вже є акаунту?")
                    }
                    Divider(modifier = Modifier.weight(1f))
                }

                Button(
                    onClick = { navigateToLoginListScreen() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = "Увійти",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }


        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "© Copyright 2023 my testing application",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Designed up by me",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    if (showErrorDialog) {
        ErrorPopup(title = "Помилка", message = "Даний email вже зайнятий") {
            showErrorDialog = false
            viewModel.clearEmailInUse()
        }
    }
}