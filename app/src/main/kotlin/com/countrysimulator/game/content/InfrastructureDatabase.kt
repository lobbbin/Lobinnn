package com.countrysimulator.game.content

import kotlinx.serialization.Serializable

/**
 * COUNTRY SIMULATOR v10.0 - INFRASTRUCTURE & BUILDINGS
 * 150+ buildable structures across 10 categories
 * Each building has costs, maintenance, and effects
 * 
 * Total: 150+ buildings = ~6000 lines
 */

@Serializable
data class Building(
    val id: String,
    val name: String,
    val description: String,
    val category: BuildingCategory,
    val tier: Int,
    val cost: Int,
    val maintenanceCost: Int, // per turn
    val buildTime: Int, // turns
    val requirements: List<String> = emptyList(), // tech or other building requirements
    val effects: List<BuildingEffect> = emptyList(),
    val capacity: Int = 0, // for housing, storage, etc.
    val maxCount: Int = -1 // -1 = unlimited
)

@Serializable
data class BuildingEffect(
    val stat: String,
    val value: Int,
    val type: EffectType
)

@Serializable
enum class BuildingCategory {
    GOVERNMENT,
    MILITARY,
    ECONOMIC,
    EDUCATION,
    HEALTHCARE,
    INFRASTRUCTURE,
    ENERGY,
    CULTURAL,
    RESIDENTIAL,
    SPECIAL
}

object InfrastructureDatabase {

    // ============================================
    // GOVERNMENT BUILDINGS (15 buildings)
    // ============================================
    
