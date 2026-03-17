package com.countrysimulator.game.domain

import kotlinx.serialization.Serializable

/**
 * ========================================================================
 * COUNTRY SIMULATOR - PHASE 2 FEATURES IMPLEMENTATION
 * Features 51-100: Depth Expansion - Government, Economic, Military, Diplomacy
 * ========================================================================
 */

// ==================== PHASE 2: GOVERNMENT FEATURES (continued) ====================

/**
 * Feature 51: Government Reform System - Gradual reforms unlocking new government types
 */
object GovernmentReformDatabase {
    val reforms = listOf(
        // Tier 1: Basic Democratization
        GovernmentReform(
            "reform_1", "Constitutional Convention",
            "Establish a written constitution", 1,
            listOf(GovernmentType.DICTATORSHIP, GovernmentType.MONARCHY),
            1800, 5000,
            listOf(LawEffect("stability", 5, EffectType.BONUS))
        ),
        GovernmentReform(
            "reform_2", "Bill of Rights",
            "Guarantee basic civil liberties", 1,
            listOf(GovernmentType.DEMOCRACY, GovernmentType.REPUBLIC),
            1790, 3000,
            listOf(LawEffect("happiness", 10, EffectType.BONUS))
        ),
        // Tier 2: Political Reform
        GovernmentReform(
            "reform_3", "Electoral Reform",
            "Implement fair elections", 2,
            listOf(GovernmentType.DICTATORSHIP, GovernmentType.MONARCHY),
            1850, 8000,
            listOf(LawEffect("stability", 10, EffectType.BONUS), LawEffect("happiness", 5, EffectType.BONUS))
        ),
        GovernmentReform(
            "reform_4", "Federalism",
            "Divide power between central and regional governments", 2,
            listOf(GovernmentType.DEMOCRACY, GovernmentType.REPUBLIC),
            1820, 6000,
            listOf(LawEffect("economy", 10, EffectType.BONUS), LawEffect("stability", 5, EffectType.BONUS))
        ),
        // Tier 3: Social Reform
        GovernmentReform(
            "reform_5", "Universal Suffrage",
            "Grant voting rights to all citizens", 3,
            listOf(GovernmentType.DEMOCRACY),
            1900, 10000,
            listOf(LawEffect("happiness", 15, EffectType.BONUS), LawEffect("stability", -5, EffectType.PENALTY))
        ),
        GovernmentReform(
            "reform_6", "Social Safety Net",
            "Establish welfare programs", 3,
            listOf(GovernmentType.DEMOCRACY, GovernmentType.SOCIALISM, GovernmentType.COMMUNISM),
            1930, 12000,
            listOf(LawEffect("happiness", 20, EffectType.BONUS), LawEffect("economy", -10, EffectType.PENALTY))
        ),
        // Tier 4: Modernization
        GovernmentReform(
            "reform_7", "Digital Governance",
            "Implement e-government services", 4,
            listOf(GovernmentType.DEMOCRACY, GovernmentType.REPUBLIC, GovernmentType.TECHNOCRACY),
            1990, 20000,
            listOf(LawEffect("technology", 15, EffectType.BONUS), LawEffect("corruption", -10, EffectType.PENALTY))
        ),
        GovernmentReform(
            "reform_8", "Green Government",
            "Environmental protection as constitutional principle", 4,
            listOf(GovernmentType.DEMOCRACY, GovernmentType.SOCIALISM),
            1992, 15000,
            listOf(LawEffect("environment", 20, EffectType.BONUS), LawEffect("economy", -5, EffectType.PENALTY))
        ),
        // Tier 5: Future Government
        GovernmentReform(
            "reform_9", "AI Governance",
            "Integrate AI into decision making", 5,
            listOf(GovernmentType.TECHNOCRACY),
            2030, 50000,
            listOf(LawEffect("technology", 25, EffectType.BONUS), LawEffect("economy", 15, EffectType.BONUS), LawEffect("happiness", -10, EffectType.PENALTY))
        )
    )
}

// ==================== PHASE 2: ECONOMIC FEATURES (continued) ====================

/**
 * Feature 51-60: Economic Depth Features
 */

