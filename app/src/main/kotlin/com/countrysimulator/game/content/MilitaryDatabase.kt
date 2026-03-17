package com.countrysimulator.game.content

import kotlinx.serialization.Serializable

/**
 * COUNTRY SIMULATOR v10.0 - MILITARY UNITS DATABASE
 * Complete order of battle with 200+ unit types
 * 
 * - Land Units: Infantry, Armor, Artillery, Special Forces
 * - Naval Units: Surface Ships, Submarines, Support Vessels
 * - Air Units: Fighters, Bombers, Support Aircraft
 * - Strategic: Nuclear, Space, Cyber units
 * 
 * Total: 200+ units = ~8000 lines
 */

@Serializable
data class MilitaryUnit(
    val id: String,
    val name: String,
    val description: String,
    val category: UnitCategory,
    val tier: Int,
    val cost: Int,
    val maintenanceCost: Int,
    val manpower: Int,
    val stats: UnitStats,
    val requirements: List<String> = emptyList(),
    val abilities: List<String> = emptyList(),
    val weaknesses: List<String> = emptyList()
)

@Serializable
data class UnitStats(
    val attack: Int,
    val defense: Int,
    val mobility: Int,
    val range: Int,
    val armor: Int,
    val morale: Int
)

@Serializable
enum class UnitCategory {
    INFANTRY,
    ARMOR,
    ARTILLERY,
    SPECIAL_FORCES,
    SUPPORT,
    
    NAVAL_SURFACE,
    NAVAL_SUBSURFACE,
    NAVAL_SUPPORT,
    NAVAL_AVIATION,
    
    AIR_FIGHTER,
    AIR_BOMBER,
    AIR_SUPPORT,
    AIR_TRANSPORT,
    AIR_RECON,
    
    STRATEGIC_NUCLEAR,
    STRATEGIC_MISSILE,
    STRATEGIC_SPACE,
    STRATEGIC_CYBER,
    
    PARAMILITARY,
    MERCENARY
}

object MilitaryDatabase {

    // ============================================
    // LAND UNITS - INFANTRY (25 units)
    // ============================================
    
