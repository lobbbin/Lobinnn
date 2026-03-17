package com.countrysimulator.game.domain

import kotlinx.serialization.Serializable

/**
 * ========================================================================
 * COUNTRY SIMULATOR - REMAINING FEATURES IMPLEMENTATION
 * Features 251-300: Social, Resources, Tech, Events, Achievements, Endgame
 * ========================================================================
 */

// ==================== CATEGORY 6: RESOURCES & INFRASTRUCTURE ====================

/**
 * Feature 121-145: Enhanced Resource Management
 */

// Resource Discovery
@Serializable
data class ResourceDiscovery(
    val id: String,
    val resource: String,
    val location: String,
    val amount: Int,
    val difficulty: Int,
    val isDiscovered: Boolean = false,
    val isExtracted: Boolean = false
)

// Strategic Reserves
@Serializable
data class StrategicReserve(
    val resourceType: String,
    val currentAmount: Int,
    val maxCapacity: Int,
    val reserveLevel: Int  // 0-100 percentage
)

// Infrastructure Development
@Serializable
data class Infrastructure(
    val roads: Int = 0,
    val ports: Int = 0,
    val airports: Int = 0,
    val railways: Int = 0,
    val powerPlants: Int = 0,
    val hospitals: Int = 0,
    val schools: Int = 0
)

// Power Grid
@Serializable
data class PowerGrid(
    val totalCapacity: Int = 0,
    val currentDemand: Int = 0,
    val renewablePercentage: Int = 0,
    val gridStability: Int = 100,
    val blackouts: Int = 0
)

// Water Management
@Serializable
data class WaterManagement(
    val reservoirs: Int = 0,
    val waterSupply: Int = 0,
    val waterDemand: Int = 0,
    val irrigationSystems: Int = 0,
    val desalinationPlants: Int = 0
)

// Mining Operations
@Serializable
data class MiningOperations(
    val activeMines: Int = 0,
    val productionRate: Int = 0,
    val safetyLevel: Int = 100,
    val environmentalImpact: Int = 0
)

// Oil & Gas Industry
@Serializable
data class OilGasIndustry(
    val refineries: Int = 0,
    val productionRate: Int = 0,
    val reserves: Int = 0,
    val exportCapacity: Int = 0
)

// Renewable Energy
@Serializable
data class RenewableEnergy(
    val solarPower: Int = 0,
    val windPower: Int = 0,
    val hydroPower: Int = 0,
    val geothermalPower: Int = 0,
    val totalCapacity: Int = 0
)

// Nuclear Power
@Serializable
data class NuclearPower(
    val reactors: Int = 0,
    val totalCapacity: Int = 0,
    val safetyRating: Int = 100,
    val wasteStorage: Int = 0
)

// Housing Development
@Serializable
data class Housing(
    val totalHousing: Int = 0,
    val affordableHousing: Int = 0,
    val homelessPopulation: Int = 0,
    val housingQuality: Int = 50,
    val overcrowding: Int = 0
)

// Urban Planning
@Serializable
data class UrbanPlanning(
    val zoningEfficiency: Int = 50,
    val publicSpaces: Int = 0,
    val greenSpaces: Int = 0,
    val trafficManagement: Int = 50,
    val urbanSprawl: Int = 0
)

// Public Transportation
@Serializable
data class PublicTransport(
    val busSystems: Int = 0,
    val metroSystems: Int = 0,
    val railSystems: Int = 0,
    val coverage: Int = 0,
    val ridership: Int = 0
)

// Telecommunications
@Serializable
data class Telecommunications(
    val internetPenetration: Int = 0,
    val mobileNetworks: Int = 0,
    val fiberOptic: Int = 0,
    val coverage: Int = 0
)

// Satellite Network
@Serializable
data class SatelliteNetwork(
    val communicationSatellites: Int = 0,
    val gpsSatellites: Int = 0,
    val spySatellites: Int = 0,
    val weatherSatellites: Int = 0
)

// Pipeline Construction
@Serializable
data class PipelineNetwork(
    val oilPipelines: Int = 0,
    val gasPipelines: Int = 0,
    val waterPipelines: Int = 0,
    val capacity: Int = 0
)

