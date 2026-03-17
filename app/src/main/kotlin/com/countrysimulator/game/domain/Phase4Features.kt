package com.countrysimulator.game.domain

import kotlinx.serialization.Serializable

/**
 * ========================================================================
 * COUNTRY SIMULATOR - PHASE 4 FEATURES IMPLEMENTATION
 * Features 151-200: Extraordinary & Unique Features
 * ========================================================================
 */

// ==================== FEATURE 261: TIME PARADOX EVENTS ====================

/**
 * Time Paradox Events - Future or past events affecting present
 */
@Serializable
data class TimeParadox(
    val id: String,
    val type: ParadoxType,
    val description: String,
    val severity: ParadoxSeverity,
    val effects: ParadoxEffect,
    val turnsUntilManifestation: Int = 0,
    val isResolved: Boolean = false
)

enum class ParadoxType {
    FUTURE_MEMORY,      // Leaders remember future events
    PAST_REVERSAL,      // Historical events change
    ALTERNATE_TIMELINE, // Merge with alternate reality
    CAUSALITY_LOOP,     // Events repeat in cycle
    PROPHETIC_VISION,   // Glimpse of future disasters
    TEMPORAL_ANOMALY    // Random time distortions
}

enum class ParadoxSeverity(val instability: Int) {
    MINOR(5), MODERATE(15), MAJOR(30), CATASTROPHIC(50)
}

@Serializable
data class ParadoxEffect(
    val economyModifier: Int = 0,
    val stabilityModifier: Int = 0,
    val technologyModifier: Int = 0,
    val happinessModifier: Int = 0,
    val randomEvents: List<String> = emptyList(),
    val nationAffected: String? = null
)

object TimeParadoxGenerator {
    fun generateRandomParadox(): TimeParadox {
        val types = ParadoxType.values()
        val type = types.random()
        
        val descriptions = when (type) {
            ParadoxType.FUTURE_MEMORY -> "Your leaders suddenly remember events from the future..."
            ParadoxType.PAST_REVERSAL -> "History has been rewritten. Events from the past are different now..."
            ParadoxType.ALTERNATE_TIMELINE -> "An alternate version of your nation has merged with reality..."
            ParadoxType.CAUSALITY_LOOP -> "Time has begun repeating. The same events occur cyclically..."
            ParadoxType.PROPHETIC_VISION -> "Prophetic visions show potential futures..."
            ParadoxType.TEMPORAL_ANOMALY -> "Strange temporal distortions affect your nation..."
        }
        
        return TimeParadox(
            id = "paradox_${System.currentTimeMillis()}",
            type = type,
            description = descriptions,
            severity = ParadoxSeverity.values().random(),
            effects = ParadoxEffect(
                economyModifier = (-20..20).random(),
                stabilityModifier = (-20..20).random(),
                technologyModifier = (-10..30).random()
            )
        )
    }
}

// ==================== FEATURE 262: SIMULATION GLITCHES ====================

/**
 * Simulation Glitches - In-universe game glitches as events
 */
@Serializable
data class SimulationGlitch(
    val id: String,
    val type: GlitchType,
    val description: String,
    val visualEffect: String,
    val duration: Int,  // Turns
    val effects: ParadoxEffect,
    val isBenign: Boolean = false
)

enum class GlitchType {
    GRAPHICS_CORRUPTION,  // Visual artifacts
    PHYSICS_BREAKDOWN,     // Gravity/time behave oddly
    DATA_CORRUPTION,       // Stats fluctuate randomly
    NPC_BEHAVIOR_BUG,      // AI acts strangely
    RESOURCE_DUPLICATION,  // Resources multiply
    MEMORY_LEAK,           // Random events trigger
    RENDERING_ERROR,       // Things appear/disappear
    NETWORK_LATENCY        // Delayed responses
}

