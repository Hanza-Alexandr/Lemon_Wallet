package com.example.lemonwallet.model.repository

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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cache")

class PreferencesDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    private object Keys{
        val IS_FIRST_OPENING_APP = booleanPreferencesKey("is_first_opening_app")
        val USER_ID = intPreferencesKey("user_id")
        val INDEXES_SELECTED_STORAGE = stringSetPreferencesKey("indexes_selected_storage")

    }
    inner class UIState{
        val indexesSelectedStorageFlow: Flow<Set<Int>> = context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                preferences[Keys.INDEXES_SELECTED_STORAGE]?.map { it.toInt() }?.toSet() ?: emptySet()
            }

        suspend fun saveSelectedIds(indexes: Set<Int>) {
            try {
                context.dataStore.edit { preferences ->
                    preferences[Keys.INDEXES_SELECTED_STORAGE] = indexes.map { it.toString() }.toSet()
                }

            }catch (e: Exception){
                TODO()
            }
        }

    }

    inner class Account{
        val userIdFlow: Flow<Int?> = context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                preferences[Keys.USER_ID]
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
    }

    inner class FirstOpen{
        val isFirstOpeningApp: Flow<Boolean> = context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                preferences[Keys.IS_FIRST_OPENING_APP] ?: true
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
    }

}
