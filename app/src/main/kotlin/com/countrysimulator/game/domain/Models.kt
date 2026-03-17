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
    val turnSummary: TurnSummary? = null,
    val textAdventureState: TextAdventureState = TextAdventureState()
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
@Serializable
enum class ScandalType(val damage: Int, val cost: Int) {
    CORRUPTION(20, 3000),
    SEXUAL(15, 2000),
    FINANCIAL(25, 4000),
    ABUSE_OF_POWER(30, 5000),
    TREASON(50, 8000),
    PLAGIARISM(10, 1500)
}

@Serializable
data class PoliticalScandal(
    val id: String,
    val name: String,
    val type: ScandalType,
    val severity: Int,
    val turnsActive: Int = 3,
    val resolved: Boolean = false
)

// 3. TRADE AGREEMENTS
@Serializable
enum class TradeAgreementType(val bonus: Int) {
    BILATERAL(10),
    MULTILATERAL(20),
    FREE_TRADE(25),
    CUSTOMS_UNION(30)
}

@Serializable
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
@Serializable
enum class TreatyType(val cost: Int, val defenseBonus: Int) {
    NON_AGGRESSION(1000, 5),
    MUTUAL_DEFENSE(3000, 20),
    ECONOMIC_PARTNERSHIP(2000, 10),
    RESEARCH_COOPERATION(2500, 0),
    CULTURAL_EXCHANGE(800, 0)
}

@Serializable
data class Treaty(
    val id: String,
    val name: String,
    val type: TreatyType,
    val signatoryIds: List<String>,
    val yearSigned: Int,
    val expiresYear: Int? = null
)

// 5. WAR & CONFLICT - Enhanced
@Serializable
enum class BattleType {
    LAND, NAVAL, AIR, GUERRILLA, SIEGE
}

@Serializable
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

@Serializable
data class WarInfo(
    val enemyNationId: String,
    val battleHistory: List<Battle> = emptyList(),
    val occupationLevel: Int = 0,
    val resistanceLevel: Int = 0,
    val peaceTalks: Boolean = false
)

// 6. REVOLUTION SYSTEM
@Serializable
enum class RevolutionType {
    COMMUNIST,
    FASCIST,
    RELIGIOUS,
    SECESSIONIST,
    DEMOCRATIC
}

@Serializable
data class RevolutionaryFaction(
    val name: String,
    val type: RevolutionType,
    val strength: Int = 0,
    val support: Int = 0,
    val controlRegions: List<String> = emptyList()
)

// 7. COUP SYSTEM
@Serializable
enum class CoupType {
    MILITARY,
    PALACE,
    COUNTER,
    FAILED
}

@Serializable
data class CoupAttempt(
    val id: String,
    val year: Int,
    val type: CoupType,
    val success: Boolean = false,
    val leaderKilled: Boolean = false,
    val stability: Int = 0
)

// 8. INTERNATIONAL SUMMITS
@Serializable
enum class SummitType(val softPowerBonus: Int, val cost: Int) {
    ECONOMIC(10, 5000),
    SECURITY(15, 6000),
    ENVIRONMENTAL(12, 4500),
    CULTURAL(8, 3000),
    PEACE(20, 8000)
}

@Serializable
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
@Serializable
enum class IncidentType(val relationPenalty: Int, val cost: Int) {
    BORDER_CLASH(15, 2000),
    ESPIONAGE_CAUGHT(20, 3000),
    TRADE_DISPUTE(10, 1500),
    PROPAGANDA(12, 1000),
    TERRITORIAL_CLAIM(25, 4000)
}

@Serializable
data class DiplomaticIncident(
    val id: String,
    val type: IncidentType,
    val nationId: String,
    val year: Int,
    val resolved: Boolean = false,
    val resolution: String = ""
)

// 10. TERRORISM
@Serializable
enum class TerroristGroup {
    DOMESTIC,
    FOREIGN,
    RELIGIOUS,
    SEPARATIST
}

@Serializable
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
@Serializable
data class RefugeeCrisis(
    val id: String,
    val originNationId: String,
    val count: Int,
    val year: Int,
    val costPerTurn: Int,
    val accepted: Boolean = false
)

