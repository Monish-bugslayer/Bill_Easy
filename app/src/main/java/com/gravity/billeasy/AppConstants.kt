package com.gravity.billeasy

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.gravity.billeasy.ui_layer.AVAILABLE_STOCK
import com.gravity.billeasy.ui_layer.BUYING_PRICE
import com.gravity.billeasy.ui_layer.PRODUCT_CATEGORY
import com.gravity.billeasy.ui_layer.PRODUCT_ID
import com.gravity.billeasy.ui_layer.PRODUCT_NAME
import com.gravity.billeasy.ui_layer.QUANTITY
import com.gravity.billeasy.ui_layer.RETAIL_PRICE
import com.gravity.billeasy.ui_layer.UNIT
import com.gravity.billeasy.ui_layer.WHOLESALE_PRICE

inline val appColorInt get() = R.color.purple_200

inline val appColor @Composable
get() = Color(LocalContext.current.resources.getColor(R.color.orange_light))

val productFields = listOf(
    PRODUCT_ID,
    PRODUCT_NAME,
    PRODUCT_CATEGORY,
    UNIT,
    AVAILABLE_STOCK,
    QUANTITY,
    BUYING_PRICE,
    RETAIL_PRICE,
    WHOLESALE_PRICE
)

enum class QuantityUnit {
    GRAMS, KILOGRAMS, PIECE, LOT, BOX, ROLL, DOZEN, SHEET, PACK, BAG, SET, LITER, MILLILITER, BUNDLE
}

enum class ProductCategory {
    CEREALS, BISCATES, CHOCOLATES, SHAMPOO, RICE, CIGARETTES, OIL, PLASTIC_ITEMS, COFFEE_AND_TEA, SOAP, SPICES, MASALA, CHIPS, FLOUR, POOJA, OTHER
}