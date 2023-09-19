package com.example.testing.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun OutlinedFormTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String?) -> Unit,
    minLength: Int = 5,
    maxLength: Int = 25,
    label: String? = null,
    placeholderText: String? = null
) {
    BaseFormField(
        modifier = modifier,
        onValueChange = onValueChange,
        onValueCheck = { value ->
            if (value.length !in minLength..maxLength) "$label повинне складатися з $minLength до $maxLength символів"
            else ""
        },
        keyboardType = KeyboardType.Text,
        label = label,
        placeholderText = placeholderText
    )
}