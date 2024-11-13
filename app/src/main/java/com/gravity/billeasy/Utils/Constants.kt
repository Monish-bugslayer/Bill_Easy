package com.gravity.billeasy.Utils

import androidx.datastore.preferences.core.stringPreferencesKey

object NavigationConstants {
    const val LOGIN_SCREEN = "loginScreen"
    const val MAIN_ACTIVITY_SCREEN = "mainActivityScreen"
}

object AppConstants{
    const val APP_DATASTORE = "appDataStore"
    val loggedInUserIdPreferenceKey = stringPreferencesKey("loggedInUserId")
}