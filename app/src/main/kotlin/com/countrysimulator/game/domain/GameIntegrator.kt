package com.countrysimulator.game.domain

import com.countrysimulator.game.presentation.*
import com.countrysimulator.game.multiplayer.*
import com.countrysimulator.game.content.*

/**
 * ========================================================================
 * GAME INTEGRATION LAYER
 * Bridges all phases together with the original game system
 * ========================================================================
 * 
 * HOW THE PHASES INTEGRATE:
 * 
 *                    ┌─────────────────────────────────────────────┐
 *                    │           ORIGINAL GAME LOOP                │
 *                    │  (GameLogic.kt, GameScreen.kt)              │
 *                    └─────────────────┬───────────────────────────┘
 *                                      │
 *                    ┌─────────────────▼───────────────────────────┐
 *                    │         GAME STATE MANAGER                  │
 *                    │    (This file - GameIntegrator)            │
 *                    └─────────────────┬───────────────────────────┘
 *                                      │
 *      ┌──────────────┬───────────────┼───────────────┬──────────────┐
 *      │              │               │               │              │
 * ┌────▼────┐   ┌────▼────┐   ┌─────▼─────┐  ┌────▼─────┐   ┌───▼───┐
 * │ PHASE 1 │   │ PHASE 2 │   │ PHASE 3   │  │ PHASE 4  │   │PHASE 5│
 * │ Core    │   │ Depth   │   │ UI/UX     │  │ Extra-   │   │Multi- │
 * │Features │◄─►│Features │◄─►│Features   │◄─►│ordinary  │◄─►│player  │
 * └────┬────┘   └────┬────┘   └─────┬─────┘  └────┬─────┘   └───┬───┘
 *      │              │               │               │              │
 *      └──────────────┴───────────────┴───────────────┴──────────────┘
 *                                      │
 *                    ┌─────────────────▼───────────────────────────┐
 *                    │         GAME ENGINE CORE                    │
 *                    │    (Original Models.kt, GameLogic.kt)       │
 *                    └─────────────────────────────────────────────┘
 * 
 * =========================================================================
 * 
 * INTEGRATION POINTS:
 * 
 * 1. PHASE 1 (Core) → Original System
 *    - LegacyData carries over between games
 *    - Era affects base stats and events
 *    - Difficulty modifiers apply to AI behavior
 *    - GameSpeed controls turn processing
 *    - PerformanceMetrics tracks history
 *    - NotificationSystem feeds events
 * 
 * 2. PHASE 2 (Depth) → Original System
 *    - GovernmentReforms modify laws system
 *    - NationalDebt affects treasury
 *    - MilitaryBudget integrates with Military
 *    - DiplomaticMissions enhance diplomacy
 *    - SpaceProgram extends Technology
 * 
 * 3. PHASE 3 (UI) → All Phases
 *    - UIState manages all views
 *    - MapState shows Phase 2 military/resources
 *    - NewsFeed displays Phase 4 events
 *    - HelpDatabase guides player
 *    - Themes apply to all screens
 * 
 * 4. PHASE 4 (Extraordinary) → Original System
 *    - TimeParadox affects any stat
 *    - SimulationGlitch triggers random effects
 *    - AISentience provides advisor
 *    - FirstContact adds diplomacy
 *    - All modify GameOver conditions
 * 
 * 5. PHASE 5 (Multiplayer) → All Phases
 *    - Replaces single player state
 *    - Syncs all game data between players
 *    - Manages async turns
 *    - Leaderboards track achievements
 * 
 * =========================================================================
 */

// ============================================================================
// MAIN INTEGRATOR CLASS
// ============================================================================

/**
 * GameIntegrator - The central hub that connects all phases
 * This class orchestrates the interaction between all feature phases
 * and the original game system.
 */
class GameIntegrator {
    
    // =========================================================================
    // PHASE INTERACTION METHODS
    // =========================================================================
    
