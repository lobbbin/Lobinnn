package com.countrysimulator.game.domain

import kotlinx.serialization.Serializable

/**
 * ========================================================================
 * COUNTRY SIMULATOR - PHASE 1 FEATURES IMPLEMENTATION
 * Features 1-50: Core Gameplay, Government & Politics, Economic System
 * ========================================================================
 */

// ==================== FEATURE 1: LEGACY SYSTEM ====================

/**
 * Legacy System - Permanent stat bonuses carried over between playthroughs
 */
@Serializable
data class LegacyData(
    val totalGamesPlayed: Int = 0,
    val totalWins: Int = 0,
    val totalLosses: Int = 0,
    val legacyPoints: Int = 0,
    val unlockedAchievements: List<String> = emptyList(),
    val bonusModifiers: LegacyBonus = LegacyBonus()
)

@Serializable
data class LegacyBonus(
    val economyBonus: Int = 0,      // +1% economy per point
    val militaryBonus: Int = 0,     // +1% military per point  
    val happinessBonus: Int = 0,   // +1% happiness per point
    val techBonus: Int = 0,        // +1% technology per point
    val stabilityBonus: Int = 0    // +1% stability per point
) {
    fun applyToStats(stats: CountryStats): CountryStats {
        return stats.copy(
            economy = (stats.economy + economyBonus).coerceAtMost(100),
            military = (stats.military + militaryBonus).coerceAtMost(100),
            happiness = (stats.happiness + happinessBonus).coerceAtMost(100),
            technology = (stats.technology + techBonus).coerceAtMost(100),
            stability = (stats.stability + stabilityBonus).coerceAtMost(100)
        )
    }
}

fun calculateLegacyPoints(gameResult: GameResult, turnsPlayed: Int): Int {
    var points = 0
    if (gameResult == GameResult.VICTORY) points += 100
    if (gameResult == GameResult.SURVIVAL) points += 50
    
    // Bonus for quick completion
    if (turnsPlayed < 100) points += 50
    else if (turnsPlayed < 200) points += 25
    
    // Difficulty bonus
    points *= 2 // Will be multiplied by difficulty modifier
    
    return points
}

enum class GameResult {
    VICTORY,
    DEFEAT,
    SURVIVAL,
    ABANDONED
}

// ==================== FEATURE 2: HISTORICAL TIMELINE MODE ====================

/**
 * Historical Timeline Mode - Start from any year 1800-2024
 */
@Serializable
enum class Era(
    val displayName: String,
    val startYear: Int,
    val endYear: Int,
    val techLevel: Int,      // 1-10 scale
    val economyMultiplier: Double,
    val militaryMultiplier: Double,
    val availableGovernmentTypes: List<GovernmentType>,
    val specialEvents: List<String>
) {
    COLONIAL_AGE(
        "Colonial Age", 1800, 1850, 1, 0.5, 0.5,
        listOf(GovernmentType.MONARCHY, GovernmentType.REPUBLIC, GovernmentType.CONFEDERACY),
        listOf("colonial_revolt", "industrial_revolution", "great_war")
    ),
    INDUSTRIAL_AGE(
        "Industrial Age", 1850, 1900, 3, 0.7, 0.7,
        listOf(GovernmentType.MONARCHY, GovernmentType.REPUBLIC, GovernmentType.DICTATORSHIP, GovernmentType.CONFEDERACY),
        listOf("world_fair", "colonial_expansion", "labor_movement")
    ),
    WORLD_WARS(
        "World Wars", 1900, 1950, 5, 0.8, 1.2,
        listOf(GovernmentType.DEMOCRACY, GovernmentType.DICTATORSHIP, GovernmentType.MONARCHY, GovernmentType.REPUBLIC),
        listOf("world_war_1", "great_depression", "world_war_2", "cold_war_start")
    ),
    COLD_WAR(
        "Cold War", 1950, 1990, 7, 1.0, 1.0,
        listOf(GovernmentType.DEMOCRACY, GovernmentType.COMMUNISM, GovernmentType.DICTATORSHIP, GovernmentType.REPUBLIC),
        listOf("space_race", "nuclear_proliferation", "decolonization", "economic_miracle")
    ),
    MODERN_AGE(
        "Modern Age", 1990, 2024, 9, 1.2, 0.9,
        listOf(GovernmentType.DEMOCRACY, GovernmentType.REPUBLIC, GovernmentType.FEDERATION, GovernmentType.SOCIALISM),
        listOf("globalization", "digital_revolution", "climate_change", "terrorism")
    ),
    FUTURE_AGE(
        "Future Age", 2024, 2100, 10, 1.5, 1.0,
        listOf(GovernmentType.DEMOCRACY, GovernmentType.TECHNOCRACY, GovernmentType.FEDERATION, GovernmentType.REPUBLIC),
        listOf("ai_emergence", "climate_collapse", "space_colonization")
    );

    companion object {
        fun fromYear(year: Int): Era {
            return when {
                year < 1850 -> COLONIAL_AGE
                year < 1900 -> INDUSTRIAL_AGE
                year < 1950 -> WORLD_WARS
                year < 1990 -> COLD_WAR
                year < 2024 -> MODERN_AGE
                else -> FUTURE_AGE
            }
        }
    }
}