// Feature 51: National Debt & Credit System
@Serializable
data class NationalDebt(
    val totalDebt: Int = 0,
    val debtToGDP: Double = 0.0,
    val creditRating: Int = 75,         // AAA=100, D=0
    val interestRate: Double = 3.0,
    val debtPayments: Int = 0,
    val maxBorrowing: Int = 50000,
    val bondYield: Double = 2.0
) {
    fun calculateDebtService(): Int {
        return (totalDebt * interestRate / 100).toInt()
    }
    
    fun canBorrow(amount: Int): Boolean {
        return (totalDebt + amount) <= maxBorrowing
    }
    
    fun borrow(amount: Int): NationalDebt {
        return copy(
            totalDebt = totalDebt + amount,
            interestRate = (interestRate + amount / 10000.0).coerceAtMost(15.0),
            creditRating = (creditRating - amount / 1000).coerceAtLeast(0)
        )
    }
    
    fun repay(amount: Int): NationalDebt {
        return copy(
            totalDebt = (totalDebt - amount).coerceAtLeast(0),
            creditRating = (creditRating + amount / 500).coerceAtMost(100)
        )
    }
}

// Feature 53: Subsidies System
@Serializable
data class Subsidy(
    val id: String,
    val name: String,
    val industry: Industry,
    val amount: Int,
    val duration: Int,  // -1 = permanent
    val effects: List<LawEffect>
)

enum class Industry {
    AGRICULTURE, ENERGY, MANUFACTURING, TECHNOLOGY, 
    MILITARY, SERVICES, MINING, TRANSPORTATION
}

// Feature 54-55: Privatization & Nationalization
@Serializable
data class StateAsset(
    val id: String,
    val name: String,
    val type: AssetType,
    val value: Int,
    val profitability: Int,  // 0-100
    val isPrivatized: Boolean = false,
    val isNationalized: Boolean = false
)

enum class AssetType {
    OIL_COMPANY, STEEL_COMPANY, BANK, AIRLINE,
    TELECOM, RAILWAY, POWER_COMPANY, ARMS_FACTORY
}

@Serializable
data class PrivatizationPlan(
    val asset: StateAsset,
    val salePrice: Int,
    val buyerReliability: Int,  // Risk of not paying
    val conditions: List<String>
)

@Serializable
data class NationalizationPlan(
    val asset: StateAsset,
    val compensation: Int,  // Must pay owners
    val efficiency: Int,   // Expected efficiency change
    val popularSupport: Int
)

// Feature 56: Banking System
@Serializable
data class BankingSystem(
    val centralBankIndependence: Int = 50,  // 0-100
    val reserveRequirement: Double = 0.1,   // 10%
    val discountRate: Double = 3.0,
    val moneySupply: Int = 10000,
    val hasDepositInsurance: Boolean = false,
    val bankRegulations: Int = 50  // 0-100 strictness
)

// Feature 57-59: Economic Sanctions & Black Market
@Serializable
data class Sanction(
    val id: String,
    val targetNationId: String,
    val type: SanctionType,
    val imposedBy: String,
    val year: Int,
    val duration: Int = -1,
    val isLifted: Boolean = false
)

@Serializable
data class BlackMarket(
    val activityLevel: Int = 10,    // 0-100
    val volume: Int = 1000,          // Value per turn
    val risk: Int = 30,             // Chance of being caught
    val taxEvasion: Int = 50        // Lost tax revenue
)

// Feature 60: Trade Agreements (Enhanced)
@Serializable
data class EnhancedTradeAgreement(
    val id: String,
    val name: String,
    val type: TradeAgreementType,
    val participants: List<String>,
    val yearSigned: Int,
    val benefits: Map<String, Int>,  // nationId -> bonus
    val tradeVolume: Int,
    val tariffReduction: Int  // Percentage
)

// ==================== PHASE 2: MILITARY FEATURES (continued) ====================

/**
 * Feature 71-95: Military Depth Features
 */

// Feature 71-72: Military Ranks & Promotion System
@Serializable
data class MilitaryRank(
    val name: String,
    val level: Int,
    val salary: Int,
    val moraleBonus: Int,
    val experienceRequired: Int
)