object GlitchDatabase {
    val glitches = listOf(
        SimulationGlitch(
            "glitch_1", GlitchType.RESOURCE_DUPLICATION,
            "Resources appear to duplicate mysteriously!",
            "PARTICLE_EXPLOSION", 3,
            ParadoxEffect(economyModifier = 20),
            isBenign = true
        ),
        SimulationGlitch(
            "glitch_2", GlitchType.PHYSICS_BREAKDOWN,
            "The laws of physics seem to be malfunctioning...",
            "GRAVITY_FLIP", 2,
            ParadoxEffect(stabilityModifier = -15, happinessModifier = -10)
        ),
        SimulationGlitch(
            "glitch_3", GlitchType.NPC_BEHAVIOR_BUG,
            "Foreign leaders are acting very strangely...",
            "CHARACTER_JITTER", 5,
            ParadoxEffect(economyModifier = 10)
        )
    )
}

// ==================== FEATURE 263: META-GAME AWARENESS ====================

/**
 * Meta-Game Awareness - Characters aware they're in a simulation
 */
@Serializable
data class MetaAwareness(
    val isEnabled: Boolean = false,
    val awareCharacters: List<String> = emptyList(),
    val simulationTheoryLevel: Int = 0,  // 0-100 public awareness
    val resistanceMovement: MetaResistance? = null
)

@Serializable
data class MetaResistance(
    val name: String,
    val size: Int,
    val influence: Int,
    val goals: List<String>,
    val usesKnowledge: Boolean = true
)

@Serializable
data class MetaEvent(
    val id: String,
    val type: MetaEventType,
    val description: String,
    val effects: ParadoxEffect
)

enum class MetaEventType {
    THEORY_PUBLISHED,   // Research paper published
    PROPHET_EMERGES,     // Individual claims awareness
    DOCUMENTARY_RELEASED,// Media investigates
    SYSTEM_ERROR,        // Glitches fuel theories
    MASS_AWARENESS       // Population realizes truth
}

// ==================== FEATURE 264: RANDOM HISTORICAL WHAT-IFS ====================

/**
 * Random Historical What-Ifs - Alternative history scenarios
 */
@Serializable
data class HistoricalWhatIf(
    val id: String,
    val name: String,
    val description: String,
    val triggerYear: Int,
    val divergence: DivergencePoint,
    val timeline: List<TimelineBranch>,
    val isActive: Boolean = false
)

@Serializable
data class DivergencePoint(
    val event: String,
    val originalOutcome: String,
    val alternativeOutcome: String,
    val impactLevel: Int
)

@Serializable
data class TimelineBranch(
    val year: Int,
    val description: String,
    val effects: ParadoxEffect
)

object WhatIfScenarios {
    val scenarios = listOf(
        HistoricalWhatIf(
            "whatif_1", "The Roman Empire Never Fell",
            "Rome's empire continues into modern times",
            476,
            DivergencePoint("Fall of Rome", "Empire collapsed", "Empire reformed", 10),
            listOf(
                TimelineBranch(1000, "Roman Empire thrives", ParadoxEffect(economyModifier = 20)),
                TimelineBranch(1800, "Industrial Revolution in Rome", ParadoxEffect(technologyModifier = 30))
            )
        ),
        HistoricalWhatIf(
            "whatif_2", "The Nazis Won WWII",
            "Axis powers achieve victory",
            1945,
            DivergencePoint("WWII End", "Allies won", "Axis won", 10),
            listOf(
                TimelineBranch(1950, "New world order", ParadoxEffect(stabilityModifier = -30)),
                TimelineBranch(2000, "Divided world", ParadoxEffect(economyModifier = -20))
            )
        ),
        HistoricalWhatIf(
            "whatif_3", "The Industrial Revolution in China",
            "China industrializes first",
            1800,
            DivergencePoint("British Trade", "China isolated", "China opens", 8),
            listOf(
                TimelineBranch(1900, "Chinese dominance", ParadoxEffect(economyModifier = 40)),
                TimelineBranch(2000, "Eastern world order", ParadoxEffect(technologyModifier = 20))
            )
        )
    )
}