// ==================== FEATURES 3 & 14: SCENARIO EDITOR & QUICK START ====================

/**
 * Scenario Editor & Quick Start Options
 */
@Serializable
data class Scenario(
    val id: String,
    val name: String,
    val description: String,
    val difficulty: Difficulty,
    val startYear: Int,
    val playerNation: ScenarioNation,
    val aiNations: List<ScenarioNation>,
    val initialResources: Resources,
    val specialRules: List<String>,
    val isCustom: Boolean = false
)

@Serializable
data class ScenarioNation(
    val id: String,
    val name: String,
    val governmentType: GovernmentType,
    val personality: AiPersonality = AiPersonality.TRADER,
    val stats: CountryStats,
    val treasury: Int,
    val resources: Resources,
    val isPlayer: Boolean = false
)

enum class Difficulty(
    val displayName: String,
    val description: String,
    val aiAggression: Double,
    val eventFrequency: Double,
    val resourceMultiplier: Double,
    val enemyStrengthMultiplier: Double,
    val legacyMultiplier: Int
) {
    PEACEFUL(
        "Peaceful", "Relaxed gameplay for new players",
        0.5, 0.5, 1.5, 0.5, 1
    ),
    NORMAL(
        "Normal", "Standard challenge",
        1.0, 1.0, 1.0, 1.0, 2
    ),
    CHALLENGING(
        "Challenging", "Tough opposition",
        1.5, 1.2, 0.8, 1.5, 3
    ),
    HARD(
        "Hard", "Expert players only",
        2.0, 1.5, 0.6, 2.0, 4
    ),
    IMPOSSIBLE(
        "Impossible", "Nearly impossible to win",
        3.0, 2.0, 0.4, 3.0, 5
    ),
    IRONMAN(
        "Ironman", "No save/load - one life!",
        1.5, 1.5, 0.8, 1.5, 5
    )
}

// ==================== FEATURE 6: SPEED CONTROLS ====================

/**
 * Speed Controls for turn-based gameplay
 */
enum class GameSpeed(
    val displayName: String,
    val multiplier: Double,
    val delayMs: Long
) {
    PAUSED("Paused", 0.0, 0L),
    SLOW("1x Speed", 1.0, 2000L),
    NORMAL("2x Speed", 2.0, 1000L),
    FAST("5x Speed", 5.0, 500L),
    ULTRA("10x Speed", 10.0, 100L)
}

// ==================== FEATURES 11-12: NEW GAME PLUS & IRONMAN ====================

/**
 * New Game Plus & Ironman Mode flags
 */
@Serializable
data class GameMode(
    val isNewGamePlus: Boolean = false,
    val isIronman: Boolean = false,
    val isTutorial: Boolean = false,
    val baseDifficulty: Difficulty = Difficulty.NORMAL,
    val turnsPlayedInSession: Int = 0,
    val ironmanDeathCount: Int = 0
)

// ==================== FEATURE 13: TUTORIAL SYSTEM ====================

/**
 * Tutorial Mode - Interactive tutorial teaching core mechanics
 */
@Serializable
data class TutorialStep(
    val id: String,
    val title: String,
    val description: String,
    val highlightElement: String,  // UI element to highlight
    val requiredAction: TutorialAction,
    val isCompleted: Boolean = false
)

enum class TutorialAction {
    NONE,
    CLICK_COUNTRY_NAME,
    ADJUST_TAX_RATE,
    RECRUIT_TROOPS,
    DECLARE_WAR,
    SIGN_TRADE_AGREEMENT,
    PASS_LAW,
    BUILD_INFRASTRUCTURE,
    RESEARCH_TECHNOLOGY,
    IMPROVE_HAPPINESS,
    REDUCE_CORRUPTION,
    END_TURN
}

