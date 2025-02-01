package com.gravity.billeasy.ui_layer.app_screens.base_screens.sales

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gravity.billeasy.R
import com.gravity.billeasy.data_layer.models.OrderedProduct
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.data_layer.models.Sale
import com.gravity.billeasy.data_layer.models.SaleValidationState
import com.gravity.billeasy.ui_layer.BillEasyBottomSheet
import com.gravity.billeasy.ui_layer.BillEasyOutlineTextField
import com.gravity.billeasy.ui_layer.BillEasyOutlineTextFieldCustomizer
import com.gravity.billeasy.ui_layer.CustomSearchBar
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.NO_PRODUCTS_STRING_1
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.NO_PRODUCTS_STRING_2
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.ProductNotAvailable
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.SEARCH_RESULT_NOT_FOUND_STRING_1
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.SEARCH_RESULT_NOT_FOUND_STRING_2
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.SEARCH_YOUR_PRODUCT
import com.gravity.billeasy.ui_layer.viewmodel.SalesViewModel

const val CREATE_SALE = "Create sale"
const val RETAIL_PRICE = "Retail price"
const val WHOLESALE_PRICE = "Wholesale price"
const val CUSTOMER_NAME = "Customer name"
const val BILLING_DATE = "Billing date"
const val BILL_TYPE = "Bill type"
const val PAYMENT_METHOD = "Payment method"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSaleBottomSheet(salesViewModel: SalesViewModel, onDismiss: () -> Unit) {
    val listState = rememberLazyListState()
    BillEasyBottomSheet(
        sheetHeader = CREATE_SALE,
        // Need to show next button only if any modifications in the list are made
        //orderedProducts.value.isNotEmpty()
        isNeedNextButton = false,
        onDoneClick = { true },
        onDismiss = onDismiss,
    ) {
        AddSaleBottomSheetContent(salesViewModel, listState = listState)
    }
}

@Composable
fun BillDetails() {
    val sale = remember { mutableStateOf<Sale>(Sale()) }
    val onCustomNameChanged = { updatedValue: String ->
        sale.value = sale.value.copy(customerName = updatedValue)
    }
    val onBillingDateChanged = { updatedValue: String ->
        sale.value = sale.value.copy(billingDate = updatedValue)
    }
    val onBillTypeChanged = { updatedValue: String ->
        sale.value = sale.value.copy(billType = updatedValue)
    }
    val onPaymentMethodChanged = { updatedValue: String ->
        sale.value = sale.value.copy(paymentMethod = updatedValue)
    }
    val errorStates = remember { mutableStateOf(SaleValidationState()) }
    val billDetailsMapper = mutableMapOf<String, Pair<(String) -> Unit, String>>()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    billDetailsMapper.apply {
        put(CUSTOMER_NAME, Pair(onCustomNameChanged, sale.value.customerName))
        put(BILLING_DATE, Pair(onBillingDateChanged, sale.value.billingDate))
        put(BILL_TYPE, Pair(onBillTypeChanged, sale.value.billType))
        put(PAYMENT_METHOD, Pair(onPaymentMethodChanged, sale.value.paymentMethod))
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        billDetailsMapper.toList().forEach { pair ->
            val focusRequestedModifier =
                if (pair.first == CUSTOMER_NAME) Modifier.focusRequester(focusRequester)
                else Modifier
            val error = when (pair.first) {
                CUSTOMER_NAME -> errorStates.value.customerNameError
                BILLING_DATE -> errorStates.value.billingDateError
                BILL_TYPE -> errorStates.value.billTypeError
                PAYMENT_METHOD -> errorStates.value.paymentMethodError
                else -> null
            }
            val billEasyTextCustomizer = BillEasyOutlineTextFieldCustomizer(
                trailingIcon = if(pair.first == BILLING_DATE) {
                    @Composable {
                        Icon(painterResource(R.drawable.calendar), "billing date")
                    }
                } else null,
                paddingStart = 0.dp,
                paddingTop = 0.dp
            )
            BillEasyOutlineTextField(
                label = pair.first,
                value = pair.second.second,
                onValueChange = { pair.second.first(it) },
                focusManager = focusManager,
                isError = error != null,
                errorMessage = null,
                billEasyOutlineTextFieldCustomizer = billEasyTextCustomizer,
                focusRequestedModifier = focusRequestedModifier
            )

        }
    }
}