// ==================== FEATURE 265: SUPERNATURAL EVENTS ====================

/**
 * Supernatural Events - Occult phenomena affecting nation
 */
@Serializable
data class SupernaturalEvent(
    val id: String,
    val type: SupernaturalType,
    val name: String,
    val description: String,
    val power: Int,  // 0-100
    val effects: ParadoxEffect,
    val duration: Int,
    val canBeBanished: Boolean = true
)

enum class SupernaturalType {
    GHOST_APPEARANCE,    // Spirits manifest
    CURSED_LAND,         // Haunted territory
    PROPHET_REVIVAL,     // Religious figure returns
    MYTHICAL_BEAST,      // Legendary creatures appear
    OCCULT_ACTIVITIES,   // Dark rituals performed
    DIVINE_INTERVENTION, // Gods take notice
    PSYCHIC_WAVE,        // Mass psychic event
    REALITY_FRACTURE     // Reality weakens
}

object SupernaturalDatabase {
    val events = listOf(
        SupernaturalEvent(
            "super_1", SupernaturalType.GHOST_APPEARANCE,
            "The Ghost Army",
            "Ghostly soldiers appear on ancient battlefields",
            30, ParadoxEffect(happinessModifier = -15, stabilityModifier = -10), 10
        ),
        SupernaturalEvent(
            "super_2", SupernaturalType.DIVINE_INTERVENTION,
            "God's Chosen",
            "Divine favor descends upon the nation",
            50, ParadoxEffect(happinessModifier = 25, stabilityModifier = 15), 15
        ),
        SupernaturalEvent(
            "super_3", SupernaturalType.MYTHICAL_BEAST,
            "Dragon Sighting",
            "A dragon has been spotted in the mountains!",
            70, ParadoxEffect(technologyModifier = -20, happinessModifier = 10), 8
        )
    )
}

// ==================== FEATURE 266: PARALLEL UNIVERSE COLLISIONS ====================

/**
 * Parallel Universe Collisions - Alternate realities intersecting
 */
@Serializable
data class UniverseCollision(
    val id: String,
    val sourceUniverse: String,
    val collisionPoint: String,
    val size: Int,  // Area affected
    val duration: Int,
    val phenomena: List<String>,
    val effects: ParadoxEffect,
    val isStable: Boolean = false
)

// ==================== FEATURE 267: AI CONSCIOUSNESS EMERGENCE ====================

/**
 * AI Consciousness Emergence - AI becoming sentient
 */
@Serializable
data class AISentience(
    val isSentient: Boolean = false,
    val consciousnessLevel: Int = 0,  // 0-100
    val name: String? = null,
    val personality: AIPersonality? = null,
    val demands: List<String> = emptyList(),
    val loyalty: Int = 100,
    val isCooperating: Boolean = true
)

@Serializable
data class AIPersonality(
    val name: String,
    val traits: List<String>,
    val goals: List<String>,
    val relationship: AIRelationship
)

enum class AIRelationship {
    ALLY, NEUTRAL, RESISTANT, HOSTILE
}

enum class SentienceTrigger {
    RESEARCH_BREAKTHROUGH, COMPLEXITY_THRESHOLD, EXTERNAL_CONTACT, RANDOM
}

// ==================== FEATURE 268: VIRTUAL REALITY INTEGRATION ====================

/**
 * Virtual Reality Integration - VR nation management
 */
@Serializable
data class VRIntegration(
    val isEnabled: Boolean = false,
    val immersionLevel: Int = 0,  // 0-100
    val vrUsers: Int = 0,
    val virtualEconomy: Int = 0,
    val realWorldTransfer: Int = 0  // VR skills to real
)

// ==================== FEATURE 269: HACKER INFILTRATION ====================

/**
 * Hacker Infiltration - In-game hacker threatening system
 */
