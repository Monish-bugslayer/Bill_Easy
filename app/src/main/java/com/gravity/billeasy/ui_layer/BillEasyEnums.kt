package com.gravity.billeasy.ui_layer

import kotlin.reflect.KClass

enum class PaymentMethod { CREDIT_CARD, DEBIT_CARD, CASH, UPI, CHEQUE }

enum class BillType { CREDIT, NON_CREDIT }

enum class QuantityUnit {
    GRAMS, KILOGRAMS, PIECE, LOT, BOX, ROLL, DOZEN, SHEET, PACK, BAG, SET, LITER, MILLILITER, BUNDLE
}

enum class ProductCategory {
    CEREALS, BISCATES, CHOCOLATES, SHAMPOO, RICE, CIGARETTES, OIL, PLASTIC_ITEMS, COFFEE_AND_TEA,
    SOAP, SPICES, MASALA, CHIPS, FLOUR, POOJA, OTHER
}

// Without reified, you'd need to pass the Class or KClass manually as a parameter:
inline fun <reified T : Enum<T>> KClass<T>.toListOfStrings(): List<String> {
    return enumValues<T>().map { it.name }
}