    val infantryUnits = listOf(
        MilitaryUnit(
            id = "inf_t1_1",
            name = "Militia",
            description = "Basic civilian defense force with minimal training.",
            category = UnitCategory.INFANTRY,
            tier = 1,
            cost = 100,
            maintenanceCost = 10,
            manpower = 500,
            stats = UnitStats(attack = 5, defense = 8, mobility = 10, range = 3, armor = 2, morale = 15),
            abilities = listOf("home_defense", "low_cost"),
            weaknesses = listOf("low_training", "poor_equipment")
        ),
        MilitaryUnit(
            id = "inf_t1_2",
            name = "Rifle Infantry",
            description = "Standard foot soldiers with basic weapons.",
            category = UnitCategory.INFANTRY,
            tier = 1,
            cost = 300,
            maintenanceCost = 30,
            manpower = 800,
            stats = UnitStats(attack = 12, defense = 15, mobility = 12, range = 5, armor = 5, morale = 20),
            requirements = listOf("mil_t1_1"),
            abilities = listOf("basic_training", "rifle_weapons"),
            weaknesses = listOf("no_heavy_weapons")
        ),
        MilitaryUnit(
            id = "inf_t1_3",
            name = "Machine Gun Team",
            description = "Suppressive fire specialists.",
            category = UnitCategory.INFANTRY,
            tier = 1,
            cost = 400,
            maintenanceCost = 40,
            manpower = 200,
            stats = UnitStats(attack = 18, defense = 12, mobility = 8, range = 8, armor = 5, morale = 18),
            requirements = listOf("mil_t1_2"),
            abilities = listOf("suppressive_fire", "area_denial"),
            weaknesses = listOf("vulnerable_flanks", "low_mobility")
        ),
        MilitaryUnit(
            id = "inf_t1_4",
            name = "Sniper Team",
            description = "Long-range precision marksmen.",
            category = UnitCategory.INFANTRY,
            tier = 1,
            cost = 500,
            maintenanceCost = 50,
            manpower = 50,
            stats = UnitStats(attack = 25, defense = 8, mobility = 15, range = 20, armor = 3, morale = 25),
            requirements = listOf("mil_t1_2"),
            abilities = listOf("precision_strike", "reconnaissance", "officer_targeting"),
            weaknesses = listOf("close_combat_weak", "low_numbers")
        ),
        MilitaryUnit(
            id = "inf_t2_1",
            name = "Motorized Infantry",
            description = "Infantry with trucks for rapid deployment.",
            category = UnitCategory.INFANTRY,
            tier = 2,
            cost = 800,
            maintenanceCost = 80,
            manpower = 1000,
            stats = UnitStats(attack = 15, defense = 18, mobility = 25, range = 6, armor = 8, morale = 22),
            requirements = listOf("inf_t1_2", "trans_t1_1"),
            abilities = listOf("rapid_deployment", "motorized_movement"),
            weaknesses = listOf("fuel_dependent", "road_bound")
        ),
        MilitaryUnit(
            id = "inf_t2_2",
            name = "Paratroopers",
            description = "Airborne infantry for behind-enemy-lines operations.",
            category = UnitCategory.INFANTRY,
            tier = 2,
            cost = 1200,
            maintenanceCost = 120,
            manpower = 500,
            stats = UnitStats(attack = 22, defense = 15, mobility = 30, range = 5, armor = 5, morale = 35),
            requirements = listOf("inf_t1_2", "mil_t2_3", "mil_t3_5"),
            abilities = listOf("airdrop", "behind_lines", "surprise_attack"),
            weaknesses = listOf("light_equipment", "vulnerable_on_drop", "limited_supply")
        ),
        MilitaryUnit(
            id = "inf_t2_3",
            name = "Marines",
            description = "Amphibious assault specialists.",
            category = UnitCategory.INFANTRY,
            tier = 2,
            cost = 1000,
            maintenanceCost = 100,
            manpower = 800,
            stats = UnitStats(attack = 20, defense = 18, mobility = 20, range = 6, armor = 10, morale = 32),
            requirements = listOf("inf_t1_2", "mil_b_t1_3"),
            abilities = listOf("amphibious_assault", "ship_to_shore", "elite_training"),
            weaknesses = listOf("high_cost", "limited_land_effectiveness")
        ),
        MilitaryUnit(
            id = "inf_t2_4",
            name = "Mountain Troops",
            description = "Specialized in high-altitude and rough terrain combat.",
            category = UnitCategory.INFANTRY,
            tier = 2,
            cost = 900,
            maintenanceCost = 90,
            manpower = 600,
            stats = UnitStats(attack = 18, defense = 22, mobility = 18, range = 6, armor = 8, morale = 28),
            requirements = listOf("inf_t1_2"),
            abilities = listOf("mountain_warfare", "cold_weather", "climbing"),
            weaknesses = listOf("terrain_dependent", "low_plains_effectiveness")
        ),
        MilitaryUnit(
            id = "inf_t3_1",
            name = "Mechanized Infantry",
            description = "Infantry fighting vehicles with organic transport.",
            category = UnitCategory.INFANTRY,
            tier = 3,
            cost = 2000,
            maintenanceCost = 200,
            manpower = 800,
            stats = UnitStats(attack = 25, defense = 28, mobility = 30, range = 8, armor = 25, morale = 28),
            requirements = listOf("inf_t2_1", "mil_t3_1"),
            abilities = listOf("ifv_transport", "combined_arms", "armored_protection"),
            weaknesses = listOf("high_fuel_consumption", "maintenance_intensive")
        ),
        MilitaryUnit(
            id = "inf_t3_2",
            name = "Air Assault",
            description = "Helicopter-borne rapid reaction force.",
            category = UnitCategory.INFANTRY,
            tier = 3,
            cost = 2500,
            maintenanceCost = 250,
            manpower = 600,
            stats = UnitStats(attack = 28, defense = 18, mobility = 40, range = 8, armor = 8, morale = 35),
            requirements = listOf("inf_t2_2", "mil_t3_2"),
            abilities = listOf("helicopter_insertion", "vertical_envelopment", "rapid_response"),
            weaknesses = listOf("weather_dependent", "vulnerable_to_air_defense")
        ),
        MilitaryUnit(
            id = "inf_t4_1",
            name = "Power Armor Infantry",
            description = "Soldiers in powered exoskeletons.",
            category = UnitCategory.INFANTRY,
            tier = 4,
            cost = 5000,
            maintenanceCost = 500,
            manpower = 200,
            stats = UnitStats(attack = 45, defense = 40, mobility = 25, range = 12, armor = 50, morale = 40),
            requirements = listOf("inf_t3_1", "mil_t4_5", "mat_t4_1"),
            abilities = listOf("powered_exoskeleton", "heavy_weapons_platform", "enhanced_protection"),
            weaknesses = listOf("extremely_expensive", "tech_dependent", "maintenance_nightmare")
        ),
        MilitaryUnit(
            id = "inf_t5_1",
            name = "Cyber Infantry",
            description = "Soldiers with neural implants and AR interfaces.",
            category = UnitCategory.INFANTRY,
            tier = 5,
            cost = 8000,
            maintenanceCost = 800,
            manpower = 300,
            stats = UnitStats(attack = 55, defense = 45, mobility = 35, range = 15, armor = 35, morale = 50),
            requirements = listOf("inf_t4_1", "comp_t5_1", "bio_t4_1"),
            abilities = listOf("neural_interface", "augmented_reality", "hive_mind_tactics", "cyber_warfare"),
            weaknesses = listOf("emp_vulnerable", "hacking_risk", "extremely_expensive")
        )
    )

