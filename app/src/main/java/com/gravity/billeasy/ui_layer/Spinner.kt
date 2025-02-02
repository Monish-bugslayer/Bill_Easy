package com.gravity.billeasy.ui_layer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gravity.billeasy.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    selectedValue: String,
    options: List<String>,
    label: String,
    modifier: Modifier = Modifier,
    onValueChangedEvent: (String) -> Unit,
    supportedString: String?,
    isError: Boolean,
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = modifier
    ) {
        Column {
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedValue,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                onValueChange = { },
                isError = isError,
                // TODO in productsAddOrEditScreen the new add form method is not used hence we are
                //  giving hadcoded string in supported text and it is always visible even without
                //  any error. By now i have avoid its visibility by adding isError. Once moved to
                //  new logic of add like we do it in Edit shop or AddBill, we can remove this isError condition
                supportingText = { if (supportedString != null && isError) Text(supportedString) },
                label = { Text(text = label) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
            )

            if (expanded) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(
                            LocalContext.current.resources.getColor(
                                R.color.orange_dim
                            )
                        )
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max)
                            .menuAnchor()
                            .verticalScroll(rememberScrollState())
                            .padding(top = 5.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        options.forEach { option ->
                            Text(text = option,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp)
                                    .clickable {
                                        expanded = false
                                        onValueChangedEvent(option)
                                    })
                        }
                    }
                }
            }
        }
    }
}

