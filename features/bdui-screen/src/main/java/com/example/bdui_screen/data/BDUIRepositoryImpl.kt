package com.example.bdui_screen.data

import android.util.Log
import com.example.bdui_screen.domain.BDUIComponent
import com.example.bdui_screen.domain.BDUIResponse
import com.example.bdui_screen.domain.IBDUIRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

private const val LOG_TAG = "BDUI_REPOSITORY_LOG"
class BDUIRepositoryImpl @Inject constructor(
    private val networkRepository: BDUIRepositoryMockNetwork,
    private val localRepository: BDUIRepositoryLocal
): IBDUIRepository{
    override fun observePromoScreen(): Flow<List<BDUIComponent>> = channelFlow {
        var localCache: List<BDUIComponent>? = null
        var netWorkResponse: List<BDUIComponent>? = null

        val localJob = launch {
            Log.i(LOG_TAG, "start local coroutine")
            val cache = localRepository.getBDUIComponent()
            val json = localRepository.getJson()
            localCache = cache
            if (cache != null && json != null && netWorkResponse == null) {
                Log.i(LOG_TAG, "emit local response")
                send(cache)
            }
        }
        val netWork = launch {
            Log.i(LOG_TAG, "start network coroutine")
            networkRepository.observePromoScreen().collect { components ->
                val (list, json) = components
                if (list != localCache) {
                    netWorkResponse = list
                    send(list)
                    Log.i(LOG_TAG, "emit net response")
                    localRepository.saveJson(json)
                }
            }
        }
    }
}

class BDUIRepositoryMockNetwork @Inject constructor() {
    suspend fun observePromoScreen(): Flow<Pair<List<BDUIComponent>, String>> {
        return flow{
            while (true){
                delay(5000.milliseconds)
                val json = if(Random.nextInt(1,3) ==1) BDUI_MAIN_PROMO else BDUI_INTERACTIVE
                val response = bduiJsonConfig.decodeFromString<BDUIResponse>(json)
                emit(Pair(response.components, json))
            }
        }
    }
}

val Context.bduiDataStore: DataStore<Preferences> by preferencesDataStore(name = "bdui_cache")

class BDUIRepositoryLocal @Inject constructor(
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: Context
) {
    private val dataStore = context.bduiDataStore

    companion object {
        private val BDUI_JSON_KEY = stringPreferencesKey("bdui_json_key")
    }

    suspend fun saveJson(json: String) {
        dataStore.edit { preferences ->
            preferences[BDUI_JSON_KEY] = json
        }
    }
    suspend fun getBDUIComponent(): List<BDUIComponent>? = withContext(Dispatchers.Default){
        val json = getJson()
        if (json==null) {
            null
        }
        else{
            bduiJsonConfig.decodeFromString<BDUIResponse>(json).components
        }
    }

    suspend fun getJson(): String? = withContext(Dispatchers.IO){
        dataStore.data.map { preferences ->
            preferences[BDUI_JSON_KEY]
        }.firstOrNull()
    }
}
