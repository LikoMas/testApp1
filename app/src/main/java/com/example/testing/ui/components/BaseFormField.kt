package com.example.testing.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BaseFormField(
    modifier: Modifier = Modifier,
    onValueChange: (String?) -> Unit,
    onValueCheck: (String) -> String,
    label: String? = null,
    placeholderText: String? = null,
    keyboardType: KeyboardType
) {
    var errorText by remember { mutableStateOf("") }
    var triggered by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var value by remember { mutableStateOf("") }

    LaunchedEffect(key1 = value){
        errorText = onValueCheck(value)
    }

    Column(modifier = modifier.focusRequester(focusRequester = focusRequester)) {
        OutlinedTextField(
            value = value, onValueChange = { newValue ->

                value = newValue
                if (!triggered) triggered = true
                onValueChange(if (value != "" && errorText == "") value else null)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { label?.let { Text(text = label) } },
            placeholder = { placeholderText?.let { Text(text = placeholderText) } },
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(onClick = {
                        value = ""
                        onValueChange(null)
                        focusManager.clearFocus()
                    }) { Icon(imageVector = Icons.Default.Close, contentDescription = null) }
                }
            },
            isError = errorText.isNotEmpty() && triggered,
            supportingText = { if (errorText.isNotEmpty() && triggered) Text(text = errorText) },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    errorText.takeIf { it.isEmpty() }?.let {
                        onValueChange(value)
                    }
                }
            ),
            singleLine = true
        )
    }
}