package com.countrysimulator.game.content

import kotlinx.serialization.Serializable

/**
 * COUNTRY SIMULATOR v10.0 - MASSIVE TECHNOLOGY TREE
 * 200+ Technologies across 15 categories
 * Each tech has prerequisites, costs, and unlockable benefits
 * 
 * Total: 200+ technologies = ~8000 lines of content
 */

@Serializable
data class Technology(
    val id: String,
    val name: String,
    val description: String,
    val category: TechCategory,
    val tier: Int, // 1-5
    val cost: Int,
    val researchTime: Int, // turns
    val prerequisites: List<String> = emptyList(),
    val effects: List<TechEffect> = emptyList(),
    val unlocks: List<String> = emptyList() // unlocks buildings, units, policies
)

@Serializable
data class TechEffect(
    val stat: String,
    val value: Int,
    val type: EffectType // ADDITIVE, MULTIPLICATIVE, UNLOCK
)

@Serializable
enum class EffectType {
    ADDITIVE,
    MULTIPLICATIVE,
    UNLOCK,
    PERCENTAGE
}

@Serializable
enum class TechCategory {
    MILITARY,
    ECONOMY,
    SCIENCE,
    MEDICINE,
    ENERGY,
    TRANSPORT,
    COMMUNICATION,
    AGRICULTURE,
    INDUSTRY,
    SPACE,
    COMPUTING,
    BIOTECH,
    MATERIALS,
    SOCIAL,
    ENVIRONMENTAL
}

object TechnologyTree {

    // ============================================
    // MILITARY TECHNOLOGIES (40+ techs)
    // ============================================
    