object MilitaryRankSystem {
    val ranks = listOf(
        MilitaryRank("Private", 1, 100, 0, 0),
        MilitaryRank("Corporal", 2, 150, 5, 100),
        MilitaryRank("Sergeant", 3, 200, 10, 300),
        MilitaryRank("Staff Sergeant", 4, 250, 15, 600),
        MilitaryRank("Lieutenant", 5, 350, 20, 1000),
        MilitaryRank("Captain", 6, 450, 25, 1500),
        MilitaryRank("Major", 7, 550, 30, 2500),
        MilitaryRank("Colonel", 8, 700, 35, 4000),
        MilitaryRank("General", 9, 900, 40, 6000),
        MilitaryRank("Field Marshal", 10, 1200, 50, 10000)
    )
    
    fun getRank(experience: Int): MilitaryRank {
        return ranks.lastOrNull { it.experienceRequired <= experience } ?: ranks[0]
    }
}

// Feature 73: Enhanced Draft System
@Serializable
data class DraftSystem(
    val isConscription: Boolean = false,  // true = mandatory service
    val draftLength: Int = 24,           // months
    val draftAge: Int = 18,
    val exemptionCategories: List<String> = emptyList(),
    val draftEvasionRate: Int = 10,       // percentage avoiding service
    val trainingQuality: Int = 50
)

// Feature 74: Military Budget Allocation
@Serializable
data class MilitaryBudget(
    val totalBudget: Int = 5000,
    val recruitmentAllocation: Int = 30,  // percentage
    val equipmentAllocation: Int = 35,
    val rdAllocation: Int = 20,
    val trainingAllocation: Int = 15,
    val veteranCareAllocation: Int = 0
) {
    fun getRecruitmentAmount(): Int = totalBudget * recruitmentAllocation / 100
    fun getEquipmentAmount(): Int = totalBudget * equipmentAllocation / 100
    fun getRdAmount(): Int = totalBudget * rdAllocation / 100
    fun getTrainingAmount(): Int = totalBudget * trainingAllocation / 100
}

// Feature 76: Base Construction
@Serializable
data class MilitaryBase(
    val id: String,
    val name: String,
    val location: String,
    val type: BaseType,
    val level: Int = 1,
    val capacity: Int = 5000,
    val currentManpower: Int = 0,
    val equipmentLevel: Int = 1,
    val costPerTurn: Int = 100
)

enum class BaseType {
    ARMY_BASE, NAVAL_BASE, AIR_BASE, 
    MISSILE_SITE, COMMAND_CENTER, TRAINING_CENTER
}

// Feature 79-80: Missile Defense & Space Military
@Serializable
data class MissileDefense(
    val isActive: Boolean = false,
    val coverageLevel: Int = 0,  // 0-100
    val effectiveness: Int = 0,  // 0-100
    val cost: Int = 5000
)

@Serializable
data class SpaceProgram(
    val isActive: Boolean = false,
    val satellites: Int = 0,
    val spaceForce: Int = 0,
    val orbitalStations: Int = 0,
    val budget: Int = 0,
    val capabilityLevel: Int = 0
)

// Feature 81: Cyber Warfare
@Serializable
data class CyberWarfare(
    val defenseLevel: Int = 10,
    val attackLevel: Int = 0,
    val infrastructureProtection: Int = 50,
    val espionageCapability: Int = 10,
    val cost: Int = 1000
)

// Feature 83: Private Military Companies (Enhanced)
@Serializable
data class PrivateMilitary(
    val isActive: Boolean = false,
    val contractedGroups: List<MercenaryCompany> = emptyList(),
    val totalPower: Int = 0,
    val loyalty: Int = 80,
    val costPerTurn: Int = 0
)

@Serializable
data class MercenaryCompany(
    val id: String,
    val name: String,
    val specialty: String,
    val power: Int,
    val costPerTurn: Int,
    val loyalty: Int,
    val contractMonthsRemaining: Int,
    val reputation: Int  // 0-100
)

// Feature 85-89: War & Occupation System
@Serializable
data class OccupationZone(
    val id: String,
    val nationId: String,
    val occupationType: OccupationType,
    val resistanceLevel: Int = 0,
    val garrisonSize: Int = 1000,
    val exploitationLevel: Int = 50,  // Resource extraction
    val loyalty: Int = 10,
    val yearsUnderOccupation: Int = 0
)

