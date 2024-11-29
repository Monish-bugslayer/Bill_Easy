package com.gravity.billeasy

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

inline val appColorInt get() = R.color.orange_light

inline val appColor @Composable
get() = Color(LocalContext.current.resources.getColor(R.color.orange_light))

enum class QuantityUnit {
    GRAMS, KILOGRAMS, PIECE, LOT, BOX, ROLL, DOZEN, SHEET, PACK, BAG, SET, LITER, MILLILITER, BUNDLE
}

enum class ProductCategory {
    CEREALS, BISCATES, CHOCOLATES, SHAMPOO, RICE, CIGARETTES, OIL, PLASTIC_ITEMS, COFFEE_AND_TEA, SOAP, SPICES, MASALA, CHIPS, FLOUR, POOJA, OTHER
}