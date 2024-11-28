package com.gravity.billeasy.ui_layer

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gravity.billeasy.appColor
import com.gravity.billeasy.data.model.Product
import com.gravity.billeasy.viewmodel.SearchViewModel

@Composable
fun MyProducts() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
//        SearchState()
//        ProductCardList()
    }
}

/*
Creating two functions one is manages the
states and state updates and passing the data to the stateless function
*/

@Composable
fun SearchState(viewModel: SearchViewModel) {
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    Search(searchQuery = viewModel.searchQuery,
        searchResults = searchResults,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    searchQuery: String, searchResults: List<Product>, onSearchQueryChange: (String) -> Unit
) {
    SearchBar(query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = {},
        placeholder = {
            Text(text = "Search movies")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = ""
            )
        },
        trailingIcon = {},
        content = {},
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp,
        colors = SearchBarDefaults.colors(containerColor = appColor)
    )
}

@Composable
fun ProductCardList(products: List<Product>) {
    var expandedProductId by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product,
                isExpanded = expandedProductId == product.productId,
                onCardClick = {
                    expandedProductId =
                        if (expandedProductId == product.productId) null else product.productId
                })
        }
    }
}

@Composable
fun ProductCard(product: Product, isExpanded: Boolean, onCardClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
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
                    text = "$${product.retailPrice}",
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
                    text = "Buying Price: $${product.buyingPrice}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Wholesale Price: $${product.wholeSalePrice}",
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}


@Preview
@Composable
fun PreviewProductCardList() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        val sampleProducts = listOf(
            Product(1, "Laptop", "Electronics", "pcs", 50, 10, 14.99f, 19.99f, 17.49f),
            Product(2, "Headphonescvbhjhgfghj", "Accessories", "pcs", 30, 5, 7.99f, 12.49f, 10.99f),
            Product(3, "Mouse", "Electronics", "pcs", 100, 25, 3.99f, 5.99f, 4.99f)
        )
        ProductCardList(products = sampleProducts)
    }
}