    /**
     * Process one complete game turn, coordinating all phases
     */
    fun processTurn(
        state: CompleteGameState,
        turnActions: TurnActions
    ): CompleteGameState {
        var updated = state
        
        // 1. Process UI actions from Phase 3
        updated = processUIActions(updated, turnActions)
        
        // 2. Process Phase 1 - Core mechanics
        updated = processPhase1Turn(updated)
        
        // 3. Process Phase 2 - Depth features  
        updated = processPhase2Turn(updated)
        
        // 4. Process Phase 4 - Extraordinary events
        updated = processPhase4Turn(updated)
        
        // 5. Check victory/defeat conditions
        updated = checkGameEndConditions(updated)
        
        // 6. Update Phase 3 - UI state
        updated = updateUIState(updated)
        
        // 7. Process Phase 5 - Multiplayer sync (if applicable)
        if (updated.multiplayerSession != null) {
            updated = syncMultiplayer(updated)
        }
        
        return updated
    }
    
    // =========================================================================
    // PHASE 1: CORE FEATURES → ORIGINAL SYSTEM
    // =========================================================================
    
    private fun processPhase1Turn(state: CompleteGameState): CompleteGameState {
        var updated = state
        var country = state.country
        
        // Apply Era modifiers to base stats
        country = applyEraModifiers(country, state.era)
        
        // Apply Seasonal effects (Phase 1 Feature 20)
        val season = Season.fromMonth((country.turnCount % 12) + 1)
        country = country.copy(
            stats = country.stats.copy(
                economy = (country.stats.economy + season.economyEffect).coerceIn(0, 100),
                happiness = (country.stats.happiness + season.happinessEffect).coerceIn(0, 100),
                military = (country.stats.military + season.militaryEffect).coerceIn(0, 100)
            )
        )
        
        // Apply Difficulty modifiers to AI
        val difficultyModifier = state.gameMode.baseDifficulty.aiAggression
        
        // Process Inflation (Phase 1 Feature 50)
        updated = updated.copy(
            inflationControl = updated.inflationControl.copy(
                currentInflation = (updated.inflationControl.currentInflation + 
                    (Math.random() * 2 - 1)).coerceIn(-5.0, 15.0)
            )
        )
        
        // Apply Performance Metrics tracking
        updated = updated.copy(
            performanceMetrics = updated.performanceMetrics.record(
                country.stats,
                country.treasury,
                country.stats.population
            )
        )
        
        // Apply Legacy bonuses (if New Game Plus)
        if (state.gameMode.isNewGamePlus) {
            country = country.copy(
                stats = state.legacyData.bonusModifiers.applyToStats(country.stats)
            )
        }
        
        updated = updated.copy(country = country)
        return updated
    }
    
    private fun applyEraModifiers(country: Country, era: Era): Country {
        return country.copy(
            stats = country.stats.copy(
                technology = (country.stats.techBonus * era.techLevel).coerceAtMost(100)
            )
        )
    }
    
    // =========================================================================
    // PHASE 2: DEPTH FEATURES → ORIGINAL SYSTEM  
    // =========================================================================
    
    private fun processPhase2Turn(state: CompleteGameState): CompleteGameState {
        var updated = state
        var country = state.country
        
        // Process National Debt payments
        val debtService = updated.nationalDebtData.calculateDebtService()
        if (updated.nationalDebtData.totalDebt > 0 && country.treasury >= debtService) {
            country = country.copy(treasury = country.treasury - debtService)
            updated = updated.copy(
                nationalDebtData = updated.nationalDebtData.copy(
                    totalDebt = (updated.nationalDebtData.totalDebt - debtService).coerceAtLeast(0)
                )
            )
        }
        
        // Process Military Budget allocation
        val budget = state.militaryBudget
        val equipmentUpgrade = budget.getEquipmentAmount()
        
        // Process Bureaucracy efficiency
        val bureau = updated.bureaucracy
        if (bureau.paperworkDelay > 0) {
            // Some decisions take longer
        }
        
        // Process Emergency Powers duration
        if (updated.emergencyPowers.isActive) {
            val remaining = updated.emergencyPowers.turnsRemaining - 1
            if (remaining <= 0) {
                updated = updated.copy(emergencyPowers = EmergencyPowers())
            } else {
                updated = updated.copy(
                    emergencyPowers = updated.emergencyPowers.copy(turnsRemaining = remaining)
                )
            }
        }
        
        // Process Stock Market updates
        updated = updateStockMarket(updated)
        
        updated = updated.copy(country = country)
        return updated
    }
    
