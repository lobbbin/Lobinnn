package com.countrysimulator.game.domain

import kotlinx.serialization.Serializable

// ============================================
// COUNTRY SIMULATOR v10.0 - MAJOR UPDATE
// "The Living Nation" - Deep Emergent Gameplay
// ============================================

// ────────────────────────────────────────────
// 1. ADVANCED ECONOMY SIMULATION
// ────────────────────────────────────────────

@Serializable
data class EconomySector(
    val name: String,
    val size: Int = 100, // 0-1000
    val growth: Int = 0, // -100 to 100
    val employment: Int = 50, // 0-100
    val productivity: Int = 50, // 0-100
    val investment: Int = 0
)

@Serializable
data class EconomicIndicators(
    val gdpGrowth: Double = 2.0, // -10 to 10
    val inflation: Double = 2.0, // 0 to 50
    val unemployment: Double = 5.0, // 0 to 50
    val interestRate: Double = 3.0, // 0 to 25
    val debtToGdp: Double = 50.0, // 0 to 500
    val creditRating: Int = 50, // 0-100 (AAA to D)
    val currencyStrength: Int = 50, // 0-100
    val tradeBalance: Int = 0, // negative = deficit
    val budgetDeficit: Int = 0, // negative = surplus
    val foreignReserves: Int = 1000
)

@Serializable
enum class EconomicPolicy {
    LAISSEZ_FAIRE,      // Free market, low regulation
    KEYNESIAN,          // Government spending
    MONETARIST,         // Control money supply
    AUSTERITY,          // Cut spending
    INDUSTRIAL_POLICY,  // Target specific sectors
    GREEN_ECONOMY       // Environmental focus
}

@Serializable
data class StockMarket(
    val index: Int = 1000,
    val volatility: Int = 20, // 0-100
    val bullMarket: Boolean = true,
    val crashRisk: Int = 0, // 0-100
    val listedCompanies: Int = 100
)

@Serializable
data class BankingSystem(
    val stability: Int = 80, // 0-100
    val nonPerformingLoans: Int = 5, // 0-100
    val capitalAdequacy: Int = 15, // percentage
    val bankRunRisk: Int = 0 // 0-100
)

// ────────────────────────────────────────────
// 2. PUBLIC OPINION & MEDIA
// ────────────────────────────────────────────

@Serializable
data class PublicOpinion(
    val approvalRating: Int = 50, // 0-100
    val trustInGovernment: Int = 50, // 0-100
    val nationalMood: Int = 50, // 0-100 (despair to euphoria)
    val politicalPolarization: Int = 30, // 0-100
    val protestActivity: Int = 0, // 0-100
    val strikeActivity: Int = 0, // 0-100
    val crimeConcern: Int = 30, // 0-100
    val topIssues: List<Issue> = emptyList()
)

@Serializable
data class Issue(
    val name: String,
    val importance: Int = 50, // 0-100
    val governmentRating: Int = 50, // 0-100
    val trend: Int = 0 // -100 to 100 (getting worse to better)
)

@Serializable
data class MediaLandscape(
    val pressFreedom: Int = 70, // 0-100
    val stateMedia: Int = 20, // 0-100
    val socialMediaInfluence: Int = 50, // 0-100
    val fakeNewsLevel: Int = 20, // 0-100
    val mediaOwnership: MediaOwnership = MediaOwnership.DIVERSE
)

@Serializable
enum class MediaOwnership {
    DIVERSE,
    CONCENTRATED,
    STATE_CONTROLLED,
    FOREIGN_OWNED
}

@Serializable
data class Poll(
    val id: String,
    val date: Int,
    val approvalRating: Int,
    val economyRating: Int,
    val securityRating: Int,
    val topConcern: String,
    val margin: Int = 3
)

// ────────────────────────────────────────────
// 3. CHARACTER SYSTEM
// ────────────────────────────────────────────

