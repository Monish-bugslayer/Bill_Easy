package com.gravity.billeasy.Utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.gravity.billeasy.R
import com.gravity.billeasy.appColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    selectedValue: String,
    options: List<String>,
    label: String,
    modifier: Modifier = Modifier,
    onValueChangedEvent: (String) -> Unit,
    supportedString: String,
    isError: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = modifier
    ) {
        Column {
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = appColor,
                    focusedLabelColor = colorResource(R.color.black),
                    cursorColor = colorResource(R.color.black)
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedValue,
                onValueChange = { },
                isError = isError,
                supportingText = { if (isError) Text(supportedString) },
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
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .menuAnchor()
                            .padding(top = 5.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        items(options) { option ->
                            Text(text = option,
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