enum class OccupationType {
    MILITARY, ADMINISTRATIVE, PUPPET, VASSAL
}

@Serializable
data class ResistanceMovement(
    val strength: Int = 0,
    val organization: Int = 0,
    val foreignSupport: Int = 0,
    val target: String,  // Nation being resisted
    val activityLevel: Int = 50
)

@Serializable
data class PeaceTreaty(
    val id: String,
    val name: String,
    val parties: List<String>,
    val terms: TreatyTerms,
    val yearSigned: Int,
    val duration: Int = -1,
    val violations: Int = 0
)

@Serializable
data class TreatyTerms(
    val territorialChanges: Map<String, String> = emptyMap(),
    val warReparations: Int = 0,
    val disarmament: Boolean = false,
    val militaryLimit: Int = -1,
    val occupationZones: List<String> = emptyMap(),
    val tradeRestrictions: Boolean = false
)

// Feature 92-93: Military Academies & Joint Operations
@Serializable
data class MilitaryAcademy(
    val id: String,
    val name: String,
    val branch: String,  // Army, Navy, Air, Combined
    val level: Int = 1,
    val capacity: Int = 1000,
    val graduatesPerYear: Int = 100,
    val quality: Int = 50,
    val cost: Int = 2000
)

@Serializable
data class JointOperation(
    val id: String,
    val name: String,
    val participatingBranches: List<String>,
    val status: OperationStatus,
    val objective: String,
    val successProbability: Int,
    val assignedUnits: Map<String, Int>
)

enum class OperationStatus {
    PLANNING, IN_PROGRESS, SUCCESSFUL, FAILED, ABORTED
}

// Feature 94: Special Forces
@Serializable
data class SpecialForces(
    val totalStrength: Int = 0,
    val operators: List<SpecialOpsUnit> = emptyList(),
    val trainingLevel: Int = 50,
    val equipmentLevel: Int = 50
)

@Serializable
data class SpecialOpsUnit(
    val id: String,
    val name: String,
    val specialty: SpecialOpsType,
    val size: Int,
    val readiness: Int,  // 0-100
    val experience: Int
)

enum class SpecialOpsType {
    RECON, DIRECT_ACTION, COUNTER_TERRORISM, 
    SPECIAL_RECONNAISSANCE, FOREIGN_INTERNAL_DEFENSE
}

// Feature 95: Fortification System
@Serializable
data class Fortification(
    val id: String,
    val location: String,
    val type: FortificationType,
    val level: Int = 1,
    val defenseBonus: Int = 10,
    val cost: Int = 1000,
    val maintenance: Int = 100
)

enum class FortificationType {
    BORDER_FORT, COASTAL_BATTERY, MOUNTAIN_PASS, 
    CITY_BUNKER, ANTI_AIR, UNDERGROUND
}

// Feature 91: Veteran Care
@Serializable
data class VeteranCare(
    val hospitals: Int = 0,
    val pensionLevel: Int = 50,  // 0-100
    val disabilityBenefits: Int = 50,
    val employmentPrograms: Boolean = false,
    val mentalHealthSupport: Boolean = false,
    val budget: Int = 1000
)

// Feature 90: Defense Contractors
@Serializable
data class DefenseIndustry(
    val domesticProduction: Int = 50,  // 0-100
    val exportCapability: Int = 30,
    val researchCapability: Int = 50,
    val qualityLevel: Int = 50,
    val contractors: List<ArmsCompany> = emptyList()
)

@Serializable
data class ArmsCompany(
    val id: String,
    val name: String,
    val specialty: String,
    val productionCapacity: Int,
    val researchLevel: Int,
    val contracts: List<String> = emptyList()
)

// ==================== PHASE 2: DIPLOMATIC FEATURES (continued) ====================

/**
 * Feature 96-120: Enhanced Diplomacy
 */

// Feature 96: Diplomatic Missions
@Serializable
data class DiplomaticMission(
    val id: String,
    val targetNationId: String,
    val type: MissionType,
    val staff: Int = 10,
    val budget: Int = 1000,
    val effectiveness: Int = 50,
    val yearsActive: Int = 0
)

enum class MissionType {
    EMBASSY, CONSULATE, TRADE_MISSION, CULTURAL_MISSION, INTELLIGENCE_POST
}

