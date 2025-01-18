package com.gravity.billeasy.ui_layer.app_screens.base_screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.gravity.billeasy.ui_layer.BillEasyOutlineTextField
import com.gravity.billeasy.ui_layer.EditableFields
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.PRODUCT_NAME

@Composable
fun EditShop(shopDetailsMapper: HashMap<String, EditableFields>) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(shopDetailsMapper.toList()) { field ->
            val focusRequestedModifier =
                if (field.first == PRODUCT_NAME) Modifier.focusRequester(focusRequester) else Modifier
            BillEasyOutlineTextField(
                label = field.first,
                value = field.second.fieldName.value,
                onValueChange = { it ->
                    field.second.fieldName.value = it
                },
                focusRequestedModifier = focusRequestedModifier,
                focusManager = focusManager,
                isError = field.second.isError.value
            )
        }
    }
}