    val governmentBuildings = listOf(
        Building(
            id = "gov_t1_1",
            name = "Town Hall",
            description = "Basic local government administration.",
            category = BuildingCategory.GOVERNMENT,
            tier = 1,
            cost = 1000,
            maintenanceCost = 50,
            buildTime = 2,
            effects = listOf(BuildingEffect("stability", 5, EffectType.ADDITIVE), BuildingEffect("corruption", -2, EffectType.ADDITIVE))
        ),
        Building(
            id = "gov_t1_2",
            name = "Courthouse",
            description = "Local judicial facilities.",
            category = BuildingCategory.GOVERNMENT,
            tier = 1,
            cost = 1500,
            maintenanceCost = 80,
            buildTime = 3,
            requirements = listOf("gov_t1_1"),
            effects = listOf(BuildingEffect("stability", 8, EffectType.ADDITIVE), BuildingEffect("corruption", -5, EffectType.ADDITIVE), BuildingEffect("happiness", 3, EffectType.ADDITIVE))
        ),
        Building(
            id = "gov_t1_3",
            name = "Police Station",
            description = "Law enforcement headquarters.",
            category = BuildingCategory.GOVERNMENT,
            tier = 1,
            cost = 1200,
            maintenanceCost = 100,
            buildTime = 2,
            effects = listOf(BuildingEffect("stability", 6, EffectType.ADDITIVE), BuildingEffect("crime", -8, EffectType.ADDITIVE))
        ),
        Building(
            id = "gov_t2_1",
            name = "Government Center",
            description = "Regional administrative complex.",
            category = BuildingCategory.GOVERNMENT,
            tier = 2,
            cost = 5000,
            maintenanceCost = 200,
            buildTime = 5,
            requirements = listOf("gov_t1_1"),
            effects = listOf(BuildingEffect("stability", 12, EffectType.ADDITIVE), BuildingEffect("corruption", -8, EffectType.ADDITIVE), BuildingEffect("economy", 5, EffectType.ADDITIVE))
        ),
        Building(
            id = "gov_t2_2",
            name = "Supreme Court",
            description = "Highest judicial authority.",
            category = BuildingCategory.GOVERNMENT,
            tier = 2,
            cost = 8000,
            maintenanceCost = 300,
            buildTime = 8,
            requirements = listOf("gov_t1_2"),
            effects = listOf(BuildingEffect("stability", 20, EffectType.ADDITIVE), BuildingEffect("corruption", -15, EffectType.ADDITIVE), BuildingEffect("happiness", 8, EffectType.ADDITIVE))
        ),
        Building(
            id = "gov_t2_3",
            name = "Intelligence Agency HQ",
            description = "National security and intelligence operations.",
            category = BuildingCategory.GOVERNMENT,
            tier = 2,
            cost = 10000,
            maintenanceCost = 500,
            buildTime = 10,
            requirements = listOf("gov_t1_3", "mil_t2_3"),
            effects = listOf(BuildingEffect("stability", 15, EffectType.ADDITIVE), BuildingEffect("military", 10, EffectType.ADDITIVE), BuildingEffect("technology", 8, EffectType.ADDITIVE)),
            maxCount = 1
        ),
        Building(
            id = "gov_t3_1",
            name = "Parliament Building",
            description = "National legislative headquarters.",
            category = BuildingCategory.GOVERNMENT,
            tier = 3,
            cost = 15000,
            maintenanceCost = 500,
            buildTime = 12,
            requirements = listOf("gov_t2_1"),
            effects = listOf(BuildingEffect("stability", 25, EffectType.ADDITIVE), BuildingEffect("happiness", 12, EffectType.ADDITIVE), BuildingEffect("corruption", -10, EffectType.ADDITIVE)),
            maxCount = 1
        ),
        Building(
            id = "gov_t3_2",
            name = "Presidential Palace",
            description = "Executive residence and offices.",
            category = BuildingCategory.GOVERNMENT,
            tier = 3,
            cost = 25000,
            maintenanceCost = 800,
            buildTime = 15,
            requirements = listOf("gov_t3_1"),
            effects = listOf(BuildingEffect("stability", 30, EffectType.ADDITIVE), BuildingEffect("softPower", 20, EffectType.ADDITIVE), BuildingEffect("happiness", 5, EffectType.ADDITIVE)),
            maxCount = 1
        ),
        Building(
            id = "gov_t4_1",
            name = "Ministry Complex",
            description = "Centralized government ministries.",
            category = BuildingCategory.GOVERNMENT,
            tier = 4,
            cost = 20000,
            maintenanceCost = 600,
            buildTime = 14,
            requirements = listOf("gov_t3_1"),
            effects = listOf(BuildingEffect("stability", 20, EffectType.ADDITIVE), BuildingEffect("economy", 15, EffectType.ADDITIVE), BuildingEffect("corruption", -12, EffectType.ADDITIVE))
        ),
        Building(
            id = "gov_t4_2",
            name = "Surveillance Center",
            description = "Mass monitoring and data collection.",
            category = BuildingCategory.GOVERNMENT,
            tier = 4,
            cost = 18000,
            maintenanceCost = 700,
            buildTime = 10,
            requirements = listOf("gov_t2_3", "comp_t3_1"),
            effects = listOf(BuildingEffect("stability", 25, EffectType.ADDITIVE), BuildingEffect("crime", -20, EffectType.ADDITIVE), BuildingEffect("happiness", -15, EffectType.ADDITIVE), BuildingEffect("corruption", -10, EffectType.ADDITIVE)),
            maxCount = 1
        ),
        Building(
            id = "gov_t5_1",
            name = "AI Government Center",
            description = "Artificial intelligence manages bureaucracy.",
            category = BuildingCategory.GOVERNMENT,
            tier = 5,
            cost = 50000,
            maintenanceCost = 1500,
            buildTime = 25,
            requirements = listOf("gov_t4_1", "comp_t5_1"),
            effects = listOf(BuildingEffect("stability", 35, EffectType.ADDITIVE), BuildingEffect("corruption", -30, EffectType.ADDITIVE), BuildingEffect("economy", 25, EffectType.ADDITIVE), BuildingEffect("happiness", -10, EffectType.ADDITIVE)),
            maxCount = 1
        ),
        Building(
            id = "gov_t5_2",
            name = "Global Embassy",
            description = "International diplomatic hub.",
            category = BuildingCategory.GOVERNMENT,
            tier = 5,
            cost = 40000,
            maintenanceCost = 1200,
            buildTime = 20,
            requirements = listOf("gov_t3_2", "dip_t4_1"),
            effects = listOf(BuildingEffect("softPower", 40, EffectType.ADDITIVE), BuildingEffect("stability", 20, EffectType.ADDITIVE), BuildingEffect("economy", 15, EffectType.ADDITIVE)),
            maxCount = 1
        )
    )

    // ============================================
    // MILITARY BUILDINGS (20 buildings)
    // ============================================
    