// 12. PANDEMICS
@Serializable
enum class PandemicType(val deathRate: Double) {
    INFLUENZA(0.02),
    CORONAVIRUS(0.03),
    EBOLA(0.50),
    PLAGUE(0.60),
    UNKNOWN(0.10)
}

@Serializable
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
@Serializable
data class Famine(
    val id: String,
    val severity: Int,
    val affectedPopulation: Int,
    val deaths: Int = 0,
    val active: Boolean = true
)

// 14. NATURAL DISASTERS
@Serializable
enum class DisasterType(val damageMultiplier: Double) {
    EARTHQUAKE(1.5),
    HURRICANE(1.2),
    TSUNAMI(1.8),
    FLOOD(1.0),
    DROUGHT(0.8),
    WILDFIRE(1.1),
    VOLCANO(2.0)
}

@Serializable
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
@Serializable
enum class EconomicCrisisType {
    RECESSION,
    DEPRESSION,
    STOCK_CRASH,
    INFLATION,
    DEFLATION,
    BANKING_CRISIS
}

@Serializable
data class EconomicCrisis(
    val id: String,
    val type: EconomicCrisisType,
    val severity: Int,
    val duration: Int,
    val active: Boolean = true
)

// 16. SPACE RACE
@Serializable
enum class SpaceMilestone(val cost: Int, val techBonus: Int) {
    FIRST_SATELLITE(3000, 10),
    MANNED_SPACEFLIGHT(5000, 15),
    MOON_LANDING(10000, 25),
    SPACE_STATION(15000, 30),
    MARS_MISSION(30000, 50)
}

@Serializable
data class SpaceProgram(
    val active: Boolean = false,
    val milestones: List<SpaceMilestone> = emptyList(),
    val budget: Int = 0,
    val currentGoal: SpaceMilestone? = null
)

// 17. NUCLEAR PROLIFERATION
@Serializable
enum class NuclearStockpile(val warheads: Int, val cost: Int) {
    SINGLE_WARHEAD(1, 5000),
    SMALL(5, 15000),
    MEDIUM(25, 40000),
    LARGE(100, 100000),
    SUPERPOWER(500, 300000)
}

@Serializable
data class NuclearArsenal(
    val warheads: Int = 0,
    val missiles: Int = 0,
    val submarines: Int = 0,
    val bombers: Int = 0,
    val active: Boolean = false
)

// 18. INTELLIGENCE OPERATIONS
@Serializable
enum class IntelOperation(val cost: Int, val risk: Int) {
    COUNTER_INTEL(2000, 10),
    HACK_INFRASTRUCTURE(3000, 20),
    ELECTION_INTERFERENCE(5000, 30),
    ASSASSINATION(8000, 50),
    TECH_STEALING(4000, 25)
}

@Serializable
data class IntelAgency(
    val budget: Int = 1000,
    val agents: Int = 100,
    val successRate: Int = 50,
    val securityLevel: Int = 50
)

// 19. PROPAGANDA CAMPAIGNS
@Serializable
enum class PropagandaType(val happinessChange: Int, val stabilityChange: Int, val cost: Int) {
    NATIONALISM(10, 15, 1000),
    FEAR(-5, 20, 800),
    GLORY(15, 10, 1500),
    UNITY(5, 15, 1200),
    ENEMY(-10, 5, 900)
}

@Serializable
data class PropagandaCampaign(
    val id: String,
    val type: PropagandaType,
    val turnsRemaining: Int,
    val effectiveness: Int = 100
)

// 20. INFRASTRUCTURE PROJECTS
@Serializable
enum class ProjectType(val cost: Int, val duration: Int) {
    HIGHWAY(5000, 3),
    RAILWAY(7000, 4),
    AIRPORT(4000, 2),
    SEAPORT(4500, 3),
    DAM(8000, 5),
    NUCLEAR_PLANT(12000, 6),
    SPACE_CENTER(15000, 8)
}

@Serializable
data class InfrastructureProject(
    val id: String,
    val name: String,
    val type: ProjectType,
    val progress: Int = 0,
    val costPaid: Int = 0
)

