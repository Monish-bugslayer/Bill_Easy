package com.gravity.billeasy.ui_layer.app_screens.loginscreens

import kotlinx.serialization.Serializable

@Serializable
data class Otp(val mobileNumber: String, val dataFrom: String)