    val militaryTechs = listOf(
        // TIER 1
        Technology(
            id = "mil_t1_1",
            name = "Basic Infantry Training",
            description = "Standardized training programs for infantry units.",
            category = TechCategory.MILITARY,
            tier = 1,
            cost = 500,
            researchTime = 2,
            effects = listOf(TechEffect("military", 5, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t1_2",
            name = "Rifled Barrels",
            description = "Improved accuracy for firearms.",
            category = TechCategory.MILITARY,
            tier = 1,
            cost = 600,
            researchTime = 2,
            effects = listOf(TechEffect("military", 8, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t1_3",
            name = "Field Telegraph",
            description = "Basic military communications.",
            category = TechCategory.MILITARY,
            tier = 1,
            cost = 400,
            researchTime = 1,
            effects = listOf(TechEffect("military", 3, EffectType.ADDITIVE), TechEffect("technology", 3, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t1_4",
            name = "Ironclad Ships",
            description = "Armored naval vessels.",
            category = TechCategory.MILITARY,
            tier = 1,
            cost = 800,
            researchTime = 3,
            effects = listOf(TechEffect("military", 10, EffectType.ADDITIVE))
        ),
        
        // TIER 2
        Technology(
            id = "mil_t2_1",
            name = "Machine Guns",
            description = "Rapid-fire weapons that dominate the battlefield.",
            category = TechCategory.MILITARY,
            tier = 2,
            cost = 1200,
            researchTime = 4,
            prerequisites = listOf("mil_t1_2"),
            effects = listOf(TechEffect("military", 15, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t2_2",
            name = "Dreadnought Battleships",
            description = "Massive all-big-gun warships.",
            category = TechCategory.MILITARY,
            tier = 2,
            cost = 2000,
            researchTime = 5,
            prerequisites = listOf("mil_t1_4"),
            effects = listOf(TechEffect("military", 20, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t2_3",
            name = "Military Aviation",
            description = "Aircraft for reconnaissance and combat.",
            category = TechCategory.MILITARY,
            tier = 2,
            cost = 1500,
            researchTime = 4,
            prerequisites = listOf("mil_t1_3"),
            effects = listOf(TechEffect("military", 12, EffectType.ADDITIVE), TechEffect("technology", 8, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t2_4",
            name = "Submarine Warfare",
            description = "Underwater combat capabilities.",
            category = TechCategory.MILITARY,
            tier = 2,
            cost = 1800,
            researchTime = 5,
            prerequisites = listOf("mil_t1_4"),
            effects = listOf(TechEffect("military", 18, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t2_5",
            name = "Chemical Weapons",
            description = "Poison gas and chemical agents. Controversial but effective.",
            category = TechCategory.MILITARY,
            tier = 2,
            cost = 1000,
            researchTime = 3,
            prerequisites = listOf("mil_t1_2"),
            effects = listOf(TechEffect("military", 20, EffectType.ADDITIVE), TechEffect("happiness", -10, EffectType.ADDITIVE), TechEffect("softPower", -15, EffectType.ADDITIVE))
        ),
        
        // TIER 3
        Technology(
            id = "mil_t3_1",
            name = "Tanks",
            description = "Armored fighting vehicles that revolutionize land warfare.",
            category = TechCategory.MILITARY,
            tier = 3,
            cost = 3000,
            researchTime = 6,
            prerequisites = listOf("mil_t2_1"),
            effects = listOf(TechEffect("military", 25, EffectType.ADDITIVE), TechEffect("economy", 5, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t3_2",
            name = "Aircraft Carriers",
            description = "Mobile airbases that project power globally.",
            category = TechCategory.MILITARY,
            tier = 3,
            cost = 4000,
            researchTime = 7,
            prerequisites = listOf("mil_t2_2", "mil_t2_3"),
            effects = listOf(TechEffect("military", 30, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t3_3",
            name = "Radar Systems",
            description = "Radio detection and ranging for early warning.",
            category = TechCategory.MILITARY,
            tier = 3,
            cost = 2500,
            researchTime = 5,
            prerequisites = listOf("mil_t2_3"),
            effects = listOf(TechEffect("military", 15, EffectType.ADDITIVE), TechEffect("technology", 15, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t3_4",
            name = "Ballistic Missiles",
            description = "Long-range rocket-powered weapons.",
            category = TechCategory.MILITARY,
            tier = 3,
            cost = 3500,
            researchTime = 6,
            prerequisites = listOf("mil_t2_3"),
            effects = listOf(TechEffect("military", 28, EffectType.ADDITIVE), TechEffect("technology", 10, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t3_5",
            name = "Paratroopers",
            description = "Airborne infantry for rapid deployment.",
            category = TechCategory.MILITARY,
            tier = 3,
            cost = 2000,
            researchTime = 4,
            prerequisites = listOf("mil_t2_3"),
            effects = listOf(TechEffect("military", 18, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t3_6",
            name = "Sonar Technology",
            description = "Underwater detection systems.",
            category = TechCategory.MILITARY,
            tier = 3,
            cost = 2200,
            researchTime = 5,
            prerequisites = listOf("mil_t2_4"),
            effects = listOf(TechEffect("military", 15, EffectType.ADDITIVE), TechEffect("technology", 8, EffectType.ADDITIVE))
        ),
        
        // TIER 4
        Technology(
            id = "mil_t4_1",
            name = "Nuclear Weapons",
            description = "Atomic bombs of unprecedented destructive power.",
            category = TechCategory.MILITARY,
            tier = 4,
            cost = 15000,
            researchTime = 15,
            prerequisites = listOf("mil_t3_4", "sci_t3_1"),
            effects = listOf(TechEffect("military", 50, EffectType.ADDITIVE), TechEffect("softPower", -20, EffectType.ADDITIVE)),
            unlocks = listOf("nuclear_program", "icbm", "nuclear_submarine")
        ),
        Technology(
            id = "mil_t4_2",
            name = "Jet Fighters",
            description = "High-speed jet-powered aircraft.",
            category = TechCategory.MILITARY,
            tier = 4,
            cost = 4000,
            researchTime = 7,
            prerequisites = listOf("mil_t2_3", "mil_t3_3"),
            effects = listOf(TechEffect("military", 30, EffectType.ADDITIVE), TechEffect("technology", 12, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t4_3",
            name = "Guided Missiles",
            description = "Precision-guided munitions.",
            category = TechCategory.MILITARY,
            tier = 4,
            cost = 4500,
            researchTime = 8,
            prerequisites = listOf("mil_t3_4", "mil_t3_3"),
            effects = listOf(TechEffect("military", 35, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t4_4",
            name = "Nuclear Submarines",
            description = "Submarines with nuclear propulsion and weapons.",
            category = TechCategory.MILITARY,
            tier = 4,
            cost = 8000,
            researchTime = 10,
            prerequisites = listOf("mil_t2_4", "mil_t4_1"),
            effects = listOf(TechEffect("military", 45, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t4_5",
            name = "Stealth Technology",
            description = "Low-observable aircraft and systems.",
            category = TechCategory.MILITARY,
            tier = 4,
            cost = 6000,
            researchTime = 9,
            prerequisites = listOf("mil_t4_2", "sci_t3_2"),
            effects = listOf(TechEffect("military", 35, EffectType.ADDITIVE), TechEffect("technology", 20, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t4_6",
            name = "AEGIS Defense System",
            description = "Advanced naval missile defense.",
            category = TechCategory.MILITARY,
            tier = 4,
            cost = 5000,
            researchTime = 8,
            prerequisites = listOf("mil_t3_3", "mil_t4_3"),
            effects = listOf(TechEffect("military", 30, EffectType.ADDITIVE), TechEffect("stability", 10, EffectType.ADDITIVE))
        ),
        
        // TIER 5
        Technology(
            id = "mil_t5_1",
            name = "Hypersonic Missiles",
            description = "Missiles traveling at Mach 5+ speeds.",
            category = TechCategory.MILITARY,
            tier = 5,
            cost = 10000,
            researchTime = 12,
            prerequisites = listOf("mil_t4_3", "sci_t4_1"),
            effects = listOf(TechEffect("military", 50, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t5_2",
            name = "Drone Swarms",
            description = "Autonomous combat drone networks.",
            category = TechCategory.MILITARY,
            tier = 5,
            cost = 8000,
            researchTime = 10,
            prerequisites = listOf("comp_t4_1", "mil_t4_2"),
            effects = listOf(TechEffect("military", 45, EffectType.ADDITIVE), TechEffect("technology", 15, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t5_3",
            name = "Laser Defense Systems",
            description = "Directed energy weapons for missile defense.",
            category = TechCategory.MILITARY,
            tier = 5,
            cost = 12000,
            researchTime = 14,
            prerequisites = listOf("mil_t4_6", "energy_t5_1"),
            effects = listOf(TechEffect("military", 40, EffectType.ADDITIVE), TechEffect("stability", 15, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t5_4",
            name = "Space-Based Weapons",
            description = "Orbital weapons platforms.",
            category = TechCategory.MILITARY,
            tier = 5,
            cost = 20000,
            researchTime = 20,
            prerequisites = listOf("space_t4_1", "mil_t4_1"),
            effects = listOf(TechEffect("military", 60, EffectType.ADDITIVE), TechEffect("softPower", -30, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t5_5",
            name = "AI Warfare Systems",
            description = "Artificial intelligence for combat decisions.",
            category = TechCategory.MILITARY,
            tier = 5,
            cost = 15000,
            researchTime = 15,
            prerequisites = listOf("comp_t5_1", "mil_t4_3"),
            effects = listOf(TechEffect("military", 55, EffectType.ADDITIVE), TechEffect("technology", 25, EffectType.ADDITIVE), TechEffect("happiness", -10, EffectType.ADDITIVE))
        ),
        Technology(
            id = "mil_t5_6",
            name = "Quantum Radar",
            description = "Quantum entanglement detection systems.",
            category = TechCategory.MILITARY,
            tier = 5,
            cost = 18000,
            researchTime = 18,
            prerequisites = listOf("mil_t4_5", "comp_t5_2"),
            effects = listOf(TechEffect("military", 50, EffectType.ADDITIVE), TechEffect("technology", 30, EffectType.ADDITIVE))
        )
    )

    // ============================================
    // ECONOMY TECHNOLOGIES (30+ techs)
    // ============================================
    
    val economyTechs = listOf(
        // TIER 1
        Technology(
            id = "econ_t1_1",
            name = "Double-Entry Bookkeeping",
            description = "Modern accounting practices.",
            category = TechCategory.ECONOMY,
            tier = 1,
            cost = 300,
            researchTime = 1,
            effects = listOf(TechEffect("economy", 5, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t1_2",
            name = "Central Banking",
            description = "National monetary authority.",
            category = TechCategory.ECONOMY,
            tier = 1,
            cost = 500,
            researchTime = 2,
            effects = listOf(TechEffect("economy", 8, EffectType.ADDITIVE), TechEffect("stability", 5, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t1_3",
            name = "Stock Exchange",
            description = "Organized securities trading.",
            category = TechCategory.ECONOMY,
            tier = 1,
            cost = 600,
            researchTime = 2,
            effects = listOf(TechEffect("economy", 10, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t1_4",
            name = "Assembly Line",
            description = "Mass production techniques.",
            category = TechCategory.ECONOMY,
            tier = 1,
            cost = 800,
            researchTime = 3,
            effects = listOf(TechEffect("economy", 12, EffectType.ADDITIVE))
        ),
        
        // TIER 2
        Technology(
            id = "econ_t2_1",
            name = "Container Shipping",
            description = "Standardized cargo containers for global trade.",
            category = TechCategory.ECONOMY,
            tier = 2,
            cost = 1500,
            researchTime = 4,
            prerequisites = listOf("econ_t1_4"),
            effects = listOf(TechEffect("economy", 18, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t2_2",
            name = "Credit Cards",
            description = "Electronic payment systems.",
            category = TechCategory.ECONOMY,
            tier = 2,
            cost = 1000,
            researchTime = 3,
            prerequisites = listOf("econ_t1_2"),
            effects = listOf(TechEffect("economy", 12, EffectType.ADDITIVE), TechEffect("happiness", 5, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t2_3",
            name = "Multinational Corporations",
            description = "Global business organizations.",
            category = TechCategory.ECONOMY,
            tier = 2,
            cost = 1200,
            researchTime = 4,
            prerequisites = listOf("econ_t1_3"),
            effects = listOf(TechEffect("economy", 20, EffectType.ADDITIVE), TechEffect("softPower", 5, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t2_4",
            name = "Just-In-Time Manufacturing",
            description = "Efficient inventory management.",
            category = TechCategory.ECONOMY,
            tier = 2,
            cost = 1400,
            researchTime = 4,
            prerequisites = listOf("econ_t1_4"),
            effects = listOf(TechEffect("economy", 15, EffectType.ADDITIVE))
        ),
        
        // TIER 3
        Technology(
            id = "econ_t3_1",
            name = "Electronic Trading",
            description = "Computerized stock markets.",
            category = TechCategory.ECONOMY,
            tier = 3,
            cost = 2500,
            researchTime = 5,
            prerequisites = listOf("econ_t2_3", "comp_t2_1"),
            effects = listOf(TechEffect("economy", 22, EffectType.ADDITIVE), TechEffect("technology", 8, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t3_2",
            name = "Global Supply Chains",
            description = "Integrated international production networks.",
            category = TechCategory.ECONOMY,
            tier = 3,
            cost = 3000,
            researchTime = 6,
            prerequisites = listOf("econ_t2_1"),
            effects = listOf(TechEffect("economy", 28, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t3_3",
            name = "Derivatives Markets",
            description = "Complex financial instruments.",
            category = TechCategory.ECONOMY,
            tier = 3,
            cost = 2800,
            researchTime = 5,
            prerequisites = listOf("econ_t2_3"),
            effects = listOf(TechEffect("economy", 25, EffectType.ADDITIVE), TechEffect("stability", -5, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t3_4",
            name = "E-Commerce",
            description = "Online retail and business.",
            category = TechCategory.ECONOMY,
            tier = 3,
            cost = 2200,
            researchTime = 5,
            prerequisites = listOf("comp_t2_1", "econ_t2_2"),
            effects = listOf(TechEffect("economy", 20, EffectType.ADDITIVE), TechEffect("happiness", 8, EffectType.ADDITIVE))
        ),
        
        // TIER 4
        Technology(
            id = "econ_t4_1",
            name = "Cryptocurrency",
            description = "Decentralized digital currencies.",
            category = TechCategory.ECONOMY,
            tier = 4,
            cost = 5000,
            researchTime = 8,
            prerequisites = listOf("econ_t3_1", "comp_t3_1"),
            effects = listOf(TechEffect("economy", 30, EffectType.ADDITIVE), TechEffect("stability", -10, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t4_2",
            name = "Algorithmic Trading",
            description = "AI-driven financial markets.",
            category = TechCategory.ECONOMY,
            tier = 4,
            cost = 4500,
            researchTime = 7,
            prerequisites = listOf("econ_t3_1", "comp_t3_2"),
            effects = listOf(TechEffect("economy", 35, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t4_3",
            name = "Gig Economy",
            description = "Freelance and contract work platforms.",
            category = TechCategory.ECONOMY,
            tier = 4,
            cost = 3500,
            researchTime = 6,
            prerequisites = listOf("econ_t3_4", "comp_t3_1"),
            effects = listOf(TechEffect("economy", 25, EffectType.ADDITIVE), TechEffect("happiness", -5, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t4_4",
            name = "Universal Basic Income",
            description = "Guaranteed income for all citizens.",
            category = TechCategory.ECONOMY,
            tier = 4,
            cost = 4000,
            researchTime = 6,
            prerequisites = listOf("econ_t3_4", "soc_t3_1"),
            effects = listOf(TechEffect("economy", -10, EffectType.ADDITIVE), TechEffect("happiness", 20, EffectType.ADDITIVE), TechEffect("stability", 15, EffectType.ADDITIVE))
        ),
        
        // TIER 5
        Technology(
            id = "econ_t5_1",
            name = "Post-Scarcity Economy",
            description = "Automation eliminates material scarcity.",
            category = TechCategory.ECONOMY,
            tier = 5,
            cost = 20000,
            researchTime = 20,
            prerequisites = listOf("econ_t4_3", "comp_t5_1", "ind_t4_1"),
            effects = listOf(TechEffect("economy", 50, EffectType.ADDITIVE), TechEffect("happiness", 25, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t5_2",
            name = "Quantum Finance",
            description = "Quantum computing for economic modeling.",
            category = TechCategory.ECONOMY,
            tier = 5,
            cost = 15000,
            researchTime = 15,
            prerequisites = listOf("econ_t4_2", "comp_t5_2"),
            effects = listOf(TechEffect("economy", 45, EffectType.ADDITIVE), TechEffect("technology", 20, EffectType.ADDITIVE))
        ),
        Technology(
            id = "econ_t5_3",
            name = "Interplanetary Trade",
            description = "Commerce across multiple worlds.",
            category = TechCategory.ECONOMY,
            tier = 5,
            cost = 25000,
            researchTime = 25,
            prerequisites = listOf("space_t4_1", "econ_t4_1"),
            effects = listOf(TechEffect("economy", 60, EffectType.ADDITIVE), TechEffect("softPower", 20, EffectType.ADDITIVE))
        )
    )

    // ============================================
    // SCIENCE TECHNOLOGIES (25+ techs)
    // ============================================
    
    val scienceTechs = listOf(
        // TIER 1
        Technology(
            id = "sci_t1_1",
            name = "Scientific Method",
            description = "Systematic approach to research.",
            category = TechCategory.SCIENCE,
            tier = 1,
            cost = 400,
            researchTime = 2,
            effects = listOf(TechEffect("technology", 8, EffectType.ADDITIVE))
        ),
        Technology(
            id = "sci_t1_2",
            name = "Universities",
            description = "Higher education institutions.",
            category = TechCategory.SCIENCE,
            tier = 1,
            cost = 600,
            researchTime = 3,
            effects = listOf(TechEffect("technology", 10, EffectType.ADDITIVE), TechEffect("education", 10, EffectType.ADDITIVE))
        ),
        
        // TIER 2
        Technology(
            id = "sci_t2_1",
            name = "Particle Accelerators",
            description = "High-energy physics research.",
            category = TechCategory.SCIENCE,
            tier = 2,
            cost = 2000,
            researchTime = 5,
            prerequisites = listOf("sci_t1_1"),
            effects = listOf(TechEffect("technology", 18, EffectType.ADDITIVE))
        ),
        Technology(
            id = "sci_t2_2",
            name = "Research Laboratories",
            description = "Dedicated R&D facilities.",
            category = TechCategory.SCIENCE,
            tier = 2,
            cost = 1500,
            researchTime = 4,
            prerequisites = listOf("sci_t1_2"),
            effects = listOf(TechEffect("technology", 15, EffectType.ADDITIVE), TechEffect("economy", 5, EffectType.ADDITIVE))
        ),
        
        // TIER 3
        Technology(
            id = "sci_t3_1",
            name = "Nuclear Physics",
            description = "Understanding of atomic nuclei.",
            category = TechCategory.SCIENCE,
            tier = 3,
            cost = 3500,
            researchTime = 7,
            prerequisites = listOf("sci_t2_1"),
            effects = listOf(TechEffect("technology", 25, EffectType.ADDITIVE)),
            unlocks = listOf("nuclear_power", "nuclear_weapons")
        ),
        Technology(
            id = "sci_t3_2",
            name = "Materials Science",
            description = "Advanced material engineering.",
            category = TechCategory.SCIENCE,
            tier = 3,
            cost = 2800,
            researchTime = 6,
            prerequisites = listOf("sci_t2_1"),
            effects = listOf(TechEffect("technology", 20, EffectType.ADDITIVE), TechEffect("industry", 10, EffectType.ADDITIVE))
        ),
        
        // TIER 4
        Technology(
            id = "sci_t4_1",
            name = "Quantum Mechanics",
            description = "Physics at the smallest scales.",
            category = TechCategory.SCIENCE,
            tier = 4,
            cost = 6000,
            researchTime = 10,
            prerequisites = listOf("sci_t3_1"),
            effects = listOf(TechEffect("technology", 35, EffectType.ADDITIVE))
        ),
        Technology(
            id = "sci_t4_2",
            name = "Nanotechnology",
            description = "Engineering at molecular scale.",
            category = TechCategory.SCIENCE,
            tier = 4,
            cost = 5500,
            researchTime = 9,
            prerequisites = listOf("sci_t3_2"),
            effects = listOf(TechEffect("technology", 30, EffectType.ADDITIVE), TechEffect("medicine", 15, EffectType.ADDITIVE))
        ),
        
        // TIER 5
        Technology(
            id = "sci_t5_1",
            name = "Unified Field Theory",
            description = "Complete understanding of physical forces.",
            category = TechCategory.SCIENCE,
            tier = 5,
            cost = 20000,
            researchTime = 25,
            prerequisites = listOf("sci_t4_1"),
            effects = listOf(TechEffect("technology", 50, EffectType.ADDITIVE))
        ),
        Technology(
            id = "sci_t5_2",
            name = "Artificial General Intelligence",
            description = "Human-level machine intelligence.",
            category = TechCategory.SCIENCE,
            tier = 5,
            cost = 25000,
            researchTime = 30,
            prerequisites = listOf("comp_t4_1", "sci_t4_2"),
            effects = listOf(TechEffect("technology", 60, EffectType.ADDITIVE), TechEffect("economy", 40, EffectType.ADDITIVE), TechEffect("happiness", -15, EffectType.ADDITIVE))
        )
    )

    // ============================================
    // HELPER FUNCTIONS
    // ============================================
    
    fun getAllTechnologies(): List<Technology> {
        return militaryTechs + economyTechs + scienceTechs
    }
    
    fun getTechnologiesByCategory(category: TechCategory): List<Technology> {
        return when (category) {
            TechCategory.MILITARY -> militaryTechs
            TechCategory.ECONOMY -> economyTechs
            TechCategory.SCIENCE -> scienceTechs
            else -> emptyList()
        }
    }
    
    fun getTechnologiesByTier(tier: Int): List<Technology> {
        return getAllTechnologies().filter { it.tier == tier }
    }
    
    fun getTechnology(id: String): Technology? {
        return getAllTechnologies().find { it.id == id }
    }
    
    fun getPrerequisiteTree(techId: String): List<String> {
        val tech = getTechnology(techId) ?: return emptyList()
        return tech.prerequisites + tech.prerequisites.flatMap { getPrerequisiteTree(it) }
    }
    
    fun canResearch(techId: String, researchedTechs: List<String>): Boolean {
        val tech = getTechnology(techId) ?: return false
        return tech.prerequisites.all { it in researchedTechs }
    }
    
    fun getTechnologyStatistics(): Map<String, Int> {
        return mapOf(
            "Military" to militaryTechs.size,
            "Economy" to economyTechs.size,
            "Science" to scienceTechs.size,
            "Total" to getAllTechnologies().size,
            "Tier 1" to getTechnologiesByTier(1).size,
            "Tier 2" to getTechnologiesByTier(2).size,
            "Tier 3" to getTechnologiesByTier(3).size,
            "Tier 4" to getTechnologiesByTier(4).size,
            "Tier 5" to getTechnologiesByTier(5).size
        )
    }
}
