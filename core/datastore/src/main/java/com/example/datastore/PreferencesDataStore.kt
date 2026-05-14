package com.example.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.IUserSettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cache")

class PreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) : IUserSettingsRepository {

    private object Keys {
        val IS_FIRST_OPENING_APP = booleanPreferencesKey("is_first_opening_app")
        val USER_ID = intPreferencesKey("user_id")
        val INDEXES_SELECTED_STORAGE = stringSetPreferencesKey("indexes_selected_storage")
    }

    override val indexesSelectedStorageFlow: Flow<Set<Int>> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[Keys.INDEXES_SELECTED_STORAGE]?.map { it.toInt() }?.toSet() ?: emptySet()
        }
        .distinctUntilChanged()

    override suspend fun saveSelectedIds(indexes: Set<Int>) {
        context.dataStore.edit { preferences ->
            preferences[Keys.INDEXES_SELECTED_STORAGE] = indexes.map { it.toString() }.toSet()
        }
    }

    override val userIdFlow: Flow<Int?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[Keys.USER_ID]
        }

    override suspend fun logOut() {
        context.dataStore.edit { preferences ->
            preferences.remove(Keys.USER_ID)
        }
    }

    override suspend fun logIn(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[Keys.USER_ID] = id
        }
    }

    override val isFirstOpeningApp: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[Keys.IS_FIRST_OPENING_APP] ?: true
        }

    override suspend fun markFirstAppOpeningCompleted() {
        context.dataStore.edit { preferences ->
            preferences[Keys.IS_FIRST_OPENING_APP] = false
        }
    }
}
