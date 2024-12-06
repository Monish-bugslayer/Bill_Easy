package com.gravity.billeasy.data_layer.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val productId: Long = 0,
    val productName: String,
    val productCategory: String,
    val unit: String,
    val availableStock: Long,
    val quantity: Long,
    val buyingPrice: Double,
    val retailPrice: Double,
    val wholeSalePrice: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(productId)
        parcel.writeString(productName)
        parcel.writeString(productCategory)
        parcel.writeString(unit)
        parcel.writeLong(availableStock)
        parcel.writeLong(quantity)
        parcel.writeDouble(buyingPrice)
        parcel.writeDouble(retailPrice)
        parcel.writeDouble(wholeSalePrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}