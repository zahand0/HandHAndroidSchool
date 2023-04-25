package com.zahand0.cowboys.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zahand0.cowboys.domain.repository.DataStoreOperations
import com.zahand0.cowboys.presentation.ui.util.Constants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    private val dataStore = context.dataStore

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey] = token
        }
    }

    override fun readAccessToken() =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[accessTokenKey]
                onBoardingState
            }

    companion object {
        val accessTokenKey = stringPreferencesKey(name = Constants.PREFERENCES_ACCESS_TOKEN_KEY)
    }
}