    private fun updateStockMarket(state: CompleteGameState): CompleteGameState {
        val market = state.stockMarket
        if (!market.isActive) return state
        
        val trendChange = when (market.trend) {
            com.countrysimulator.game.domain.Phase1Features.MarketTrend.BEARISH -> -20
            com.countrysimulator.game.domain.Phase1Features.MarketTrend.STABLE -> 0
            com.countrysimulator.game.domain.Phase1Features.MarketTrend.BULLISH -> 20
            com.countrysimulator.game.domain.Phase1Features.MarketTrend.VOLATILE -> 
                (Math.random() * 60 - 30).toInt()
        }
        
        val newIndex = (market.marketIndex + trendChange).coerceIn(500, 5000)
        
        return state.copy(
            stockMarket = market.copy(marketIndex = newIndex)
        )
    }
    
    // =========================================================================
    // PHASE 3: UI/UX → ALL PHASES
    // =========================================================================
    
    private fun processUIActions(state: CompleteGameState, actions: TurnActions): CompleteGameState {
        var updated = state
        
        // Handle tutorial progression
        if (state.gameMode.isTutorial) {
            updated = processTutorial(updated, actions)
        }
        
        // Add news from events
        updated = updateNews(updated)
        
        return updated
    }
    
    private fun processTutorial(state: CompleteGameState, actions: TurnActions): CompleteGameState {
        val currentStep = state.country.tutorial.firstOrNull { !it.isCompleted } ?: return state
        
        val completedAction = when (currentStep.requiredAction) {
            TutorialAction.END_TURN -> actions.endedTurn
            TutorialAction.DECLARE_WAR -> actions.declaredWar
            else -> false
        }
        
        if (completedAction) {
            val updatedTutorial = state.country.tutorial.map {
                if (it.id == currentStep.id) it.copy(isCompleted = true) else it
            }
            val country = state.country.copy(tutorial = updatedTutorial)
            return state.copy(country = country)
        }
        
        return state
    }
    
    private fun updateNews(state: CompleteGameState): CompleteGameState {
        // Generate news from game events
        val newArticle = NewsGenerator.generateNewsItem(
            NewsCategory.ECONOMIC,
            state.country.name
        )
        
        val uiState = state.uiState.copy(
            newsFeed = NewsFeed(
                articles = (state.uiState.newsFeed.articles + newArticle).take(100),
                unreadCount = state.uiState.newsFeed.unreadCount + 1
            )
        )
        
        return state.copy(uiState = uiState)
    }
    
    private fun updateUIState(state: CompleteGameState): CompleteGameState {
        val metrics = state.performanceMetrics
        
        // Update graphs with new data
        val newGraphs = listOf(
            StatGraph(
                type = GraphType.LINE,
                title = "Economy",
                dataPoints = metrics.economyHistory.mapIndexed { index, value ->
                    GraphDataPoint(index, value)
                }
            )
        )
        
        val uiState = state.uiState.copy(graphs = newGraphs)
        
        return state.copy(uiState = uiState)
    }
    
    // =========================================================================
    // PHASE 4: EXTRAORDINARY → ALL PHASES
    // =========================================================================
    