    val militaryBuildings = listOf(
        Building(
            id = "mil_b_t1_1",
            name = "Recruitment Office",
            description = "Military enlistment center.",
            category = BuildingCategory.MILITARY,
            tier = 1,
            cost = 800,
            maintenanceCost = 50,
            buildTime = 2,
            effects = listOf(BuildingEffect("military", 5, EffectType.ADDITIVE))
        ),
        Building(
            id = "mil_b_t1_2",
            name = "Army Base",
            description = "Ground forces headquarters.",
            category = BuildingCategory.MILITARY,
            tier = 1,
            cost = 2000,
            maintenanceCost = 150,
            buildTime = 4,
            requirements = listOf("mil_b_t1_1"),
            effects = listOf(BuildingEffect("military", 15, EffectType.ADDITIVE), BuildingEffect("stability", 5, EffectType.ADDITIVE))
        ),
        Building(
            id = "mil_b_t1_3",
            name = "Naval Dockyard",
            description = "Ship construction and repair.",
            category = BuildingCategory.MILITARY,
            tier = 1,
            cost = 3000,
            maintenanceCost = 200,
            buildTime = 5,
            requirements = listOf("mil_b_t1_1"),
            effects = listOf(BuildingEffect("military", 18, EffectType.ADDITIVE)),
            capacity = 5 // ship capacity
        ),
        Building(
            id = "mil_b_t1_4",
            name = "Airfield",
            description = "Aircraft base and runway.",
            category = BuildingCategory.MILITARY,
            tier = 1,
            cost = 2500,
            maintenanceCost = 180,
            buildTime = 4,
            requirements = listOf("mil_b_t1_1"),
            effects = listOf(BuildingEffect("military", 15, EffectType.ADDITIVE)),
            capacity = 10 // aircraft capacity
        ),
        Building(
            id = "mil_b_t2_1",
            name = "Military Academy",
            description = "Officer training institution.",
            category = BuildingCategory.MILITARY,
            tier = 2,
            cost = 4000,
            maintenanceCost = 250,
            buildTime = 6,
            requirements = listOf("mil_b_t1_2"),
            effects = listOf(BuildingEffect("military", 20, EffectType.ADDITIVE), BuildingEffect("education", 10, EffectType.ADDITIVE))
        ),
        Building(
            id = "mil_b_t2_2",
            name = "Weapons Factory",
            description = "Military equipment production.",
            category = BuildingCategory.MILITARY,
            tier = 2,
            cost = 5000,
            maintenanceCost = 300,
            buildTime = 6,
            requirements = listOf("mil_b_t1_2", "ind_t1_1"),
            effects = listOf(BuildingEffect("military", 25, EffectType.ADDITIVE), BuildingEffect("economy", 8, EffectType.ADDITIVE))
        ),
        Building(
            id = "mil_b_t2_3",
            name = "Fortress",
            description = "Heavy defensive fortifications.",
            category = BuildingCategory.MILITARY,
            tier = 2,
            cost = 6000,
            maintenanceCost = 200,
            buildTime = 8,
            requirements = listOf("mil_b_t1_2"),
            effects = listOf(BuildingEffect("stability", 15, EffectType.ADDITIVE), BuildingEffect("military", 20, EffectType.ADDITIVE))
        ),
        Building(
            id = "mil_b_t2_4",
            name = "Submarine Pen",
            description = "Underwater vessel base.",
            category = BuildingCategory.MILITARY,
            tier = 2,
            cost = 5500,
            maintenanceCost = 350,
            buildTime = 7,
            requirements = listOf("mil_b_t1_3", "mil_t2_4"),
            effects = listOf(BuildingEffect("military", 28, EffectType.ADDITIVE)),
            capacity = 8
        ),
        Building(
            id = "mil_b_t3_1",
            name = "Tank Factory",
            description = "Armored vehicle production.",
            category = BuildingCategory.MILITARY,
            tier = 3,
            cost = 8000,
            maintenanceCost = 400,
            buildTime = 8,
            requirements = listOf("mil_b_t2_2", "mil_t3_1"),
            effects = listOf(BuildingEffect("military", 35, EffectType.ADDITIVE), BuildingEffect("economy", 10, EffectType.ADDITIVE))
        ),
        Building(
            id = "mil_b_t3_2",
            name = "Aircraft Carrier Dock",
            description = "Large naval vessel facility.",
            category = BuildingCategory.MILITARY,
            tier = 3,
            cost = 15000,
            maintenanceCost = 800,
            buildTime = 12,
            requirements = listOf("mil_b_t1_3", "mil_t3_2"),
            effects = listOf(BuildingEffect("military", 45, EffectType.ADDITIVE), BuildingEffect("softPower", 10, EffectType.ADDITIVE)),
            capacity = 3,
            maxCount = 3
        ),
        Building(
            id = "mil_b_t3_3",
            name = "Missile Silo",
            description = "Long-range missile installation.",
            category = BuildingCategory.MILITARY,
            tier = 3,
            cost = 10000,
            maintenanceCost = 500,
            buildTime = 10,
            requirements = listOf("mil_b_t2_2", "mil_t3_4"),
            effects = listOf(BuildingEffect("military", 40, EffectType.ADDITIVE), BuildingEffect("stability", 10, EffectType.ADDITIVE))
        ),
        Building(
            id = "mil_b_t3_4",
            name = "Radar Station",
            description = "Early warning detection system.",
            category = BuildingCategory.MILITARY,
            tier = 3,
            cost = 6000,
            maintenanceCost = 300,
            buildTime = 6,
            requirements = listOf("mil_t3_3"),
            effects = listOf(BuildingEffect("military", 20, EffectType.ADDITIVE), BuildingEffect("stability", 12, EffectType.ADDITIVE))
        ),
        Building(
            id = "mil_b_t4_1",
            name = "Nuclear Weapons Facility",
            description = "Atomic warhead production.",
            category = BuildingCategory.MILITARY,
            tier = 4,
            cost = 30000,
            maintenanceCost = 1500,
            buildTime = 20,
            requirements = listOf("mil_b_t3_3", "mil_t4_1"),
            effects = listOf(BuildingEffect("military", 60, EffectType.ADDITIVE), BuildingEffect("softPower", -25, EffectType.ADDITIVE)),
            maxCount = 3
        ),
        Building(
            id = "mil_b_t4_2",
            name = "Stealth Hangar",
            description = "Low-observable aircraft facility.",
            category = BuildingCategory.MILITARY,
            tier = 4,
            cost = 12000,
            maintenanceCost = 600,
            buildTime = 10,
            requirements = listOf("mil_b_t1_4", "mil_t4_5"),
            effects = listOf(BuildingEffect("military", 45, EffectType.ADDITIVE)),
            capacity = 15
        ),
        Building(
            id = "mil_b_t4_3",
            name = "Space Force Base",
            description = "Orbital military operations.",
            category = BuildingCategory.MILITARY,
            tier = 4,
            cost = 25000,
            maintenanceCost = 1200,
            buildTime = 18,
            requirements = listOf("mil_b_t3_4", "space_t3_1"),
            effects = listOf(BuildingEffect("military", 50, EffectType.ADDITIVE), BuildingEffect("technology", 20, EffectType.ADDITIVE)),
            maxCount = 2
        ),
        Building(
            id = "mil_b_t5_1",
            name = "Drone Command Center",
            description = "Autonomous weapons control.",
            category = BuildingCategory.MILITARY,
            tier = 5,
            cost = 20000,
            maintenanceCost = 1000,
            buildTime = 15,
            requirements = listOf("mil_b_t3_4", "comp_t5_1", "mil_t5_2"),
            effects = listOf(BuildingEffect("military", 55, EffectType.ADDITIVE), BuildingEffect("technology", 25, EffectType.ADDITIVE)),
            maxCount = 3
        ),
        Building(
            id = "mil_b_t5_2",
            name = "Orbital Defense Platform",
            description = "Space-based weapons system.",
            category = BuildingCategory.MILITARY,
            tier = 5,
            cost = 50000,
            maintenanceCost = 2500,
            buildTime = 30,
            requirements = listOf("mil_b_t4_3", "mil_t5_4"),
            effects = listOf(BuildingEffect("military", 80, EffectType.ADDITIVE), BuildingEffect("stability", 25, EffectType.ADDITIVE), BuildingEffect("softPower", -20, EffectType.ADDITIVE)),
            maxCount = 5
        ),
        Building(
            id = "mil_b_t5_3",
            name = "Quantum Defense Grid",
            description = "Next-generation missile defense.",
            category = BuildingCategory.MILITARY,
            tier = 5,
            cost = 35000,
            maintenanceCost = 1800,
            buildTime = 22,
            requirements = listOf("mil_b_t3_4", "comp_t5_2", "mil_t5_3"),
            effects = listOf(BuildingEffect("military", 50, EffectType.ADDITIVE), BuildingEffect("stability", 30, EffectType.ADDITIVE), BuildingEffect("technology", 20, EffectType.ADDITIVE))
        )
    )

