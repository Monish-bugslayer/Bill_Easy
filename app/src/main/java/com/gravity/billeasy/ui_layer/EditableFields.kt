package com.gravity.billeasy.ui_layer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.KeyboardType
import com.gravity.billeasy.data_layer.models.Shop
import com.gravity.billeasy.data_layer.models.ShopValidationState
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.AVAILABLE_STOCK
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.BUYING_PRICE
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.PRODUCT_CATEGORY
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.PRODUCT_NAME
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.QUANTITY
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.RETAIL_PRICE
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.UNIT
import com.gravity.billeasy.ui_layer.app_screens.base_screens.all_products.WHOLESALE_PRICE
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.GST_NUMBER
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.OWNER_ADDRESS
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.OWNER_MOBILE_NUMBER
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.OWNER_NAME
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.SHOP_ADDRESS
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.SHOP_EMAIL_ADDRESS
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.SHOP_MOBILE_NUMBER
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.SHOP_NAME
import com.gravity.billeasy.ui_layer.app_screens.base_screens.home.TIN_NUMBER

@Stable
data class EditableFields (
    val fieldName: MutableState<String>,
    var isError: MutableState<Boolean>
)

fun validateShopDetails(shop: Shop): ShopValidationState {
    return ShopValidationState(
        nameError = validateNameFields(shop.name, SHOP_NAME),
        addressError = validateNameFields(shop.address, SHOP_ADDRESS),
        emailError = validateEmail(shop.emailAddress, SHOP_EMAIL_ADDRESS),
        mobileError = validateMobileNumber(shop.mobileNumber, SHOP_MOBILE_NUMBER),
        gstError = validateGSTNumber(shop.gstNumber, GST_NUMBER),
        tinError = validateTINNumber(shop.tinNumber, TIN_NUMBER),
        ownerNameError = validateNameFields(shop.ownerName, OWNER_NAME),
        ownerAddressError = validateNameFields(shop.ownerAddress, OWNER_ADDRESS),
        ownerMobileError = validateMobileNumber(shop.ownerMobileNumber, OWNER_MOBILE_NUMBER)
    )
}

fun validateNameFields(name: String, key: String): String? {
    return when {
        name.isEmpty() -> "$key should not be empty"
        else -> null
    }
}

fun validateEmail(email: String, key: String): String? {
    return when {
        email.isEmpty() -> "$key should not be empty"
        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
        else -> null
    }
}

fun validateMobileNumber(mobile: String, key: String): String? {
    return when {
        mobile.isEmpty() -> "$key number is required"
        !mobile.matches(Regex("^[0-9]{10}$")) -> "Invalid mobile number format"
        else -> null
    }
}

fun validateGSTNumber(gst: String, key: String): String? {
    return when {
        gst.isEmpty() -> "$key number is required"
        !gst.matches(Regex("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$")) -> "Invalid GST format"
        else -> null
    }
}

fun validateTINNumber(tin: String, key: String): String? {
    return when {
        tin.isEmpty() -> "$key number is required"
        !tin.matches(Regex("^[0-9]{11}$")) -> "Invalid TIN format"
        else -> null
    }
}

// TODO need to remove this and update to newer method of adding and validating like shop create and edit
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

fun isFormValid(validationState: ShopValidationState): Boolean {
    return validationState.run {
        nameError == null && addressError == null && emailError == null &&
                mobileError == null && gstError == null && tinError == null &&
                ownerNameError == null && ownerAddressError == null && ownerMobileError == null
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