    private fun processPhase4Turn(state: CompleteGameState): CompleteGameState {
        var updated = state
        
        // Process time paradoxes
        updated = processTimeParadoxes(updated)
        
        // Process supernatural events  
        updated = processSupernatural(updated)
        
        // Process AI sentience
        updated = processAISentience(updated)
        
        // Random chance of glitches
        if (Math.random() < 0.05) {
            val glitch = GlitchDatabase.glitches.random()
            updated = updated.copy(
                extraordinaryState = updated.extraordinaryState.copy(
                    simulationGlitches = updated.extraordinaryState.simulationGlitches + glitch
                )
            )
        }
        
        return updated
    }
    
    private fun processTimeParadoxes(state: CompleteGameState): CompleteGameState {
        val paradoxes = state.extraordinaryState.timeParadoxes
        
        if (paradoxes.isEmpty()) return state
        
        // Check for manifestation
        val manifesting = paradoxes.filter { it.turnsUntilManifestation <= 0 && !it.isResolved }
        
        if (manifesting.isNotEmpty()) {
            val effect = manifesting.first().effects
            val country = state.country.copy(
                stats = state.country.stats.copy(
                    economy = (state.country.stats.economy + effect.economyModifier).coerceIn(0, 100),
                    stability = (state.country.stats.stability + effect.stabilityModifier).coerceIn(0, 100),
                    technology = (state.country.stats.technology + effect.technologyModifier).coerceIn(0, 100)
                )
            )
            return state.copy(
                country = country,
                extraordinaryState = state.extraordinaryState.copy(
                    timeParadoxes = state.extraordinaryState.timeParadoxes.map {
                        if (it.id == manifesting.first().id) it.copy(isResolved = true) else it
                    }
                )
            )
        }
        
        return state
    }
    
    private fun processSupernatural(state: CompleteGameState): CompleteGameState {
        val events = state.extraordinaryState.supernaturalEvents
        
        if (events.isEmpty()) return state
        
        // Apply effects from active supernatural events
        val activeEffects = events.map { it.effects }
        
        var country = state.country
        for (effect in activeEffects) {
            country = country.copy(
                stats = country.stats.copy(
                    happiness = (country.stats.happiness + effect.happinessModifier).coerceIn(0, 100),
                    stability = (country.stats.stability + effect.stabilityModifier).coerceIn(0, 100)
                )
            )
        }
        
        return state.copy(country = country)
    }
    
    private fun processAISentience(state: CompleteGameState): CompleteGameState {
        val ai = state.extraordinaryState.aiSentience
        
        if (!ai.isSentient) return state
        
        // AI might provide bonuses or penalties based on loyalty
        if (ai.isCooperating && ai.loyalty > 70) {
            val country = state.country.copy(
                stats = state.country.stats.copy(
                    technology = (state.country.stats.technology + 2).coerceAtMost(100)
                )
            )
            return state.copy(country = country)
        }
        
        return state
    }
    
    // =========================================================================
    // PHASE 5: MULTIPLAYER SYNC
    // =========================================================================
    
    private fun syncMultiplayer(state: CompleteGameState): CompleteGameState {
        val session = state.multiplayerSession ?: return state
        
        if (session.status == SessionStatus.IN_PROGRESS) {
            // Broadcast state to all players
            // This would integrate with actual network code
        }
        
        return state
    }
    
    // =========================================================================
    // VICTORY/DEFEAT CONDITIONS
    // =========================================================================
    
    private fun checkGameEndConditions(state: CompleteGameState): CompleteGameState {
        // Check victory conditions (Phase 1 Feature 15)
        val victory = FeatureUtils.checkVictoryConditions(state)
        if (victory != null) {
            return state.copy(isGameOver = true, gameOverReason = GameOverReason.TECH_FAILURE)
        }
        
        // Check defeat conditions
        val country = state.country
        if (country.treasury < -10000) {
            return state.copy(isGameOver = true, gameOverReason = GameOverReason.BANKRUPTCY)
        }
        if (country.stats.happiness <= 0) {
            return state.copy(isGameOver = true, gameOverReason = GameOverReason.REVOLUTION)
        }
        if (country.stats.stability <= 0) {
            return state.copy(isGameOver = true, gameOverReason = GameOverReason.CIVIL_WAR)
        }
        
        return state
    }
    