// Port Development
@Serializable
data class Ports(
    val majorPorts: Int = 0,
    val fishingPorts: Int = 0,
    val capacity: Int = 0,
    val containerTerminals: Int = 0
)

// Airport Construction
@Serializable
data class Airports(
    val internationalAirports: Int = 0,
    val domesticAirports: Int = 0,
    val capacity: Int = 0,
    val routes: Int = 0
)

// Border Infrastructure
@Serializable
data class BorderInfrastructure(
    val checkpoints: Int = 0,
    val borderWalls: Int = 0,
    val surveillanceLevel: Int = 0,
    val crossingPoints: Int = 0
)

// Disaster Resilience
@Serializable
data class DisasterResilience(
    val floodProtection: Int = 0,
    val earthquakeProtection: Int = 0,
    val hurricaneProtection: Int = 0,
    val emergencyServices: Int = 0
)

// Smart Grid
@Serializable
data class SmartGrid(
    val isImplemented: Boolean = false,
    val efficiency: Int = 0,
    val integration: Int = 0,
    val cost: Int = 0
)

// Resource Recycling
@Serializable
data class RecyclingProgram(
    val recyclingRate: Int = 0,
    val wasteReduction: Int = 0,
    val facilities: Int = 0,
    val education: Int = 0
)

// Alternative Resources
@Serializable
data class AlternativeResources(
    val syntheticFuel: Int = 0,
    val labGrownMaterials: Int = 0,
    val recycledMaterials: Int = 0
)

// Resource Monopolies
@Serializable
data class ResourceMonopoly(
    val resourceType: String,
    val controlPercentage: Int = 0,
    val priceInfluence: Int = 0
)

// ==================== CATEGORY 7: TECHNOLOGY & RESEARCH ====================

/**
 * Feature 146-165: Enhanced Technology System
 */

// Research Priorities
@Serializable
data class ResearchPriority(
    val field: ResearchField,
    val priority: Int = 50,  // 0-100
    val funding: Int = 0
)

enum class ResearchField {
    MILITARY_TECH, ECONOMIC_TECH, MEDICAL_TECH,
    SPACE_TECH, ENERGY_TECH, COMPUTER_TECH
}

// Research Agreements
@Serializable
data class ResearchAgreement(
    val id: String,
    val participants: List<String>,
    val sharedFields: List<ResearchField>,
    val cost: Int,
    val progress: Int = 0,
    val duration: Int = 10
)

// Patent System
@Serializable
data class Patent(
    val id: String,
    val name: String,
    val field: ResearchField,
    val ownerId: String,
    val expirationYear: Int,
    val licensingFee: Int = 0
)

// Space Program
@Serializable
data class SpaceProgram(
    val budget: Int = 0,
    val launchFacilities: Int = 0,
    val astronauts: Int = 0,
    val missions: List<SpaceMission> = emptyList(),
    val stations: Int = 0,
    val colonies: Int = 0
)

@Serializable
data class SpaceMission(
    val id: String,
    val name: String,
    val type: MissionType,
    val destination: String,
    val status: MissionStatus = MissionStatus.PLANNED
)

enum class MissionType {
    ORBITAL, LUNAR, MARS, ASTEROID, DEEP_SPACE
}

enum class MissionStatus {
    PLANNED, IN_PROGRESS, SUCCESSFUL, FAILED
}

// AI Development
@Serializable
data class AIDevelopment(
    val level: Int = 0,
    val applications: List<String> = emptyList(),
    val ethics: Int = 50,
    val regulations: Int = 0
)

// Biotechnology
@Serializable
data class Biotechnology(
    val geneticResearch: Int = 0,
    val medicalBreakthroughs: Int = 0,
    val agricultural: Int = 0,
    val ethicalConcerns: Int = 0
)

// Nanotechnology
@Serializable
data class Nanotechnology(
    val manufacturing: Int = 0,
    val medicine: Int = 0,
    val materials: Int = 0,
    val military: Int = 0
)