object TutorialDatabase {
    val steps = listOf(
        TutorialStep(
            "tut_1", "Welcome to Country Simulator",
            "Lead your nation to greatness through diplomacy, economics, and military power.",
            "country_name", TutorialAction.CLICK_COUNTRY_NAME
        ),
        TutorialStep(
            "tut_2", "Manage Your Economy",
            "Adjust tax rates to balance treasury income with citizen happiness.",
            "tax_rate", TutorialAction.ADJUST_TAX_RATE
        ),
        TutorialStep(
            "tut_3", "Build Your Military",
            "Recruit troops to defend your nation or expand your influence.",
            "military_tab", TutorialAction.RECRUIT_TROOPS
        ),
        TutorialStep(
            "tut_4", "Diplomatic Relations",
            "Build alliances and trade agreements with other nations.",
            "diplomacy_tab", TutorialAction.SIGN_TRADE_AGREEMENT
        ),
        TutorialStep(
            "tut_5", "Pass Laws",
            "Enact policies that shape your nation's future.",
            "laws_tab", TutorialAction.PASS_LAW
        ),
        TutorialStep(
            "tut_6", "Infrastructure Development",
            "Build roads, ports, and facilities to improve stability.",
            "infrastructure_button", TutorialAction.BUILD_INFRASTRUCTURE
        ),
        TutorialStep(
            "tut_7", "Research Technology",
            "Advance your nation through technological innovation.",
            "technology_tab", TutorialAction.RESEARCH_TECHNOLOGY
        ),
        TutorialStep(
            "tut_8", "Keep Citizens Happy",
            "Balance military spending with social programs.",
            "happiness_stat", TutorialAction.IMPROVE_HAPPINESS
        ),
        TutorialStep(
            "tut_9", "Fight Corruption",
            "Reduce corruption to improve government efficiency.",
            "corruption_stat", TutorialAction.REDUCE_CORRUPTION
        ),
        TutorialStep(
            "tut_10", "End Your Turn",
            "Each turn represents a season. End turn to see results.",
            "end_turn_button", TutorialAction.END_TURN
        )
    )
}

// ==================== FEATURE 15: VICTORY CONDITIONS ====================

/**
 * Multiple Victory Conditions
 */
enum class VictoryType(
    val displayName: String,
    val description: String,
    val requirement: String
) {
    ECONOMIC_DOMINANCE(
        "Economic Dominance",
        "Become the world's wealthiest nation",
        "Economy rating: 100, Treasury: 100,000"
    ),
    MILITARY_SUPERPOWER(
        "Military Superpower",
        "Build the most powerful military",
        "Military rating: 100, Total troops: 100,000"
    ),
    DIPLOMATIC_INFLUENCE(
        "Diplomatic Influence",
        "Lead the international community",
        "50+ allies, UN Secretary-General"
    ),
    TECHNOLOGICAL_SUPREMACY(
        "Technological Supremacy",
        "Achieve technological singularity",
        "Technology rating: 100, All techs researched"
    ),
    CULTURAL_VICTORY(
        "Cultural Victory",
        "Become the world's cultural capital",
        "Soft Power: 100, Tourism: Maximum"
    ),
    WORLD_CONQUEST(
        "World Conquest",
        "Control all nations through military",
        "All nations: conquered or vassalized"
    ),
    SURVIVAL(
        "Survival Victory",
        "Survive for 500 turns without collapse",
        "Turn count: 500, No defeat conditions"
    )
}

// ==================== FEATURE 16: DEFEAT ANALYSIS ====================

/**
 * Defeat Analysis - Detailed breakdown of what led to game over
 */
