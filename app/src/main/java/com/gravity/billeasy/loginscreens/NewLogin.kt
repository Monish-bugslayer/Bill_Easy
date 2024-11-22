package com.gravity.billeasy.loginscreens

import kotlinx.serialization.Serializable

@Serializable
data class Otp(val mobileNumber: String, val dataFrom: String)