package com.gravity.billeasy.ui_layer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gravity.billeasy.data_layer.models.Product

@Composable
fun CustomSearchBar(
    productsList: List<Product>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit),
    trailingIcon: @Composable (() -> Unit),
    placeHolderText: @Composable (() -> Unit),
    onSearch: (String) -> Unit,
    keyboardOption: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions(onSearch = { onSearch(searchQuery) }),
    content: @Composable (ColumnScope.() -> Unit)

) {
    Column(modifier = Modifier.fillMaxSize().padding(top = 10.dp, start = 10.dp, end = 10.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            label = placeHolderText,
            keyboardOptions = keyboardOption,
            keyboardActions = keyboardActions
        )
        content()
    }
}