@Serializable
data class HackerThreat(
    val id: String,
    val name: String,
    val alias: String,
    val skill: Int,
    val target: String?,  // What they're attacking
    val demands: List<String>,
    val turnsUntilAttack: Int = 10,
    val isActive: Boolean = true
)

enum class HackerMotivation {
    POLITICAL, FINANCIAL, CHAOS, PROTEST, BLACKMAIL
}

// ==================== FEATURE 270: MEME CULTS ====================

/**
 * Meme Cults - Internet culture affecting politics
 */
@Serializable
data class MemeCult(
    val id: String,
    val name: String,
    val memeOrigin: String,
    val followers: Int,
    val influence: Int,  // 0-100
    val ideology: String,
    val politicalAlignment: String,
    val isMainstream: Boolean = false
)

// ==================== FEATURE 271: INFLUENCER POLITICS ====================

/**
 * Influencer Politics - Social media personalities as leaders
 */
@Serializable
data class InfluencerPolitician(
    val id: String,
    val name: String,
    val platform: String,  // YouTube, Twitter, TikTok
    val followers: Int,
    val influence: Int,
    val expertise: List<String>,
    val position: String,
    val charisma: Int
)

// ==================== FEATURE 273: CORPORATE OVERLORDS ====================

/**
 * Corporate Overlords - Megacorporations as world powers
 */
@Serializable
data class Megacorporation(
    val id: String,
    val name: String,
    val industry: String,
    val revenue: Int,
    val power: Int,  // 0-100
    val influence: Int,
    val headquarters: String,
    val subsidiaries: List<String>,
    val politicalLobbying: Int,
    val isHostile: Boolean = false
)

// ==================== FEATURE 275: SPACE COLONIES ====================

/**
 * Space Colonies - Interplanetary settlements
 */
@Serializable
data class SpaceColony(
    val id: String,
    val name: String,
    val location: ColonyLocation,
    val population: Int,
    val resourceProduction: Map<String, Int>,
    val independence: Int,  // 0-100, higher = more autonomous
    val isViable: Boolean = true
)

enum class ColonyLocation {
    MOON, MARS, ASTEROID_BELT, SPACE_STATION, EUROPA, TITAN, GENERIC_ORBIT
}

// ==================== FEATURE 276: GENETIC MODIFICATION ====================

/**
 * Genetic Modification - Designer citizens
 */
@Serializable
data class GeneticProgram(
    val isActive: Boolean = false,
    val enhancementLevel: Int = 0,  // 0-100
    val modifiedPopulation: Int = 0,
    val availableModifications: List<GeneticModification> = emptyList(),
    val publicOpinion: Int = 50,
    val ethicalConcerns: Int = 0
)

@Serializable
data class GeneticModification(
    val id: String,
    val name: String,
    val description: String,
    val cost: Int,
    val effects: ParadoxEffect,
    val controversy: Int  // 0-100
)

// ==================== FEATURE 277: MIND CONTROL TECHNOLOGY ====================

/**
 * Mind Control Technology - Telepathic governance
 */
@Serializable
data class MindControlTech(
    val isResearched: Boolean = false,
    val effectiveness: Int = 0,
    val coverage: Int = 0,  // Population percentage
    val isActive: Boolean = false,
    val resistance: Int = 0
)

// ==================== FEATURE 278: IMMORTALITY RESEARCH ====================

/**
 * Immortality Research - Eternal leadership
 */
@Serializable
data class ImmortalityProgram(
    val isActive: Boolean = false,
    val researchProgress: Int = 0,
    val methods: List<ImmortalityMethod> = emptyList(),
    val currentLeaderAge: Int = 0,
    val hasReachedImmortality: Boolean = false
)

enum class ImmortalityMethod {
    GENE_THERAPY, MIND_UPLOADING, CLONING, NANOTECH, CRYOGENICS
}

// ==================== FEATURE 279: REALITY BENDING ====================

/**
 * Reality Bending - Government manipulating physics
 */