// Feature 98: Summit Meetings
@Serializable
data class SummitMeeting(
    val id: String,
    val name: String,
    val hostNationId: String,
    val attendeeNationIds: List<String>,
    val topics: List<String>,
    val year: Int,
    val outcome: SummitOutcome = SummitOutcome.NONE,
    val agreements: List<String> = emptyList()
)

enum class SummitOutcome {
    NONE, SUCCESS, PARTIAL_SUCCESS, FAILURE, BREAKDOWN
}

// Feature 104: Non-Aggression Pacts
@Serializable
data class NonAggressionPact(
    val id: String,
    val parties: List<String>,
    val yearSigned: Int,
    val duration: Int = -1,
    val clauses: List<String> = emptyList(),
    val violatedBy: String? = null
)

// Feature 106: Propaganda Abroad
@Serializable
data class ForeignPropaganda(
    val targetRegions: Map<String, Int> = emptyMap(),  // region -> intensity
    val budget: Int = 1000,
    val effectiveness: Int = 50,
    val methods: List<PropagandaMethod> = emptyList()
)

enum class PropagandaMethod {
    MEDIA, CULTURAL, ACADEMIC, COVERT, DIGITAL
}

// Feature 108: World Leader Summit
@Serializable
data class WorldSummit(
    val id: String,
    val name: String,
    val year: Int,
    val host: String,
    val attendees: List<String>,
    val agenda: List<String>,
    val resolutions: List<String>,
    val importance: Int  // 1-10
)

// Feature 112: Refugee Policy
@Serializable
data class RefugeePolicy(
    val acceptanceRate: Int = 30,  // 0-100%
    val integrationPrograms: Boolean = false,
    val quota: Int = 1000,
    val vettingLevel: Int = 50,
    val costPerRefugee: Int = 500
)

// Feature 113: Tourism Industry
@Serializable
data class TourismIndustry(
    val attractiveness: Int = 50,  // Based on culture, safety, etc.
    val visitorsPerYear: Int = 0,
    val revenue: Int = 0,
    val infrastructure: Int = 50,
    val marketingBudget: Int = 1000
)

// Feature 114: Cultural Diplomacy
@Serializable
data class CulturalDiplomacy(
    val culturalCenters: Int = 0,
    val exchangePrograms: Int = 0,
    val internationalBroadcasting: Boolean = false,
    val culturalHeritageSites: Int = 0,
    val softPower: Int = 50
)

// Feature 117: Border Disputes
@Serializable
data class BorderDispute(
    val id: String,
    val territory: String,
    val claimants: List<String>,
    val historicalClaims: Map<String, String> = emptyMap(),
    val currentTension: Int = 50,
    val disputedSince: Int
)

// Feature 118: Historical Grievances
@Serializable
data class HistoricalGrievance(
    val id: String,
    val description: String,
    val involvedNations: List<String>,
    val yearCreated: Int,
    val currentSignificance: Int,  // 0-100
    val resolutionPossible: Boolean = false
)

// Feature 120: International Prestige
@Serializable
data class InternationalPrestige(
    val rank: Int = 50,  // World ranking
    val score: Int = 50,
    val components: PrestigeComponents = PrestigeComponents()
)

@Serializable
data class PrestigeComponents(
    val economic: Int = 50,
    val military: Int = 50,
    val cultural: Int = 50,
    val diplomatic: Int = 50,
    val technological: Int = 50
)

// ==================== PHASE 2 GAME LOGIC EXTENSIONS ====================

object GameLogicPhase2 {
    
    /**
     * Process military turn
     */
    fun processMilitaryTurn(country: Country): Country {
        var updated = country
        
        // Process military budget
        val budget = MilitaryBudget(totalBudget = 5000)
        val newMilitary = updated.military
        
        // Equipment degrades over time
        val armyEquipment = (newMilitary.army.equipmentLevel - 1).coerceAtLeast(0)
        val navyEquipment = (newMilitary.navy.equipmentLevel - 1).coerceAtLeast(0)
        val airEquipment = (newMilitary.airForce.equipmentLevel - 1).coerceAtLeast(0)
        
        return updated.copy(
            military = newMilitary.copy(
                army = newMilitary.army.copy(equipmentLevel = armyEquipment),
                navy = newMilitary.navy.copy(equipmentLevel = navyEquipment),
                airForce = newMilitary.airForce.copy(equipmentLevel = airEquipment)
            )
        )
    }
    