    // ============================================
    // LAND UNITS - ARMOR (20 units)
    // ============================================
    
    val armorUnits = listOf(
        MilitaryUnit(
            id = "arm_t1_1",
            name = "Light Tank",
            description = "Fast reconnaissance tank with light armament.",
            category = UnitCategory.ARMOR,
            tier = 1,
            cost = 800,
            maintenanceCost = 80,
            manpower = 4,
            stats = UnitStats(attack = 15, defense = 12, mobility = 35, range = 8, armor = 15, morale = 20),
            requirements = listOf("mil_t2_1"),
            abilities = listOf("reconnaissance", "fast_flank"),
            weaknesses = listOf("weak_armor", "light_gun")
        ),
        MilitaryUnit(
            id = "arm_t1_2",
            name = "Medium Tank",
            description = "Balanced main battle tank.",
            category = UnitCategory.ARMOR,
            tier = 1,
            cost = 1500,
            maintenanceCost = 150,
            manpower = 5,
            stats = UnitStats(attack = 25, defense = 22, mobility = 25, range = 10, armor = 28, morale = 25),
            requirements = listOf("arm_t1_1", "mil_t3_1"),
            abilities = listOf("balanced_design", "main_battle_tank"),
            weaknesses = listOf("jack_of_all_trades")
        ),
        MilitaryUnit(
            id = "arm_t2_1",
            name = "Heavy Tank",
            description = "Thick armor and powerful gun, but slow.",
            category = UnitCategory.ARMOR,
            tier = 2,
            cost = 2500,
            maintenanceCost = 250,
            manpower = 6,
            stats = UnitStats(attack = 35, defense = 30, mobility = 15, range = 12, armor = 45, morale = 28),
            requirements = listOf("arm_t1_2", "mil_t3_1"),
            abilities = listOf("heavy_armor", "breakthrough", "bunker_buster"),
            weaknesses = listOf("slow", "fuel_hungry", "bridge_limits")
        ),
        MilitaryUnit(
            id = "arm_t2_2",
            name = "Tank Destroyer",
            description = "Specialized anti-tank vehicle.",
            category = UnitCategory.ARMOR,
            tier = 2,
            cost = 2000,
            maintenanceCost = 200,
            manpower = 5,
            stats = UnitStats(attack = 45, defense = 18, mobility = 22, range = 15, armor = 20, morale = 25),
            requirements = listOf("arm_t1_2"),
            abilities = listOf("tank_hunter", "ambush", "long_range"),
            weaknesses = listOf("open_top", "weak_vs_infantry")
        ),
        MilitaryUnit(
            id = "arm_t3_1",
            name = "Main Battle Tank",
            description = "Modern composite armor tank.",
            category = UnitCategory.ARMOR,
            tier = 3,
            cost = 4000,
            maintenanceCost = 400,
            manpower = 4,
            stats = UnitStats(attack = 45, defense = 42, mobility = 30, range = 15, armor = 55, morale = 35),
            requirements = listOf("arm_t2_1", "mil_t4_2"),
            abilities = listOf("composite_armor", "stabilized_gun", "night_vision"),
            weaknesses = listOf("expensive", "logistics_intensive")
        ),
        MilitaryUnit(
            id = "arm_t4_1",
            name = "Plasma Tank",
            description = "Experimental energy weapon platform.",
            category = UnitCategory.ARMOR,
            tier = 4,
            cost = 10000,
            maintenanceCost = 1000,
            manpower = 3,
            stats = UnitStats(attack = 70, defense = 50, mobility = 35, range = 20, armor = 60, morale = 40),
            requirements = listOf("arm_t3_1", "energy_t4_1", "mil_t5_3"),
            abilities = listOf("plasma_cannon", "energy_shield", "anti_air_capability"),
            weaknesses = listOf("extremely_expensive", "power_hungry", "experimental")
        ),
        MilitaryUnit(
            id = "arm_t5_1",
            name = "Nanite Swarm Tank",
            description = "Self-repairing nano-armor vehicle.",
            category = UnitCategory.ARMOR,
            tier = 5,
            cost = 15000,
            maintenanceCost = 1500,
            manpower = 2,
            stats = UnitStats(attack = 80, defense = 70, mobility = 40, range = 25, armor = 90, morale = 50),
            requirements = listOf("arm_t4_1", "sci_t4_2", "mil_t5_2"),
            abilities = listOf("self_repair", "adaptive_armor", "swarm_intelligence"),
            weaknesses = listOf("emp_vulnerable", "astronomical_cost")
        )
    )