// Quantum Computing
@Serializable
data class QuantumComputing(
    val processors: Int = 0,
    val capability: Int = 0,
    val errorCorrection: Int = 0
)

// Fusion Power
@Serializable
data class FusionPower(
    val researchProgress: Int = 0,
    val reactors: Int = 0,
    val energyOutput: Int = 0,
    val breakthroughYear: Int? = null
)

// Climate Engineering
@Serializable
data class ClimateEngineering(
    val carbonCapture: Int = 0,
    val solarRadiation: Int = 0,
    val oceanFertilization: Int = 0,
    val effectiveness: Int = 0
)

// Research Universities
@Serializable
data class ResearchUniversity(
    val id: String,
    val name: String,
    val quality: Int = 50,
    val fields: List<ResearchField> = emptyList(),
    val funding: Int = 0
)

// Tech Diffusion
@Serializable
data class TechDiffusion(
    val adoptionRate: Int = 0,
    val foreignTechnology: Int = 0,
    val indigenousInnovation: Int = 0
)

// Research Scandal
@Serializable
data class ResearchScandal(
    val id: String,
    val type: String,
    val severity: Int,
    val affectedField: String,
    val publicTrust: Int = 0
)

// Brain Drain
@Serializable
data class BrainDrain(
    val scientistsLost: Int = 0,
    val destinationCountries: List<String> = emptyList(),
    val cause: String = ""
)

// ==================== CATEGORY 8: SOCIAL & DOMESTIC POLICY ====================

/**
 * Feature 166-234: Social Policy Features
 */

// Education System (Enhanced)
@Serializable
data class EducationSystem(
    val primaryEnrollment: Int = 0,
    val secondaryEnrollment: Int = 0,
    val universityEnrollment: Int = 0,
    val quality: Int = 50,
    val funding: Int = 0,
    val literacyRate: Int = 0
)

// Healthcare System (Enhanced)
@Serializable
data class HealthcareSystem(
    val coverage: Int = 0,
    val quality: Int = 50,
    val hospitals: Int = 0,
    val doctors: Int = 0,
    val lifeExpectancy: Int = 0,
    val funding: Int = 0
)

// Social Security
@Serializable
data class SocialSecurity(
    val isUniversal: Boolean = false,
    val contributionRate: Int = 0,
    val pensionAge: Int = 65,
    val benefitLevel: Int = 0,
    val sustainability: Int = 100
)

// Welfare Programs
@Serializable
data class WelfarePrograms(
    val unemploymentBenefits: Int = 0,
    val foodStamps: Boolean = false,
    val housingAssistance: Boolean = false,
    val energyAssistance: Boolean = false,
    val recipients: Int = 0
)

// Housing Policy
@Serializable
data class HousingPolicy(
    val socialHousing: Int = 0,
    val rentControl: Boolean = false,
    val homeownershipRate: Int = 0,
    val homelessRate: Int = 0
)

// Crime Prevention
@Serializable
data class CrimePrevention(
    val policeForce: Int = 0,
    val preventionPrograms: Int = 0,
    val surveillance: Int = 0,
    val communityPolicing: Int = 0
)

// Prison System
@Serializable
data class PrisonSystem(
    val capacity: Int = 0,
    val population: Int = 0,
    val rehabilitation: Int = 0,
    val overcrowding: Int = 0
)

// Drug Policy
@Serializable
data class DrugPolicy(
    val approach: DrugApproach = DrugApproach.CRIMINALIZATION,
    val treatmentPrograms: Int = 0,
    val enforcementLevel: Int = 50
)

enum class DrugApproach {
    CRIMINALIZATION, DECRIMINALIZATION, LEGALIZATION, MEDICAL
}

// Immigration Control
@Serializable
data class ImmigrationControl(
    val borderSecurity: Int = 0,
    val visaRequirements: Int = 50,
    val quotas: Int = 0,
    val vettingLevel: Int = 50
)

// Integration Policy
@Serializable
data class IntegrationPolicy(
    val languagePrograms: Int = 0,
    val culturalPrograms: Int = 0,
    val employmentPrograms: Int = 0,
    val citizenshipPath: Boolean = false
)

