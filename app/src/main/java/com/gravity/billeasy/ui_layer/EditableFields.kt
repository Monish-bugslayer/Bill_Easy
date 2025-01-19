package com.gravity.billeasy.ui_layer

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.KeyboardType
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.AVAILABLE_STOCK
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.BUYING_PRICE
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.PRODUCT_CATEGORY
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.PRODUCT_NAME
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.QUANTITY
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.RETAIL_PRICE
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.UNIT
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.WHOLESALE_PRICE
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.OWNER_MOBILE_NUMBER
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.SHOP_MOBILE_NUMBER
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.SHOP_NAME

data class EditableFields(
    val fieldName: MutableState<String>,
    var isError: MutableState<Boolean>
)

fun validateField(fields: List<Pair<String, EditableFields>>): Boolean {
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

fun decideKeyboardType(fieldType: String): KeyboardType {
    return when (fieldType) {
        AVAILABLE_STOCK -> KeyboardType.Number
        QUANTITY -> KeyboardType.Number
        BUYING_PRICE -> KeyboardType.Number
        RETAIL_PRICE -> KeyboardType.Number
        WHOLESALE_PRICE -> KeyboardType.Number
        OWNER_MOBILE_NUMBER -> KeyboardType.Number
        SHOP_MOBILE_NUMBER -> KeyboardType.Number
        else -> KeyboardType.Text
    }
}
