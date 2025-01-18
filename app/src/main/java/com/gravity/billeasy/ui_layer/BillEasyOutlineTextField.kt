package com.gravity.billeasy.ui_layer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.decideKeyboardType

@Composable
fun BillEasyOutlineTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
    isError: Boolean,
    focusRequestedModifier: Modifier
) {
    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) },
        maxLines = 1,
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = "$label should not be empty")
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = decideKeyboardType(label),
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 20.dp, start = 20.dp)
            .then(focusRequestedModifier)
    )
}