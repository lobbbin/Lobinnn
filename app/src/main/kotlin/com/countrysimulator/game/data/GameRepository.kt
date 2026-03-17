package com.countrysimulator.game.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.countrysimulator.game.domain.GameState
import com.countrysimulator.game.domain.PersistedGameState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_data_v6_2")

class GameRepository(private val context: Context) {

    private val STATE_KEY = stringPreferencesKey("persisted_game_state_json")

    suspend fun saveGame(gameState: GameState) {
        val persisted = PersistedGameState(
            country = gameState.country,
            aiNations = gameState.aiNations,
            globalMarket = gameState.globalMarket
        )
        val json = Json.encodeToString(persisted)
        context.dataStore.edit { preferences ->
            preferences[STATE_KEY] = json
        }
    }

    fun loadGame(): Flow<GameState?> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[STATE_KEY] ?: return@map null
            try {
                val persisted = Json.decodeFromString<PersistedGameState>(json)
                GameState(
                    country = persisted.country,
                    aiNations = persisted.aiNations,
                    globalMarket = persisted.globalMarket,
                    eventHistory = persisted.country.eventHistory,
                    turnSummary = null
                )
            } catch (e: Exception) {
                null // Corrupted save, return null to start fresh
            }
        }
    }

    suspend fun clearGame() {
        context.dataStore.edit { it.clear() }
    }
}
