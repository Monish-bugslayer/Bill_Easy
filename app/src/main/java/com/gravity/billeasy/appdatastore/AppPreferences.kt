package com.gravity.billeasy.appdatastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.gravity.billeasy.AppPreference
import java.io.InputStream
import java.io.OutputStream

object AppPreferences: Serializer<AppPreference> {
    override val defaultValue: AppPreference
        get() = AppPreference.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppPreference {
        try { return AppPreference.parseFrom(input) }
        catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto. ", exception)
        }
    }

    override suspend fun writeTo(t: AppPreference, output: OutputStream) = t.writeTo(output)
}