package com.gravity.billeasy.ui_layer.app_screens.base_screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.gravity.billeasy.data_layer.models.ShopValidationState
import com.gravity.billeasy.ui_layer.BillEasyOutlineTextField
import com.gravity.billeasy.ui_layer.IsNeedButton

@Composable
fun EditShop(
    shopDetailsMapper: MutableMap<String, Pair<(String) -> Unit, String>>,
    errorStates: ShopValidationState,
    isNeedButton: IsNeedButton,
    onValidateShop: () -> Unit = {}
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
                        onValidateShop()
                        isNeedButton.onButtonClick()
                    }) {
                        Text(isNeedButton.buttonText)
                    }
                }
            }

            is IsNeedButton.No -> {}
        }
    }
}