    // =========================================================================
    // INTEGRATION HELPERS
    // =========================================================================
    
    /**
     * Convert original GameState to CompleteGameState
     * This is the main entry point for integrating with the original system
     */
    fun fromOriginalState(
        original: GameState,
        era: Era = Era.MODERN_AGE,
        difficulty: Difficulty = Difficulty.NORMAL,
        isTutorial: Boolean = false
    ): CompleteGameState {
        return CompleteGameState(
            country = original.country,
            aiNations = original.aiNations,
            globalMarket = original.globalMarket,
            isGameOver = original.isGameOver,
            gameOverReason = original.gameOverReason,
            era = era,
            gameMode = GameMode(
                baseDifficulty = difficulty,
                isTutorial = isTutorial
            ),
            performanceMetrics = PerformanceMetrics(),
            uiState = UIState()
        )
    }
    
    /**
     * Convert CompleteGameState back to original for legacy compatibility
     */
    fun toOriginalState(complete: CompleteGameState): GameState {
        return GameState(
            country = complete.country,
            aiNations = complete.aiNations,
            globalMarket = complete.globalMarket,
            isGameOver = complete.isGameOver,
            gameOverReason = complete.gameOverReason,
            eventHistory = complete.country.eventHistory,
            newsHeadline = complete.uiState.newsFeed.articles.firstOrNull()?.title
        )
    }
}

/**
 * Actions the player takes during a turn (for Phase 3 Tutorial)
 */
data class TurnActions(
    val endedTurn: Boolean = false,
    val declaredWar: Boolean = false,
    val signedTreaty: Boolean = false,
    val passedLaw: Boolean = false,
    val builtInfrastructure: Boolean = false,
    val researched: Boolean = false
)

// ============================================================================
// CONVENIENCE EXTENSION FUNCTIONS
// ============================================================================

/**
 * Easy access to CompleteGameState from anywhere
 */
val GameState.toComplete: CompleteGameState
    get() = GameIntegrator().fromOriginalState(this)

/**
 * Easy access back to original state
 */
val CompleteGameState.toOriginal: GameState
    get() = GameIntegrator().toOriginalState(this)

/**
 * Apply difficulty modifiers to a nation
 */
fun Country.applyDifficulty(difficulty: Difficulty): Country {
    val multiplier = difficulty.enemyStrengthMultiplier
    return copy(
        stats = stats.copy(
            economy = (stats.economy / multiplier).toInt().coerceIn(0, 100),
            military = (stats.military / multiplier).toInt().coerceIn(0, 100),
            stability = (stats.stability / multiplier).toInt().coerceIn(0, 100)
        )
    )
}

/**
 * Get all active bonuses from a complete game state
 */
fun CompleteGameState.getAllBonuses(): Map<String, Int> {
    val bonuses = mutableMapOf<String, Int>()
    
    // From Era
    bonuses["economy_era"] = era.economyMultiplier.toInt()
    
    // From Legacy
    bonuses["economy_legacy"] = legacyData.bonusModifiers.economyBonus
    bonuses["military_legacy"] = legacyData.bonusModifiers.militaryBonus
    
    // From Government Type
    bonuses["economy_gov"] = governmentType.economyBonus
    bonuses["military_gov"] = governmentType.militaryBonus
    
    // From Emergency Powers
    if (emergencyPowers.isActive) {
        emergencyPowers.effects.forEach { effect ->
            bonuses["${effect.stat}_emergency"] = effect.value
        }
    }
    
    // From AI Sentience
    if (extraordinaryState.aiSentience.isSentient && 
        extraordinaryState.aiSentience.isCooperating) {
        bonuses["technology_ai"] = 5
    }
    
    return bonuses
}