// Language Policy
@Serializable
data class LanguagePolicy(
    val officialLanguages: List<String> = emptyList(),
    val multilingualism: Int = 0,
    val languageTests: Boolean = false
)

// Cultural Policy
@Serializable
data class CulturalPolicy(
    val artsFunding: Int = 0,
    val heritageSites: Int = 0,
    val censorship: Int = 0,
    val creativeIndustry: Int = 0
)

// Sports Development
@Serializable
data class SportsDevelopment(
    val facilities: Int = 0,
    val professionalLeagues: Int = 0,
    val youthPrograms: Int = 0,
    val internationalSuccess: Int = 0
)

// Religious Freedom
@Serializable
data class ReligiousFreedom(
    val level: Int = 100,
    val stateReligion: Boolean = false,
    val religiousDiversity: Int = 0,
    val discrimination: Int = 0
)

// LGBTQ+ Rights
@Serializable
data class LGBTQRights(
    val marriage: Boolean = false,
    val adoption: Boolean = false,
    val antiDiscrimination: Boolean = false,
    val militaryService: Boolean = false
)

// Women's Rights
@Serializable
data class WomensRights(
    val suffrage: Boolean = true,
    val workplaceEquality: Int = 0,
    val reproductiveRights: Int = 50,
    val politicalRepresentation: Int = 0
)

// Minority Rights
@Serializable
data class MinorityRights(
    val protections: Int = 0,
    val representation: Int = 0,
    val autonomousRegions: Int = 0,
    val discrimination: Int = 0
)

// Workers' Rights
@Serializable
data class WorkersRights(
    val minimumWage: Int = 0,
    val unions: Boolean = false,
    val workplaceSafety: Int = 50,
    val childLabor: Boolean = false
)

// Animal Rights
@Serializable
data class AnimalRights(
    val welfareLaws: Int = 0,
    val testingRestrictions: Int = 0,
    val farmingRegulations: Int = 0,
    val publicAttitude: Int = 50
)

// Privacy Laws
@Serializable
data class PrivacyLaws(
    val dataProtection: Int = 0,
    val surveillanceLimits: Int = 50,
    val governmentAccess: Int = 50,
    val corporateResponsibility: Int = 0
)

// Internet Freedom
@Serializable
data class InternetFreedom(
    val access: Int = 0,
    val censorship: Int = 0,
    val netNeutrality: Boolean = true,
    val digitalRights: Int = 100
)

// Gun Control
@Serializable
data class GunControl(
    val regulation: GunRegulation = GunRegulation.MODERATE,
    val backgroundChecks: Boolean = false,
    val assaultWeapons: Boolean = false,
    val registration: Boolean = false
)

enum class GunRegulation {
    PERMISSIVE, MODERATE, RESTRICTIVE, PROHIBITIVE
}

// Death Penalty
@Serializable
data class DeathPenalty(
    val isLegal: Boolean = false,
    val executions: Int = 0,
    val method: String = "",
    val publicSupport: Int = 0
)

// Urban vs Rural
@Serializable
data class UrbanRuralBalance(
    val urbanPopulation: Int = 0,
    val ruralPopulation: Int = 0,
    val developmentGap: Int = 0,
    val migration: Int = 0
)

// ==================== CATEGORY 9: EVENTS & RANDOM OCCURRENCES ====================

/**
 * Feature 191-210: Event System Expansion
 */

// Demographic Shifts
@Serializable
data class DemographicShift(
    val type: DemographicType,
    val magnitude: Int,
    val causes: List<String>,
    val effects: String
)

enum class DemographicType {
    BABY_BOOM, AGING_POPULATION, IMMIGRATION_WAVE,
    EMIGRATION, URBANIZATION, RURAL_EXODUS
}

// Social Movements
@Serializable
data class SocialMovement(
    val id: String,
    val name: String,
    val type: MovementType,
    val size: Int = 0,
    val influence: Int = 0,
    val goals: List<String> = emptyList()
)

enum class MovementType {
    REFORM, REVOLUTIONARY, NATIONALIST, RELIGIOUS,
    ENVIRONMENTAL, LABOR, CIVIL_RIGHTS
}

