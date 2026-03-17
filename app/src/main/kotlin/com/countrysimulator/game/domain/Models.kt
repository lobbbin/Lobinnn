package com.countrysimulator.game.domain

import kotlinx.serialization.Serializable

@Serializable
enum class GovernmentType(
    val displayName: String,
    val description: String,
    val economyBonus: Int,
    val militaryBonus: Int,
    val happinessBonus: Int,
    val stabilityBonus: Int,
    val techBonus: Int
) {
    DEMOCRACY("Democracy", "Balanced bonuses. Free elections, moderate taxes.", 5, 0, 5, 5, 5),
    MONARCHY("Monarchy", "Economic bonus. Low citizen happiness.", 10, 5, -10, 5, 0),
    REPUBLIC("Republic", "Trade bonus. Limited military power.", 15, -5, 5, 5, 5),
    DICTATORSHIP("Dictatorship", "Military bonus. Very low happiness.", 0, 15, -15, 10, 0),
    COMMUNISM("Communism", "Production bonus. Limited foreign trade.", 5, 5, -5, 10, 10),
    THEOCRACY("Theocracy", "Religious authority. High stability, limited progress.", 0, 0, 10, 20, -5),
    FEDERATION("Federation", "United states. Strong economy, shared defense.", 15, 10, 5, 0, 10),
    CONFEDERACY("Confederacy", "States rights. High autonomy, weak central power.", 5, -5, 10, -10, 0),
    TECHNOCRACY("Technocracy", "Rule by experts. High tech, moderate happiness.", 10, 0, 5, 5, 20),
    SOCIALISM("Socialism", "Workers paradise. High equality, lower efficiency.", -5, 0, 20, 10, 5)
}

@Serializable
data class CountryStats(
    val population: Int = 1000000,
    val economy: Int = 50,
    val military: Int = 30,
    val happiness: Int = 60,
    val stability: Int = 50,
    val technology: Int = 20,
    val education: Int = 30,
    val healthcare: Int = 30,
    val environment: Int = 50,
    val crime: Int = 20,
    val corruption: Int = 10,
    val propaganda: Int = 0,
    val softPower: Int = 0,
    val security: Int = 30
)

@Serializable
data class Resources(
    val food: Int = 100,
    val energy: Int = 100,
    val materials: Int = 50,
    val maxFood: Int = 200,
    val maxEnergy: Int = 200,
    val maxMaterials: Int = 150
)

@Serializable
enum class RelationStatus { ENEMY, RIVAL, NEUTRAL, FRIENDLY, ALLY }

@Serializable
enum class SanctionType { TRADE_EMBARGO, ARMS_EMBARGO, TRAVEL_BAN }

@Serializable
data class DiplomaticRelation(
    val nationName: String,
    val nationId: String,
    val relationScore: Int = 50,
    val status: RelationStatus = RelationStatus.NEUTRAL,
    val isAtWar: Boolean = false,
    val hasTradeAgreement: Boolean = false,
    val hasNonAggressionPact: Boolean = false,
    val hasAlliance: Boolean = false,
    val warScore: Int = 0,
    val warExhaustion: Int = 0,
    val sanctions: List<SanctionType> = emptyList(),
    val isSpying: Boolean = false
)

@Serializable
enum class AiPersonality { AGGRESSIVE, PEACEFUL, TRADER, SCIENTIFIC, ISOLATIONIST }

@Serializable
data class AiNation(
    val id: String,
    val name: String,
    val governmentType: GovernmentType,
    val personality: AiPersonality,
    val stats: CountryStats,
    val treasury: Int = 5000,
    val isAlive: Boolean = true,
    val isUNMember: Boolean = true,
    val military: Military = Military()
)

@Serializable
enum class UNResolutionType(val displayName: String) {
    CONDEMNATION("Condemnation"),
    SANCTIONS("Sanctions"),
    PEACEKEEPING_MISSION("Peacekeeping Mission"),
    HUMANITARIAN_AID("Humanitarian Aid"),
    GLOBAL_INITIATIVE("Global Initiative")
}

@Serializable
enum class ResolutionStatus { PROPOSED, PASSED, FAILED }

@Serializable
data class UNResolution(
    val id: String,
    val type: UNResolutionType,
    val targetNationId: String?,
    val description: String,
    val yearProposed: Int,
    val votesFor: Int = 0,
    val votesAgainst: Int = 0,
    val status: ResolutionStatus = ResolutionStatus.PROPOSED
)

@Serializable
data class UnitedNations(
    val memberCount: Int = 0,
    val activeResolutions: List<UNResolution> = emptyList(),
    val passedResolutions: List<UNResolution> = emptyList()
)

@Serializable
enum class SpyMissionType(val displayName: String, val cost: Int, val duration: Int) {
    GATHER_INTEL("Gather Intel", 200, 2),
    STEAL_TECH("Steal Technology", 500, 4),
    SABOTAGE_ECONOMY("Sabotage Economy", 800, 3),
    INCITE_UNREST("Incite Unrest", 600, 5),
    STAGE_COUP("Stage Coup", 2000, 8)
}

@Serializable
data class SpyMission(
    val id: String,
    val targetNationId: String,
    val targetNationName: String,
    val type: SpyMissionType,
    val successChance: Int,
    val turnsRemaining: Int,
    val costPerTurn: Int
)

@Serializable
enum class MilitaryDoctrine(val displayName: String, val description: String) {
    BALANCED("Balanced", "No specific focus."),
    OFFENSIVE("Offensive", "Bonus to attack, penalty to defense."),
    DEFENSIVE("Defensive", "Bonus to defense, penalty to attack."),
    GUERRILLA("Guerrilla", "Bonus to resistance, penalty to open field battles.")
}

