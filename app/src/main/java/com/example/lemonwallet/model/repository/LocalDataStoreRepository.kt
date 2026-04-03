package com.example.lemonwallet.model.repository

import android.content.Context
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.text.get


    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cache")

class LocalDataStoreRepository(private val context: Context) {
    private object Keys{
        val IS_FIRST_OPENING_APP = booleanPreferencesKey("is_first_opening_app")
        val USER_ID = intPreferencesKey("user_id")
    }

    val userIdFlow: Flow<Int?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[Keys.USER_ID]
        }

    val isFirstOpeningApp: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[Keys.IS_FIRST_OPENING_APP] ?: true
        }


    suspend fun logOut(){
        try{

            context.dataStore.edit {
                TODO()
            }
        }
        catch (e: IOException){
            TODO()
        }
    }

    suspend fun logIn(id: Int){
        try {
            context.dataStore.edit { preferences ->
                preferences[Keys.USER_ID] = id
            }
        }
        catch (e: IOException){
            TODO()
        }
    }

    /**
     * Функция срабатывает единежды после первого открытия приложения. И помечает переменную первого отрытия
     */
    suspend fun markFirstAppOpeningCompleted(){
        try {
            context.dataStore.edit {preferences ->
                preferences[Keys.IS_FIRST_OPENING_APP] = false

            }
        }
        catch (e: IOException){
            TODO()
        }
    }

    suspend fun complexUpdate() {
        context.dataStore.updateData { preferences ->
            // Здесь мы должны вернуть объект Preferences
            preferences.toMutablePreferences().apply {
                this[Keys.USER_ID] = 123
            }.toPreferences()
        }
    }


}
