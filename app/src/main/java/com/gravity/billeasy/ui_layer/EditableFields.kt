package com.gravity.billeasy.ui_layer

import androidx.compose.runtime.MutableState

data class EditableFields(
    val fieldName: MutableState<String>,
    var isError: MutableState<Boolean>
)
