package com.gravity.billeasy.ui_layer.app_screens

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.BillEasyBottomSheet
import com.gravity.billeasy.ui_layer.viewmodel.AppViewModel

private const val ADD_YOUR_PRODUCT = "Add your product"
private const val EDIT_YOUR_PRODUCT = "Edit your product"

@Composable
fun ProductAddOrEditBottomSheet(
    isForAdd: Boolean, viewModel: AppViewModel, product: Product?, onDismiss: () -> Unit
) {
    val listState = rememberLazyListState()
    val addProductFieldsMap = mutableMapOf<String, AddOrEditProductField>()
    viewModel.initUnitAndCategoryTable()
    val productName = remember { mutableStateOf(product?.productName ?: "") }
    val productCategory = remember { mutableStateOf(product?.productCategory ?: "") }
    val unit = remember { mutableStateOf(product?.unit ?: "") }
    val productId = remember {
        mutableStateOf(if (product?.productId == null) "" else product.productId.toString())
    }
    val availableStock = remember {
        mutableStateOf(
            if (product?.availableStock == null) "" else product.availableStock.toString()
        )
    }
    val quantity = remember {
        mutableStateOf(
            if (product?.quantity == null) "" else product.quantity.toString()
        )
    }
    val buyingPrice = remember {
        mutableStateOf(
            if (product?.buyingPrice == null) "" else product.buyingPrice.toString()
        )
    }
    val retailPrice = remember {
        mutableStateOf(
            if (product?.retailPrice == null) "" else product.retailPrice.toString()
        )
    }
    val wholeSalePrice = remember {
        mutableStateOf(
            if (product?.wholeSalePrice == null) "" else product.wholeSalePrice.toString()
        )
    }

    addProductFieldsMap.apply {
        put(PRODUCT_NAME, AddOrEditProductField(productName, remember { mutableStateOf(false) }))
        put(
            PRODUCT_CATEGORY,
            AddOrEditProductField(productCategory, remember { mutableStateOf(false) })
        )
        put(UNIT, AddOrEditProductField(unit, remember { mutableStateOf(false) }))
        put(
            AVAILABLE_STOCK,
            AddOrEditProductField(availableStock, remember { mutableStateOf(false) })
        )
        put(QUANTITY, AddOrEditProductField(quantity, remember { mutableStateOf(false) }))
        put(BUYING_PRICE, AddOrEditProductField(buyingPrice, remember { mutableStateOf(false) }))
        put(RETAIL_PRICE, AddOrEditProductField(retailPrice, remember { mutableStateOf(false) }))
        put(
            WHOLESALE_PRICE,
            AddOrEditProductField(wholeSalePrice, remember { mutableStateOf(false) })
        )
    }

    BillEasyBottomSheet(
        listState = listState,
        sheetHeader = if (isForAdd) ADD_YOUR_PRODUCT else EDIT_YOUR_PRODUCT,
        onDoneClick = {
            if (validateAddOrEditProductField(addProductFieldsMap.toList())) {
                val newProduct = Product(
                    productId = if (isForAdd) 0 else productId.value.toLong(),
                    productName = addProductFieldsMap.getValue(PRODUCT_NAME).fieldName.value,
                    productCategory = addProductFieldsMap.getValue(
                        PRODUCT_CATEGORY
                    ).fieldName.value,
                    unit = addProductFieldsMap.getValue(UNIT).fieldName.value,
                    availableStock = addProductFieldsMap.getValue(
                        AVAILABLE_STOCK
                    ).fieldName.value.toLong(),
                    quantity = addProductFieldsMap.getValue(QUANTITY).fieldName.value.toLong(),
                    buyingPrice = addProductFieldsMap.getValue(BUYING_PRICE).fieldName.value.toDouble(),
                    wholeSalePrice = addProductFieldsMap.getValue(
                        WHOLESALE_PRICE
                    ).fieldName.value.toDouble(),
                    retailPrice = addProductFieldsMap.getValue(RETAIL_PRICE).fieldName.value.toDouble()
                )
                if (isForAdd) viewModel.addProduct(newProduct) else viewModel.editProduct(newProduct)
                true
            }
            else { false }
        },
        onDismiss = onDismiss
    ) {
        ProductAddOrEditScreen(
            productFieldMapper = addProductFieldsMap, listState = listState
        )
    }

//    ModalBottomSheet(
//        sheetState = sheetState,
//        containerColor = colorResource(R.color.white),
//        onDismissRequest = {
//            bottomSheetScope.launch {
//                if(isKeyboardVisible) {
//                    keyboardController?.hide()
//                    delay(200)
//                    onDismiss()
//                } else {
//                    onDismiss()
//                }
//            }
//        }) {
//        Box(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp * 0.85).dp)) {
//            Column {
//                Row(modifier = Modifier
//                    .fillMaxWidth()
//                    .drawBehind {
//                        drawLine(
//                            color = Color.Gray,
//                            start = Offset(0f, size.height),
//                            end = Offset(size.width, size.height),
//                            strokeWidth = 0.7f
//                        )
//                    }
//                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween) {
//                    Text(
//                        text = if (isForAdd) ADD_YOUR_PRODUCT else EDIT_YOUR_PRODUCT,
//                        fontWeight = FontWeight.W500,
//                        fontSize = 18.sp,
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
//                    Image(imageVector = Icons.Default.Check,
//                        colorFilter = ColorFilter.tint(colorResource(R.color.black)),
//                        contentDescription = "",
//                        alignment = Alignment.CenterEnd,
//                        modifier = Modifier
//                            .padding(end = 10.dp)
//                            .clickable {
//                                if (validateAddOrEditProductField(addProductFieldsMap.toList())) {
//                                    val newProduct = Product(
//                                        productId = if (isForAdd) 0 else productId.value.toLong(),
//                                        productName = addProductFieldsMap.getValue(PRODUCT_NAME).fieldName.value,
//                                        productCategory = addProductFieldsMap.getValue(
//                                            PRODUCT_CATEGORY
//                                        ).fieldName.value,
//                                        unit = addProductFieldsMap.getValue(UNIT).fieldName.value,
//                                        availableStock = addProductFieldsMap.getValue(
//                                            AVAILABLE_STOCK
//                                        ).fieldName.value.toLong(),
//                                        quantity = addProductFieldsMap.getValue(QUANTITY).fieldName.value.toLong(),
//                                        buyingPrice = addProductFieldsMap.getValue(BUYING_PRICE).fieldName.value.toDouble(),
//                                        wholeSalePrice = addProductFieldsMap.getValue(
//                                            WHOLESALE_PRICE
//                                        ).fieldName.value.toDouble(),
//                                        retailPrice = addProductFieldsMap.getValue(RETAIL_PRICE).fieldName.value.toDouble()
//                                    )
//                                    if (isForAdd) viewModel.addProduct(newProduct) else viewModel.editProduct(
//                                        newProduct
//                                    )
//                                    bottomSheetScope.launch {
//                                        isNeedToDismiss = true
//                                        if (isKeyboardVisible) {
//                                            println("YEah")
//                                            keyboardController?.hide()
//                                            delay(200)
//                                            sheetState.hide()
//                                        } else {
//                                            sheetState.hide()
//                                        }
//                                    }.invokeOnCompletion {
//                                        if (!sheetState.isVisible) {
//                                            onDismiss()
//                                        }
//                                    }
//                                }
//                            })
//                }
//                ProductAddOrEditScreen(
//                    productFieldMapper = addProductFieldsMap, listState = listState
//                )
//            }
//        }
//    }
}

data class AddOrEditProductField(
    val fieldName: MutableState<String>,
    var isError: MutableState<Boolean>
)

fun validateAddOrEditProductField(fields: List<Pair<String, AddOrEditProductField>>): Boolean {
    while(true) {
        fields.forEach { field ->
            if (field.second.fieldName.value.isEmpty()) {
                field.second.isError.value = true
                return false
            }
        }
        return true
    }
}

