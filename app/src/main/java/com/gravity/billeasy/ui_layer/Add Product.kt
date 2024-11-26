package com.gravity.billeasy.ui_layer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gravity.billeasy.Utils.Spinner

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
const val PRODUCT_NAME = "Product name"
const val PRODUCT_CATEGORY = "Product category"
const val UNIT = "Unit"
const val AVAILABLE_STOCK = "Available stock"
const val QUANTITY = "Quantity"
const val BUYING_PRICE = "Buying price"
const val RETAIL_PRICE = "Retail price"
const val WHOLESALE_PRICE = "Wholesale price"

enum class QuantityUnit {
    GRAMS, KILOGRAMS, PIECE, LOT, BOX, ROLL, DOZEN, SHEET, PACK, BAG, SET, LITER, MILLILITER, BUNDLE
}

enum class ProductCategory {
    CEREALS, BISCATES, CHOCOLATES, SHAMPOO, RICE, CIGARETTES, OIL, PLASTIC_ITEMS, COFFEE_AND_TEA, SOAP, SPICES, MASALA, CHIPS, FLOUR, POOJA, OTHER
}

data class Product(
    val productName: String,
    val productCategory: String,
    val unit: String,
    val availableStock: Int,
    val quantity: Int,
    val buyingPrice: Float,
    val retailPrice: Float,
    val wholeSalePrice: Float
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddProduct() {

    val productName = remember { mutableStateOf("") }
    val productCategory = remember { mutableStateOf("") }
    val unit = remember { mutableStateOf("") }
    val availableStock = remember { mutableStateOf("") }
    val quantity = remember { mutableStateOf("") }
    val buyingPrice = remember { mutableStateOf("") }
    val retailPrice = remember { mutableStateOf("") }
    val wholeSalePrice = remember { mutableStateOf("") }
    val addProductFieldsMap = mutableMapOf<String, MutableState<String>>()
    var selectedUnit by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    addProductFieldsMap.apply {
        put(PRODUCT_NAME, productName)
        put(PRODUCT_CATEGORY, productCategory)
        put(UNIT, unit)
        put(AVAILABLE_STOCK, availableStock)
        put(QUANTITY, quantity)
        put(BUYING_PRICE, buyingPrice)
        put(RETAIL_PRICE, retailPrice)
        put(WHOLESALE_PRICE, wholeSalePrice)
    }

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
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        stickyHeader {
            Text(
                text = "Add your product",
                modifier = Modifier.fillMaxWidth().background(Color.White).padding(top = 15.dp),
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }

        items(addProductFieldsMap.toList()) { field ->
            when (field.first) {
                UNIT -> {
                    Spinner (
                    selectedValue = selectedUnit,
                    options = unitOptions,
                    label = UNIT,
                    onValueChangedEvent = { selectedUnit = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, end = 20.dp, start = 20.dp)
                    )
                }
                PRODUCT_CATEGORY -> {
                    Spinner (
                        selectedValue = selectedCategory,
                        options = categoryOptions,
                        label = PRODUCT_CATEGORY,
                        onValueChangedEvent = { selectedCategory = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 20.dp, start = 20.dp)
                    )
                }
                else -> {
                    OutlinedTextField(
                        value = field.second.value,
                        onValueChange = { field.second.value = it },
                        label = { Text(text = field.first) },
                        maxLines = 1,
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

        item {
            ElevatedButton(
                onClick = { /* TODO validate empty fields and ask confirmation and add product in DB */ },
                // TODO need to change color of button
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(text = ADD_PRODUCT)
            }
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

