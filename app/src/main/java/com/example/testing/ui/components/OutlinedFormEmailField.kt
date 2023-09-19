package com.example.testing.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun OutlinedFormEmailField(
    modifier: Modifier = Modifier,
    onValueChange: (String?) -> Unit,
    minLength: Int = 5,
    maxLength: Int = 25,
    label: String? = null,
    placeholderText: String? = null
) {
    val emailRegex = Regex("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+[.]+[a-zA-Z]{2,})$")
    BaseFormField(
        modifier = modifier,
        onValueChange = onValueChange,
        onValueCheck = { value ->
            if (!value.matches(emailRegex)) "Невірно вказаний Email"
            else if (value.length !in minLength..maxLength) "Email повинен складатися з $minLength до $maxLength символів"
            else ""
        },
        keyboardType = KeyboardType.Email,
        label = label,
        placeholderText = placeholderText
    )
}