@Serializable
data class DefeatAnalysis(
    val turnNumber: Int,
    val year: Int,
    val cause: GameOverReason,
    val statsAtDeath: CountryStats,
    val finalRanking: Int,
    val totalNationsAlive: Int,
    val contributingFactors: List<String>,
    val suggestions: List<String>
) {
    companion object {
        fun generate(state: GameState, ranking: Int, totalNations: Int): DefeatAnalysis {
            val factors = mutableListOf<String>()
            val suggestions = mutableListOf<String>()
            
            val stats = state.country.stats
            
            if (stats.economy < 20) {
                factors.add("Economy collapsed (${stats.economy}%)")
                suggestions.add("Focus on economic development and trade agreements")
            }
            if (stats.happiness < 15) {
                factors.add("Citizens revolted (Happiness: ${stats.happiness}%)")
                suggestions.add("Increase social spending and reduce oppression")
            }
            if (stats.stability < 10) {
                factors.add("Government unstable (Stability: ${stats.stability}%)")
                suggestions.add("Build infrastructure and maintain order")
            }
            if (state.country.treasury < 0) {
                factors.add("National bankruptcy")
                suggestions.add("Reduce spending and increase taxes carefully")
            }
            
            return DefeatAnalysis(
                turnNumber = state.country.turnCount,
                year = state.country.year,
                cause = state.gameOverReason ?: GameOverReason.BANKRUPTCY,
                statsAtDeath = stats,
                ranking = ranking,
                totalNationsAlive = totalNations,
                contributingFactors = factors,
                suggestions = suggestions
            )
        }
    }
}

// ==================== FEATURE 18: PERFORMANCE METRICS ====================

/**
 * Performance Metrics - Real-time graphs showing stat trends
 */
@Serializable
data class PerformanceMetrics(
    val economyHistory: List<Int> = emptyList(),
    val militaryHistory: List<Int> = emptyList(),
    val happinessHistory: List<Int> = emptyList(),
    val stabilityHistory: List<Int> = emptyList(),
    val technologyHistory: List<Int> = emptyList(),
    val treasuryHistory: List<Int> = emptyList(),
    val populationHistory: List<Int> = emptyList()
) {
    fun record(stats: CountryStats, treasury: Int, population: Int): PerformanceMetrics {
        return copy(
            economyHistory = (economyHistory + stats.economy).takeLast(50),
            militaryHistory = (militaryHistory + stats.military).takeLast(50),
            happinessHistory = (happinessHistory + stats.happiness).takeLast(50),
            stabilityHistory = (stabilityHistory + stats.stability).takeLast(50),
            technologyHistory = (technologyHistory + stats.technology).takeLast(50),
            treasuryHistory = (treasuryHistory + treasury).takeLast(50),
            populationHistory = (populationHistory + population).takeLast(50)
        )
    }
}

// ==================== FEATURE 19: NOTIFICATION SYSTEM ====================

/**
 * Notification System - Customizable alerts for important events
 */
@Serializable
data class Notification(
    val id: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val turn: Int,
    val isRead: Boolean = false,
    val priority: NotificationPriority = NotificationPriority.NORMAL
)

enum class NotificationType {
    DIPLOMATIC,
    ECONOMIC,
    MILITARY,
    POLITICAL,
    DISASTER,
    ACHIEVEMENT,
    TECHNOLOGICAL,
    SOCIAL
}

enum class NotificationPriority {
    LOW,
    NORMAL,
    HIGH,
    URGENT
}

@Serializable
data class NotificationSettings(
    val enableDiplomatic: Boolean = true,
    val enableEconomic: Boolean = true,
    val enableMilitary: Boolean = true,
    val enablePolitical: Boolean = true,
    val enableDisasters: Boolean = true,
    val enableAchievements: Boolean = true,
    val enableTechnological: Boolean = true,
    val enableSocial: Boolean = true,
    val enableSound: Boolean = true,
    val enableVibration: Boolean = true
)

// ==================== FEATURE 20: SEASON SYSTEM ====================

/**
 * Season System - Each turn represents a season with different effects
 */
enum class Season(
    val displayName: String,
    val economyEffect: Int,      // Positive = boom, Negative = recession
    val happinessEffect: Int,   // Holiday bonuses
    val militaryEffect: Int,     // Weather affects training
    val resourceConsumption: Int // Food/energy consumption multiplier
) {
    SPRING("Spring", 5, 0, 5, 1),
    SUMMER("Summer", 10, 10, 0, 1),
    AUTUMN("Autumn", 0, 0, 5, 1),
    WINTER("Winter", -10, -5, -10, 2);

    companion object {
        fun fromMonth(month: Int): Season {
            return when (month) {
                in 3..5 -> SPRING
                in 6..8 -> SUMMER
                in 9..11 -> AUTUMN
                else -> WINTER
            }
        }
    }
}

// ==================== FEATURES 27-45: ADDITIONAL GOVERNMENT FEATURES ====================

/**
 * Feature 27: Censorship Level
 */
