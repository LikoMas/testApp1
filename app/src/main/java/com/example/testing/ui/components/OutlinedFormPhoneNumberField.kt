package com.example.testing.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OutlinedFormPhoneNumberField(
    modifier: Modifier = Modifier,
    onValueChange: (String?) -> Unit,
    label: String? = null,
    placeholderText: String? = null
) {

    val phoneNumberRegex = Regex("^([0-9]){9,12}$")
    BaseFormField(
        modifier = modifier,
        onValueChange = onValueChange,
        onValueCheck = { value ->
            if (!value.matches(phoneNumberRegex)) "Невірно вказаний номер телефону"
            else ""
        },
        keyboardType = KeyboardType.Phone,
        label = label,
        placeholderText = placeholderText
    )
}