package com.example.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.settings.ISettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cache")

class PreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) : ISettingsRepository {

    private object Keys {
        val IS_FIRST_OPENING_APP = booleanPreferencesKey("is_first_opening_app")
        val USER_ID = stringPreferencesKey("user_id")
        val IS_GUEST = booleanPreferencesKey("is_guest")
        val ID_SELECTED_STORAGE = stringSetPreferencesKey("id_selected_storage")
    }

    override val idSelectedStorageFlow: Flow<Set<String>> =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                preferences[Keys.ID_SELECTED_STORAGE] ?: emptySet()
            }


    override suspend fun saveSelectedIds(id: Set<String>) {
        context.dataStore.edit { preferences ->
            preferences[Keys.ID_SELECTED_STORAGE] = id.map { it }.toSet()
        }
    }

    override val userIdFlow: Flow<String?> = context.dataStore.data
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

    override suspend fun logIn(id: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.USER_ID] = id
        }
    }

    override suspend fun loginAsGuest() {
        context.dataStore.edit { preferences ->
            preferences[Keys.USER_ID] = UUID.randomUUID().toString()
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