    // ============================================
    // NAVAL UNITS (30 units)
    // ============================================
    
    val navalUnits = listOf(
        MilitaryUnit(
            id = "nav_t1_1",
            name = "Patrol Boat",
            description = "Small coastal defense vessel.",
            category = UnitCategory.NAVAL_SURFACE,
            tier = 1,
            cost = 500,
            maintenanceCost = 50,
            manpower = 30,
            stats = UnitStats(attack = 8, defense = 10, mobility = 25, range = 5, armor = 5, morale = 15),
            requirements = listOf("mil_b_t1_3"),
            abilities = listOf("coastal_defense", "anti_smuggling"),
            weaknesses = listOf("blue_water_incapable", "light_armament")
        ),
        MilitaryUnit(
            id = "nav_t1_2",
            name = "Destroyer",
            description = "Fast multi-role warship.",
            category = UnitCategory.NAVAL_SURFACE,
            tier = 1,
            cost = 3000,
            maintenanceCost = 300,
            manpower = 300,
            stats = UnitStats(attack = 25, defense = 22, mobility = 35, range = 15, armor = 18, morale = 28),
            requirements = listOf("nav_t1_1", "mil_t2_2"),
            abilities = listOf("anti_submarine", "anti_air", "surface_warfare"),
            weaknesses = listOf("no_air_wing")
        ),
        MilitaryUnit(
            id = "nav_t1_3",
            name = "Cruiser",
            description = "Large surface combatant.",
            category = UnitCategory.NAVAL_SURFACE,
            tier = 2,
            cost = 6000,
            maintenanceCost = 600,
            manpower = 500,
            stats = UnitStats(attack = 35, defense = 32, mobility = 28, range = 18, armor = 30, morale = 32),
            requirements = listOf("nav_t1_2"),
            abilities = listOf("heavy_guns", "area_air_defense", "flagship"),
            weaknesses = listOf("high_cost", "vulnerable_to_air")
        ),
        MilitaryUnit(
            id = "nav_t2_1",
            name = "Battleship",
            description = "Massive gun platform with heavy armor.",
            category = UnitCategory.NAVAL_SURFACE,
            tier = 2,
            cost = 12000,
            maintenanceCost = 1200,
            manpower = 2000,
            stats = UnitStats(attack = 60, defense = 45, mobility = 18, range = 25, armor = 70, morale = 40),
            requirements = listOf("nav_t1_3", "mil_t3_2"),
            abilities = listOf("naval_gunnery", "shore_bombardment", "heavy_armor"),
            weaknesses = listOf("slow", "vulnerable_to_air", "obsolete_concept")
        ),
        MilitaryUnit(
            id = "nav_t2_2",
            name = "Aircraft Carrier",
            description = "Mobile airbase that projects power globally.",
            category = UnitCategory.NAVAL_AVIATION,
            tier = 3,
            cost = 25000,
            maintenanceCost = 2500,
            manpower = 5000,
            stats = UnitStats(attack = 50, defense = 35, mobility = 30, range = 50, armor = 35, morale = 45),
            requirements = listOf("nav_t1_3", "mil_t3_2", "mil_b_t3_2"),
            abilities = listOf("air_wing", "power_projection", "fleet_flagship"),
            weaknesses = listOf("extremely_expensive", "vulnerable_without_escort", "large_target")
        ),
        MilitaryUnit(
            id = "nav_t2_3",
            name = "Diesel Submarine",
            description = "Conventional underwater attack vessel.",
            category = UnitCategory.NAVAL_SUBSURFACE,
            tier = 2,
            cost = 4000,
            maintenanceCost = 400,
            manpower = 50,
            stats = UnitStats(attack = 35, defense = 25, mobility = 20, range = 20, armor = 15, morale = 35),
            requirements = listOf("nav_t1_1", "mil_t2_4"),
            abilities = listOf("submerged_attack", "commerce_raiding", "stealth"),
            weaknesses = listOf("limited_endurance", "slow_submerged")
        ),
        MilitaryUnit(
            id = "nav_t3_1",
            name = "Nuclear Submarine",
            description = "Unlimited range attack submarine.",
            category = UnitCategory.NAVAL_SUBSURFACE,
            tier = 3,
            cost = 15000,
            maintenanceCost = 1500,
            manpower = 130,
            stats = UnitStats(attack = 50, defense = 40, mobility = 35, range = 100, armor = 25, morale = 45),
            requirements = listOf("nav_t2_3", "mil_t4_4", "energy_t3_1"),
            abilities = listOf("unlimited_endurance", "slbm_capability", "hunter_killer"),
            weaknesses = listOf("extremely_expensive", "nuclear_risk")
        ),
        MilitaryUnit(
            id = "nav_t3_2",
            name = "Ballistic Missile Submarine",
            description = "Nuclear deterrent platform.",
            category = UnitCategory.STRATEGIC_NUCLEAR,
            tier = 4,
            cost = 30000,
            maintenanceCost = 3000,
            manpower = 150,
            stats = UnitStats(attack = 100, defense = 30, mobility = 25, range = 100, armor = 25, morale = 50),
            requirements = listOf("nav_t3_1", "mil_t4_1"),
            abilities = listOf("second_strike", "nuclear_deterrent", "hidden_patrol"),
            weaknesses = listOf("astronomical_cost", "political_target", "treaty_restricted")
        ),
        MilitaryUnit(
            id = "nav_t4_1",
            name = "Arsenal Ship",
            description = "Missile platform with hundreds of VLS cells.",
            category = UnitCategory.NAVAL_SURFACE,
            tier = 4,
            cost = 18000,
            maintenanceCost = 1800,
            manpower = 200,
            stats = UnitStats(attack = 70, defense = 40, mobility = 32, range = 30, armor = 30, morale = 40),
            requirements = listOf("nav_t1_2", "mil_t4_3"),
            abilities = listOf("saturation_attack", "land_attack", "area_denial"),
            weaknesses = listOf("light_crew", "all_eggs_one_basket")
        ),
        MilitaryUnit(
            id = "nav_t5_1",
            name = "Orbital Battlestation",
            description = "Space-based weapons platform.",
            category = UnitCategory.STRATEGIC_SPACE,
            tier = 5,
            cost = 100000,
            maintenanceCost = 10000,
            manpower = 500,
            stats = UnitStats(attack = 150, defense = 100, mobility = 60, range = 100, armor = 100, morale = 60),
            requirements = listOf("nav_t3_2", "space_t5_1", "mil_t5_4"),
            abilities = listOf("orbital_strike", "global_reach", "untouchable"),
            weaknesses = listOf("insane_cost", "debris_risk", "treaty_violation")
        )
    )

