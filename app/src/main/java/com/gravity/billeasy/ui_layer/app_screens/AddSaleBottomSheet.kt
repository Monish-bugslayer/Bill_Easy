package com.gravity.billeasy.ui_layer.app_screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gravity.billeasy.R
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.BillEasyBottomSheet
import com.gravity.billeasy.ui_layer.CustomSearchBar
import com.gravity.billeasy.ui_layer.app_screens.base_screens.NO_PRODUCTS_STRING_1
import com.gravity.billeasy.ui_layer.app_screens.base_screens.NO_PRODUCTS_STRING_2
import com.gravity.billeasy.ui_layer.app_screens.base_screens.ProductNotAvailable
import com.gravity.billeasy.ui_layer.app_screens.base_screens.SEARCH_RESULT_NOT_FOUND_STRING_1
import com.gravity.billeasy.ui_layer.app_screens.base_screens.SEARCH_RESULT_NOT_FOUND_STRING_2
import com.gravity.billeasy.ui_layer.app_screens.base_screens.SEARCH_YOUR_PRODUCT
import com.gravity.billeasy.ui_layer.viewmodel.AppViewModel

const val CREATE_SALE = "Create sale"

@Composable
fun AddSaleBottomSheet(appViewModel: AppViewModel, onDismiss: () -> Unit) {
    val listState = rememberLazyListState()
    val searchResults by appViewModel.searchResults.collectAsStateWithLifecycle()
    println("appViewModel.searchQuery ${appViewModel.searchQuery}")
    println("searchResults in sale ${searchResults}")
    BillEasyBottomSheet(
        listState = listState,
        sheetHeader = CREATE_SALE,
        onDoneClick = { true },
        onDismiss = onDismiss
    ) {
        AddSaleBottomSheetContent(
            searchQuery = appViewModel.searchQuery,
            searchResults = searchResults,
            onSearchQueryChange = {
                println("it sale ${it}")
                appViewModel.onSearchQueryChange(it) },
            products = appViewModel.allProducts.value,
            listState = listState
        )
    }
}


@Composable
fun AddSaleBottomSheetContent(
    searchQuery: String,
    searchResults: List<Product>,
    onSearchQueryChange: (String) -> Unit,
    products: List<Product>,
    listState: LazyListState
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    CustomSearchBar(searchQuery = searchQuery,
        onSearchQueryChange = { onSearchQueryChange },
        onSearch = { keyboardController?.hide() },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = colorResource(R.color.black)
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        placeHolderText = { Text(text = SEARCH_YOUR_PRODUCT) }) {
        if (products.isEmpty()) {
            ProductNotAvailable(NO_PRODUCTS_STRING_1, NO_PRODUCTS_STRING_2)
        } else if (searchResults.isEmpty()) {
            ProductNotAvailable(SEARCH_RESULT_NOT_FOUND_STRING_1, SEARCH_RESULT_NOT_FOUND_STRING_2)
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(top = 15.dp, bottom = 10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(count = searchResults.size, key = { searchResults[it].productId }) {
                    val product = searchResults[it]
                    SaleProductCard(product)
                }
            }
        }
    }
}

@Composable
fun SaleProductCard(product: Product) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .animateContentSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.productName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )

                    CounterBox()

                    Text(
                        text = "Total ₹ ${0.00}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(2f),
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(IntrinsicSize.Max)
                ) {
                    Column(modifier = Modifier.selectableGroup()) {
                        Text(
                            text = "Qty: ${product.quantity}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = "Category: ${product.productCategory}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Unit: ${product.unit}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Available Stock: ${product.availableStock}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Buying Price: ₹ ${product.buyingPrice}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        RadioButtonAndText(
                            product.retailPrice.toString(), product.wholeSalePrice.toString()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CounterBox(){
    Row(modifier = Modifier.border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.primary
    )) {
        val iconBorderColor = MaterialTheme.colorScheme.primary
        Icon(
            painter = painterResource(R.drawable.baseline_remove_24),
            contentDescription = "remove",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 5.dp, top = 2.dp, bottom = 2.dp, end = 5.dp)
                .drawBehind {
                    drawLine(
                        start = Offset(x = size.width, y = 0f),
                        end = Offset(x = size.width, y = size.height),
                        color = iconBorderColor
                    )
            }
        )
        Text(
            text = "0",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 5.dp, top = 2.dp, bottom = 2.dp, end = 5.dp))
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "remove",
            modifier = Modifier.align(Alignment.CenterVertically)
                .padding(start = 5.dp, top = 2.dp, bottom = 2.dp, end = 5.dp)
                .drawBehind {
                    drawLine(
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = size.height),
                        color = iconBorderColor
                    )
            }
        )
    }
}

@Composable
fun RadioButtonAndText(
    retailPrice: String, wholeSalePrice: String
) {
    val radioOptions = listOf<String>(
        "Wholesale Price: ₹ $wholeSalePrice", "Retail Price: ₹ $retailPrice"
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    radioOptions.forEach { text ->
        Row(
            Modifier
                .fillMaxWidth()
                .selectable(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) },
                    role = Role.RadioButton
                ).padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = (text == selectedOption), onClick = null)
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }

}