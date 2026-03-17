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
    val results: Map<String, Int> = emptyMap(),
    val type: ElectionType = ElectionType.GENERAL,
    val campaignCost: Int = 5000,
    val incumbentParty: String = "",
    val challengerParties: List<String> = emptyList()
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

// ========== 25+ NEW MAJOR GAMEPLAY FEATURES ==========

// 1. ELECTIONS SYSTEM
enum class ElectionType(val years: Int) {
    GENERAL(4),
    PRESIDENTIAL(4),
    PARLIAMENTARY(5),
    LOCAL(2)
}

// 2. POLITICAL SCANDALS
enum class ScandalType(val damage: Int, val cost: Int) {
    CORRUPTION(20, 3000),
    SEXUAL(15, 2000),
    FINANCIAL(25, 4000),
    ABUSE_OF_POWER(30, 5000),
    TREASON(50, 8000),
    PLAGIARISM(10, 1500)
}

data class PoliticalScandal(
    val id: String,
    val name: String,
    val type: ScandalType,
    val severity: Int,
    val turnsActive: Int = 3,
    val resolved: Boolean = false
)

// 3. TRADE AGREEMENTS
enum class TradeAgreementType(val bonus: Int) {
    BILATERAL(10),
    MULTILATERAL(20),
    FREE_TRADE(25),
    CUSTOMS_UNION(30)
}

data class TradeAgreement(
    val id: String,
    val name: String,
    val type: TradeAgreementType,
    val memberIds: List<String>,
    val yearSigned: Int,
    val economyBonus: Int,
    val expiresYear: Int? = null
)

// 4. TREATIES & ALLIANCES
enum class TreatyType(val cost: Int, val defenseBonus: Int) {
    NON_AGGRESSION(1000, 5),
    MUTUAL_DEFENSE(3000, 20),
    ECONOMIC_PARTNERSHIP(2000, 10),
    RESEARCH_COOPERATION(2500, 0),
    CULTURAL_EXCHANGE(800, 0)
}

data class Treaty(
    val id: String,
    val name: String,
    val type: TreatyType,
    val signatoryIds: List<String>,
    val yearSigned: Int,
    val expiresYear: Int? = null
)

// 5. WAR & CONFLICT - Enhanced
enum class BattleType {
    LAND, NAVAL, AIR, GUERRILLA, SIEGE
}

data class Battle(
    val id: String,
    val name: String,
    val location: String,
    val attackerId: String,
    val defenderId: String,
    val attackerCasualties: Int = 0,
    val defenderCasualties: Int = 0,
    val attackerWins: Boolean = false,
    val turn: Int = 0
)

data class WarInfo(
    val enemyNationId: String,
    val battleHistory: List<Battle> = emptyList(),
    val occupationLevel: Int = 0,
    val resistanceLevel: Int = 0,
    val peaceTalks: Boolean = false
)

// 6. REVOLUTION SYSTEM
enum class RevolutionType {
    COMMUNIST,
    FASCIST,
    RELIGIOUS,
    SECESSIONIST,
    DEMOCRATIC
}

data class RevolutionaryFaction(
    val name: String,
    val type: RevolutionType,
    val strength: Int = 0,
    val support: Int = 0,
    val controlRegions: List<String> = emptyList()
)

// 7. COUP SYSTEM
enum class CoupType {
    MILITARY,
    PALACE,
    COUNTER,
    FAILED
}

data class CoupAttempt(
    val id: String,
    val year: Int,
    val type: CoupType,
    val success: Boolean = false,
    val leaderKilled: Boolean = false,
    val stability: Int = 0
)

// 8. INTERNATIONAL SUMMITS
enum class SummitType(val softPowerBonus: Int, val cost: Int) {
    ECONOMIC(10, 5000),
    SECURITY(15, 6000),
    ENVIRONMENTAL(12, 4500),
    CULTURAL(8, 3000),
    PEACE(20, 8000)
}

data class Summit(
    val id: String,
    val name: String,
    val type: SummitType,
    val hostId: String,
    val participantIds: List<String>,
    val year: Int,
    val agreements: List<String> = emptyList()
)

// 9. DIPLOMATIC INCIDENTS
enum class IncidentType(val relationPenalty: Int, val cost: Int) {
    BORDER_CLASH(15, 2000),
    ESPIONAGE_CAUGHT(20, 3000),
    TRADE_DISPUTE(10, 1500),
    PROPAGANDA(12, 1000),
    TERRITORIAL_CLAIM(25, 4000)
}

data class DiplomaticIncident(
    val id: String,
    val type: IncidentType,
    val nationId: String,
    val year: Int,
    val resolved: Boolean = false,
    val resolution: String = ""
)