// 21. SOCIAL REFORMS
@Serializable
enum class ReformType(val happinessBonus: Int, val economyPenalty: Int, val cost: Int) {
    UNIVERSAL_HEALTHCARE(20, -5, 5000),
    UNIVERSAL_INCOME(25, -10, 8000),
    WORKERS_RIGHTS(15, -3, 3000),
    CIVIL_RIGHTS(20, 0, 4000),
    ENVIRONMENTAL_LAWS(10, -8, 3500)
}

@Serializable
data class SocialReform(
    val id: String,
    val type: ReformType,
    val proposed: Boolean = false,
    val passed: Boolean = false,
    val votesFor: Int = 0,
    val votesAgainst: Int = 0
)

// 22. REGIONAL DEVELOPMENT
@Serializable
enum class RegionType {
    URBAN,
    RURAL,
    COASTAL,
    MOUNTAINS,
    DESERT,
    INDUSTRIAL
}

@Serializable
data class Region(
    val id: String,
    val name: String,
    val type: RegionType,
    val population: Int = 0,
    val development: Int = 50,
    val resources: Map<String, Int> = emptyMap()
)

// 23. INTERNATIONAL ORGANIZATIONS
@Serializable
enum class OrgType(val membershipCost: Int, val benefits: Int) {
    NATO(5000, 30),
    EU(4000, 25),
    UN(2000, 15),
    OPEC(3000, 20),
    ASEAN(2500, 18),
    AFRICAN_UNION(1500, 12)
}

@Serializable
data class InternationalOrg(
    val id: String,
    val name: String,
    val type: OrgType,
    val memberIds: List<String>,
    val leaderId: String? = null,
    val founded: Int
)

// 24. CULTURAL INFLUENCE
@Serializable
enum class CulturalExport {
    MUSIC,
    FILM,
    LITERATURE,
    FOOD,
    SPORTS,
    FASHION
}

@Serializable
data class CulturalInfluence(
    val exports: Map<CulturalExport, Int> = emptyMap(),
    val globalReach: Int = 0,
    val annualRevenue: Int = 0
)

// 25. INTELLECTUAL PROPERTY
@Serializable
data class Patents(
    val count: Int = 0,
    val royalties: Int = 0,
    val techBonus: Int = 0
)

// 26. G20 / SUMMIT PARTICIPATION
@Serializable
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
@Serializable
data class ForeignBase(
    val id: String,
    val hostNationId: String,
    val location: String,
    val troops: Int,
    val annualCost: Int
)

// 28. AMBASSADORS
@Serializable
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
    val ambassadors: List<Ambassador> = emptyList(),
    // ============================================
    // FEATURE 300 - NEW GAMEPLAY SYSTEMS
    // ============================================
    // Feature 1: Legacy System
    val legacyData: LegacyData = LegacyData(),
    // Feature 2: Historical Timeline
    val startYear: Int = 2024,
    val currentEra: Era = Era.MODERN_AGE,
    // Features 21-45: Government & Politics
    val politicalParties: List<PoliticalParty> = emptyList(),
    val currentElection: Election? = null,
    val laws: List<Law> = emptyList(),
    val politicalFactions: List<PoliticalFaction> = emptyList(),
    val constitution: Constitution? = null,
    val censorshipLevel: Int = 0,
    val ministers: List<Minister> = emptyList(),
    val rulingElite: RulingElite = RulingElite(),
    // Features 46-70: Economic System
    val stockMarket: StockMarket = StockMarket(),
    val tradeRoutes: List<TradeRoute> = emptyList(),
    val tariffs: TariffSystem = TariffSystem(),
    val nationalCurrency: Currency = Currency(),
    val inflation: Double = 0.0,
    val nationalDebt: NationalDebt = NationalDebt(),
    val taxSystem: TaxSystem = TaxSystem(),
    val subsidies: List<Subsidy> = emptyList(),
    val blackMarket: BlackMarket = BlackMarket(),
    val economicZones: List<EconomicZone> = emptyList(),
    // Features 71-95: Military
    val militaryBranches: MilitaryBranches = MilitaryBranches(),
    val militaryBases: List<MilitaryBase> = emptyList(),
    val defenseContractors: List<DefenseContractor> = emptyList(),
    val veteranCare: VeteranCare = VeteranCare(),
    val militaryAcademies: List<MilitaryAcademy> = emptyList(),
    val fortifications: List<Fortification> = emptyList(),
    // Features 96-120: Diplomacy
    val diplomaticMissions: List<DiplomaticMission> = emptyList(),
    val foreignMinister: Diplomat? = null,
    val spyNetworks: List<SpyNetwork> = emptyList(),
    val refugeePolicy: RefugeePolicy = RefugeePolicy(),
    // Features 121-145: Resources
    val resourceReserves: ResourceReserves = ResourceReserves(),
    val powerGrid: PowerGrid = PowerGrid(),
    val waterManagement: WaterManagement = WaterManagement(),
    // Features 146-165: Technology
    val researchPriorities: List<ResearchPriority> = emptyList(),
    val researchUniversities: List<ResearchUniversity> = emptyList(),
    // Features 166-190: Social
    val socialSecurity: SocialSecurity = SocialSecurity(),
    val welfarePrograms: List<WelfareProgram> = emptyList(),
    val housingPolicy: HousingPolicy = HousingPolicy(),
    // Features 191-210: Events
    val activeEvents: List<GameEvent> = emptyList(),
    val disasterHistory: List<DisasterEvent> = emptyList()
)