@Serializable
data class Character(
    val id: String,
    val name: String,
    val role: CharacterRole,
    val age: Int = 40,
    val personality: Personality,
    val skills: Map<Skill, Int> = emptyMap(),
    val loyalty: Int = 50, // 0-100
    val corruption: Int = 10, // 0-100
    val ambition: Int = 50, // 0-100
    val popularity: Int = 50, // 0-100
    val secrets: List<String> = emptyList(),
    val relationships: Map<String, Int> = emptyMap(), // characterId -> relationship score
    val agenda: Agenda? = null,
    val isAlive: Boolean = true,
    val health: Int = 100 // 0-100
)

@Serializable
enum class CharacterRole {
    ADVISOR,
    MINISTER,
    GENERAL,
    BUSINESS_LEADER,
    UNION_LEADER,
    RELIGIOUS_LEADER,
    MEDIA_MOGL,
    ACTIVIST,
    CRIME_BOSS,
    FOREIGN_AGENT,
    FAMILY_MEMBER,
    RIVAL
}

@Serializable
enum class Personality {
    HONEST,
    CUNNING,
    AGGRESSIVE,
    DIPLOMATIC,
    IDEALISTIC,
    PRAGMATIC,
    PARANOID,
    CHARISMATIC,
    RUTHLESS,
    COMPASSIONATE
}

@Serializable
enum class Skill {
    ECONOMICS,
    MILITARY,
    DIPLOMACY,
    PROPAGANDA,
    INTELLIGENCE,
    NEGOTIATION,
    MANAGEMENT,
    PUBLIC_SPEAKING
}

@Serializable
data class Agenda(
    val type: AgendaType,
    val progress: Int = 0, // 0-100
    val goal: String,
    val turnsToComplete: Int = 10
)

@Serializable
enum class AgendaType {
    POWER_GRAB,           // Try to usurp player
    REFORM,               // Push for policy change
    CORRUPTION,           // Steal money
    REVENGE,              // Harm someone
    LEGACY,               // Build something lasting
    SURVIVAL              // Stay alive/powerful
}

@Serializable
data class Relationship(
    val characterId: String,
    val trust: Int = 50, // 0-100
    val respect: Int = 50, // 0-100
    val fear: Int = 0, // 0-100
    val love: Int = 0, // 0-100 (for family/romance)
    val debt: Int = 0 // money owed
)

// ────────────────────────────────────────────
// 4. CRISIS SYSTEM
// ────────────────────────────────────────────

@Serializable
data class CrisisManager(
    val activeCrises: List<Crisis> = emptyList(),
    val crisisRisk: Map<CrisisType, Int> = emptyMap(), // type -> probability 0-100
    val emergencyPowers: Int = 0, // 0-100
    val disasterPreparedness: Int = 50 // 0-100
)

@Serializable
data class Crisis(
    val id: String,
    val type: CrisisType,
    val severity: Int = 50, // 0-100
    val duration: Int = 0, // turns active
    val peakSeverity: Int = 50,
    val contained: Boolean = false,
    val casualties: Int = 0,
    val economicDamage: Int = 0,
    val response: CrisisResponse? = null
)

// Note: Enum classes don't need @Serializable annotation
enum class CrisisType {
    // Economic
    BANK_RUN,
    STOCK_CRASH,
    CURRENCY_CRISIS,
    SOVEREIGN_DEFAULT,
    HYPERS_INFLATION,
    MASS_UNEMPLOYMENT,
    
    // Social
    CIVIL_UNREST,
    GENERAL_STRIKE,
    REFUGEE_CRISIS,
    TERRORIST_CAMPAIGN,
    CRIME_EPDEMIC,
    
    // Political
    CONSTITUTIONAL_CRISIS,
    SUCCESSION_CRISIS,
    CORRUPTION_SCANDAL,
    IMPEACHMENT,
    
    // Military
    WAR,
    COUP_ATTEMPT,
    TERRORIST_ATTACK,
    BORDER_CONFLICT,
    
    // Environmental
    NATURAL_DISASTER,
    PANDEMIC,
    FAMINE,
    ENVIRONMENTAL_COLLAPSE,
    NUCLEAR_ACCIDENT,
    