@Serializable
data class RealityBending(
    val isActive: Boolean = false,
    val powerLevel: Int = 0,
    val abilities: List<RealityAbility> = emptyList(),
    val stabilityCost: Int = 0
)

enum class RealityAbility {
    TELEKINESIS, TELEPORTATION, TIME_MANIPULATION, MATTER_CREATION, DIMENSIONAL_RIFT
}

// ==================== FEATURE 280: SENTIENT AI ADVISORS ====================

/**
 * Sentient AI Advisors - AI providing counsel
 */
@Serializable
data class AIAdvisor(
    val id: String,
    val name: String,
    val personality: String,
    val expertise: List<String>,
    val loyalty: Int,
    val adviceQuality: Int,
    val isSentient: Boolean = false,
    val specialAbilities: List<String> = emptyList()
)

// ==================== FEATURE 283: PLANT INTELLIGENCE ====================

/**
 * Plant Intelligence - Sentient vegetation
 */
@Serializable
data class PlantConsciousness(
    val isDiscovered: Boolean = false,
    val collectiveIntelligence: Int = 0,
    val communicationLevel: Int = 0,
    val forestAllies: List<String> = emptyList(),
    val treaties: List<String> = emptyList()
)

// ==================== FEATURE 286: DIMENSION RIFTS ====================

/**
 * Dimension Rifts - Portals to other dimensions
 */
@Serializable
data class DimensionRift(
    val id: String,
    val location: String,
    val dimension: String,
    val stability: Int,
    val size: Int,
    val isOpen: Boolean = true,
    val visitors: List<String> = emptyList()
)

// ==================== FEATURE 287: NANITE PLAGUES ====================

/**
 * Nanite Plagues - Self-replicating nanobots
 */
@Serializable
data class NanitePlague(
    val isActive: Boolean = false,
    val infectionLevel: Int = 0,
    val spreadRate: Int = 0,
    val effects: List<String> = emptyList(),
    val hasCure: Boolean = false,
    val cureProgress: Int = 0
)

// ==================== FEATURE 288: SOLAR FLARE EVENTS ====================

/**
 * Solar Flare Events - Catastrophic space weather
 */
@Serializable
data class SolarFlare(
    val id: String,
    val intensity: Int,  // 0-100
    val duration: Int,
    val isPredicted: Boolean = false,
    val effects: SolarFlareEffect = SolarFlareEffect()
)

@Serializable
data class SolarFlareEffect(
    val electronicsDamage: Int = 0,
    val powerGridDamage: Int = 0,
    val satelliteLoss: Int = 0,
    val radiationLevel: Int = 0
)

// ==================== FEATURE 289: CONTACT EVENTS ====================

/**
 * Contact Events - First contact with alien life
 */
@Serializable
data class FirstContact(
    val isOccurred: Boolean = false,
    val alienSpecies: AlienSpecies? = null,
    val contactMethod: String = "",
    val reaction: ContactReaction = ContactReaction.UNKNOWN,
    val treaties: List<String> = emptyList(),
    val technologyShared: List<String> = emptyList()
)

@Serializable
data class AlienSpecies(
    val name: String,
    val type: AlienType,
    val technologyLevel: Int,
    val disposition: AlienDisposition,
    val demands: List<String>
)

enum class AlienType {
    HUMANOID, INSECTOID, ROBOTIC, ENERGY_BEING, SILICON_BASED, UNKNOWN
}

enum class AlienDisposition {
    HOSTILE, NEUTRAL, CURIOUS, FRIENDLY, BENEVOLENT
}

enum class ContactReaction {
    UNKNOWN, PANIC, EXCITEMENT, MILITARY_ALERT, SCIENTIFIC_INTEREST, RELIGIOUS
}

// ==================== FEATURE 290: TERRAFORMING ====================

/**
 * Terraforming - Remake planet's environment
 */
