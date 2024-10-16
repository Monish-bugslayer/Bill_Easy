package com.gravity.billeasy.presentation_layer

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.gravity.billeasy.Utils.AppConstants


object AppDataStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AppConstants.APP_DATASTORE)


    suspend fun writeIntToDataStore(context: Context, intKey: Preferences.Key<Int>, value: Int) {
        context.dataStore.edit { preferences ->
            preferences[intKey] = value
        }
    }

    suspend fun writeStringDataToKeyStore(
        context: Context, stringKey: Preferences.Key<String>, value: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[stringKey] = value
        }
    }

}