// ============================================
// FEATURE 300 - NEW DATA CLASSES
// ============================================

// Feature 1: Legacy System
@Serializable
data class LegacyData(
    val totalGamesPlayed: Int = 0,
    val totalWins: Int = 0,
    val totalLosses: Int = 0,
    val legacyPoints: Int = 0,
    val economyBonus: Int = 0,
    val militaryBonus: Int = 0,
    val happinessBonus: Int = 0,
    val techBonus: Int = 0,
    val stabilityBonus: Int = 0
)

// Feature 2: Historical Timeline
@Serializable
enum class Era(
    val displayName: String,
    val startYear: Int,
    val endYear: Int,
    val techLevel: Int
) {
    COLONIAL_AGE("Colonial Age", 1800, 1850, 1),
    INDUSTRIAL_AGE("Industrial Age", 1850, 1900, 3),
    WORLD_WARS("World Wars", 1900, 1950, 5),
    COLD_WAR("Cold War", 1950, 1990, 7),
    MODERN_AGE("Modern Age", 1990, 2024, 9),
    FUTURE_AGE("Future Age", 2024, 2100, 10)
}

// Feature 21: Political Parties
@Serializable
data class PoliticalParty(
    val id: String,
    val name: String,
    val ideology: PartyIdeology,
    val popularity: Int = 20,
    val seats: Int = 0
)

@Serializable
enum class PartyIdeology {
    CONSERVATIVE, LIBERAL, SOCIALIST, COMMUNIST, LIBERTARIAN,
    NATIONALIST, GREEN, CENTRIST, RELIGIOUS, MONARCHIST
}

// Feature 23: Elections
@Serializable
data class Election(
    val id: String,
    val year: Int,
    val parties: List<PoliticalParty>,
    val isActive: Boolean = true,
    val votingTurnsRemaining: Int = 2
)

// Feature 24: Laws
@Serializable
data class Law(
    val id: String,
    val name: String,
    val description: String,
    val category: LawCategory,
    val effects: Map<String, Int>,
    val isActive: Boolean = false
)

@Serializable
enum class LawCategory {
    ECONOMIC, MILITARY, SOCIAL, ENVIRONMENTAL, EDUCATION,
    HEALTHCARE, SECURITY, FOREIGN, CONSTITUTIONAL
}

// Feature 25: Political Factions
@Serializable
data class PoliticalFaction(
    val id: String,
    val name: String,
    val influence: Int,
    val demands: List<String>,
    val power: Int
)

// Feature 26: Constitution
@Serializable
data class Constitution(
    val id: String,
    val amendments: List<ConstitutionAmendment> = emptyList(),
    val isDemocratic: Boolean = true,
    val hasBillOfRights: Boolean = false
)

