package com.example.testing.ui.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.testing.ui.components.OutlinedFormEmailField
import com.example.testing.ui.components.OutlinedFormPasswordField

@Composable
fun LoginScreen(
    navigateToPostsListScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showErrorDialog by remember { mutableStateOf(false) }

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    LaunchedEffect(uiState.userMatches) {
        if (uiState.userMatches == true) {
            navigateToPostsListScreen()
        } else if (uiState.userMatches == false) {
            showErrorDialog = true
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
                .weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                text = "Логін до додатку",
                style = MaterialTheme.typography.headlineLarge
            )
            OutlinedFormEmailField(
                onValueChange = { newValue ->
                    emailValue = newValue ?:  ""
                },
                label = "Email",
                placeholderText = "Введіть ваш email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            OutlinedFormPasswordField(
                onValueChange = { newValue ->
                    passwordValue = newValue ?: ""
                },
                label = "Пароль",
                placeholderText = "Введіть пароль",
                minLength = 5,
                maxLength = 15,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Button(
                onClick = {
                    viewModel.checkUser(email = emailValue, password = passwordValue)
                },
                enabled = emailValue != "" && passwordValue != "" ,
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
                    Text(text = "Немає акаунту?")
                }
                Divider(modifier = Modifier.weight(1f))
            }

            Button(
                onClick = { navigateToRegisterScreen() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = "Реєстрація",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
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
        ErrorPopup(title = "Помилка", message = "Невірно вказано email або пароль") {
            showErrorDialog = false
            viewModel.clearUserMatches()
        }
    }
}

@Composable
fun ErrorPopup(
    title: String,
    message: String,
    onClose: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onClose() },
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(
                onClick = { onClose() },
                content = { Text("OK") }
            )
        }
    )
}