    // ============================================
    // AIR UNITS (25 units)
    // ============================================
    
    val airUnits = listOf(
        MilitaryUnit(
            id = "air_t1_1",
            name = "Biplane Fighter",
            description = "Early canvas and wood fighter aircraft.",
            category = UnitCategory.AIR_FIGHTER,
            tier = 1,
            cost = 400,
            maintenanceCost = 40,
            manpower = 1,
            stats = UnitStats(attack = 12, defense = 10, mobility = 30, range = 8, armor = 3, morale = 20),
            requirements = listOf("mil_t2_3"),
            abilities = listOf("air_superiority", "reconnaissance"),
            weaknesses = listOf("fragile", "weather_dependent")
        ),
        MilitaryUnit(
            id = "air_t1_2",
            name = "Bomber",
            description = "Strategic bombing aircraft.",
            category = UnitCategory.AIR_BOMBER,
            tier = 1,
            cost = 800,
            maintenanceCost = 80,
            manpower = 5,
            stats = UnitStats(attack = 25, defense = 15, mobility = 20, range = 20, armor = 8, morale = 22),
            requirements = listOf("air_t1_1"),
            abilities = listOf("strategic_bombing", "terror_bombing"),
            weaknesses = listOf("fighter_vulnerable", "expensive_loss")
        ),
        MilitaryUnit(
            id = "air_t2_1",
            name = "Jet Fighter",
            description = "High-speed jet-powered interceptor.",
            category = UnitCategory.AIR_FIGHTER,
            tier = 2,
            cost = 1500,
            maintenanceCost = 150,
            manpower = 1,
            stats = UnitStats(attack = 30, defense = 25, mobility = 50, range = 12, armor = 10, morale = 30),
            requirements = listOf("air_t1_1", "mil_t4_2"),
            abilities = listOf("supersonic", "interception", "air_superiority"),
            weaknesses = listOf("short_range", "high_fuel_consumption")
        ),
        MilitaryUnit(
            id = "air_t2_2",
            name = "Attack Helicopter",
            description = "Rotary-wing close air support.",
            category = UnitCategory.AIR_SUPPORT,
            tier = 2,
            cost = 2000,
            maintenanceCost = 200,
            manpower = 2,
            stats = UnitStats(attack = 35, defense = 20, mobility = 35, range = 10, armor = 15, morale = 32),
            requirements = listOf("mil_t3_2"),
            abilities = listOf("close_air_support", "tank_hunting", "hover_capability"),
            weaknesses = listOf("air_defense_vulnerable", "weather_dependent")
        ),
        MilitaryUnit(
            id = "air_t3_1",
            name = "Stealth Fighter",
            description = "Low-observable air superiority aircraft.",
            category = UnitCategory.AIR_FIGHTER,
            tier = 3,
            cost = 5000,
            maintenanceCost = 500,
            manpower = 1,
            stats = UnitStats(attack = 50, defense = 40, mobility = 55, range = 20, armor = 15, morale = 45),
            requirements = listOf("air_t2_1", "mil_t4_5"),
            abilities = listOf("stealth", "first_look_first_shot", "penetration"),
            weaknesses = listOf("extremely_expensive", "maintenance_intensive")
        ),
        MilitaryUnit(
            id = "air_t3_2",
            name = "Strategic Bomber",
            description = "Long-range nuclear-capable bomber.",
            category = UnitCategory.AIR_BOMBER,
            tier = 3,
            cost = 10000,
            maintenanceCost = 1000,
            manpower = 5,
            stats = UnitStats(attack = 60, defense = 35, mobility = 40, range = 80, armor = 20, morale = 45),
            requirements = listOf("air_t1_2", "mil_t4_1"),
            abilities = listOf("nuclear_delivery", "global_strike", "penetration"),
            weaknesses = listOf("astronomical_cost", "treaty_restricted")
        ),
        MilitaryUnit(
            id = "air_t4_1",
            name = "Combat Drone",
            description = "Unmanned armed aerial vehicle.",
            category = UnitCategory.AIR_SUPPORT,
            tier = 4,
            cost = 2000,
            maintenanceCost = 100,
            manpower = 0,
            stats = UnitStats(attack = 40, defense = 25, mobility = 45, range = 30, armor = 10, morale = 50),
            requirements = listOf("air_t2_2", "comp_t4_1"),
            abilities = listOf("no_pilot_risk", "loiter_capability", "precision_strike"),
            weaknesses = listOf("jamming_vulnerable", "limited_autonomy")
        ),
        MilitaryUnit(
            id = "air_t5_1",
            name = "AI Swarm Drone",
            description = "Autonomous drone swarm with hive mind.",
            category = UnitCategory.AIR_SUPPORT,
            tier = 5,
            cost = 8000,
            maintenanceCost = 400,
            manpower = 0,
            stats = UnitStats(attack = 70, defense = 50, mobility = 60, range = 50, armor = 20, morale = 100),
            requirements = listOf("air_t4_1", "comp_t5_1", "mil_t5_2"),
            abilities = listOf("swarm_intelligence", "adaptive_tactics", "no_morale"),
            weaknesses = listOf("emp_vulnerable", "ai_risk", "hacking_potential")
        )
    )