@Serializable
data class ConstitutionAmendment(
    val id: String,
    val name: String,
    val description: String,
    val yearEnacted: Int
)

// Feature 31: Ministers / Cabinet
@Serializable
data class Minister(
    val id: String,
    val name: String,
    val position: MinisterPosition,
    val skill: Int,
    val loyalty: Int
)

@Serializable
enum class MinisterPosition {
    FINANCE, DEFENSE, FOREIGN, JUSTICE, HEALTH, EDUCATION,
    ECONOMY, INTERIOR, SCIENCE, CULTURE, ENVIRONMENT, LABOR
}

// Feature 33: Ruling Elite
@Serializable
data class RulingElite(
    val satisfaction: Int = 80,
    val power: Int = 50,
    val corruption: Int = 10
)

// Feature 46: Stock Market
@Serializable
data class StockMarket(
    val isOpen: Boolean = true,
    val marketIndex: Double = 10000.0,
    val volatility: Double = 0.02,
    val tradingVolume: Int = 0
)

// Feature 47: Trade Routes
@Serializable
data class TradeRoute(
    val id: String,
    val partnerNationId: String,
    val goods: Map<String, Int>,
    val annualValue: Int,
    val isActive: Boolean = true
)

// Feature 48: Tariff System
@Serializable
data class TariffSystem(
    val importTariff: Double = 0.05,
    val exportTariff: Double = 0.02,
    val luxuryTariff: Double = 0.15
)

// Feature 49: Currency
@Serializable
data class Currency(
    val name: String = "Dollar",
    val symbol: String = "$",
    val exchangeRate: Double = 1.0,
    val inflationRate: Double = 0.02
)

// Feature 51: National Debt
@Serializable
data class NationalDebt(
    val totalDebt: Int = 0,
    val debtToGDP: Double = 0.0,
    val interestRate: Double = 0.03,
    val creditRating: String = "AAA"
)

// Feature 52: Tax System
@Serializable
data class TaxSystem(
    val incomeTaxRate: Double = 0.25,
    val corporateTaxRate: Double = 0.21,
    val salesTaxRate: Double = 0.08,
    val propertyTaxRate: Double = 0.01
)

// Feature 53: Subsidies
@Serializable
data class Subsidy(
    val id: String,
    val industry: String,
    val amount: Int,
    val isActive: Boolean = true
)

// Feature 56: Black Market
@Serializable
data class BlackMarket(
    val activityLevel: Int = 10,
    val isActive: Boolean = false,
    val riskLevel: Int = 50
)

// Feature 61: Economic Zones
@Serializable
data class EconomicZone(
    val id: String,
    val name: String,
    val taxBreak: Double = 0.5,
    val location: String,
    val companies: Int = 0
)

// Feature 71: Military Branches
@Serializable
data class MilitaryBranches(
    val army: BranchInfo = BranchInfo("Army", 10000),
    val navy: BranchInfo = BranchInfo("Navy", 1000),
    val airForce: BranchInfo = BranchInfo("Air Force", 500),
    val coastGuard: BranchInfo = BranchInfo("Coast Guard", 200)
)

@Serializable
data class BranchInfo(
    val name: String,
    val personnel: Int,
    val readiness: Int = 80,
    val equipmentQuality: Int = 70
)

// Feature 76: Military Bases
@Serializable
data class MilitaryBase(
    val id: String,
    val name: String,
    val location: String,
    val branch: String,
    val capacity: Int,
    val isOverseas: Boolean = false
)

// Feature 90: Defense Contractors
@Serializable
data class DefenseContractor(
    val id: String,
    val name: String,
    val annualRevenue: Int,
    val employees: Int,
    val contracts: List<String> = emptyList()
)

// Feature 94: Veteran Care
@Serializable
data class VeteranCare(
    val hospitals: Int = 0,
    val benefits: Int = 50,
    val budget: Int = 1000
)

// Feature 95: Military Academies
@Serializable
data class MilitaryAcademy(
    val id: String,
    val name: String,
    val branch: String,
    val annualGraduates: Int,
    val quality: Int = 70
)

