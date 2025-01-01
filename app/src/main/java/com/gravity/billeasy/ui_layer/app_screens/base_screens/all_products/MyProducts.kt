package com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gravity.billeasy.R
import com.gravity.billeasy.ShowOrHideBottomSheet
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.CustomSearchBar
import com.gravity.billeasy.ui_layer.viewmodel.AppViewModel

const val SEARCH_RESULT_NOT_FOUND_STRING_1 = "No products found"
const val SEARCH_RESULT_NOT_FOUND_STRING_2 = "Try adjusting your search or add a new product"
const val NO_PRODUCTS_STRING_1 = "Your shop is empty"
const val NO_PRODUCTS_STRING_2 = "Click the add icon below and fill your shop with products"
const val SEARCH_YOUR_PRODUCT = "Search your product"

@Composable
fun MyProducts(viewModel: AppViewModel) {
    val bottomSheetVisibility = remember { mutableStateOf(false) }
    val product = remember { mutableStateOf<Product?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        SearchProduct(viewModel, onEditProduct = {
            bottomSheetVisibility.value = true
            product.value = it
        })
        if(bottomSheetVisibility.value) {
            ShowOrHideBottomSheet(
                isNeedToShowAddSaleBottomSheet = null,
                isNeedToShowAddProductBottomSheet = bottomSheetVisibility,
                isForAdd = false,
                appViewModel = viewModel,
                product = product.value
            )
        }
    }
}

/*
Creating two functions one is manages the
states and state updates and passing the data to the stateless function
*/
@Composable
fun SearchProduct(appViewModel: AppViewModel, onEditProduct: (Product) -> Unit) {
    val searchResults by appViewModel.searchResults.collectAsStateWithLifecycle()
    SearchableColumn(
        products = appViewModel.allProducts.value,
        searchQuery = appViewModel.searchQuery,
        searchResults = searchResults,
        onSearchQueryChange = { appViewModel.onSearchQueryChange(it) },
        onDelete = { appViewModel.deleteProduct(it) },
        onEdit = onEditProduct
    )
}

@Composable
fun SearchableColumn(
    products: List<Product>,
    searchQuery: String,
    searchResults: List<Product>,
    onSearchQueryChange: (String) -> Unit,
    onDelete: (Product) -> Unit,
    onEdit: (Product) -> Unit
) {
    var expandedProductId by remember { mutableStateOf<Long?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current

    CustomSearchBar(
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        onSearch = { keyboardController?.hide() },
        isNeedDownloadDataAction = true,
        allProducts = products,
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
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(count = searchResults.size, key = { searchResults[it].productId }) {
                    val product = searchResults[it]
                    ProductCard(
                        product = product,
                        isExpanded = expandedProductId == product.productId,
                        onCardClick = {
                            expandedProductId =
                                if (expandedProductId == product.productId) null else product.productId
                        },
                        onDelete = onDelete,
                        onEdit = onEdit
                    )
                }
            }
        }
    }
}

@Composable
fun ProductNotAvailable(header: String, footer: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = header, style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = footer, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProductCard(
    product: Product,
    isExpanded: Boolean,
    onCardClick: () -> Unit,
    onDelete: (Product) -> Unit,
    onEdit: (Product) -> Unit
) {
    val isDeleted = remember { mutableStateOf(false) }
    var isEdit by remember { mutableStateOf(false) }
    val animationDuration = 500
    val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = {
        when(it) {
            SwipeToDismissBoxValue.StartToEnd -> {
                isEdit = true
                return@rememberSwipeToDismissBoxState true
            }
            else -> {
                return@rememberSwipeToDismissBoxState false
            }
        }
    })

    LaunchedEffect(isEdit) {
        if(isEdit) {
            onEdit(product)
            dismissState.reset()
            isEdit = false
        }
    }

    AnimatedVisibility(
        visible = !isDeleted.value,
        exit = shrinkVertically(animationSpec = tween(animationDuration), shrinkTowards = Alignment.Top)+ fadeOut()
    ) {

        SwipeToDismissBox(
            state = dismissState, backgroundContent = {
                Card {
                    if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
                        SwipeToDismissBoxBackground(
                            contentAlignment = Alignment.CenterStart,
                            actionIcon = R.drawable.edit,
                            actionText = "Edit product",
                            actionColor = Color.Blue
                        )
                    }
                }
            }, enableDismissFromEndToStart = false, enableDismissFromStartToEnd = true
        ) {
            SwipeToDismissBoxContent(product, onCardClick, isExpanded, onDelete, isDeleted)
        }
    }
}

@Composable
fun SwipeToDismissBoxContent(
    product: Product,
    onCardClick: () -> Unit,
    isExpanded: Boolean,
    onDelete: (Product) -> Unit,
    isDeleted: MutableState<Boolean>
) {
    var isNeedToShowDeleteAlertDialogue by remember { mutableStateOf(false) }
    if(isNeedToShowDeleteAlertDialogue) {
        showAlertDialogue(
            dialogTitle = "Delete product",
            dialogContentText = "Deleted product cannot be retrived, are you sure you want to delete it",
            confirmButtonText = "Delete",
            dismissButtonText = "Cancel",
            onConfirmRequest = {
                onDelete(product)
                isNeedToShowDeleteAlertDialogue = false
                isDeleted.value = true
            },
            onDismissRequest = { isNeedToShowDeleteAlertDialogue = false }
        )
    }
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

                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Box(modifier = Modifier.fillMaxHeight().width(IntrinsicSize.Max)) {
                        Column {
                            Text(
                                text = "Category: ${product.productCategory}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Unit: ${product.unit}", style = MaterialTheme.typography.bodyMedium
                            )
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
                    Image(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .align(Alignment.CenterVertically)
                            .clip(CircleShape)
                            .clickable(indication = ripple(bounded = true),
                                interactionSource = remember { MutableInteractionSource() }) {
                                isNeedToShowDeleteAlertDialogue = true
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun showAlertDialogue(
    dialogTitle: String,
    dialogContentText: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {
    AlertDialog(
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogContentText) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmRequest() }) {
                Text(text = confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = dismissButtonText)
            }
        }
    )

}


@Composable
fun SwipeToDismissBoxBackground(
    contentAlignment: Alignment, actionText: String, actionIcon: Int, actionColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = contentAlignment
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = actionText,
                textAlign = TextAlign.Center,
                color = actionColor,
                modifier = Modifier.padding(start = 16.dp)
            )
            Icon(
                painter = painterResource(actionIcon),
                contentDescription = actionText,
                tint = actionColor,
            )
        }
    }
}