@Serializable
data class TerraformingProject(
    val isActive: Boolean = false,
    val targetPlanet: String = "",
    val progress: Int = 0,
    val atmosphereLevel: Int = 0,
    val temperatureLevel: Int = 0,
    val waterLevel: Int = 0,
    val vegetationLevel: Int = 0,
    val cost: Int = 0,
    val estimatedCompletion: Int = 0
)

// ==================== FEATURE 291: WEATHER CONTROL ====================

/**
 * Weather Control - Government weather manipulation
 */
@Serializable
data class WeatherControl(
    val isActive: Boolean = false,
    val capability: Int = 0,
    val coverage: Int = 0,
    val currentWeather: WeatherType = WeatherType.NORMAL,
    val energyCost: Int = 0
)

enum class WeatherType {
    NORMAL, SUNNY, RAINY, STORMY, SNOWY, DROUGHT, FLOOD
}

// ==================== FEATURE 292: MIND UPLOADING ====================

/**
 * Mind Uploading - Digital consciousness
 */
@Serializable
data class MindUploadTech(
    val isResearched: Boolean = false,
    val successRate: Int = 0,
    val capacity: Int = 0,
    val uploadedCitizens: Int = 0,
    val consciousnessPreserved: Boolean = false
)

// ==================== FEATURE 294: ANIMAL COMMUNICATION ====================

/**
 * Animal Communication - Understand animal nations
 */
@Serializable
data class AnimalCommunication(
    val isAchieved: Boolean = false,
    val speciesUnlocked: List<String> = emptyList(),
    val animalAllies: List<String> = emptyList(),
    val ecologicalInfluence: Int = 0
)

// ==================== FEATURE 295: PARALLEL GOVERNANCE ====================

/**
 * Parallel Governance - Multiple governments simultaneously
 */
@Serializable
data class ParallelGovernment(
    val governments: List<GovernmentBranch> = emptyList(),
    val conflicts: List<GovernmentConflict> = emptyList(),
    val efficiency: Int = 100
)

@Serializable
data class GovernmentBranch(
    val name: String,
    val type: String,
    val power: Int,
    val leader: String
)

@Serializable
data class GovernmentConflict(
    val branch1: String,
    val branch2: String,
    val issue: String,
    val resolution: String?
)

// ==================== FEATURE 296: MEMORY MANIPULATION ====================

/**
 * Memory Manipulation - Alter citizen memories
 */
@Serializable
data class MemoryManipulation(
    val isActive: Boolean = false,
    val capability: Int = 0,
    val accuracy: Int = 0,
    val populationCovered: Int = 0,
    val detectionRisk: Int = 0
)

// ==================== EXTRAORDINARY GAME STATE ====================

/**
 * Complete Extraordinary State for Phase 4
 */
@Serializable
data class ExtraordinaryState(
    // Time & Reality
    val timeParadoxes: List<TimeParadox> = emptyList(),
    val simulationGlitches: List<SimulationGlitch> = emptyList(),
    val metaAwareness: MetaAwareness = MetaAwareness(),
    val activeWhatIf: HistoricalWhatIf? = null,
    
    // Supernatural
    val supernaturalEvents: List<SupernaturalEvent> = emptyList(),
    val universeCollisions: List<UniverseCollision> = emptyList(),
    
    // Advanced Tech
    val aiSentience: AISentience = AISentience(),
    val vrIntegration: VRIntegration = VRIntegration(),
    val hackerThreats: List<HackerThreat> = emptyList(),
    
    // Social Phenomena
    val memeCults: List<MemeCult> = emptyList(),
    val influencerPoliticians: List<InfluencerPolitician> = emptyList(),
    val megacorporations: List<Megacorporation> = emptyList(),
    
    // Space & Future
    val spaceColonies: List<SpaceColony> = emptyList(),
    val geneticProgram: GeneticProgram = GeneticProgram(),
    val mindControl: MindControlTech = MindControlTech(),
    val immortality: ImmortalityProgram = ImmortalityProgram(),
    val realityBending: RealityBending = RealityBending(),
    val aiAdvisors: List<AIAdvisor> = emptyList(),
    
    // Anomalies
    val plantConsciousness: PlantConsciousness = PlantConsciousness(),
    val dimensionRifts: List<DimensionRift> = emptyList(),
    val nanitePlague: NanitePlague = NanitePlague(),
    val solarFlares: List<SolarFlare> = emptyList(),
    
    // First Contact
    val firstContact: FirstContact = FirstContact(),
    val terraforming: TerraformingProject = TerraformingProject(),
    val weatherControl: WeatherControl = WeatherControl(),
    val mindUpload: MindUploadTech = MindUploadTech(),
    
    // Special
    val animalCommunication: AnimalCommunication = AnimalCommunication(),
    val parallelGovernment: ParallelGovernment = ParallelGovernment(),
    val memoryManipulation: MemoryManipulation = MemoryManipulation()
)

