package com.gravity.billeasy.ui_layer

sealed class IsNeedButton() {
    data class Yes(val onButtonClick: () -> Unit, val buttonText: String): IsNeedButton()
    object No: IsNeedButton()
}