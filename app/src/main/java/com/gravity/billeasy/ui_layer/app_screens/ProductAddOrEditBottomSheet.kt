package com.gravity.billeasy.ui_layer.app_screens

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gravity.billeasy.R
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.viewmodel.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val ADD_YOUR_PRODUCT = "Add your product"
private const val EDIT_YOUR_PRODUCT = "Edit your product"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddOrEditBottomSheet(
    isForAdd: Boolean, viewModel: ProductViewModel, product: Product?, onDismiss: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val bottomSheetScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val keyboardCoroutineScope = rememberCoroutineScope()
    var isNeedToDismiss by remember { mutableStateOf(false) }
    val currentView = LocalView.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { listState.firstVisibleItemIndex == 0 || isNeedToDismiss })
    val isKeyboardVisible by remember {
        mutableStateOf(keyboardAsState(currentView, keyboardCoroutineScope).value)
    }
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
    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = colorResource(R.color.white),
        onDismissRequest = {
            bottomSheetScope.launch {
                if(isKeyboardVisible) {
                    keyboardController?.hide()
                    delay(200)
                    onDismiss()
                } else {
                    onDismiss()
                }
            }
        }) {
        Box(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp * 0.85).dp)) {
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 0.7f
                        )
                    }
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = if (isForAdd) ADD_YOUR_PRODUCT else EDIT_YOUR_PRODUCT,
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Image(imageVector = Icons.Default.Check,
                        colorFilter = ColorFilter.tint(colorResource(R.color.black)),
                        contentDescription = "",
                        alignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable {
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
                                    if (isForAdd) viewModel.addProduct(newProduct) else viewModel.editProduct(
                                        newProduct
                                    )
                                    bottomSheetScope.launch {
                                        isNeedToDismiss = true
                                        if (isKeyboardVisible) {
                                            println("YEah")
                                            keyboardController?.hide()
                                            delay(200)
                                            sheetState.hide()
                                        } else {
                                            sheetState.hide()
                                        }
                                    }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            onDismiss()
                                        }
                                    }
                                }
                            })
                }
                ProductAddOrEditScreen(
                    productFieldMapper = addProductFieldsMap, listState = listState
                )
            }
        }
    }
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


fun keyboardAsState(currentView: View, coroutineScope: CoroutineScope): State<Boolean> {
    val keyboardState = mutableStateOf(false)
    coroutineScope.launch {
        ViewCompat.setOnApplyWindowInsetsListener(currentView) { _, insets ->
            keyboardState.value = insets.isVisible(WindowInsetsCompat.Type.ime())
            insets
        }
    }
    return keyboardState
}