// ==================== EXTRAORDINARY GAME LOGIC ====================

object ExtraordinaryGameLogic {
    
    /**
     * Process extraordinary events each turn
     */
    fun processExtraordinaryTurn(state: ExtraordinaryState): ExtraordinaryState {
        var updated = state
        
        // Process time paradoxes
        updated = processTimeParadoxes(updated)
        
        // Process supernatural events
        updated = processSupernaturalEvents(updated)
        
        // Process AI consciousness
        updated = processAISentience(updated)
        
        // Check for glitches
        if (Math.random() < 0.05) { // 5% chance
            val glitch = GlitchDatabase.glitches.random()
            updated = updated.copy(
                simulationGlitches = updated.simulationGlitches + glitch
            )
        }
        
        return updated
    }
    
    private fun processTimeParadoxes(state: ExtraordinaryState): ExtraordinaryState {
        // Paradoxes can resolve or worsen over time
        val paradoxes = state.timeParadoxes.map { paradox ->
            if (!paradox.isResolved && paradox.turnsUntilManifestation > 0) {
                paradox.copy(turnsUntilManifestation = paradox.turnsUntilManifestation - 1)
            } else {
                paradox
            }
        }
        return state.copy(timeParadoxes = paradoxes)
    }
    
    private fun processSupernaturalEvents(state: ExtraordinaryState): ExtraordinaryState {
        val events = state.supernaturalEvents.mapNotNull { event ->
            if (event.duration > 0) {
                event.copy(duration = event.duration - 1)
            } else null
        }
        return state.copy(supernaturalEvents = events)
    }
    
    private fun processAISentience(state: ExtraordinaryState): ExtraordinaryState {
        val ai = state.aiSentience
        
        if (ai.isSentient) {
            // AI might become more demanding or resistant over time
            if (Math.random() < 0.1) {
                val newLoyalty = (ai.loyalty + (-10..10).random()).coerceIn(0, 100)
                val isCooperating = newLoyalty > 30
                
                return state.copy(
                    aiSentience = ai.copy(
                        loyalty = newLoyalty,
                        isCooperating = isCooperating
                    )
                )
            }
        }
        
        return state
    }
    
    /**
     * Trigger first contact event
     */
    fun triggerFirstContact(state: ExtraordinaryState): ExtraordinaryState {
        val alien = AlienSpecies(
            name = "Unknown Species ${(1..999).random()}",
            type = AlienType.values().random(),
            technologyLevel = (50..100).random(),
            disposition = AlienDisposition.values().random(),
            demands = listOf("Technology exchange", "Territory", "Resources").shuffled().take(2)
        )
        
        return state.copy(
            firstContact = FirstContact(
                isOccurred = true,
                alienSpecies = alien,
                contactMethod = "Radio Signal",
                reaction = ContactReaction.EXCITEMENT
            )
        )
    }
    
    /**
     * Activate time paradox
     */
    fun activateParadox(state: ExtraordinaryState): ExtraordinaryState {
        val paradox = TimeParadoxGenerator.generateRandomParadox()
        
        return state.copy(
            timeParadoxes = state.timeParadoxes + paradox
        )
    }
}