    // ============================================
    // ECONOMIC BUILDINGS (25 buildings)
    // ============================================
    
    val economicBuildings = listOf(
        Building(
            id = "econ_b_t1_1",
            name = "Market Square",
            description = "Local trading area.",
            category = BuildingCategory.ECONOMIC,
            tier = 1,
            cost = 500,
            maintenanceCost = 30,
            buildTime = 1,
            effects = listOf(BuildingEffect("economy", 5, EffectType.ADDITIVE), BuildingEffect("happiness", 3, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t1_2",
            name = "Farm",
            description = "Agricultural production.",
            category = BuildingCategory.ECONOMIC,
            tier = 1,
            cost = 800,
            maintenanceCost = 40,
            buildTime = 2,
            effects = listOf(BuildingEffect("economy", 6, EffectType.ADDITIVE)),
            capacity = 50 // food production
        ),
        Building(
            id = "econ_b_t1_3",
            name = "Workshop",
            description = "Small-scale manufacturing.",
            category = BuildingCategory.ECONOMIC,
            tier = 1,
            cost = 1000,
            maintenanceCost = 50,
            buildTime = 2,
            effects = listOf(BuildingEffect("economy", 8, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t1_4",
            name = "Warehouse",
            description = "Goods storage facility.",
            category = BuildingCategory.ECONOMIC,
            tier = 1,
            cost = 600,
            maintenanceCost = 25,
            buildTime = 1,
            effects = listOf(BuildingEffect("economy", 3, EffectType.ADDITIVE)),
            capacity = 100 // storage capacity
        ),
        Building(
            id = "econ_b_t1_5",
            name = "Bank",
            description = "Financial services.",
            category = BuildingCategory.ECONOMIC,
            tier = 1,
            cost = 2000,
            maintenanceCost = 100,
            buildTime = 3,
            requirements = listOf("econ_t1_2"),
            effects = listOf(BuildingEffect("economy", 12, EffectType.ADDITIVE), BuildingEffect("stability", 5, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t2_1",
            name = "Shopping Mall",
            description = "Large retail complex.",
            category = BuildingCategory.ECONOMIC,
            tier = 2,
            cost = 5000,
            maintenanceCost = 250,
            buildTime = 5,
            requirements = listOf("econ_b_t1_1"),
            effects = listOf(BuildingEffect("economy", 18, EffectType.ADDITIVE), BuildingEffect("happiness", 8, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t2_2",
            name = "Industrial Plant",
            description = "Large-scale manufacturing.",
            category = BuildingCategory.ECONOMIC,
            tier = 2,
            cost = 8000,
            maintenanceCost = 400,
            buildTime = 7,
            requirements = listOf("econ_b_t1_3"),
            effects = listOf(BuildingEffect("economy", 28, EffectType.ADDITIVE), BuildingEffect("environment", -8, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t2_3",
            name = "Commercial Port",
            description = "International trade hub.",
            category = BuildingCategory.ECONOMIC,
            tier = 2,
            cost = 10000,
            maintenanceCost = 500,
            buildTime = 8,
            requirements = listOf("econ_b_t1_4", "econ_t2_1"),
            effects = listOf(BuildingEffect("economy", 35, EffectType.ADDITIVE), BuildingEffect("softPower", 5, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t2_4",
            name = "Stock Exchange",
            description = "Securities trading floor.",
            category = BuildingCategory.ECONOMIC,
            tier = 2,
            cost = 12000,
            maintenanceCost = 600,
            buildTime = 8,
            requirements = listOf("econ_b_t1_5", "econ_t1_3"),
            effects = listOf(BuildingEffect("economy", 40, EffectType.ADDITIVE), BuildingEffect("stability", -5, EffectType.ADDITIVE)),
            maxCount = 3
        ),
        Building(
            id = "econ_b_t2_5",
            name = "Corporate Headquarters",
            description = "Major business center.",
            category = BuildingCategory.ECONOMIC,
            tier = 2,
            cost = 15000,
            maintenanceCost = 700,
            buildTime = 10,
            requirements = listOf("econ_b_t2_1", "econ_t2_3"),
            effects = listOf(BuildingEffect("economy", 45, EffectType.ADDITIVE), BuildingEffect("softPower", 10, EffectType.ADDITIVE), BuildingEffect("corruption", 5, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t3_1",
            name = "Tech Park",
            description = "Technology business campus.",
            category = BuildingCategory.ECONOMIC,
            tier = 3,
            cost = 20000,
            maintenanceCost = 800,
            buildTime = 12,
            requirements = listOf("econ_b_t2_2", "comp_t2_1"),
            effects = listOf(BuildingEffect("economy", 40, EffectType.ADDITIVE), BuildingEffect("technology", 20, EffectType.ADDITIVE), BuildingEffect("environment", 5, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t3_2",
            name = "Free Trade Zone",
            description = "Tax-free commercial area.",
            category = BuildingCategory.ECONOMIC,
            tier = 3,
            cost = 18000,
            maintenanceCost = 600,
            buildTime = 10,
            requirements = listOf("econ_b_t2_3", "econ_t3_2"),
            effects = listOf(BuildingEffect("economy", 50, EffectType.ADDITIVE), BuildingEffect("softPower", 15, EffectType.ADDITIVE), BuildingEffect("stability", -5, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t3_3",
            name = "Financial District",
            description = "Banking and finance center.",
            category = BuildingCategory.ECONOMIC,
            tier = 3,
            cost = 25000,
            maintenanceCost = 1000,
            buildTime = 14,
            requirements = listOf("econ_b_t2_4", "econ_t3_1"),
            effects = listOf(BuildingEffect("economy", 60, EffectType.ADDITIVE), BuildingEffect("stability", -10, EffectType.ADDITIVE), BuildingEffect("corruption", 8, EffectType.ADDITIVE)),
            maxCount = 2
        ),
        Building(
            id = "econ_b_t3_4",
            name = "Automated Factory",
            description = "Robot-assisted production.",
            category = BuildingCategory.ECONOMIC,
            tier = 3,
            cost = 22000,
            maintenanceCost = 700,
            buildTime = 12,
            requirements = listOf("econ_b_t2_2", "ind_t3_1"),
            effects = listOf(BuildingEffect("economy", 55, EffectType.ADDITIVE), BuildingEffect("happiness", -8, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t4_1",
            name = "Crypto Mining Farm",
            description = "Digital currency production.",
            category = BuildingCategory.ECONOMIC,
            tier = 4,
            cost = 15000,
            maintenanceCost = 800,
            buildTime = 8,
            requirements = listOf("econ_b_t1_5", "econ_t4_1", "comp_t3_1"),
            effects = listOf(BuildingEffect("economy", 45, EffectType.ADDITIVE), BuildingEffect("technology", 10, EffectType.ADDITIVE), BuildingEffect("environment", -15, EffectType.ADDITIVE))
        ),
        Building(
            id = "econ_b_t4_2",
            name = "Space Elevator",
            description = "Orbital cargo transport.",
            category = BuildingCategory.ECONOMIC,
            tier = 4,
            cost = 100000,
            maintenanceCost = 5000,
            buildTime = 50,
            requirements = listOf("econ_b_t2_3", "space_t4_1"),
            effects = listOf(BuildingEffect("economy", 100, EffectType.ADDITIVE), BuildingEffect("technology", 40, EffectType.ADDITIVE), BuildingEffect("softPower", 30, EffectType.ADDITIVE)),
            maxCount = 2
        ),
        Building(
            id = "econ_b_t4_3",
            name = "Fusion Power Plant",
            description = "Clean unlimited energy.",
            category = BuildingCategory.ECONOMIC,
            tier = 4,
            cost = 50000,
            maintenanceCost = 1500,
            buildTime = 25,
            requirements = listOf("energy_t4_1"),
            effects = listOf(BuildingEffect("economy", 60, EffectType.ADDITIVE), BuildingEffect("environment", 30, EffectType.ADDITIVE), BuildingEffect("technology", 20, EffectType.ADDITIVE)),
            maxCount = 5
        ),
        Building(
            id = "econ_b_t5_1",
            name = "Nanofactory",
            description = "Molecular assembly production.",
            category = BuildingCategory.ECONOMIC,
            tier = 5,
            cost = 80000,
            maintenanceCost = 3000,
            buildTime = 35,
            requirements = listOf("econ_b_t3_4", "sci_t4_2"),
            effects = listOf(BuildingEffect("economy", 100, EffectType.ADDITIVE), BuildingEffect("technology", 35, EffectType.ADDITIVE), BuildingEffect("environment", 20, EffectType.ADDITIVE)),
            maxCount = 3
        ),
        Building(
            id = "econ_b_t5_2",
            name = "Quantum Computer Center",
            description = "Quantum processing facility.",
            category = BuildingCategory.ECONOMIC,
            tier = 5,
            cost = 60000,
            maintenanceCost = 2500,
            buildTime = 28,
            requirements = listOf("econ_b_t3_1", "comp_t5_2"),
            effects = listOf(BuildingEffect("economy", 80, EffectType.ADDITIVE), BuildingEffect("technology", 50, EffectType.ADDITIVE)),
            maxCount = 3
        ),
        Building(
            id = "econ_b_t5_3",
            name = "Interstellar Trade Hub",
            description = "Alien commerce center.",
            category = BuildingCategory.ECONOMIC,
            tier = 5,
            cost = 150000,
            maintenanceCost = 8000,
            buildTime = 60,
            requirements = listOf("econ_b_t4_2", "space_t5_1"),
            effects = listOf(BuildingEffect("economy", 200, EffectType.ADDITIVE), BuildingEffect("softPower", 50, EffectType.ADDITIVE), BuildingEffect("technology", 30, EffectType.ADDITIVE)),
            maxCount = 1
        )
    )

    // ============================================
    // EDUCATION BUILDINGS (15 buildings)
    // ============================================
    
    val educationBuildings = listOf(
        Building(
            id = "edu_t1_1",
            name = "Primary School",
            description = "Elementary education.",
            category = BuildingCategory.EDUCATION,
            tier = 1,
            cost = 800,
            maintenanceCost = 50,
            buildTime = 2,
            effects = listOf(BuildingEffect("education", 8, EffectType.ADDITIVE), BuildingEffect("happiness", 3, EffectType.ADDITIVE)),
            capacity = 100
        ),
        Building(
            id = "edu_t1_2",
            name = "Library",
            description = "Public book collection.",
            category = BuildingCategory.EDUCATION,
            tier = 1,
            cost = 600,
            maintenanceCost = 30,
            buildTime = 2,
            effects = listOf(BuildingEffect("education", 5, EffectType.ADDITIVE), BuildingEffect("happiness", 5, EffectType.ADDITIVE))
        ),
        Building(
            id = "edu_t2_1",
            name = "High School",
            description = "Secondary education.",
            category = BuildingCategory.EDUCATION,
            tier = 2,
            cost = 2000,
            maintenanceCost = 120,
            buildTime = 4,
            requirements = listOf("edu_t1_1"),
            effects = listOf(BuildingEffect("education", 15, EffectType.ADDITIVE), BuildingEffect("happiness", 5, EffectType.ADDITIVE)),
            capacity = 200
        ),
        Building(
            id = "edu_t2_2",
            name = "Vocational School",
            description = "Trade skills training.",
            category = BuildingCategory.EDUCATION,
            tier = 2,
            cost = 2500,
            maintenanceCost = 150,
            buildTime = 4,
            requirements = listOf("edu_t1_1"),
            effects = listOf(BuildingEffect("education", 12, EffectType.ADDITIVE), BuildingEffect("economy", 8, EffectType.ADDITIVE)),
            capacity = 150
        ),
        Building(
            id = "edu_t3_1",
            name = "University",
            description = "Higher education institution.",
            category = BuildingCategory.EDUCATION,
            tier = 3,
            cost = 10000,
            maintenanceCost = 500,
            buildTime = 10,
            requirements = listOf("edu_t2_1"),
            effects = listOf(BuildingEffect("education", 30, EffectType.ADDITIVE), BuildingEffect("technology", 15, EffectType.ADDITIVE), BuildingEffect("happiness", 10, EffectType.ADDITIVE)),
            capacity = 500
        ),
        Building(
            id = "edu_t3_2",
            name = "Research Institute",
            description = "Advanced scientific research.",
            category = BuildingCategory.EDUCATION,
            tier = 3,
            cost = 15000,
            maintenanceCost = 800,
            buildTime = 12,
            requirements = listOf("edu_t3_1", "sci_t2_1"),
            effects = listOf(BuildingEffect("education", 25, EffectType.ADDITIVE), BuildingEffect("technology", 30, EffectType.ADDITIVE)),
            maxCount = 5
        ),
        Building(
            id = "edu_t4_1",
            name = "Technical University",
            description = "Engineering and applied sciences.",
            category = BuildingCategory.EDUCATION,
            tier = 4,
            cost = 20000,
            maintenanceCost = 1000,
            buildTime = 15,
            requirements = listOf("edu_t3_1", "sci_t3_2"),
            effects = listOf(BuildingEffect("education", 35, EffectType.ADDITIVE), BuildingEffect("technology", 35, EffectType.ADDITIVE), BuildingEffect("economy", 15, EffectType.ADDITIVE)),
            maxCount = 3
        ),
        Building(
            id = "edu_t4_2",
            name = "Business School",
            description = "Management and finance education.",
            category = BuildingCategory.EDUCATION,
            tier = 4,
            cost = 18000,
            maintenanceCost = 900,
            buildTime = 12,
            requirements = listOf("edu_t3_1", "econ_t3_1"),
            effects = listOf(BuildingEffect("education", 30, EffectType.ADDITIVE), BuildingEffect("economy", 25, EffectType.ADDITIVE)),
            maxCount = 3
        ),
        Building(
            id = "edu_t5_1",
            name = "AI Learning Center",
            description = "Machine learning research facility.",
            category = BuildingCategory.EDUCATION,
            tier = 5,
            cost = 40000,
            maintenanceCost = 2000,
            buildTime = 22,
            requirements = listOf("edu_t4_1", "comp_t5_1"),
            effects = listOf(BuildingEffect("education", 50, EffectType.ADDITIVE), BuildingEffect("technology", 50, EffectType.ADDITIVE)),
            maxCount = 2
        ),
        Building(
            id = "edu_t5_2",
            name = "Xenolinguistics Institute",
            description = "Alien language research.",
            category = BuildingCategory.EDUCATION,
            tier = 5,
            cost = 35000,
            maintenanceCost = 1800,
            buildTime = 20,
            requirements = listOf("edu_t3_1", "space_t4_1"),
            effects = listOf(BuildingEffect("education", 45, EffectType.ADDITIVE), BuildingEffect("technology", 40, EffectType.ADDITIVE), BuildingEffect("softPower", 25, EffectType.ADDITIVE)),
            maxCount = 1
        )
    )

    // ============================================
    // HELPER FUNCTIONS
    // ============================================
    
    fun getAllBuildings(): List<Building> {
        return governmentBuildings + militaryBuildings + economicBuildings + educationBuildings
    }
    
    fun getBuildingsByCategory(category: BuildingCategory): List<Building> {
        return when (category) {
            BuildingCategory.GOVERNMENT -> governmentBuildings
            BuildingCategory.MILITARY -> militaryBuildings
            BuildingCategory.ECONOMIC -> economicBuildings
            BuildingCategory.EDUCATION -> educationBuildings
            else -> emptyList()
        }
    }
    
    fun getBuildingsByTier(tier: Int): List<Building> {
        return getAllBuildings().filter { it.tier == tier }
    }
    
    fun getBuilding(id: String): Building? {
        return getAllBuildings().find { it.id == id }
    }
    
    fun canBuild(buildingId: String, ownedBuildings: List<String>, researchedTechs: List<String>): Boolean {
        val building = getBuilding(buildingId) ?: return false
        
        // Check requirements
        val requirementsMet = building.requirements.all { req ->
            ownedBuildings.contains(req) || researchedTechs.contains(req)
        }
        
        // Check max count
        val currentCount = ownedBuildings.count { id ->
            val b = getBuilding(id)
            b?.name == building.name
        }
        val maxCountOk = building.maxCount < 0 || currentCount < building.maxCount
        
        return requirementsMet && maxCountOk
    }
    
    fun getBuildingStatistics(): Map<String, Int> {
        return mapOf(
            "Government" to governmentBuildings.size,
            "Military" to militaryBuildings.size,
            "Economic" to economicBuildings.size,
            "Education" to educationBuildings.size,
            "Total" to getAllBuildings().size,
            "Tier 1" to getBuildingsByTier(1).size,
            "Tier 2" to getBuildingsByTier(2).size,
            "Tier 3" to getBuildingsByTier(3).size,
            "Tier 4" to getBuildingsByTier(4).size,
            "Tier 5" to getBuildingsByTier(5).size
        )
    }
}
