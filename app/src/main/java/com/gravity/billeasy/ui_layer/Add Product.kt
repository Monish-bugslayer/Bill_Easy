package com.gravity.billeasy.ui_layer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
const val ALPHA_DISABLED = 0.5f
const val ALPHA_FULL = 1f

enum class AddProductFields {
    PRODUCT_NAME, PRODUCT_CATEGORY, UNIT, AVAILABLE_STOCK, QUANTITY, BUYING_PRICE, RETAIL_PRICE, WHOLESALE_PRICE
}

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
    val addProductFieldsMap = mutableMapOf<AddProductFields, MutableState<String>>()

    addProductFieldsMap.apply {
        put(AddProductFields.PRODUCT_NAME, productName)
        put(AddProductFields.PRODUCT_CATEGORY, productCategory)
        put(AddProductFields.UNIT, unit)
        put(AddProductFields.AVAILABLE_STOCK, availableStock)
        put(AddProductFields.QUANTITY, quantity)
        put(AddProductFields.BUYING_PRICE, buyingPrice)
        put(AddProductFields.RETAIL_PRICE, retailPrice)
        put(AddProductFields.WHOLESALE_PRICE, wholeSalePrice)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Add your product",
            modifier = Modifier.padding(top = 15.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.W500
        )

        val options: List<String> = listOf(
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

        // completed spinner TODO need to arrange it in the desire position in lazy column
        var t by remember { mutableStateOf("") }
        Spinner(selectedValue = t, options = options, label = "Lable", onValueChangedEvent = {
            t = it
        })

        LazyColumn {

            items(addProductFieldsMap.toList()) { field ->
                OutlinedTextField(value = field.second.value,
                    onValueChange = { field.second.value = it },
                    label = { Text(text = field.first.name) },
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

        ElevatedButton(
            onClick = { /* TODO validate empty fields and ask confirmation and add product in DB */ },
            // TODO need to change color of button
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = ADD_PRODUCT)
        }

    }
}

fun decideKeyboardType(fieldType: AddProductFields): KeyboardType {
    return when (fieldType) {
        AddProductFields.PRODUCT_NAME -> KeyboardType.Text
        AddProductFields.PRODUCT_CATEGORY -> KeyboardType.Text
        AddProductFields.UNIT -> KeyboardType.Text
        AddProductFields.AVAILABLE_STOCK -> KeyboardType.Number
        AddProductFields.QUANTITY -> KeyboardType.Number
        AddProductFields.BUYING_PRICE -> KeyboardType.Number
        AddProductFields.RETAIL_PRICE -> KeyboardType.Number
        AddProductFields.WHOLESALE_PRICE -> KeyboardType.Number
    }
}

