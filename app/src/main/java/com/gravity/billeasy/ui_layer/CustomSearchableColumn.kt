package com.gravity.billeasy.ui_layer

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gravity.billeasy.R
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.data_import_export.ImportAndExportData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CustomSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit),
    trailingIcon: @Composable (() -> Unit),
    placeHolderText: @Composable (() -> Unit),
    onSearch: (String) -> Unit,
    keyboardOption: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions(onSearch = { onSearch(searchQuery) }),
    isNeedDownloadDataAction: Boolean = false,
    allProducts: List<Product>? = null,
    content: @Composable (ColumnScope.() -> Unit)

) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 10.dp)) {
        if (isNeedDownloadDataAction) {
            SearchBarWithCustomActions (
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                placeHolderText = placeHolderText,
                onSearch = onSearch,
                keyboardOption = keyboardOption,
                keyboardActions = keyboardActions,
                allProducts = allProducts!!,
                context = LocalContext.current
            )
        } else {
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
        }
        content()
    }
}

@Composable
fun SearchBarWithCustomActions(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit),
    trailingIcon: @Composable (() -> Unit),
    placeHolderText: @Composable (() -> Unit),
    onSearch: (String) -> Unit,
    keyboardOption: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions: KeyboardActions = KeyboardActions(onSearch = { onSearch(searchQuery) }),
    allProducts: List<Product>,
    context: Context
) {

    val importAndExportData: ImportAndExportData = remember { ImportAndExportData(context) }
    val coroutineScope = rememberCoroutineScope()

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            coroutineScope.launch(Dispatchers.IO) {
                try { importAndExportData.importFile(uri) } catch (e: Exception) {
                    println(e.message)
                }
            }
        }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            label = placeHolderText,
            keyboardOptions = keyboardOption,
            keyboardActions = keyboardActions
        )
        Image(painter = painterResource(R.drawable.export),
            contentDescription = "Download products",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, radius = 100.dp)
                ) {
                    // TODO need to listen download start and complete to avoid multiple clicks and multiple downloads
                    val jSonResponse = importAndExportData.convertClassToJson(allProducts)
                    importAndExportData.writeTextToFile(jSonResponse, coroutineScope)
                    Toast
                        .makeText(context, "Download started", Toast.LENGTH_LONG)
                        .show()
                }
        )
        Image(painter = painterResource(R.drawable.import_file),
            contentDescription = "Import products",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, radius = 100.dp)
                ) { filePicker.launch("*/*") }
        )

    }
}