// Feature 96: Fortifications
@Serializable
data class Fortification(
    val id: String,
    val name: String,
    val location: String,
    val strength: Int,
    val type: FortificationType
)

@Serializable
enum class FortificationType {
    BORDER_DEFENSE, COASTAL_DEFENSE, ANTI_AIR, BUNKER, TRENCH
}

// Feature 97: Diplomatic Missions
@Serializable
data class DiplomaticMission(
    val id: String,
    val nationId: String,
    val type: MissionType,
    val staff: Int,
    val budget: Int
)

@Serializable
enum class MissionType {
    EMBASSY, CONSULATE, TRADE_MISSION, CULTURAL_CENTER
}

// Feature 105: Spy Networks
@Serializable
data class SpyNetwork(
    val id: String,
    val targetNationId: String,
    val agents: Int,
    val effectiveness: Int,
    val coverIdentity: String
)

// Feature 112: Refugee Policy
@Serializable
data class RefugeePolicy(
    val acceptRefugees: Boolean = false,
    val annualQuota: Int = 0,
    val integrationPrograms: Boolean = false
)

// Feature 124: Resource Reserves
@Serializable
data class ResourceReserves(
    val oil: Int = 1000,
    val naturalGas: Int = 800,
    val minerals: Int = 500,
    val rareEarth: Int = 100
)

// Feature 126: Power Grid
@Serializable
data class PowerGrid(
    val capacity: Int = 1000,
    val demand: Int = 800,
    val renewablePercentage: Double = 0.2,
    val nuclearPlants: Int = 0
)

// Feature 127: Water Management
@Serializable
data class WaterManagement(
    val reservoirs: Int = 5,
    val treatmentPlants: Int = 10,
    val irrigationSystems: Int = 20,
    val waterQuality: Int = 70
)

// Feature 147: Research Priorities
@Serializable
data class ResearchPriority(
    val field: String,
    val funding: Int,
    val progress: Int = 0
)

// Feature 161: Research Universities
@Serializable
data class ResearchUniversity(
    val id: String,
    val name: String,
    val ranking: Int,
    val researchOutput: Int,
    val students: Int
)

// Feature 168: Social Security
@Serializable
data class SocialSecurity(
    val pensionAge: Int = 65,
    val pensionAmount: Int = 1000,
    val coverage: Double = 0.8
)

// Feature 169: Welfare Programs
@Serializable
data class WelfareProgram(
    val id: String,
    val name: String,
    val recipients: Int,
    val annualCost: Int,
    val type: WelfareType
)

@Serializable
enum class WelfareType {
    UNEMPLOYMENT, HOUSING, FOOD, HEALTHCARE, CHILD_SUPPORT
}

// Feature 170: Housing Policy
@Serializable
data class HousingPolicy(
    val publicHousingPercentage: Double = 0.1,
    val rentControl: Boolean = false,
    val affordableHousingPrograms: Int = 0
)

// Feature 191: Disaster Events
@Serializable
data class DisasterEvent(
    val id: String,
    val type: DisasterType,
    val year: Int,
    val severity: Int,
    val affectedPopulation: Int,
    val economicDamage: Int
)

@Serializable
enum class DisasterType {
    EARTHQUAKE, HURRICANE, FLOOD, WILDFIRE, TSUNAMI, VOLCANO,
    DROUGHT, PANDEMIC, INDUSTRIAL_ACCIDENT
}

// ----------------------------------------------------
// EXTENDED: EXTREME TEXT-BASED ADVENTURE MODE MODELS
// ----------------------------------------------------

@Serializable
data class TextAdventureState(
    val isActive: Boolean = false,
    val currentFeatureNodeId: String? = null,
    val historyLog: List<String> = emptyList(),
    val completedFeaturesCount: Int = 0
)

data class FeatureNode(
    val id: String,
    val title: String,
    val description: String,
    val options: List<FeatureOption>
)

data class FeatureOption(
    val label: String,
    val effectText: String,
    val effect: (CountryStats, Int, Resources) -> Triple<CountryStats, Int, Resources>
)