    // Cascading (trigger other crises)
    SYSTEMIC_COLLAPSE,
    REVOLUTION,
    STATE_FAILURE
}

@Serializable
data class CrisisResponse(
    val strategy: ResponseStrategy,
    val effectiveness: Int = 0, // 0-100
    val publicSupport: Int = 50, // 0-100
    val cost: Int = 0,
    val casualties: Int = 0
)

@Serializable
enum class ResponseStrategy {
    DENIAL,               // Ignore and hope it passes
    CONTAINMENT,          // Limit spread
    FULL_MOBILIZATION,    // All resources
    NEGOTIATION,          // Compromise
    AUTHORITARIAN,        // Force and control
    REFORM,               // Address root causes
    EXTERNAL_AID,         // Ask for help
    SACRIFICE             // Sacrifice something valuable
}

// ────────────────────────────────────────────
// 5. MICRO ACTIONS (Daily/Turn Decisions)
// ────────────────────────────────────────────

@Serializable
data class MicroActionState(
    val dailyActions: List<DailyAction> = emptyList(),
    val actionPoints: Int = 3,
    val maxActionPoints: Int = 3,
    val stressLevel: Int = 0, // 0-100 (affects decision quality)
    val energyLevel: Int = 100, // 0-100 (affects available actions)
    val lastRestTurn: Int = 0
)

@Serializable
data class DailyAction(
    val id: String,
    val name: String,
    val description: String,
    val category: ActionCategory,
    val cost: ActionCost,
    val effects: List<Effect>,
    val cooldown: Int = 0,
    val prerequisites: List<String> = emptyList(),
    val isAvailable: Boolean = true
)

@Serializable
enum class ActionCategory {
    POLITICAL,
    ECONOMIC,
    DIPLOMATIC,
    MILITARY,
    SOCIAL,
    PERSONAL,
    SECRET
}

@Serializable
data class ActionCost(
    val actionPoints: Int = 1,
    val money: Int = 0,
    val politicalCapital: Int = 0,
    val stress: Int = 0,
    val energy: Int = 0
)

@Serializable
data class Effect(
    val stat: String,
    val value: Int,
    val duration: Int = 1, // turns, 0 = permanent
    val delayed: Boolean = false, // effect happens later
    val delayedTurns: Int = 0
)

// ────────────────────────────────────────────
// 6. MACRO ACTIONS (Long-term Policies)
// ────────────────────────────────────────────

@Serializable
data class MacroActionState(
    val activePolicies: List<Policy> = emptyList(),
    val proposedPolicies: List<PolicyProposal> = emptyList(),
    val policySlots: Int = 5,
    val politicalCapital: Int = 50 // 0-100
)

@Serializable
data class Policy(
    val id: String,
    val name: String,
    val description: String,
    val category: PolicyCategory,
    val effects: List<Effect>,
    val upkeepCost: Int = 0,
    val support: Int = 50, // 0-100 (public/political support)
    val opposition: Int = 30, // 0-100
    val turnsActive: Int = 0,
    val maxDuration: Int = -1 // -1 = permanent
)

@Serializable
enum class PolicyCategory {
    TAXATION,
    WELFARE,
    REGULATION,
    FOREIGN_POLICY,
    DEFENSE,
    INFRASTRUCTURE,
    EDUCATION,
    HEALTHCARE,
    ENVIRONMENT,
    IMMIGRATION,
    TRADE,
    MONETARY
}

@Serializable
data class PolicyProposal(
    val policy: Policy,
    val proposer: String, // character id
    val support: Int = 0,
    val opposition: Int = 0,
    val turnsInDebate: Int = 0,
    val turnsToPass: Int = 3
)

// ────────────────────────────────────────────
// 7. DYNAMIC EVENT SYSTEM
// ────────────────────────────────────────────

