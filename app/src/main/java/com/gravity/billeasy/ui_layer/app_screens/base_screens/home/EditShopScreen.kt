package com.gravity.billeasy.ui_layer.app_screens.base_screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.gravity.billeasy.data_layer.models.Shop
import com.gravity.billeasy.data_layer.models.ShopValidationState
import com.gravity.billeasy.ui_layer.BillEasyBottomSheet
import com.gravity.billeasy.ui_layer.BillEasyOutlineTextField
import com.gravity.billeasy.ui_layer.IsNeedButton
import com.gravity.billeasy.ui_layer.isShopFormValid
import com.gravity.billeasy.ui_layer.validateShopDetails
import com.gravity.billeasy.ui_layer.viewmodel.ShopViewModel

@Composable
fun EditShopDetails (
    shopViewModel: ShopViewModel,
    isNeedToLaunchShopDetailsEditScreen: MutableState<Boolean>? = null,
    innerPadding: PaddingValues? = null,
    isNeedButton: IsNeedButton = IsNeedButton.No
) {
    val shopDetailsMapper: MutableMap<String, Pair<(String) -> Unit, String>> =
        mutableMapOf<String, Pair<(String) -> Unit, String>>()
    val errorStates = remember { mutableStateOf(ShopValidationState()) }
    val shop = remember { mutableStateOf<Shop>(Shop()) }
    val onNameChanged = { updatedValue: String -> shop.value = shop.value.copy(name = updatedValue) }
    val onAddressChanged = { updatedValue: String -> shop.value = shop.value.copy(address = updatedValue) }
    val onEmailAddressChanged = { updatedValue: String -> shop.value = shop.value.copy(emailAddress = updatedValue) }
    val onMobileNumberChanged = { updatedValue: String -> shop.value = shop.value.copy(mobileNumber = updatedValue) }
    val onGstNumberChanged = { updatedValue: String -> shop.value = shop.value.copy(gstNumber = updatedValue) }
    val onTinNumberChanged = { updatedValue: String -> shop.value = shop.value.copy(tinNumber = updatedValue) }
    val onOwnerAddressChanged = { updatedValue: String -> shop.value = shop.value.copy(ownerAddress = updatedValue) }
    val onOwnerMobileNumberChanged = { updatedValue: String -> shop.value = shop.value.copy(ownerMobileNumber = updatedValue) }
    val onOwnerNameChanged = { updatedValue: String -> shop.value = shop.value.copy(ownerName = updatedValue) }

    shopDetailsMapper.apply {
        put(SHOP_NAME, Pair(onNameChanged, shop.value.name))
        put(SHOP_ADDRESS, Pair(onAddressChanged, shop.value.address))
        put(SHOP_EMAIL_ADDRESS, Pair(onEmailAddressChanged, shop.value.emailAddress))
        put(SHOP_MOBILE_NUMBER, Pair(onMobileNumberChanged, shop.value.mobileNumber))
        put(GST_NUMBER, Pair(onGstNumberChanged, shop.value.gstNumber))
        put(TIN_NUMBER, Pair(onTinNumberChanged, shop.value.tinNumber))
        put(OWNER_NAME, Pair(onOwnerNameChanged, shop.value.ownerName))
        put(OWNER_ADDRESS, Pair(onOwnerAddressChanged, shop.value.ownerAddress))
        put(OWNER_MOBILE_NUMBER, Pair(onOwnerMobileNumberChanged, shop.value.ownerMobileNumber))
    }
    if(isNeedToLaunchShopDetailsEditScreen != null && isNeedToLaunchShopDetailsEditScreen.value) {
        BillEasyBottomSheet(
            sheetHeader = EDIT_SHOP,
            onDoneClick = {
                errorStates.value = validateShopDetails(shop.value)
                if(isShopFormValid(errorStates.value)) {
                    shopViewModel.updateShop(shop.value)
                    true
                }
                false
            },
            onDismiss = { isNeedToLaunchShopDetailsEditScreen.value = false }
        ) { EditShop(shopDetailsMapper = shopDetailsMapper, errorStates = errorStates.value, isNeedButton) }
    } else {
        if(innerPadding != null) {
            CreateShop(
                paddingValues = innerPadding,
            ) {
                EditShop(shopDetailsMapper = shopDetailsMapper, errorStates = errorStates.value, isNeedButton) {
                    errorStates.value = validateShopDetails(shop.value)
                    if(isShopFormValid(errorStates.value)) {
                        shopViewModel.addShop(shop.value)
                        true
                    } else false
                }
            }
        }
    }
}




@Composable
fun EditShop(
    shopDetailsMapper: MutableMap<String, Pair<(String) -> Unit, String>>,
    errorStates: ShopValidationState,
    isNeedButton: IsNeedButton,
    onValidateShop: () -> Boolean = { false }
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    LazyColumn(modifier = Modifier.fillMaxSize().imePadding(), horizontalAlignment = Alignment.CenterHorizontally) {
        items(shopDetailsMapper.toList()) { field ->
            val focusRequestedModifier =
                if (field.first == SHOP_NAME) Modifier.focusRequester(focusRequester) else Modifier

            val error = when (field.first) {
                SHOP_NAME -> errorStates.nameError
                SHOP_ADDRESS -> errorStates.addressError
                SHOP_EMAIL_ADDRESS -> errorStates.emailError
                SHOP_MOBILE_NUMBER -> errorStates.mobileError
                GST_NUMBER -> errorStates.gstError
                TIN_NUMBER -> errorStates.tinError
                OWNER_NAME -> errorStates.ownerNameError
                OWNER_ADDRESS -> errorStates.ownerAddressError
                OWNER_MOBILE_NUMBER -> errorStates.ownerMobileError
                else -> null
            }

            BillEasyOutlineTextField (
                label = field.first,
                value = field.second.second,
                onValueChange = { it -> field.second.first(it) },
                focusRequestedModifier = focusRequestedModifier,
                focusManager = focusManager,
                isError = error != null,
                errorMessage = error
            )
        }

        when(isNeedButton) {
            is IsNeedButton.Yes -> {
                item {
                    ElevatedButton(onClick = {
                        if(onValidateShop()) {
                            // TODO after clicking need to close the keyboard and then we have to do this action.
                            isNeedButton.onButtonClick()
                        }
                    }) {
                        Text(isNeedButton.buttonText)
                    }
                }
            }

            is IsNeedButton.No -> {}
        }
    }
}