package com.gravity.billeasy.appdatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.gravity.billeasy.DatabaseTablePreferences

private const val DATA_STORE_FILE_NAME = "store.proto"
val Context.databasePreferenceDataStore: DataStore<DatabaseTablePreferences> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = DatabaseSerializer
)