enum class CensorshipLevel(
    val displayName: String,
    val stabilityBonus: Int,
    val happinessPenalty: Int,
    val propagandaMultiplier: Double
) {
    FREE("Free Press", 0, 0, 1.0),
    LIGHT("Light Censorship", 5, -5, 1.2),
    MODERATE("Moderate Censorship", 10, -10, 1.5),
    HEAVY("Heavy Censorship", 15, -20, 2.0),
    TOTAL("Totalitarian", 20, -30, 3.0)
}

/**
 * Feature 31: Bureaucracy System
 */
@Serializable
data class Bureaucracy(
    val efficiency: Int = 80,     // 0-100, higher = faster decisions
    val corruption: Int = 10,      // 0-100
    val paperworkDelay: Int = 1,  // Turns added to decisions
    val departmentCount: Int = 5
)

/**
 * Feature 33: State Religion
 */
@Serializable
enum class Religion(
    val displayName: String,
    val stabilityBonus: Int,
    val happinessBonus: Int,
    val techPenalty: Int,
    val allowed: Boolean = true
) {
    NONE("Secular State", 0, 0, 0),
    Christianity("Christianity", 10, 5, -5),
    Islam("Islam", 10, 5, -5),
    Buddhism("Buddhism", 5, 10, 0),
    Judaism("Judaism", 5, 5, 5),
    Hinduism("Hinduism", 10, 5, -5),
    OTHER("Other", 5, 5, 0)
}

/**
 * Feature 34: National Symbols
 */
@Serializable
data class NationalSymbols(
    val flagDesign: Int = 0,      // 0-9 for different patterns
    val anthem: String = "National Anthem",
    val nationalDay: String = "Independence Day",
    val nationalAnimal: String = "Eagle",
    val nationalFlower: String = "Rose"
)

/**
 * Feature 36: Policy Slots
 */
@Serializable
data class PolicySlots(
    val economicSlots: Int = 3,
    val militarySlots: Int = 2,
    val socialSlots: Int = 3,
    val diplomaticSlots: Int = 2,
    val usedEconomic: Int = 0,
    val usedMilitary: Int = 0,
    val usedSocial: Int = 0,
    val usedDiplomatic: Int = 0
) {
    fun canUsePolicy(category: PolicyCategory): Boolean {
        return when (category) {
            PolicyCategory.ECONOMIC -> usedEconomic < economicSlots
            PolicyCategory.MILITARY -> usedMilitary < militarySlots
            PolicyCategory.SOCIAL -> usedSocial < socialSlots
            PolicyCategory.DIPLOMATIC -> usedDiplomatic < diplomaticSlots
        }
    }
    
    fun useSlot(category: PolicyCategory): PolicySlots {
        return when (category) {
            PolicyCategory.ECONOMIC -> copy(usedEconomic = usedEconomic + 1)
            PolicyCategory.MILITARY -> copy(usedMilitary = usedMilitary + 1)
            PolicyCategory.SOCIAL -> copy(usedSocial = usedSocial + 1)
            PolicyCategory.DIPLOMATIC -> copy(usedDiplomatic = usedDiplomatic + 1)
        }
    }
}

enum class PolicyCategory {
    ECONOMIC, MILITARY, SOCIAL, DIPLOMATIC
}

/**
 * Feature 37: Government Reform System
 */
@Serializable
data class GovernmentReform(
    val id: String,
    val name: String,
    val description: String,
    val tier: Int,                 // 1-5
    val requiredGovernmentTypes: List<GovernmentType>,
    val requiredYear: Int,
    val cost: Int,
    val effects: List<LawEffect>,
    val isEnacted: Boolean = false
)

/**
 * Feature 38: Emergency Powers
 */
@Serializable
data class EmergencyPowers(
    val isActive: Boolean = false,
    val turnsRemaining: Int = 0,
    val type: EmergencyPowerType? = null,
    val effects: List<LawEffect> = emptyList()
)

enum class EmergencyPowerType(
    val displayName: String,
    val duration: Int,
    val stabilityBonus: Int,
    val happinessPenalty: Int,
    val cost: Int
) {
    WAR_DECLARATION("War Powers", 5, 20, -15, 5000),
    MARTIAL_LAW("Martial Law", 3, 25, -25, 3000),
    ECONOMIC_EMERGENCY("Economic Emergency", 4, 10, -10, 4000),
    NATURAL_DISASTER("Disaster Response", 2, 15, -5, 2000)
}

