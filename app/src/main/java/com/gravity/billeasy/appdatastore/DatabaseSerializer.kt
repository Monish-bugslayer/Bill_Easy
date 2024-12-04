package com.gravity.billeasy.appdatastore
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.gravity.billeasy.DatabaseTablePreferences
import java.io.InputStream
import java.io.OutputStream

object DatabaseSerializer: Serializer<DatabaseTablePreferences> {
    override val defaultValue: DatabaseTablePreferences = DatabaseTablePreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): DatabaseTablePreferences {
        try {
            return DatabaseTablePreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto. ", exception)
        }
    }

    override suspend fun writeTo(t: DatabaseTablePreferences, output: OutputStream) = t.writeTo(output)
}