@Serializable
data class EventMemory(
    val pastEvents: List<PastEvent> = emptyList(),
    val eventChains: Map<String, Int> = emptyMap(), // chainId -> progress
    val triggeredFlags: Set<String> = emptySet()
)

@Serializable
data class PastEvent(
    val eventId: String,
    val turn: Int,
    val outcome: String,
    val consequences: List<String>
)

data class EventChain(
    val id: String,
    val name: String,
    val stages: List<EventChainStage>,
    val currentStage: Int = 0,
    val active: Boolean = false
)

data class EventChainStage(
    val stage: Int,
    val trigger: EventTrigger,
    val event: GameEvent,
    val nextStageTrigger: EventTrigger
)

@Serializable
data class EventTrigger(
    val type: TriggerType,
    val condition: String,
    val value: Int,
    val comparison: Comparison
)

@Serializable
enum class TriggerType {
    STAT_THRESHOLD,
    TURN_NUMBER,
    EVENT_OCCURRED,
    POLICY_ACTIVE,
    CRISIS_ACTIVE,
    CHARACTER_ALIVE,
    RELATIONSHIP_LEVEL
}

@Serializable
enum class Comparison {
    LESS_THAN,
    EQUAL,
    GREATER_THAN
}

// ────────────────────────────────────────────
// 8. LEGACY SYSTEM
// ────────────────────────────────────────────

@Serializable
data class LegacyData(
    val totalPlaythroughs: Int = 0,
    val totalTurns: Int = 0,
    val achievements: List<Achievement> = emptyList(),
    val unlockedBonuses: List<LegacyBonus> = emptyList(),
    val historicalLeaders: List<HistoricalLeader> = emptyList(),
    val nationLegacy: NationLegacy = NationLegacy()
)

@Serializable
data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val unlocked: Boolean = false,
    val unlockDate: Int = 0,
    val difficulty: AchievementDifficulty
)

@Serializable
enum class AchievementDifficulty {
    EASY,
    NORMAL,
    HARD,
    LEGENDARY
}

@Serializable
data class LegacyBonus(
    val id: String,
    val name: String,
    val description: String,
    val effect: String,
    val unlocked: Boolean = false
)

@Serializable
data class HistoricalLeader(
    val name: String,
    val governmentType: GovernmentType,
    val turnsInPower: Int,
    val achievements: List<String>,
    val failures: List<String>,
    val legacyRating: Int, // 0-100
    val endedBy: GameOverReason?
)

@Serializable
data class NationLegacy(
    val reputation: Int = 50, // 0-100 (pariah to superpower)
    val historicalAllies: List<String> = emptyList(),
    val historicalEnemies: List<String> = emptyList(),
    val monuments: List<Monument> = emptyList(),
    val nationalMyths: List<String> = emptyList()
)

@Serializable
data class Monument(
    val name: String,
    val description: String,
    val cost: Int,
    val prestige: Int,
    val built: Boolean = false
)

// ────────────────────────────────────────────
// 9. INTEGRATED v10.0 GAME STATE
// ────────────────────────────────────────────

@Serializable
data class GameV10(
    // Core game state (existing)
    val country: Country,
    val aiNations: List<AiNation> = emptyList(),
    val globalMarket: GlobalMarket = GlobalMarket(),
    val isGameOver: Boolean = false,
    val gameOverReason: GameOverReason? = null,
    @kotlinx.serialization.Transient
    val lastEvent: GameEvent? = null,
    val eventHistory: List<String> = emptyList(),
    val newsHeadline: String? = null,
    val turnSummary: TurnSummary? = null,
    
    // v10.0 New Systems
    val economy: EconomyState = EconomyState(),
    val publicOpinion: PublicOpinion = PublicOpinion(),
    val media: MediaLandscape = MediaLandscape(),
    val characters: List<Character> = emptyList(),
    val crisisManager: CrisisManager = CrisisManager(),
    val microActions: MicroActionState = MicroActionState(),
    val macroActions: MacroActionState = MacroActionState(),
    val eventMemory: EventMemory = EventMemory(),
    val legacy: LegacyData = LegacyData(),
    
    // Meta
    val gameVersion: String = "10.0.0",
    val difficulty: Difficulty = Difficulty.NORMAL,
    val turn: Int = 0
)

