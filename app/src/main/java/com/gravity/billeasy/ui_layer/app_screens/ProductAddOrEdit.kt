package com.gravity.billeasy.ui_layer.app_screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gravity.billeasy.R
import com.gravity.billeasy.Spinner
import com.gravity.billeasy.appColor
import com.gravity.billeasy.data_layer.models.Product
import com.gravity.billeasy.ui_layer.ProductCategory
import com.gravity.billeasy.ui_layer.QuantityUnit
import com.gravity.billeasy.ui_layer.viewmodel.ProductViewModel
import kotlinx.serialization.Serializable

/*
Fields:
Product name
Product category
Available stock
Quantity Unit
Quantity
Buying price
Retail price
Wholesale price
*/

const val ADD_PRODUCT = "Add product"
const val EDIT_PRODUCT = "Update product"
const val PRODUCT_NAME = "Product name"
const val PRODUCT_ID = "Product Id"
const val PRODUCT_CATEGORY = "Product category"
const val UNIT = "Unit"
const val AVAILABLE_STOCK = "Available stock"
const val QUANTITY = "Quantity"
const val BUYING_PRICE = "Buying price"
const val RETAIL_PRICE = "Retail price"
const val WHOLESALE_PRICE = "Wholesale price"

@Composable
fun ProductAddOrEditScreen(productFieldMapper: MutableMap<String, AddOrEditProductField>) {

    // TODO need to find how to write extension of Enum to get this list
    val unitOptions: List<String> = listOf(
        QuantityUnit.BAG.name,
        QuantityUnit.BOX.name,
        QuantityUnit.LOT.name,
        QuantityUnit.SET.name,
        QuantityUnit.PACK.name,
        QuantityUnit.BUNDLE.name,
        QuantityUnit.DOZEN.name,
        QuantityUnit.GRAMS.name,
        QuantityUnit.KILOGRAMS.name,
        QuantityUnit.LITER.name,
        QuantityUnit.MILLILITER.name,
        QuantityUnit.PIECE.name,
        QuantityUnit.ROLL.name,
        QuantityUnit.SHEET.name
    )

    val categoryOptions: List<String> = listOf(
        ProductCategory.CEREALS.name,
        ProductCategory.OIL.name,
        ProductCategory.BISCATES.name,
        ProductCategory.SOAP.name,
        ProductCategory.SPICES.name,
        ProductCategory.SHAMPOO.name,
        ProductCategory.CHIPS.name,
        ProductCategory.CHOCOLATES.name,
        ProductCategory.CIGARETTES.name,
        ProductCategory.COFFEE_AND_TEA.name,
        ProductCategory.PLASTIC_ITEMS.name,
        ProductCategory.POOJA.name,
        ProductCategory.OTHER.name,
        ProductCategory.FLOUR.name,
        ProductCategory.RICE.name,
        ProductCategory.MASALA.name,
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(productFieldMapper.toList()) { field ->
            when (field.first) {
                UNIT -> {
                    Spinner(
                        selectedValue = field.second.fieldName.value,
                        options = unitOptions,
                        label = UNIT,
                        onValueChangedEvent = { field.second.fieldName.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 20.dp, start = 20.dp),
                        isError = field.second.isError.value,
                        supportedString = "${field.first} should not be empty"
                    )
                }

                PRODUCT_CATEGORY -> {
                    Spinner(
                        selectedValue = field.second.fieldName.value,
                        options = categoryOptions,
                        label = PRODUCT_CATEGORY,
                        onValueChangedEvent = { field.second.fieldName.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 20.dp, start = 20.dp),
                        isError = field.second.isError.value,
                        supportedString = "${field.first} should not be empty"
                    )
                }

                else -> {
                    if(field.first != PRODUCT_ID) {
                        OutlinedTextField(
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = appColor,
                                focusedLabelColor = colorResource(R.color.black),
                                cursorColor = colorResource(R.color.black)
                            ),
                            value = field.second.fieldName.value,
                            onValueChange = { field.second.fieldName.value = it },
                            label = { Text(text = field.first) },
                            maxLines = 1,
                            isError = field.second.isError.value,
                            supportingText = {
                                if (field.second.isError.value) {
                                    Text(text = "${field.first} should not be empty")
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = decideKeyboardType(field.first)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, end = 20.dp, start = 20.dp)
                        )
                    }
                }
            }
        }

        item {
//            ElevatedButton(colors = ButtonDefaults.buttonColors(containerColor = appColor),
//                onClick = {
//                    if (validateAddProductField(addProductFieldsMap.toList())) {
//                        val newProduct = Product(
//                            productId = if(isForAdd) 0 else productId.value.toLong() ,
//                            productName = addProductFieldsMap.getValue(PRODUCT_NAME).fieldName.value,
//                            productCategory = addProductFieldsMap.getValue(PRODUCT_CATEGORY).fieldName.value,
//                            unit = addProductFieldsMap.getValue(UNIT).fieldName.value,
//                            availableStock = addProductFieldsMap.getValue(AVAILABLE_STOCK).fieldName.value.toLong(),
//                            quantity = addProductFieldsMap.getValue(QUANTITY).fieldName.value.toLong(),
//                            buyingPrice = addProductFieldsMap.getValue(BUYING_PRICE).fieldName.value.toDouble(),
//                            wholeSalePrice = addProductFieldsMap.getValue(WHOLESALE_PRICE).fieldName.value.toDouble(),
//                            retailPrice = addProductFieldsMap.getValue(RETAIL_PRICE).fieldName.value.toDouble()
//                        )
//                        println(newProduct.toString())
//                        if(isForAdd) viewModel.addProduct(newProduct) else viewModel.editProduct(newProduct)
//                        navigateBackAfterAddOrEditProduct()
//                    }
//                }) {
//                Text(text = if(isForAdd) ADD_PRODUCT else EDIT_PRODUCT, color = Color.Black)
//            }
        }
    }
}


fun decideKeyboardType(fieldType: String): KeyboardType {
    return when (fieldType) {
        PRODUCT_NAME -> KeyboardType.Text
        PRODUCT_CATEGORY -> KeyboardType.Text
        UNIT -> KeyboardType.Text
        AVAILABLE_STOCK -> KeyboardType.Number
        QUANTITY -> KeyboardType.Number
        BUYING_PRICE -> KeyboardType.Number
        RETAIL_PRICE -> KeyboardType.Number
        WHOLESALE_PRICE -> KeyboardType.Number
        else -> KeyboardType.Text
    }
}