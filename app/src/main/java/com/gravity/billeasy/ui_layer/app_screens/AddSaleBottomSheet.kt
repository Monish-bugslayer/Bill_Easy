package com.gravity.billeasy.ui_layer.app_screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.ripple
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
    BillEasyBottomSheet(
        listState = listState,
        sheetHeader = CREATE_SALE,
        onDoneClick = { true },
        onDismiss = onDismiss
    ) {
        AddSaleBottomSheetContent(appViewModel, listState = listState)
    }
}


@Composable
fun AddSaleBottomSheetContent(
    viewModel: AppViewModel,
    listState: LazyListState
) {
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchQuery = viewModel.searchQuery
    val products = viewModel.allProducts.value
    CustomSearchBar(
        searchQuery = searchQuery,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
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
                IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
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
                        modifier = Modifier.width(80.dp).padding(end = 8.dp)
                    )

                    CounterBox()

                    // TODO: Need to get the selected option price( wholesale or retail )
                    Text(
                        text = "Total ₹ ${product.retailPrice}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
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
        var productCount = 0
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, radius = 100.dp)
                ){ productCount-- }
        )
        Text(
            text = productCount.toString(),
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, radius = 100.dp)
                ){ productCount++ }
        )
    }
}

// TODO: try to send Map<String, Double> in place of radio option so that by returing the key we
//  can get the value in total price text
@Composable
fun RadioButtonAndText(retailPrice: String, wholeSalePrice: String) {
    val radioOptions = listOf<String>(
        "Retail Price: ₹ $retailPrice", "Wholesale Price: ₹ $wholeSalePrice"
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
//    its equivalent
//    val state = remember { mutableStateOf(radioOptions[0]) }
//    val selectedOption = state.value
//    val onOptionSelected = { newValue: String -> state.value = newValue }
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