// Rare Events
@Serializable
data class RareEvent(
    val id: String,
    val type: RareEventType,
    val description: String,
    val probability: Double,
    val effects: ParadoxEffect
)

enum class RareEventType {
    METEOR_STRIKE, VOLCANIC_SUPERERUPTION, SOLAR_STORM,
    DISCOVERY_OF_CIVILIZATION, FIRST_CONTACT, TIME_ANOMALY
}

// ==================== CATEGORY 10: ACHIEVEMENTS & QUESTS ====================

/**
 * Feature 211-225: Achievement System Expansion
 */

// Achievement Categories (Enhanced)
enum class AchievementCategory {
    MILITARY, ECONOMIC, DIPLOMATIC, TECHNOLOGICAL,
    CULTURAL, SURVIVAL, EXPLORATION, SPECIAL
}

// Achievement Tiers
enum class AchievementTier(
    val displayName: String,
    val points: Int,
    val color: String
) {
    BRONZE("Bronze", 10, "#CD7F32"),
    SILVER("Silver", 25, "#C0C0C0"),
    GOLD("Gold", 50, "#FFD700"),
    PLATINUM("Platinum", 100, "#E5E4E2"),
    DIAMOND("Diamond", 250, "#B9F2FF")
}

// Hidden Achievements
@Serializable
data class HiddenAchievement(
    val id: String,
    val name: String,
    val description: String,
    val hint: String,
    val isSecret: Boolean = true
)

// Quest Chains
@Serializable
data class QuestChain(
    val id: String,
    val name: String,
    val quests: List<Quest> = emptyList(),
    val isCompleted: Boolean = false,
    val rewards: List<QuestReward> = emptyList()
)

@Serializable
data class Quest(
    val id: String,
    val name: String,
    val description: String,
    val objectives: List<String> = emptyList(),
    val isCompleted: Boolean = false
)

@Serializable
data class QuestReward(
    val type: String,
    val value: Int,
    val description: String
)

// Daily Challenges
@Serializable
data class DailyChallenge(
    val id: String,
    val name: String,
    val description: String,
    val objectives: List<String>,
    val rewards: List<QuestReward>,
    val expiresAt: Long,
    val isCompleted: Boolean = false
)

// Weekly Goals
@Serializable
data class WeeklyGoal(
    val id: String,
    val name: String,
    val description: String,
    val target: Int,
    val progress: Int = 0,
    val rewards: List<QuestReward>
)

// Seasonal Events
@Serializable
data class SeasonalEvent(
    val id: String,
    val name: String,
    val season: String,
    val description: String,
    val specialRules: List<String> = emptyList(),
    val uniqueRewards: List<QuestReward> = emptyList()
)

// Legacy Achievements
@Serializable
data class LegacyAchievement(
    val id: String,
    val name: String,
    val description: String,
    val bonus: LegacyBonus,
    val requirements: List<String> = emptyList()
)

// Completionist Tracking
@Serializable
data class CompletionistProgress(
    val totalAchievements: Int = 0,
    val unlockedAchievements: Int = 0,
    val totalQuests: Int = 0,
    val completedQuests: Int = 0,
    val totalChallenges: Int = 0,
    val completedChallenges: Int = 0,
    val completionPercentage: Double = 0.0
)

// Speedrun Records
@Serializable
data class SpeedrunRecord(
    val category: String,
    val playerId: String,
    val time: Long,  // Milliseconds
    val date: Long,
    val nation: String
)

// Challenge Modes
@Serializable
data class ChallengeMode(
    val id: String,
    val name: String,
    val description: String,
    val restrictions: List<String> = emptyList(),
    val objectives: List<String> = emptyList(),
    val difficulty: Difficulty
)

// Community Goals
@Serializable
data class CommunityGoal(
    val id: String,
    val name: String,
    val description: String,
    val target: Int,
    val currentProgress: Int = 0,
    val contributors: List<String> = emptyList(),
    val reward: QuestReward
)