@Serializable
data class EconomyState(
    val indicators: EconomicIndicators = EconomicIndicators(),
    val sectors: List<EconomySector> = listOf(
        EconomySector("Agriculture"),
        EconomySector("Manufacturing"),
        EconomySector("Services"),
        EconomySector("Technology"),
        EconomySector("Finance"),
        EconomySector("Energy")
    ),
    val stockMarket: StockMarket = StockMarket(),
    val banking: BankingSystem = BankingSystem(),
    val policy: EconomicPolicy = EconomicPolicy.KEYNESIAN,
    val taxRate: Double = 20.0,
    val spendingRate: Double = 20.0
)

@Serializable
enum class Difficulty {
    EASY,       // Bonuses to player
    NORMAL,     // Standard
    HARD,       // Penalties to player
    REALISTIC,  // No bonuses, harsh consequences
    IMPOSSIBLE  // Everything against you
}

// Helper function to create initial v10.0 game state
fun createInitialGameV10(country: Country, aiNations: List<AiNation>): GameV10 {
    return GameV10(
        country = country,
        aiNations = aiNations,
        characters = generateInitialCharacters(country),
        turn = 1
    )
}

fun generateInitialCharacters(country: Country): List<Character> {
    return listOf(
        Character(
            id = "char_1",
            name = "Dr. Elena Vance",
            role = CharacterRole.ADVISOR,
            age = 52,
            personality = Personality.PRAGMATIC,
            skills = mapOf(Skill.ECONOMICS to 85, Skill.MANAGEMENT to 75),
            loyalty = 70,
            corruption = 15,
            ambition = 40,
            popularity = 60,
            agenda = Agenda(AgendaType.LEGACY, goal = "Economic reform", turnsToComplete = 20)
        ),
        Character(
            id = "char_2",
            name = "General Marcus Steel",
            role = CharacterRole.GENERAL,
            age = 58,
            personality = Personality.AGGRESSIVE,
            skills = mapOf(Skill.MILITARY to 90, Skill.MANAGEMENT to 60),
            loyalty = 65,
            corruption = 20,
            ambition = 70,
            popularity = 75,
            agenda = Agenda(AgendaType.POWER_GRAB, goal = "Military expansion", turnsToComplete = 15)
        ),
        Character(
            id = "char_3",
            name = "Victoria Cross",
            role = CharacterRole.RIVAL,
            age = 45,
            personality = Personality.CUNNING,
            skills = mapOf(Skill.DIPLOMACY to 80, Skill.PROPAGANDA to 85),
            loyalty = 20,
            corruption = 40,
            ambition = 90,
            popularity = 55,
            agenda = Agenda(AgendaType.POWER_GRAB, goal = "Usurp leadership", turnsToComplete = 30)
        ),
        Character(
            id = "char_4",
            name = "Alexander Gold",
            role = CharacterRole.BUSINESS_LEADER,
            age = 60,
            personality = Personality.PRAGMATIC,
            skills = mapOf(Skill.ECONOMICS to 95, Skill.NEGOTIATION to 85),
            loyalty = 50,
            corruption = 60,
            ambition = 60,
            popularity = 40,
            agenda = Agenda(AgendaType.CORRUPTION, goal = "Tax loopholes", turnsToComplete = 10)
        ),
        Character(
            id = "char_5",
            name = "Maria Santos",
            role = CharacterRole.UNION_LEADER,
            age = 48,
            personality = Personality.IDEALISTIC,
            skills = mapOf(Skill.PUBLIC_SPEAKING to 90, Skill.NEGOTIATION to 75),
            loyalty = 45,
            corruption = 10,
            ambition = 50,
            popularity = 70,
            agenda = Agenda(AgendaType.REFORM, goal = "Worker protections", turnsToComplete = 12)
        )
    )
}