@Serializable
data class MilitaryBranch(
    val name: String,
    val manpower: Int = 1000,
    val equipmentLevel: Int = 1,
    val experience: Int = 0
)

@Serializable
data class NuclearProgram(
    val hasProgram: Boolean = false,
    val researchProgress: Int = 0,
    val warheads: Int = 0
)

@Serializable
data class MercenaryGroup(
    val name: String,
    val power: Int,
    val costPerTurn: Int,
    val contractTurnsRemaining: Int
)

@Serializable
data class WarTheater(
    val id: String,
    val name: String,
    val enemyNationId: String,
    val playerStrength: Int,
    val enemyStrength: Int,
    val territoryControlled: Int = 50,
    val isActive: Boolean = true
)

@Serializable
data class Military(
    val army: MilitaryBranch = MilitaryBranch("Army"),
    val navy: MilitaryBranch = MilitaryBranch("Navy"),
    val airForce: MilitaryBranch = MilitaryBranch("Air Force"),
    val doctrine: MilitaryDoctrine = MilitaryDoctrine.BALANCED,
    val nuclearProgram: NuclearProgram = NuclearProgram(),
    val mercenaries: List<MercenaryGroup> = emptyList(),
    val warTheaters: List<WarTheater> = emptyList(),
    val basesAbroad: Int = 0
)

@Serializable
data class GlobalMarket(
    val foodPrice: Int = 10,
    val energyPrice: Int = 15,
    val materialsPrice: Int = 20,
    val globalInstability: Int = 10
)

@Serializable
enum class Ideology(val displayName: String) {
    LIBERAL("Liberal"),
    CONSERVATIVE("Conservative"),
    SOCIALIST("Socialist"),
    NATIONALIST("Nationalist"),
    AUTHORITARIAN("Authoritarian"),
    ECOLOGIST("Ecologist")
}

@Serializable
data class PoliticalParty(
    val name: String,
    val ideology: Ideology,
    val popularity: Int = 0,
    val influence: Int = 0
)

@Serializable
data class Law(
    val id: String,
    val name: String,
    val description: String,
    val isActive: Boolean = false,
    val cost: Int = 0,
    val stabilityEffect: Int = 0,
    val economyEffect: Int = 0,
    val happinessEffect: Int = 0,
    val corruptionEffect: Int = 0
)

@Serializable
data class PoliticalFaction(
    val name: String,
    val loyalty: Int = 50,
    val power: Int = 20
)

@Serializable
data class Minister(
    val id: String,
    val name: String,
    val role: MinisterRole,
    val skill: Int = 50,
    val corruption: Int = 10,
    val loyalty: Int = 70
)

@Serializable
enum class MinisterRole(val displayName: String) {
    ECONOMY("Minister of Economy"),
    DEFENSE("Minister of Defense"),
    INTERIOR("Minister of Interior"),
    EDUCATION("Minister of Education"),
    HEALTH("Minister of Health"),
    FOREIGN_AFFAIRS("Minister of Foreign Affairs")
}

@Serializable
data class Election(
    val year: Int,
    val isActive: Boolean = false,
    val turnsRemaining: Int = 0,
    val results: Map<String, Int> = emptyMap()
)

@Serializable
enum class TaxRate(val label: String, val multiplier: Double, val happinessEffect: Int) {
    LOW("Low (10%)", 0.5, 5),
    NORMAL("Normal (20%)", 1.0, 0),
    HIGH("High (40%)", 1.5, -10)
}

@Serializable
data class Country(
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
    val military: Military = Military()
)

@Serializable
data class TurnSummary(
    val grossIncome: Int,
    val totalExpenses: Int,
    val netChange: Int,
    val messages: List<String>
)

@Serializable
data class PersistedGameState(
    val country: Country,
    val aiNations: List<AiNation>,
    val globalMarket: GlobalMarket
)

data class GameState(
    val country: Country,
    val aiNations: List<AiNation> = emptyList(),
    val globalMarket: GlobalMarket = GlobalMarket(),
    val isGameOver: Boolean = false,
    val gameOverReason: GameOverReason? = null,
    val lastEvent: GameEvent? = null,
    val eventHistory: List<String> = emptyList(),
    val newsHeadline: String? = null,
    val turnSummary: TurnSummary? = null
)

// GameEvent uses functions so it CANNOT be serialized. It is transient and handled in memory.
data class GameEvent(
    val id: String,
    val title: String,
    val description: String,
    val category: EventCategory,
    val severity: EventSeverity,
    val effect: (CountryStats) -> CountryStats,
    val options: List<EventOption>,
    val prerequisites: ((Country) -> Boolean)? = null
)

enum class EventCategory {
    ECONOMIC,
    MILITARY,
    POLITICAL,
    DISASTER,
    SCIENTIFIC,
    CULTURAL,
    DIPLOMATIC,
    ENVIRONMENTAL,
    SOCIAL
}

enum class EventSeverity {
    MINOR,
    MODERATE,
    MAJOR,
    CATASTROPHIC
}

data class EventOption(
    val label: String,
    val description: String,
    val effect: (CountryStats, Int, Resources) -> Triple<CountryStats, Int, Resources>
)

enum class GameOverReason {
    BANKRUPTCY,
    REVOLUTION,
    INVASION,
    TECH_FAILURE,
    FAMINE,
    ENVIRONMENTAL_COLLAPSE,
    NUCLEAR_WINTER,
    CIVIL_WAR,
    ASSASSINATION,
    COUP
}
