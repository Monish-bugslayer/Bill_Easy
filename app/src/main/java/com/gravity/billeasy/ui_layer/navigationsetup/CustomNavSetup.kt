package com.gravity.billeasy.ui_layer.navigationsetup

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.gravity.billeasy.data_layer.models.Product
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

//class ProductNavType : NavType<Product>(isNullableAllowed = true) {
//    override fun get(bundle: Bundle, key: String): Product? {
//        return bundle.getParcelable(key)
//    }
//
//    override fun parseValue(value: String): Product {
//        return Json.decodeFromString(Uri.decode(value))
//    }
//
//    override fun put(bundle: Bundle, key: String, value: Product) {
//        bundle.putParcelable(key, value)
//    }
//
//    override fun serializeAsValue(value: Product): String {
//        return Uri.encode(Json.encodeToString(value))
//    }
//}