    /**
     * Handle war declaration with full mechanics
     */
    fun declareWarWithJustification(
        country: Country, 
        targetId: String, 
        justification: WarJustification
    ): Country {
        val baseState = GameLogic.declareWar(country.toGameState(), targetId)
        
        // Apply justification bonuses/penalties
        val stabilityEffect = when (justification) {
            WarJustification.SELF_DEFENSE -> 0
            WarJustification.UN_APPROVED -> -10
            WarJustification.PREEMPTIVE -> -5
            WarJustification.IDEOLOGICAL -> -15
            WarJustification.ECONOMIC -> -20
            WarJustification.REVANCHIST -> -10
        }
        
        return baseState.country.copy(
            stats = baseState.country.stats.copy(
                stability = (baseState.country.stats.stability + stabilityEffect).coerceIn(0, 100)
            )
        )
    }
    
    private fun Country.toGameState(): GameState {
        return GameState(
            country = this,
            aiNations = emptyList(),
            globalMarket = GlobalMarket()
        )
    }
}

enum class WarJustification(
    val displayName: String,
    val diplomaticPenalty: Int,
    val stabilityBonus: Int
) {
    SELF_DEFENSE("Self Defense", 0, 10),
    UN_APPROVED("UN Approved", -20, 5),
    PREEMPTIVE("Preemptive Strike", -15, 0),
    IDEOLOGICAL("Ideological War", -30, -10),
    ECONOMIC("Resource War", -35, -15),
    REVANCHIST("Revanchism", -25, -5)
}

// ==================== EXTENDED COUNTRY MODEL ====================

/**
 * Country with Phase 2 features
 */
@Serializable
data class CountryV3(
    // All previous fields
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
    
    // Phase 1 Fields
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
    val nationalDebt: Int = 0,
    val creditRating: Int = 75,
    
    // Phase 2 NEW FIELDS
    val nationalDebtData: NationalDebt = NationalDebt(),
    val subsidies: List<Subsidy> = emptyList(),
    val stateAssets: List<StateAsset> = emptyList(),
    val bankingSystem: BankingSystem = BankingSystem(),
    val blackMarket: BlackMarket = BlackMarket(),
    val tradeAgreements: List<EnhancedTradeAgreement> = emptyList(),
    val draftSystem: DraftSystem = DraftSystem(),
    val militaryBudget: MilitaryBudget = MilitaryBudget(),
    val militaryBases: List<MilitaryBase> = emptyList(),
    val missileDefense: MissileDefense = MissileDefense(),
    val spaceProgram: SpaceProgram = SpaceProgram(),
    val cyberWarfare: CyberWarfare = CyberWarfare(),
    val privateMilitary: PrivateMilitary = PrivateMilitary(),
    val occupationZones: List<OccupationZone> = emptyList(),
    val resistanceMovements: List<ResistanceMovement> = emptyList(),
    val peaceTreaties: List<PeaceTreaty> = emptyList(),
    val militaryAcademies: List<MilitaryAcademy> = emptyList(),
    val specialForces: SpecialForces = SpecialForces(),
    val fortifications: List<Fortification> = emptyList(),
    val veteranCare: VeteranCare = VeteranCare(),
    val defenseIndustry: DefenseIndustry = DefenseIndustry(),
    val diplomaticMissions: List<DiplomaticMission> = emptyList(),
    val summits: List<SummitMeeting> = emptyList(),
    val nonAggressionPacts: List<NonAggressionPact> = emptyList(),
    val foreignPropaganda: ForeignPropaganda = ForeignPropaganda(),
    val worldSummits: List<WorldSummit> = emptyList(),
    val refugeePolicy: RefugeePolicy = RefugeePolicy(),
    val tourismIndustry: TourismIndustry = TourismIndustry(),
    val culturalDiplomacy: CulturalDiplomacy = CulturalDiplomacy(),
    val borderDisputes: List<BorderDispute> = emptyList(),
    val historicalGrievances: List<HistoricalGrievance> = emptyList(),
    val internationalPrestige: InternationalPrestige = InternationalPrestige()
)