// Season Rankings
@Serializable
data class SeasonRanking(
    val seasonId: String,
    val rankings: List<LeaderboardEntry> = emptyList(),
    val topPlayers: List<String> = emptyList(),
    val rewards: List<QuestReward> = emptyList()
)

// Reward Redemption
@Serializable
data class RewardStore(
    val availableRewards: List<StoreReward> = emptyList(),
    val playerPoints: Int = 0
)

@Serializable
data class StoreReward(
    val id: String,
    val name: String,
    val description: String,
    val cost: Int,
    val type: String
)

// ==================== CATEGORY 11: ENDGAME & CHALLENGES ====================

/**
 * Feature 226-240: Endgame Scenarios
 */

// Apocalypse Scenarios
@Serializable
data class ApocalypseScenario(
    val id: String,
    val type: ApocalypseType,
    val name: String,
    val description: String,
    val turnsUntilImpact: Int,
    val survivalDifficulty: Int,
    val uniqueMechanics: List<String>
)

enum class ApocalypseType {
    NUCLEAR_WINTER, ASTEROID_IMPACT, PANDEMIC, CLIMATE_CATASTROPHE,
    SOLAR_FLARE, AI_TAKEOVER, ALIEN_INVASION, DIMENSIONAL_RIFT
}

// Alien Invasion
@Serializable
data class AlienInvasion(
    val isActive: Boolean = false,
    val invasionForce: Int = 0,
    val technologyLevel: Int = 0,
    val objectives: List<String> = emptyList(),
    val earthAllies: List<String> = emptyList()
)

// Cold War Scenario
@Serializable
data class ColdWarScenario(
    val tension: Int = 50,
    val proxyWars: List<String> = emptyList(),
    val armsRace: Int = 0,
    val spaceRace: Int = 0,
    val espionageLevel: Int = 0
)

// World War Scenario
@Serializable
data class WorldWarScenario(
    val activeTheaters: List<String> = emptyList(),
    val participants: List<String> = emptyList(),
    val totalCasualties: Long = 0,
    val warExhaustion: Map<String, Int> = emptyMap()
)

// Colonial Independence
@Serializable
data class ColonialIndependence(
    val isColony: Boolean = false,
    val colonizer: String? = null,
    val independenceMovement: Int = 0,
    val support: Int = 0
)

// Zombie Apocalypse
@Serializable
data class ZombieApocalypse(
    val isActive: Boolean = false,
    val infectedPopulation: Int = 0,
    val survivalSectors: List<String> = emptyList(),
    val cureProgress: Int = 0
)

// Resource Depletion
@Serializable
data class ResourceDepletion(
    val criticalResources: List<String> = emptyList(),
    val depletionRate: Int = 0,
    val alternativesProgress: Int = 0
)

// ==================== COMPREHENSIVE GAME STATE ====================

/**
 * Complete Game State with All Features
 */