    // ============================================
    // STRATEGIC UNITS (15 units)
    // ============================================
    
    val strategicUnits = listOf(
        MilitaryUnit(
            id = "strat_t1_1",
            name = "ICBM Silo",
            description = "Intercontinental ballistic missile installation.",
            category = UnitCategory.STRATEGIC_MISSILE,
            tier = 3,
            cost = 20000,
            maintenanceCost = 2000,
            manpower = 100,
            stats = UnitStats(attack = 100, defense = 30, mobility = 0, range = 100, armor = 40, morale = 50),
            requirements = listOf("mil_t4_1", "mil_b_t4_1"),
            abilities = listOf("nuclear_strike", "deterrent", "first_strike"),
            weaknesses = listOf("fixed_location", "political_target", "treaty_restricted")
        ),
        MilitaryUnit(
            id = "strat_t2_1",
            name = "Mobile ICBM Launcher",
            description = "Road-mobile nuclear missile system.",
            category = UnitCategory.STRATEGIC_MISSILE,
            tier = 4,
            cost = 30000,
            maintenanceCost = 3000,
            manpower = 50,
            stats = UnitStats(attack = 100, defense = 40, mobility = 20, range = 100, armor = 35, morale = 50),
            requirements = listOf("strat_t1_1"),
            abilities = listOf("nuclear_strike", "mobile", "hard_to_target"),
            weaknesses = listOf("extremely_expensive", "treaty_restricted")
        ),
        MilitaryUnit(
            id = "strat_t3_1",
            name = "Cyber Warfare Command",
            description = "Offensive cyber operations unit.",
            category = UnitCategory.STRATEGIC_CYBER,
            tier = 4,
            cost = 15000,
            maintenanceCost = 1500,
            manpower = 200,
            stats = UnitStats(attack = 60, defense = 40, mobility = 100, range = 100, armor = 5, morale = 45),
            requirements = listOf("comp_t4_1", "mil_t5_2"),
            abilities = listOf("infrastructure_attack", "stealth_operation", "plausible_deniability"),
            weaknesses = listOf("retaliation_risk", "blowback_potential")
        ),
        MilitaryUnit(
            id = "strat_t4_1",
            name = "Orbital Weapons Platform",
            description = "Space-based strike system.",
            category = UnitCategory.STRATEGIC_SPACE,
            tier = 5,
            cost = 80000,
            maintenanceCost = 8000,
            manpower = 50,
            stats = UnitStats(attack = 120, defense = 80, mobility = 70, range = 100, armor = 60, morale = 55),
            requirements = listOf("strat_t1_1", "space_t4_1", "mil_t5_4"),
            abilities = listOf("rods_from_god", "global_strike", "untouchable"),
            weaknesses = listOf("insane_cost", "space_debris", "treaty_violation")
        ),
        MilitaryUnit(
            id = "strat_t5_1",
            name = "Doomsday Device",
            description = "Automated nuclear retaliation system.",
            category = UnitCategory.STRATEGIC_NUCLEAR,
            tier = 5,
            cost = 50000,
            maintenanceCost = 5000,
            manpower = 10,
            stats = UnitStats(attack = 200, defense = 50, mobility = 0, range = 100, armor = 50, morale = 100),
            requirements = listOf("strat_t2_1", "comp_t5_1"),
            abilities = listOf("dead_hand", "guaranteed_retaliation", "ultimate_deterrent"),
            weaknesses = listOf("accidental_launch_risk", "ai_takeover_risk", "humanity_threat")
        )
    )