// 10. TERRORISM
enum class TerroristGroup {
    DOMESTIC,
    FOREIGN,
    RELIGIOUS,
    SEPARATIST
}

data class TerroristAttack(
    val id: String,
    val location: String,
    val group: TerroristGroup,
    val casualties: Int,
    val damage: Int,
    val year: Int,
    val threatLevel: Int = 0
)

// 11. REFUGEES
data class RefugeeCrisis(
    val id: String,
    val originNationId: String,
    val count: Int,
    val year: Int,
    val costPerTurn: Int,
    val accepted: Boolean = false
)

// 12. PANDEMICS
enum class PandemicType(val deathRate: Double) {
    INFLUENZA(0.02),
    CORONAVIRUS(0.03),
    EBOLA(0.50),
    PLAGUE(0.60),
    UNKNOWN(0.10)
}

data class Pandemic(
    val id: String,
    val name: String,
    val type: PandemicType,
    val infected: Int = 0,
    val deaths: Int = 0,
    val active: Boolean = true,
    val turnStarted: Int = 0
)

// 13. FAMINE
data class Famine(
    val id: String,
    val severity: Int,
    val affectedPopulation: Int,
    val deaths: Int = 0,
    val active: Boolean = true
)

// 14. NATURAL DISASTERS
enum class DisasterType(val damageMultiplier: Double) {
    EARTHQUAKE(1.5),
    HURRICANE(1.2),
    TSUNAMI(1.8),
    FLOOD(1.0),
    DROUGHT(0.8),
    WILDFIRE(1.1),
    VOLCANO(2.0)
}

data class NaturalDisaster(
    val id: String,
    val type: DisasterType,
    val location: String,
    val magnitude: Int,
    val casualties: Int,
    val economicDamage: Int,
    val year: Int
)

// 15. ECONOMIC CRISIS
enum class CrisisType {
    RECESSION,
    DEPRESSION,
    STOCK_CRASH,
    INFLATION,
    DEFLATION,
    BANKING_CRISIS
}

data class EconomicCrisis(
    val id: String,
    val type: CrisisType,
    val severity: Int,
    val duration: Int,
    val active: Boolean = true
)

// 16. SPACE RACE
enum class SpaceMilestone(val cost: Int, val techBonus: Int) {
    FIRST_SATELLITE(3000, 10),
    MANNED_SPACEFLIGHT(5000, 15),
    MOON_LANDING(10000, 25),
    SPACE_STATION(15000, 30),
    MARS_MISSION(30000, 50)
}

data class SpaceProgram(
    val active: Boolean = false,
    val milestones: List<SpaceMilestone> = emptyList(),
    val budget: Int = 0,
    val currentGoal: SpaceMilestone? = null
)

// 17. NUCLEAR PROLIFERATION
enum class NuclearStockpile(val warheads: Int, val cost: Int) {
    SINGLE_WARHEAD(1, 5000),
    SMALL(5, 15000),
    MEDIUM(25, 40000),
    LARGE(100, 100000),
    SUPERPOWER(500, 300000)
}

data class NuclearArsenal(
    val warheads: Int = 0,
    val missiles: Int = 0,
    val submarines: Int = 0,
    val bombers: Int = 0,
    val active: Boolean = false
)

// 18. INTELLIGENCE OPERATIONS
enum class IntelOperation(val cost: Int, val risk: Int) {
    COUNTER_INTEL(2000, 10),
    HACK_INFRASTRUCTURE(3000, 20),
    ELECTION_INTERFERENCE(5000, 30),
    ASSASSINATION(8000, 50),
    TECH_STEALING(4000, 25)
}

data class IntelAgency(
    val budget: Int = 1000,
    val agents: Int = 100,
    val successRate: Int = 50,
    val securityLevel: Int = 50
)

// 19. PROPAGANDA CAMPAIGNS
enum class PropagandaType(val happinessChange: Int, val stabilityChange: Int, val cost: Int) {
    NATIONALISM(10, 15, 1000),
    FEAR(-5, 20, 800),
    GLORY(15, 10, 1500),
    UNITY(5, 15, 1200),
    ENEMY(-10, 5, 900)
}

data class PropagandaCampaign(
    val id: String,
    val type: PropagandaType,
    val turnsRemaining: Int,
    val effectiveness: Int = 100
)

// 20. INFRASTRUCTURE PROJECTS
enum class ProjectType(val cost: Int, val duration: Int) {
    HIGHWAY(5000, 3),
    RAILWAY(7000, 4),
    AIRPORT(4000, 2),
    SEAPORT(4500, 3),
    DAM(8000, 5),
    NUCLEAR_PLANT(12000, 6),
    SPACE_CENTER(15000, 8)
}