/**
 * Feature 39: Succession System
 */
@Serializable
data class SuccessionSystem(
    val currentLeader: String = "President",
    val successor: String? = null,
    val successionType: SuccessionType = SuccessionType.DEMOCRATIC,
    val stabilityModifier: Int = 0
)

enum class SuccessionType(
    val displayName: String,
    val instabilityRisk: Int,
    val transitionBonus: Int
) {
    DEMOCRATIC("Democratic Election", 0, 0),
    HEREDITARY("Hereditary", 15, -10),
    APPOINTED("Appointed", 10, -5),
    MILITARY("Military Council", 20, -15),
    REVOLUTIONARY("Revolutionary", 30, -20)
}

/**
 * Feature 41: Secret Police
 */
@Serializable
data class SecretPolice(
    val level: Int = 0,              // 0-10
    val effectiveness: Int = 0,     // Based on level
    val cost: Int = 500,
    val publicApproval: Int = 0     // Reduced by secret police
)

/**
 * Feature 42: Public Holidays
 */
@Serializable
data class PublicHoliday(
    val id: String,
    val name: String,
    val month: Int,
    val day: Int,
    val happinessBonus: Int,
    val economyPenalty: Int,
    val isNational: Boolean = true
)

/**
 * Feature 43: National Identity
 */
@Serializable
data class NationalIdentity(
    val primaryCulture: String = "Default",
    val secondaryCultures: List<String> = emptyList(),
    val language: String = "English",
    val identityStrength: Int = 50,   // 0-100
    val nationalismLevel: Int = 50    // 0-100
)

/**
 * Feature 44: Ruling Elite
 */
@Serializable
data class RulingElite(
    val satisfaction: Int = 70,     // 0-100
    val power: Int = 50,             // 0-100
    val corruption: Int = 20,        // 0-100
    val influence: Int = 50          // 0-100
)

// ==================== FEATURES 46-50: ECONOMIC SYSTEM ====================

/**
 * Feature 46: Stock Market
 */
@Serializable
data class StockMarket(
    val isActive: Boolean = false,
    val marketIndex: Int = 1000,
    val volatility: Double = 1.0,     // 0.5-2.0
    val trend: MarketTrend = MarketTrend.STABLE,
    val playerInvestments: List<Investment> = emptyList()
)

enum class MarketTrend {
    BEARISH,    // Declining
    STABLE,     // Flat
    BULLISH,    // Rising
    VOLATILE    // Fluctuating
}

@Serializable
data class Investment(
    val id: String,
    val name: String,
    val amount: Int,
    val returnRate: Double,
    val risk: InvestmentRisk,
    val turnsRemaining: Int
)

enum class InvestmentRisk(val displayName: String, val multiplier: Double) {
    SAFE("Safe", 1.0),
    MODERATE("Moderate", 1.5),
    RISKY("Risky", 2.0),
    SPECULATIVE("Speculative", 3.0)
}

/**
 * Feature 48: Tariff System
 */
@Serializable
data class TariffSystem(
    val importTariff: Int = 10,      // 0-100%
    val exportTariff: Int = 5,       // 0-100%
    val luxuryTariff: Int = 25,      // 0-100%
    val strategicTariff: Int = 15    // 0-100%
)

/**
 * Feature 49: National Currency
 */
@Serializable
data class Currency(
    val name: String = "Dollar",
    val symbol: String = "$",
    val exchangeRate: Double = 1.0,  // Against USD
    val isFloating: Boolean = true,
    val inflationRate: Double = 0.0  // -10% to +10%
)

/**
 * Feature 50: Inflation Control
 */
@Serializable
data class InflationControl(
    val currentInflation: Double = 0.0,  // -5% to +15%
    val targetInflation: Double = 2.0,
    val interestRate: Double = 3.0,      // 0-10%
    val moneySupply: Int = 10000,
    val centralBankIndependence: Int = 50 // 0-100
)

// ==================== ENHANCED COUNTRY DATA STRUCTURE ====================

/**
 * Enhanced Country with Phase 1 Features
 */
