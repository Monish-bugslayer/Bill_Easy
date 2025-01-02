package com.gravity.billeasy.ui_layer

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gravity.billeasy.R
import com.gravity.billeasy.appColor
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
    onImportComplete: (List<Product>) -> Unit,
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
                context = LocalContext.current,
                onImportComplete = onImportComplete
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
    context: Context,
    onImportComplete: (List<Product>) -> Unit
) {

    val importAndExportData: ImportAndExportData = remember { ImportAndExportData(context) }
    val coroutineScope = rememberCoroutineScope()

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val productList = importAndExportData.importFile(uri, context)
                    onImportComplete(productList)
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier.padding(start = 10.dp),
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            label = placeHolderText,
            keyboardOptions = keyboardOption,
            keyboardActions = keyboardActions
        )
        ColumOptions(
            onImportClick = { filePicker.launch("application/json") },
            onDownloadClick = {
                importAndExportData.writeTextToFile(allProducts, coroutineScope)
                Toast.makeText(context, "Download started", Toast.LENGTH_LONG).show()
            }
        )
    }
}

@Composable
fun ColumOptions(onImportClick: () -> Unit, onDownloadClick: () -> Unit) {
    var expanded = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            containerColor = colorResource(R.color.white)
        ) {
            DropdownMenuItem(
                leadingIcon = { Icon(
                    painter = painterResource(R.drawable.import_file),
                    contentDescription = "import data"
                ) },
                text = { Text("Import data") },
                onClick = {
                    onImportClick()
                    expanded.value = false
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                color = colorResource(R.color.white)
            )

            DropdownMenuItem(
                leadingIcon = { Icon(
                    painter = painterResource(R.drawable.baseline_download_24),
                    contentDescription = "Download data"
                ) },
                text = { Text("Download data") },
                onClick = {
                    onDownloadClick()
                    expanded.value = false
                }
            )
        }
    }
}