data class InfrastructureProject(
    val id: String,
    val name: String,
    val type: ProjectType,
    val progress: Int = 0,
    val costPaid: Int = 0
)

// 21. SOCIAL REFORMS
enum class ReformType(val happinessBonus: Int, val economyPenalty: Int, val cost: Int) {
    UNIVERSAL_HEALTHCARE(20, -5, 5000),
    UNIVERSAL_INCOME(25, -10, 8000),
    WORKERS_RIGHTS(15, -3, 3000),
    CIVIL_RIGHTS(20, 0, 4000),
    ENVIRONMENTAL_LAWS(10, -8, 3500)
}

data class SocialReform(
    val id: String,
    val type: ReformType,
    val proposed: Boolean = false,
    val passed: Boolean = false,
    val votesFor: Int = 0,
    val votesAgainst: Int = 0
)

// 22. REGIONAL DEVELOPMENT
enum class RegionType {
    URBAN,
    RURAL,
    COASTAL,
    MOUNTAINS,
    DESERT,
    INDUSTRIAL
}

data class Region(
    val id: String,
    val name: String,
    val type: RegionType,
    val population: Int = 0,
    val development: Int = 50,
    val resources: Map<String, Int> = emptyMap()
)

// 23. INTERNATIONAL ORGANIZATIONS
enum class OrgType(val membershipCost: Int, val benefits: Int) {
    NATO(5000, 30),
    EU(4000, 25),
    UN(2000, 15),
    OPEC(3000, 20),
    ASEAN(2500, 18),
    AFRICAN_UNION(1500, 12)
}

data class InternationalOrg(
    val id: String,
    val name: String,
    val type: OrgType,
    val memberIds: List<String>,
    val leaderId: String? = null,
    val founded: Int
)

// 24. CULTURAL INFLUENCE
enum class CulturalExport {
    MUSIC,
    FILM,
    LITERATURE,
    FOOD,
    SPORTS,
    FASHION
}

data class CulturalInfluence(
    val exports: Map<CulturalExport, Int> = emptyMap(),
    val globalReach: Int = 0,
    val annualRevenue: Int = 0
)

// 25. INTELLECTUAL PROPERTY
data class Patents(
    val count: Int = 0,
    val royalties: Int = 0,
    val techBonus: Int = 0
)

// 26. G20 / SUMMIT PARTICIPATION
data class GlobalSummit(
    val id: String,
    val name: String,
    val type: SummitType,
    val hostId: String,
    val year: Int,
    val topics: List<String>,
    val agreements: List<String>
)

// 27. MILITARY BASES ABROAD
data class ForeignBase(
    val id: String,
    val hostNationId: String,
    val location: String,
    val troops: Int,
    val annualCost: Int
)

// 28. AMBASSADORS
data class Ambassador(
    val id: String,
    val name: String,
    val nationId: String,
    val skill: Int,
    val loyalty: Int
)

// ENHANCED COUNTRY DATA
@Serializable
data class EnhancedCountry(
    val base: Country,
    val scandals: List<PoliticalScandal> = emptyList(),
    val tradeAgreements: List<TradeAgreement> = emptyList(),
    val treaties: List<Treaty> = emptyList(),
    val wars: Map<String, WarInfo> = emptyMap(),
    val revolutionaryFactions: List<RevolutionaryFaction> = emptyList(),
    val coupAttempts: List<CoupAttempt> = emptyList(),
    val summits: List<Summit> = emptyList(),
    val incidents: List<DiplomaticIncident> = emptyList(),
    val terroristAttacks: List<TerroristAttack> = emptyList(),
    val refugeeCrises: List<RefugeeCrisis> = emptyList(),
    val pandemic: Pandemic? = null,
    val famine: Famine? = null,
    val naturalDisasters: List<NaturalDisaster> = emptyList(),
    val economicCrisis: EconomicCrisis? = null,
    val spaceProgram: SpaceProgram = SpaceProgram(),
    val nuclearArsenal: NuclearArsenal = NuclearArsenal(),
    val intelAgency: IntelAgency = IntelAgency(),
    val propagandaCampaigns: List<PropagandaCampaign> = emptyList(),
    val projects: List<InfrastructureProject> = emptyList(),
    val socialReforms: List<SocialReform> = emptyList(),
    val regions: List<Region> = emptyList(),
    val organizations: List<InternationalOrg> = emptyList(),
    val culturalInfluence: CulturalInfluence = CulturalInfluence(),
    val patents: Patents = Patents(),
    val foreignBases: List<ForeignBase> = emptyList(),
    val ambassadors: List<Ambassador> = emptyList()
)