@Serializable
data class CompleteGameState(
    // Base State
    val country: Country,
    val aiNations: List<AiNation>,
    val globalMarket: GlobalMarket,
    val isGameOver: Boolean = false,
    val gameOverReason: GameOverReason? = null,
    
    // Phase 1 Features
    val legacyData: LegacyData = LegacyData(),
    val era: Era = Era.MODERN_AGE,
    val gameMode: GameMode = GameMode(),
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
    val stockMarket: StockMarket = StockMarket(),
    val tariffSystem: TariffSystem = TariffSystem(),
    val currency: Currency = Currency(),
    val inflationControl: InflationControl = InflationControl(),
    val nationalDebtData: NationalDebt = NationalDebt(),
    
    // Phase 2 Features (abbreviated for reference)
    val diplomaticMissions: List<DiplomaticMission> = emptyList(),
    val summits: List<SummitMeeting> = emptyList(),
    val militaryBudget: MilitaryBudget = MilitaryBudget(),
    val militaryBases: List<MilitaryBase> = emptyList(),
    val missileDefense: MissileDefense = MissileDefense(),
    val spaceProgram: SpaceProgram = SpaceProgram(),
    val cyberWarfare: CyberWarfare = CyberWarfare(),
    val specialForces: SpecialForces = SpecialForces(),
    val fortifications: List<Fortification> = emptyList(),
    val veteranCare: VeteranCare = VeteranCare(),
    val defenseIndustry: DefenseIndustry = DefenseIndustry(),
    val tourismIndustry: TourismIndustry = TourismIndustry(),
    val culturalDiplomacy: CulturalDiplomacy = CulturalDiplomacy(),
    val refugeePolicy: RefugeePolicy = RefugeePolicy(),
    val internationalPrestige: InternationalPrestige = InternationalPrestige(),
    
    // Phase 3 UI Features
    val uiState: com.countrysimulator.game.presentation.UIState = com.countrysimulator.game.presentation.UIState(),
    
    // Phase 4 Extraordinary Features
    val extraordinaryState: ExtraordinaryState = ExtraordinaryState(),
    
    // Phase 5 Multiplayer Features
    val multiplayerSession: com.countrysimulator.game.multiplayer.MultiplayerSession? = null,
    val asyncGame: com.countrysimulator.game.multiplayer.AsyncGame? = null,
    
    // Additional Features
    val infrastructure: Infrastructure = Infrastructure(),
    val powerGrid: PowerGrid = PowerGrid(),
    val waterManagement: WaterManagement = WaterManagement(),
    val educationSystem: EducationSystem = EducationSystem(),
    val healthcareSystem: HealthcareSystem = HealthcareSystem(),
    val socialSecurity: SocialSecurity = SocialSecurity(),
    val welfarePrograms: WelfarePrograms = WelfarePrograms(),
    val crimePrevention: CrimePrevention = CrimePrevention(),
    val drugPolicy: DrugPolicy = DrugPolicy(),
    val immigrationControl: ImmigrationControl = ImmigrationControl(),
    val gunControl: GunControl = GunControl(),
    val deathPenalty: DeathPenalty = DeathPenalty(),
    val internetFreedom: InternetFreedom = InternetFreedom(),
    val climateEngineering: ClimateEngineering = ClimateEngineering(),
    val spaceTech: com.countrysimulator.game.domain.SpaceProgram = com.countrysimulator.game.domain.SpaceProgram(),
    val aiDevelopment: AIDevelopment = AIDevelopment(),
    val researchPriorities: List<ResearchPriority> = emptyList(),
    val researchUniversities: List<ResearchUniversity> = emptyList(),
    val achievements: List<String> = emptyList(),
    val questChains: List<QuestChain> = emptyList(),
    val completionistProgress: CompletionistProgress = CompletionistProgress(),
    val zombieApocalypse: ZombieApocalypse = ZombieApocalypse(),
    val alienInvasion: AlienInvasion = AlienInvasion(),
    val coldWar: ColdWarScenario = ColdWarScenario(),
    val worldWar: WorldWarScenario = WorldWarScenario()
)

// ==================== UTILITY FUNCTIONS ====================

object FeatureUtils {
    
    /**
     * Calculate overall nation strength
     */
    fun calculateStrength(state: CompleteGameState): Int {
        val stats = state.country.stats
        return (stats.economy + stats.military + stats.stability + 
                stats.technology + stats.happiness) / 5
    }
    
    /**
     * Check if player can win
     */
    fun checkVictoryConditions(state: CompleteGameState): VictoryType? {
        val strength = calculateStrength(state)
        if (strength >= 90) return VictoryType.ECONOMIC_DOMINANCE
        
        val allies = state.country.diplomaticRelations.count { it.hasAlliance }
        if (allies >= 50) return VictoryType.DIPLOMATIC_INFLUENCE
        
        if (state.country.stats.technology >= 100) return VictoryType.TECHNOLOGICAL_SUPREMACY
        
        if (state.country.turnCount >= 500) return VictoryType.SURVIVAL
        
        return null
    }
    
    /**
     * Process all turn-based features
     */
    fun processAllTurnFeatures(state: CompleteGameState): CompleteGameState {
        var updated = state
        
        // Update performance metrics
        updated = updated.copy(
            performanceMetrics = updated.performanceMetrics.record(
                updated.country.stats,
                updated.country.treasury,
                updated.country.stats.population
            )
        )
        
        return updated
    }
}