@Composable
fun AddSaleBottomSheetContent(
    salesViewModel: SalesViewModel, listState: LazyListState
) {
    val searchResults by salesViewModel.searchResults.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchQuery = salesViewModel.searchQuery
    val products = salesViewModel.allProducts.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
    ) {
        BillDetails()
        CustomSearchBar(searchQuery = searchQuery,
            onSearchQueryChange = { salesViewModel.onSearchQueryChange(it) },
            onSearch = { keyboardController?.hide() },
            onImportComplete = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = colorResource(R.color.black)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { salesViewModel.onSearchQueryChange("") }) {
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
                ProductNotAvailable(
                    SEARCH_RESULT_NOT_FOUND_STRING_1, SEARCH_RESULT_NOT_FOUND_STRING_2
                )
            } else {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    contentPadding = PaddingValues(top = 15.dp, bottom = 10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(count = searchResults.size, key = { searchResults[it].productId }) {
                        val product = searchResults[it]
                        SaleProductCard(product, salesViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun SaleProductCard(product: Product, salesViewModel: SalesViewModel) {
    val orderedCount = remember { mutableIntStateOf(0) }
    val orderedProducts = mutableMapOf<Long, OrderedProduct>()
    val selectedPerUnitPrice = remember { mutableDoubleStateOf(0.0) }
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
                            .width(80.dp)
                            .padding(end = 8.dp)
                    )

                    CounterBox { count ->
                        orderedCount.intValue = count
                        val orderedProduct = OrderedProduct(
                            productId = product.productId,
                            productName = product.productName,
                            productCategory = product.category,
                            pricePerUnit = selectedPerUnitPrice.doubleValue,
                            orderTotal = selectedPerUnitPrice.doubleValue.times(orderedCount.intValue),
                            orderedQuantity = orderedCount.intValue
                        )
                        if (orderedCount.intValue > 0) {
                            orderedProducts[orderedProduct.productId] = orderedProduct
                        } else if (orderedCount.intValue == 0 && orderedProducts.containsKey(
                                orderedProduct.productId
                            )
                        ) {
                            orderedProducts.remove(orderedProduct.productId)
                        }

                        salesViewModel.updateOrderedProducts(orderedProducts)
                    }

                    Text(
                        text = "Total ₹ ${selectedPerUnitPrice.doubleValue.times(orderedCount.intValue)}",
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
                            text = "Category: ${product.category}",
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
                        ) { selectedPrice ->
                            selectedPerUnitPrice.doubleValue = selectedPrice
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CounterBox(onEditProductCount: (Int) -> Unit) {
    Row(
        modifier = Modifier.border(
            width = 1.dp, color = MaterialTheme.colorScheme.primary
        ), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val iconBorderColor = MaterialTheme.colorScheme.primary
        var productCount = remember { mutableIntStateOf(0) }
        Icon(painter = painterResource(R.drawable.baseline_remove_24),
            contentDescription = "remove product count",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(5.dp)
                .drawBehind {
                    drawLine(
                        start = Offset(x = size.width, y = 0f),
                        end = Offset(x = size.width, y = size.height),
                        color = iconBorderColor
                    )
                }
                .clip(CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, radius = 100.dp)
                ) {
                    if (productCount.intValue != 0) {
                        productCount.intValue--
                        onEditProductCount(productCount.intValue)
                    }
                })
        Text(
            text = productCount.intValue.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(5.dp)
        )
        Icon(imageVector = Icons.Default.Add,
            contentDescription = "add product count",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(5.dp)
                .drawBehind {
                    drawLine(
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = size.height),
                        color = iconBorderColor
                    )
                }
                .clip(CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, radius = 100.dp)
                ) {
                    productCount.intValue++
                    onEditProductCount(productCount.intValue)
                })
    }
}

@Composable
fun RadioButtonAndText(
    retailPrice: String, wholeSalePrice: String, onChoosePrice: (Double) -> Unit
) {
    val radioOptionMap = remember { mutableMapOf<String, Double>() }
    radioOptionMap.put(RETAIL_PRICE, retailPrice.toDouble())
    radioOptionMap.put(WHOLESALE_PRICE, wholeSalePrice.toDouble())
    val state = remember { mutableStateOf(radioOptionMap[RETAIL_PRICE]) }
    val selectedOption = state.value
    val onOptionSelected = { newValue: Double ->
        state.value = newValue
        onChoosePrice(newValue)
    }
    radioOptionMap.toList().forEach { pair ->
        Row(
            Modifier
                .fillMaxWidth()
                .selectable(
                    selected = (pair.second == selectedOption),
                    onClick = { onOptionSelected(pair.second) },
                    role = Role.RadioButton
                )
                .padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = (pair.second == selectedOption), onClick = null)
            Text(
                text = "${pair.first}: ₹ ${pair.second}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}