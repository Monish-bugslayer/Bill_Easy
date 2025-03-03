package com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.gravity.billeasy.appdatastore.appPreferenceDataStore
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.BillEasyBottomSheet
import com.gravity.billeasy.ui_layer.EditableFields
import com.gravity.billeasy.ui_layer.validateField
import com.gravity.billeasy.ui_layer.viewmodel.ProductsViewModel
import kotlinx.coroutines.flow.collectLatest

private const val ADD_YOUR_PRODUCT = "Add your product"
private const val EDIT_YOUR_PRODUCT = "Edit your product"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddOrEditBottomSheet(
    isForAdd: Boolean,
    productsViewModel: ProductsViewModel,
    product: Product?,
    onDismiss: () -> Unit
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    var shopId = remember { mutableLongStateOf(0) }
    LaunchedEffect(Unit) {
       context.appPreferenceDataStore.data.collectLatest {
           if(it.currentLoggedInShopId != "") {
               shopId.longValue = it.currentLoggedInShopId.toLong()
           }
        }
    }
    val addProductFieldsMap = mutableMapOf<String, EditableFields>()
    val productName = remember { mutableStateOf(product?.productName ?: "") }
    val productCategory = remember { mutableStateOf(product?.category ?: "") }
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
        put(PRODUCT_NAME, EditableFields(productName, remember { mutableStateOf(false) }))
        put(
            PRODUCT_CATEGORY,
            EditableFields(productCategory, remember { mutableStateOf(false) })
        )
        put(UNIT, EditableFields(unit, remember { mutableStateOf(false) }))
        put(
            AVAILABLE_STOCK,
            EditableFields(availableStock, remember { mutableStateOf(false) })
        )
        put(QUANTITY, EditableFields(quantity, remember { mutableStateOf(false) }))
        put(BUYING_PRICE, EditableFields(buyingPrice, remember { mutableStateOf(false) }))
        put(RETAIL_PRICE, EditableFields(retailPrice, remember { mutableStateOf(false) }))
        put(
            WHOLESALE_PRICE,
            EditableFields(wholeSalePrice, remember { mutableStateOf(false) })
        )
    }

    BillEasyBottomSheet(
        sheetHeader = if (isForAdd) ADD_YOUR_PRODUCT else EDIT_YOUR_PRODUCT,
        onDoneClick = {
            if (validateField(addProductFieldsMap.toList())) {
                val newProduct = Product(
                    productId = if (isForAdd) 0 else productId.value.toLong(),
                    productName = addProductFieldsMap.getValue(PRODUCT_NAME).fieldName.value,
                    category = addProductFieldsMap.getValue(
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
                    retailPrice = addProductFieldsMap.getValue(RETAIL_PRICE).fieldName.value.toDouble(),
                    shopId = shopId.longValue
                )
                if (isForAdd) productsViewModel.addProduct(newProduct) else productsViewModel.editProduct(newProduct)
                true
            }
            else { false }
        },
        onDismiss = onDismiss
    ) {
        ProductAddOrEditScreen(
            productFieldMapper = addProductFieldsMap,
            listState = listState,
            shopId = shopId.longValue
        ) { productsViewModel.addProduct(it) }
    }
}