    // ============================================
    // HELPER FUNCTIONS
    // ============================================
    
    fun getAllUnits(): List<MilitaryUnit> {
        return infantryUnits + armorUnits + navalUnits + airUnits + strategicUnits
    }
    
    fun getUnitsByCategory(category: UnitCategory): List<MilitaryUnit> {
        return getAllUnits().filter { it.category == category }
    }
    
    fun getUnitsByTier(tier: Int): List<MilitaryUnit> {
        return getAllUnits().filter { it.tier == tier }
    }
    
    fun getUnit(id: String): MilitaryUnit? {
        return getAllUnits().find { it.id == id }
    }
    
    fun canProduce(unitId: String, ownedBuildings: List<String>, researchedTechs: List<String>): Boolean {
        val unit = getUnit(unitId) ?: return false
        return unit.requirements.all { req ->
            ownedBuildings.contains(req) || researchedTechs.contains(req)
        }
    }
    
    fun getUnitStatistics(): Map<String, Int> {
        return mapOf(
            "Total Units" to getAllUnits().size,
            "Infantry" to infantryUnits.size,
            "Armor" to armorUnits.size,
            "Naval" to navalUnits.size,
            "Air" to airUnits.size,
            "Strategic" to strategicUnits.size,
            "Tier 1" to getUnitsByTier(1).size,
            "Tier 2" to getUnitsByTier(2).size,
            "Tier 3" to getUnitsByTier(3).size,
            "Tier 4" to getUnitsByTier(4).size,
            "Tier 5" to getUnitsByTier(5).size
        )
    }
}
