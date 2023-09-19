package com.example.testing.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun OutlinedFormPasswordField(
    modifier: Modifier = Modifier,
    onValueChange: (String?) -> Unit,
    minLength: Int = 5,
    maxLength: Int = 25,
    label: String? = null,
    placeholderText: String? = null
) {
    //val passwordRegex = Regex("^([a-zA-Z0-9._@-])$")
    BaseFormField(
        modifier = modifier,
        onValueChange = onValueChange,
        onValueCheck = { value ->
            //if (!value.matches(passwordRegex)) "Невірно вказаний пароль"
            if (value.length !in minLength..maxLength) "Пароль повинен складатися з $minLength до $maxLength символів"
            else ""
        },
        keyboardType = KeyboardType.Password,
        label = label,
        placeholderText = placeholderText
    )
}