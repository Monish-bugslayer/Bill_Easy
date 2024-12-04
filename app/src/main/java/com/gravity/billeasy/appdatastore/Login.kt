package com.gravity.billeasy.appdatastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.gravity.billeasy.Login
import java.io.InputStream
import java.io.OutputStream

object LoginSerializer: Serializer<Login> {
    override val defaultValue: Login = Login.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Login {
        try {
            return Login.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto. ", exception)
        }
    }

    override suspend fun writeTo(t: Login, output: OutputStream) = t.writeTo(output)
}