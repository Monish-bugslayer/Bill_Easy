package com.gravity.billeasy.ui_layer

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BillEasyOutlineTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
    isError: Boolean,
    errorMessage: String?,
    billEasyOutlineTextFieldCustomizer: BillEasyOutlineTextFieldCustomizer,
    focusRequestedModifier: Modifier
) {


    OutlinedTextField(colors = OutlinedTextFieldDefaults.colors(
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
        leadingIcon = billEasyOutlineTextFieldCustomizer.leadingIcon,
        trailingIcon = billEasyOutlineTextFieldCustomizer.trailingIcon,
        isError = isError,
        supportingText = {
            if (errorMessage != null) {
                Text(text = errorMessage)
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = decideKeyboardType(label),
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = billEasyOutlineTextFieldCustomizer.paddingTop,
                end = billEasyOutlineTextFieldCustomizer.paddingEnd,
                start = billEasyOutlineTextFieldCustomizer.paddingStart
            )
            .then(focusRequestedModifier)
    )
}

data class BillEasyOutlineTextFieldCustomizer(
    val leadingIcon: @Composable (() -> Unit)? = null,
    val trailingIcon: @Composable (() -> Unit)? = null,
    val paddingTop: Dp = 10.dp,
    val paddingEnd: Dp = 20.dp,
    val paddingStart: Dp = 20.dp
)