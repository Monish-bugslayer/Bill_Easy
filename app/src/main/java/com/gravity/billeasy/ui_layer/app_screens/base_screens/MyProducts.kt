package com.gravity.billeasy.ui_layer.app_screens.base_screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.viewmodel.SearchViewModel

@Composable
fun MyProducts() {
    val searchViewModel by remember { mutableStateOf(SearchViewModel()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchProduct(searchViewModel)
    }
}

/*
Creating two functions one is manages the
states and state updates and passing the data to the stateless function
*/

@Composable
fun SearchProduct(viewModel: SearchViewModel) {
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    SearchableColumn(searchQuery = viewModel.searchQuery,
        searchResults = searchResults,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableColumn(
    searchQuery: String, searchResults: List<Product>, onSearchQueryChange: (String) -> Unit
) {
    var expandedProductId by remember { mutableStateOf<Int?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = { keyboardController?.hide() },
        placeholder = {
            Text(text = "Search your product")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = "Search"
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
        content = {
            if (searchResults.isEmpty()) {
                ProductNotAvailable()
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(count = searchResults.size, key = { searchResults[it].productId }) {
                    val product = searchResults[it]
                    ProductCard(product = product,
                        isExpanded = expandedProductId == product.productId,
                        onCardClick = {
                            expandedProductId =
                                if (expandedProductId == product.productId) null else product.productId
                        })
                }
            }
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp,
        colors = SearchBarDefaults.colors(containerColor = Color.White)
    )
}

@Composable
fun ProductNotAvailable() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "No products found", style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Try adjusting your search or add a new product",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProductCard(product: Product, isExpanded: Boolean, onCardClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onCardClick() },
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
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
                        .weight(2f)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "Qty: ${product.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "₹ ${product.retailPrice}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Category: ${product.productCategory}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = "Unit: ${product.unit}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Available Stock: ${product.availableStock}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Buying Price: ₹ ${product.buyingPrice}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Wholesale Price: ₹ ${product.wholeSalePrice}",
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}