@Serializable
data class CountryV2(
    // Basic info
    val name: String,
    val governmentType: GovernmentType,
    val stats: CountryStats,
    val resources: Resources = Resources(),
    val diplomaticRelations: List<DiplomaticRelation> = emptyList(),
    val year: Int = 2024,
    val treasury: Int = 10000,
    val turnCount: Int = 0,
    val eventHistory: List<String> = emptyList(),
    val policies: List<String> = emptyList(),
    val politicalParties: List<PoliticalParty> = emptyList(),
    val activeLaws: List<Law> = emptyList(),
    val factions: List<PoliticalFaction> = emptyList(),
    val ministers: List<Minister> = emptyList(),
    val election: Election? = null,
    val currentTermYear: Int = 0,
    val taxRate: TaxRate = TaxRate.NORMAL,
    val unitedNations: UnitedNations = UnitedNations(),
    val activeSpyMissions: List<SpyMission> = emptyList(),
    val military: Military = Military(),
    
    // PHASE 1 NEW FIELDS
    val legacyData: LegacyData = LegacyData(),
    val era: Era = Era.MODERN_AGE,
    val scenario: Scenario? = null,
    val gameMode: GameMode = GameMode(),
    val tutorial: List<TutorialStep> = TutorialDatabase.steps,
    val performanceMetrics: PerformanceMetrics = PerformanceMetrics(),
    val notifications: List<Notification> = emptyList(),
    val notificationSettings: NotificationSettings = NotificationSettings(),
    val censorshipLevel: CensorshipLevel = CensorshipLevel.FREE,
    val bureaucracy: Bureaucracy = Bureaucracy(),
    val stateReligion: Religion = Religion.NONE,
    val nationalSymbols: NationalSymbols = NationalSymbols(),
    val policySlots: PolicySlots = PolicySlots(),
    val governmentReforms: List<GovernmentReform> = emptyList(),
    val emergencyPowers: EmergencyPowers = EmergencyPowers(),
    val succession: SuccessionSystem = SuccessionSystem(),
    val secretPolice: SecretPolice = SecretPolice(),
    val publicHolidays: List<PublicHoliday> = emptyList(),
    val nationalIdentity: NationalIdentity = NationalIdentity(),
    val rulingElite: RulingElite = RulingElite(),
    
    // Economic features
    val stockMarket: StockMarket = StockMarket(),
    val tariffSystem: TariffSystem = TariffSystem(),
    val currency: Currency = Currency(),
    val inflationControl: InflationControl = InflationControl(),
    val nationalDebt: Int = 0,
    val creditRating: Int = 75  // 0-100, affects borrowing
)

// ==================== GAME LOGIC EXTENSIONS ====================

object GameLogicV2 {
    
    /**
     * Process turn with Phase 1 features
     */
    fun processTurnV2(country: CountryV2): CountryV2 {
        var updated = country.copy(turnCount = country.turnCount + 1)
        
        // Update year based on season
        val season = Season.fromMonth((updated.turnCount % 12) + 1)
        updated = updated.copy(year = updated.year + (updated.turnCount / 48))
        
        // Apply seasonal effects
        updated = updated.copy(
            stats = updated.stats.copy(
                economy = (updated.stats.economy + season.economyEffect).coerceIn(0, 100),
                happiness = (updated.stats.happiness + season.happinessEffect).coerceIn(0, 100),
                military = (updated.stats.military + season.militaryEffect).coerceIn(0, 100)
            )
        )
        
        // Update performance metrics
        val metrics = updated.performanceMetrics.record(
            updated.stats, 
            updated.treasury, 
            updated.stats.population
        )
        updated = updated.copy(performanceMetrics = metrics)
        
        // Process inflation
        updated = processInflation(updated)
        
        // Process bureaucracy delays
        updated = processBureaucracy(updated)
        
        return updated
    }
    
    private fun processInflation(country: CountryV2): CountryV2 {
        val inflation = country.inflationControl
        val newInflation = inflation.copy(
            currentInflation = (inflation.currentInflation + 
                (Math.random() * 2 - 1) * inflation.volatility
            ).coerceIn(-5.0, 15.0)
        )
        
        // Inflation affects treasury
        val inflationPenalty = (country.treasury * newInflation.currentInflation / 100).toInt()
        
        return country.copy(
            inflationControl = newInflation,
            treasury = (country.treasury - inflationPenalty).coerceAtLeast(0)
        )
    }
    
    private fun processBureaucracy(country: CountryV2): CountryV2 {
        val bureau = country.bureaucracy
        val newEfficiency = (bureau.efficiency - bureau.corruption / 10).coerceIn(0, 100)
        
        return country.copy(
            bureaucracy = bureau.copy(efficiency = newEfficiency)
        )
    }
    
    /**
     * Check victory conditions
     */
    fun checkVictory(country: CountryV2, aiNations: List<AiNation>): VictoryType? {
        // Economic Dominance
        if (country.stats.economy >= 100 && country.treasury >= 100000) {
            val richestAI = aiNations.maxByOrNull { it.stats.economy * it.treasury }
            if (richestAI != null && country.stats.economy > richestAI.stats.economy) {
                return VictoryType.ECONOMIC_DOMINANCE
            }
        }
        
        // Military Superpower
        val totalTroops = country.military.army.manpower + 
                          country.military.navy.manpower + 
                          country.military.airForce.manpower
        if (country.stats.military >= 100 && totalTroops >= 100000) {
            val strongestAI = aiNations.maxByOrNull { it.stats.military }
            if (strongestAI != null && country.stats.military > strongestAI.stats.military) {
                return VictoryType.MILITARY_SUPERPOWER
            }
        }
        
        // Diplomatic Influence
        val allyCount = country.diplomaticRelations.count { it.hasAlliance }
        if (allyCount >= 50) {
            return VictoryType.DIPLOMATIC_INFLUENCE
        }
        
        // Technological Supremacy
        if (country.stats.technology >= 100) {
            return VictoryType.TECHNOLOGICAL_SUPREMACY
        }
        
        // Survival
        if (country.turnCount >= 500 && !country.eventHistory.any { 
            it.contains("defeat") || it.contains("game over") 
        }) {
            return VictoryType.SURVIVAL
        }
        
        return null
    }
    
    /**
     * Check defeat conditions
     */
    fun checkDefeat(country: CountryV2): GameOverReason? {
        if (country.treasury < -10000) return GameOverReason.BANKRUPTCY
        if (country.stats.happiness <= 0) return GameOverReason.REVOLUTION
        if (country.stats.stability <= 0) return GameOverReason.CIVIL_WAR
        if (country.resources.food <= 0) return GameOverReason.FAMINE
        if (country.stats.environment <= 0) return GameOverReason.ENVIRONMENTAL_COLLAPSE
        
        return null
    }
    
    /**
     * Activate emergency powers
     */
    fun activateEmergencyPowers(country: CountryV2, type: EmergencyPowerType): CountryV2 {
        if (country.treasury < type.cost) return country
        
        return country.copy(
            emergencyPowers = EmergencyPowers(
                isActive = true,
                turnsRemaining = type.duration,
                type = type,
                effects = listOf(
                    LawEffect("stability", type.stabilityBonus, EffectType.BONUS),
                    LawEffect("happiness", -type.happinessPenalty, EffectType.PENALTY)
                )
            ),
            treasury = country.treasury - type.cost
        )
    }
    
    /**
     * Manage stock market
     */
    fun updateStockMarket(country: CountryV2): CountryV2 {
        val market = country.stockMarket
        val trendChange = when (market.trend) {
            MarketTrend.BEARISH -> -20
            MarketTrend.STABLE -> 0
            MarketTrend.BULLISH -> 20
            MarketTrend.VOLATILE -> (Math.random() * 60 - 30).toInt()
        }
        
        val newIndex = (market.marketIndex + trendChange).coerceIn(500, 5000)
        
        // Process investments
        val investments = market.playerInvestments.map { inv ->
            val returnAmount = (inv.amount * inv.returnRate * Math.random()).toInt()
            inv.copy(turnsRemaining = inv.turnsRemaining - 1)
        }.filter { it.turnsRemaining > 0 }
        
        val investmentGains = investments.sumOf { 
            (it.amount * it.returnRate).toInt() 
        }
        
        return country.copy(
            stockMarket = market.copy(
                marketIndex = newIndex,
                playerInvestments = investments
            ),
            treasury = country.treasury + investmentGains
        )
    }
    
    /**
     * Adjust interest rates (inflation control)
     */
    fun adjustInterestRate(country: CountryV2, newRate: Double): CountryV2 {
        val control = country.inflationControl.copy(interestRate = newRate.coerceIn(0.0, 10.0))
        
        // Higher rates reduce inflation but slow economy
        val economyEffect = if (newRate > country.inflationControl.interestRate) -5 else 5
        
        return country.copy(
            inflationControl = control,
            stats = country.stats.copy(
                economy = (country.stats.economy + economyEffect).coerceIn(0, 100)
            )
        )
    }
}
