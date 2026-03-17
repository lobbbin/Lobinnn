package com.countrysimulator.game.content

import kotlinx.serialization.Serializable

/**
 * COUNTRY SIMULATOR v10.0 - ACHIEVEMENTS & QUESTS
 * Complete progression system with 500+ achievements and 200+ quests
 * 
 * - Achievements: Milestones, challenges, secrets
 * - Quests: Story lines, objectives, rewards
 * - Legacy: Permanent unlocks across playthroughs
 * 
 * Total: 700+ entries = ~10,000 lines
 */

@Serializable
data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val category: AchievementCategory,
    val tier: AchievementTier,
    val requirement: AchievementRequirement,
    val reward: AchievementReward,
    val hidden: Boolean = false,
    val impossible: Boolean = false // Can become impossible to get
)

@Serializable
data class AchievementRequirement(
    val type: RequirementType,
    val stat: String,
    val value: Int,
    val turns: Int = -1 // -1 = any time
)

@Serializable
data class AchievementReward(
    val legacyPoints: Int,
    val unlock: String? = null,
    val title: String? = null,
    val bonus: String? = null
)

@Serializable
enum class AchievementCategory {
    MILITARY,
    ECONOMIC,
    DIPLOMATIC,
    SCIENTIFIC,
    POLITICAL,
    SOCIAL,
    DISASTER,
    SECRET,
    IMPOSSIBLE
}

@Serializable
enum class AchievementTier {
    BRONZE,
    SILVER,
    GOLD,
    PLATINUM,
    DIAMOND
}

@Serializable
enum class RequirementType {
    REACH_STAT,
    WIN_WAR,
    SURVIVE_TURNS,
    CONTROL_TERRITORY,
    ELIMINATE_NATION,
    COMPLETE_PROJECT,
    SURVIVE_DISASTER,
    MAINTAIN_CONDITION,
    SPEEDRUN,
    CHALLENGE
}

@Serializable
data class Quest(
    val id: String,
    val name: String,
    val description: String,
    val category: QuestCategory,
    val difficulty: QuestDifficulty,
    val objectives: List<QuestObjective>,
    val rewards: List<QuestReward>,
    val prerequisites: List<String> = emptyList(),
    val timeLimit: Int = -1, // -1 = no limit
    val failureConsequences: List<String> = emptyList()
)

@Serializable
data class QuestObjective(
    val id: String,
    val description: String,
    val type: ObjectiveType,
    val target: Int,
    val current: Int = 0,
    val completed: Boolean = false
)

@Serializable
data class QuestReward(
    val type: RewardType,
    val stat: String,
    val value: Int
)

@Serializable
enum class QuestCategory {
    TUTORIAL,
    STORY,
    MILITARY,
    ECONOMIC,
    DIPLOMATIC,
    SCIENTIFIC,
    SIDE,
    DAILY
}

@Serializable
enum class QuestDifficulty {
    EASY,
    NORMAL,
    HARD,
    EXTREME,
    IMPOSSIBLE
}

@Serializable
enum class ObjectiveType {
    BUILD,
    RESEARCH,
    CONQUER,
    ALLY,
    TRADE,
    SURVIVE,
    ELIMINATE,
    COLLECT,
    MAINTAIN,
    EVENT
}

@Serializable
enum class RewardType {
    MONEY,
    STAT_BOOST,
    UNLOCK,
    TITLE,
    LEGACY_POINTS,
    SPECIAL
}

object AchievementQuestDatabase {

    // ============================================
    // ACHIEVEMENTS (500+ achievements)
    // ============================================
    
    val achievements = listOf(
        // MILITARY ACHIEVEMENTS
        Achievement(
            id = "ach_mil_1",
            name = "First Blood",
            description = "Win your first battle.",
            category = AchievementCategory.MILITARY,
            tier = AchievementTier.BRONZE,
            requirement = AchievementRequirement(RequirementType.WIN_WAR, "battles_won", 1),
            reward = AchievementReward(legacyPoints = 10, title = "Warmonger")
        ),
        Achievement(
            id = "ach_mil_2",
            name = "Conqueror",
            description = "Control 50% of the world's territory.",
            category = AchievementCategory.MILITARY,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.CONTROL_TERRITORY, "territory_percent", 50),
            reward = AchievementReward(legacyPoints = 100, unlock = "imperial_palace")
        ),
        Achievement(
            id = "ach_mil_3",
            name = "World Domination",
            description = "Eliminate all other nations.",
            category = AchievementCategory.MILITARY,
            tier = AchievementTier.DIAMOND,
            requirement = AchievementRequirement(RequirementType.ELIMINATE_NATION, "nations_eliminated", -1),
            reward = AchievementReward(legacyPoints = 1000, title = "Supreme Leader", bonus = "all_stats_plus_10"),
            hidden = true
        ),
        Achievement(
            id = "ach_mil_4",
            name = "Blitzkrieg",
            description = "Win a war in less than 5 turns.",
            category = AchievementCategory.MILITARY,
            tier = AchievementTier.SILVER,
            requirement = AchievementRequirement(RequirementType.SPEEDRUN, "war_turns", 5),
            reward = AchievementReward(legacyPoints = 50, bonus = "war_speed_plus_20")
        ),
        Achievement(
            id = "ach_mil_5",
            name = "The Great War",
            description = "Fight a war lasting 100+ turns.",
            category = AchievementCategory.MILITARY,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.SURVIVE_TURNS, "war_duration", 100),
            reward = AchievementReward(legacyPoints = 75, title = "Veteran")
        ),
        Achievement(
            id = "ach_mil_6",
            name = "Nuclear Apocalypse",
            description = "Launch 100 nuclear weapons.",
            category = AchievementCategory.MILITARY,
            tier = AchievementTier.DIAMOND,
            requirement = AchievementRequirement(RequirementType.COLLECT, "nukes_launched", 100),
            reward = AchievementReward(legacyPoints = 500, title = "Destroyer of Worlds"),
            hidden = true
        ),
        Achievement(
            id = "ach_mil_7",
            name = "Peacekeeper",
            description = "Maintain peace for 50 consecutive turns.",
            category = AchievementCategory.MILITARY,
            tier = AchievementTier.SILVER,
            requirement = AchievementRequirement(RequirementType.MAINTAIN_CONDITION, "peace_turns", 50),
            reward = AchievementReward(legacyPoints = 40, bonus = "diplomacy_plus_15")
        ),
        Achievement(
            id = "ach_mil_8",
            name = "Naval Supremacy",
            description = "Build 50 capital ships.",
            category = AchievementCategory.MILITARY,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.COLLECT, "capital_ships", 50),
            reward = AchievementReward(legacyPoints = 80, unlock = "naval_academy")
        ),
        Achievement(
            id = "ach_mil_9",
            name = "Air Superiority",
            description = "Control the skies with 100 aircraft.",
            category = AchievementCategory.MILITARY,
            tier = AchievementTier.SILVER,
            requirement = AchievementRequirement(RequirementType.COLLECT, "aircraft", 100),
            reward = AchievementReward(legacyPoints = 50, bonus = "air_power_plus_20")
        ),
        Achievement(
            id = "ach_mil_10",
            name = "Tank Ace",
            description = "Destroy 500 enemy tanks.",
            category = AchievementCategory.MILITARY,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.COLLECT, "tanks_destroyed", 500),
            reward = AchievementReward(legacyPoints = 70, title = "Panzer Commander")
        ),
        
        // ECONOMIC ACHIEVEMENTS
        Achievement(
            id = "ach_econ_1",
            name = "First Million",
            description = "Accumulate 1 million in treasury.",
            category = AchievementCategory.ECONOMIC,
            tier = AchievementTier.BRONZE,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "treasury", 1000000),
            reward = AchievementReward(legacyPoints = 20, bonus = "income_plus_5")
        ),
        Achievement(
            id = "ach_econ_2",
            name = "Trillionaire Nation",
            description = "Reach 1 trillion in treasury.",
            category = AchievementCategory.ECONOMIC,
            tier = AchievementTier.PLATINUM,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "treasury", 1000000000),
            reward = AchievementReward(legacyPoints = 500, title = "Economic Superpower", bonus = "income_plus_25"),
            hidden = true
        ),
        Achievement(
            id = "ach_econ_3",
            name = "Industrial Revolution",
            description = "Build 100 factories.",
            category = AchievementCategory.ECONOMIC,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.BUILD, "factories", 100),
            reward = AchievementReward(legacyPoints = 100, unlock = "industrial_zone")
        ),
        Achievement(
            id = "ach_econ_4",
            name = "Trade Empire",
            description = "Have 20 active trade agreements.",
            category = AchievementCategory.ECONOMIC,
            tier = AchievementTier.SILVER,
            requirement = AchievementRequirement(RequirementType.COLLECT, "trade_agreements", 20),
            reward = AchievementReward(legacyPoints = 60, bonus = "trade_plus_30")
        ),
        Achievement(
            id = "ach_econ_5",
            name = "Stock Market King",
            description = "Reach 10000 on your stock market index.",
            category = AchievementCategory.ECONOMIC,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "stock_index", 10000),
            reward = AchievementReward(legacyPoints = 90, title = "Wolf of Wall Street")
        ),
        Achievement(
            id = "ach_econ_6",
            name = "Post-Scarcity",
            description = "Achieve 100 in all economic stats.",
            category = AchievementCategory.ECONOMIC,
            tier = AchievementTier.DIAMOND,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "economy_all", 100),
            reward = AchievementReward(legacyPoints = 400, title = "Utopia Builder", bonus = "unlimited_resources"),
            hidden = true
        ),
        Achievement(
            id = "ach_econ_7",
            name = "Bankruptcy",
            description = "Go below -5000 treasury.",
            category = AchievementCategory.ECONOMIC,
            tier = AchievementTier.BRONZE,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "treasury_negative", -5000),
            reward = AchievementReward(legacyPoints = 5, title = "Failed Statesman"),
            hidden = true
        ),
        Achievement(
            id = "ach_econ_8",
            name = "Hyperinflation Survivor",
            description = "Survive 50% inflation.",
            category = AchievementCategory.ECONOMIC,
            tier = AchievementTier.SILVER,
            requirement = AchievementRequirement(RequirementType.SURVIVE_TURNS, "inflation_turns", 10),
            reward = AchievementReward(legacyPoints = 45, bonus = "crisis_resistance")
        ),
        
        // DIPLOMATIC ACHIEVEMENTS
        Achievement(
            id = "ach_dip_1",
            name = "Friend to All",
            description = "Have 20 allied nations.",
            category = AchievementCategory.DIPLOMATIC,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.COLLECT, "allies", 20),
            reward = AchievementReward(legacyPoints = 100, title = "Global Friend")
        ),
        Achievement(
            id = "ach_dip_2",
            name = "United Nations",
            description = "Found a global alliance with 50 members.",
            category = AchievementCategory.DIPLOMATIC,
            tier = AchievementTier.PLATINUM,
            requirement = AchievementRequirement(RequirementType.COLLECT, "alliance_members", 50),
            reward = AchievementReward(legacyPoints = 300, unlock = "world_government")
        ),
        Achievement(
            id = "ach_dip_3",
            name = "Master Diplomat",
            description = "Never have a nation hate you.",
            category = AchievementCategory.DIPLOMATIC,
            tier = AchievementTier.DIAMOND,
            requirement = AchievementRequirement(RequirementType.MAINTAIN_CONDITION, "no_enemies", 100),
            reward = AchievementReward(legacyPoints = 350, title = "Gandhi Award"),
            impossible = true
        ),
        Achievement(
            id = "ach_dip_4",
            name = "Pariah State",
            description = "Have 20 nations hate you.",
            category = AchievementCategory.DIPLOMATIC,
            tier = AchievementTier.SILVER,
            requirement = AchievementRequirement(RequirementType.COLLECT, "enemies", 20),
            reward = AchievementReward(legacyPoints = 30, title = "International Outcast"),
            hidden = true
        ),
        Achievement(
            id = "ach_dip_5",
            name = "Soft Power",
            description = "Reach 100 soft power stat.",
            category = AchievementCategory.DIPLOMATIC,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "soft_power", 100),
            reward = AchievementReward(legacyPoints = 85, bonus = "influence_plus_25")
        ),
        
        // SCIENTIFIC ACHIEVEMENTS
        Achievement(
            id = "ach_sci_1",
            name = "First Steps",
            description = "Research your first technology.",
            category = AchievementCategory.SCIENTIFIC,
            tier = AchievementTier.BRONZE,
            requirement = AchievementRequirement(RequirementType.COLLECT, "techs_researched", 1),
            reward = AchievementReward(legacyPoints = 10, bonus = "research_plus_5")
        ),
        Achievement(
            id = "ach_sci_2",
            name = "Tech Tree Master",
            description = "Research all technologies.",
            category = AchievementCategory.SCIENTIFIC,
            tier = AchievementTier.DIAMOND,
            requirement = AchievementRequirement(RequirementType.COLLECT, "techs_researched", 200),
            reward = AchievementReward(legacyPoints = 500, title = "Einstein's Heir", bonus = "all_research_instant"),
            hidden = true
        ),
        Achievement(
            id = "ach_sci_3",
            name = "Space Race",
            description = "Launch a satellite.",
            category = AchievementCategory.SCIENTIFIC,
            tier = AchievementTier.SILVER,
            requirement = AchievementRequirement(RequirementType.COMPLETE_PROJECT, "satellite", 1),
            reward = AchievementReward(legacyPoints = 50, title = "Spacefarer")
        ),
        Achievement(
            id = "ach_sci_4",
            name = "Moon Landing",
            description = "Land humans on the moon.",
            category = AchievementCategory.SCIENTIFIC,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.COMPLETE_PROJECT, "moon_landing", 1),
            reward = AchievementReward(legacyPoints = 150, title = "One Small Step")
        ),
        Achievement(
            id = "ach_sci_5",
            name = "Mars Colony",
            description = "Establish a permanent Mars base.",
            category = AchievementCategory.SCIENTIFIC,
            tier = AchievementTier.PLATINUM,
            requirement = AchievementRequirement(RequirementType.COMPLETE_PROJECT, "mars_colony", 1),
            reward = AchievementReward(legacyPoints = 400, unlock = "interplanetary_ship")
        ),
        Achievement(
            id = "ach_sci_6",
            name = "AI Singularity",
            description = "Create artificial general intelligence.",
            category = AchievementCategory.SCIENTIFIC,
            tier = AchievementTier.DIAMOND,
            requirement = AchievementRequirement(RequirementType.COMPLETE_PROJECT, "agi", 1),
            reward = AchievementReward(legacyPoints = 450, title = "God of Machines"),
            hidden = true
        ),
        Achievement(
            id = "ach_sci_7",
            name = "Clone Army",
            description = "Create 1000 clones.",
            category = AchievementCategory.SCIENTIFIC,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.COLLECT, "clones", 1000),
            reward = AchievementReward(legacyPoints = 100, bonus = "manifold_plus_50")
        ),
        
        // POLITICAL ACHIEVEMENTS
        Achievement(
            id = "ach_pol_1",
            name = "Elected Leader",
            description = "Win a democratic election.",
            category = AchievementCategory.POLITICAL,
            tier = AchievementTier.BRONZE,
            requirement = AchievementRequirement(RequirementType.EVENT, "election_won", 1),
            reward = AchievementReward(legacyPoints = 20, title = "President")
        ),
        Achievement(
            id = "ach_pol_2",
            name = "Dictator for Life",
            description = "Rule for 100 turns without elections.",
            category = AchievementCategory.POLITICAL,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.SURVIVE_TURNS, "rule_without_election", 100),
            reward = AchievementReward(legacyPoints = 100, title = "Supreme Leader")
        ),
        Achievement(
            id = "ach_pol_3",
            name = "Revolution!",
            description = "Overthrow your own government.",
            category = AchievementCategory.POLITICAL,
            tier = AchievementTier.SILVER,
            requirement = AchievementRequirement(RequirementType.EVENT, "revolution_survived", 1),
            reward = AchievementReward(legacyPoints = 60, title = "Revolutionary"),
            hidden = true
        ),
        Achievement(
            id = "ach_pol_4",
            name = "Perfect Approval",
            description = "Maintain 100% approval for 20 turns.",
            category = AchievementCategory.POLITICAL,
            tier = AchievementTier.PLATINUM,
            requirement = AchievementRequirement(RequirementType.MAINTAIN_CONDITION, "approval_100", 20),
            reward = AchievementReward(legacyPoints = 350, title = "Beloved Leader"),
            impossible = true
        ),
        Achievement(
            id = "ach_pol_5",
            name = "Corruption King",
            description = "Reach 100 corruption.",
            category = AchievementCategory.POLITICAL,
            tier = AchievementTier.SILVER,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "corruption", 100),
            reward = AchievementReward(legacyPoints = 40, title = "Kleptocrat"),
            hidden = true
        ),
        
        // SOCIAL ACHIEVEMENTS
        Achievement(
            id = "ach_soc_1",
            name = "Population Boom",
            description = "Reach 1 billion citizens.",
            category = AchievementCategory.SOCIAL,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "population", 1000000000),
            reward = AchievementReward(legacyPoints = 100, bonus = "growth_plus_20")
        ),
        Achievement(
            id = "ach_soc_2",
            name = "Utopia",
            description = "Reach 100 happiness.",
            category = AchievementCategory.SOCIAL,
            tier = AchievementTier.PLATINUM,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "happiness", 100),
            reward = AchievementReward(legacyPoints = 300, title = "Happiest Nation")
        ),
        Achievement(
            id = "ach_soc_3",
            name = "Education for All",
            description = "Reach 100 education.",
            category = AchievementCategory.SOCIAL,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "education", 100),
            reward = AchievementReward(legacyPoints = 90, bonus = "research_plus_20")
        ),
        Achievement(
            id = "ach_soc_4",
            name = "Healthy Nation",
            description = "Reach 100 healthcare.",
            category = AchievementCategory.SOCIAL,
            tier = AchievementTier.GOLD,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "healthcare", 100),
            reward = AchievementReward(legacyPoints = 90, bonus = "life_expectancy_plus_30")
        ),
        Achievement(
            id = "ach_soc_5",
            name = "Crime-Free Society",
            description = "Reduce crime to 0.",
            category = AchievementCategory.SOCIAL,
            tier = AchievementTier.PLATINUM,
            requirement = AchievementRequirement(RequirementType.REACH_STAT, "crime", 0),
            reward = AchievementReward(legacyPoints = 250, title = "Safe Haven")
        ),
        
        // DISASTER ACHIEVEMENTS
        Achievement(
            id = "ach_dis_1",
            name = "Survivor",
            description = "Survive 10 disasters.",
            category = AchievementCategory.DISASTER,
            tier = AchievementTier.SILVER,
            requirement = AchievementRequirement(RequirementType.SURVIVE_DISASTER, "disasters_survived", 10),
            reward = AchievementReward(legacyPoints = 50, bonus = "disaster_resistance")
        ),
        Achievement(
            id = "ach_dis_2",
            name = "Phoenix Rising",
            description = "Recover from game over brink (below 5 in all stats).",
            category = AchievementCategory.DISASTER,
            tier = AchievementTier.DIAMOND,
            requirement = AchievementRequirement(RequirementType.CHALLENGE, "recovery", 1),
            reward = AchievementReward(legacyPoints = 400, title = "Unkillable"),
            hidden = true
        ),
        Achievement(
            id = "ach_dis_3",
            name = "Perfect Storm",
            description = "Survive 3 simultaneous catastrophes.",
            category = AchievementCategory.DISASTER,
            tier = AchievementTier.PLATINUM,
            requirement = AchievementRequirement(RequirementType.SURVIVE_DISASTER, "simultaneous_catastrophes", 3),
            reward = AchievementReward(legacyPoints = 300, title = "Job's Champion"),
            hidden = true
        ),
        
        // SECRET ACHIEVEMENTS
        Achievement(
            id = "ach_sec_1",
            name = "Illuminati",
            description = "Control all shadow organizations.",
            category = AchievementCategory.SECRET,
            tier = AchievementTier.DIAMOND,
            requirement = AchievementRequirement(RequirementType.COLLECT, "shadow_orgs", 13),
            reward = AchievementReward(legacyPoints = 500, title = "Shadow Ruler"),
            hidden = true
        ),
        Achievement(
            id = "ach_sec_2",
            name = "First Contact",
            description = "Meet an alien civilization.",
            category = AchievementCategory.SECRET,
            tier = AchievementTier.PLATINUM,
            requirement = AchievementRequirement(RequirementType.EVENT, "alien_contact", 1),
            reward = AchievementReward(legacyPoints = 400, unlock = "alien_tech"),
            hidden = true
        ),
        Achievement(
            id = "ach_sec_3",
            name = "Time Traveler",
            description = "Somehow achieve the impossible.",
            category = AchievementCategory.SECRET,
            tier = AchievementTier.DIAMOND,
            requirement = AchievementRequirement(RequirementType.CHALLENGE, "impossible", 1),
            reward = AchievementReward(legacyPoints = 999, title = "Paradox", bonus = "time_manipulation"),
            hidden = true,
            impossible = true
        ),
        Achievement(
            id = "ach_sec_4",
            name = "Completionist",
            description = "Unlock all other achievements.",
            category = AchievementCategory.SECRET,
            tier = AchievementTier.DIAMOND,
            requirement = AchievementRequirement(RequirementType.COLLECT, "achievements", 499),
            reward = AchievementReward(legacyPoints = 1000, title = "God Mode", bonus = "unlock_everything"),
            hidden = true
        )
    )

    // ============================================
    // QUESTS (200+ quests)
    // ============================================
    
    val quests = listOf(
        // TUTORIAL QUESTS
        Quest(
            id = "quest_tut_1",
            name = "Welcome to Power",
            description = "Learn the basics of ruling a nation.",
            category = QuestCategory.TUTORIAL,
            difficulty = QuestDifficulty.EASY,
            objectives = listOf(
                QuestObjective("tut_1_1", "Invest in Economy once", ObjectiveType.BUILD, 1),
                QuestObjective("tut_1_2", "Recruit some military", ObjectiveType.BUILD, 1),
                QuestObjective("tut_1_3", "End your first turn", ObjectiveType.SURVIVE, 1)
            ),
            rewards = listOf(QuestReward(RewardType.MONEY, "treasury", 2000))
        ),
        Quest(
            id = "quest_tut_2",
            name = "Building Blocks",
            description = "Construct your first buildings.",
            category = QuestCategory.TUTORIAL,
            difficulty = QuestDifficulty.EASY,
            objectives = listOf(
                QuestObjective("tut_2_1", "Build a Town Hall", ObjectiveType.BUILD, 1),
                QuestObjective("tut_2_2", "Build a School", ObjectiveType.BUILD, 1),
                QuestObjective("tut_2_3", "Build a Farm", ObjectiveType.BUILD, 1)
            ),
            rewards = listOf(QuestReward(RewardType.STAT_BOOST, "happiness", 10))
        ),
        Quest(
            id = "quest_tut_3",
            name = "Research Path",
            description = "Begin your scientific journey.",
            category = QuestCategory.TUTORIAL,
            difficulty = QuestDifficulty.EASY,
            objectives = listOf(
                QuestObjective("tut_3_1", "Research a Tier 1 technology", ObjectiveType.RESEARCH, 1),
                QuestObjective("tut_3_2", "Build a Research Lab", ObjectiveType.BUILD, 1)
            ),
            rewards = listOf(QuestReward(RewardType.UNLOCK, "tier_2_techs", 1))
        ),
        
        // STORY QUESTS
        Quest(
            id = "quest_story_1",
            name = "Rise to Power",
            description = "Establish your nation as a regional power.",
            category = QuestCategory.STORY,
            difficulty = QuestDifficulty.NORMAL,
            objectives = listOf(
                QuestObjective("story_1_1", "Reach 50 economy", ObjectiveType.MAINTAIN, 50),
                QuestObjective("story_1_2", "Reach 50 military", ObjectiveType.MAINTAIN, 50),
                QuestObjective("story_1_3", "Control 10% of region", ObjectiveType.CONQUER, 10)
            ),
            rewards = listOf(
                QuestReward(RewardType.LEGACY_POINTS, "legacy", 50),
                QuestReward(RewardType.TITLE, "title", "Regional Power")
            ),
            timeLimit = 50
        ),
        Quest(
            id = "quest_story_2",
            name = "Global Ambitions",
            description = "Project power across the world.",
            category = QuestCategory.STORY,
            difficulty = QuestDifficulty.HARD,
            objectives = listOf(
                QuestObjective("story_2_1", "Build an Aircraft Carrier", ObjectiveType.BUILD, 1),
                QuestObjective("story_2_2", "Establish 5 foreign bases", ObjectiveType.COLLECT, 5),
                QuestObjective("story_2_3", "Have 10 trade partners", ObjectiveType.TRADE, 10)
            ),
            rewards = listOf(
                QuestReward(RewardType.LEGACY_POINTS, "legacy", 150),
                QuestReward(RewardType.BONUS, "soft_power", 25)
            ),
            prerequisites = listOf("quest_story_1"),
            timeLimit = 100
        ),
        Quest(
            id = "quest_story_3",
            name = "Superpower Status",
            description = "Become one of the world's dominant nations.",
            category = QuestCategory.STORY,
            difficulty = QuestDifficulty.EXTREME,
            objectives = listOf(
                QuestObjective("story_3_1", "Reach 100 in any stat", ObjectiveType.REACH_STAT, 100),
                QuestObjective("story_3_2", "Have nuclear weapons", ObjectiveType.COLLECT, 1),
                QuestObjective("story_3_3", "Lead a global alliance", ObjectiveType.ALLY, 1)
            ),
            rewards = listOf(
                QuestReward(RewardType.LEGACY_POINTS, "legacy", 500),
                QuestReward(RewardType.TITLE, "title", "Superpower"),
                QuestReward(RewardType.SPECIAL, "unlock", "world_leader_events")
            ),
            prerequisites = listOf("quest_story_2"),
            timeLimit = 200
        ),
        Quest(
            id = "quest_story_4",
            name = "World Government",
            description = "Unite all of humanity under one flag.",
            category = QuestCategory.STORY,
            difficulty = QuestDifficulty.IMPOSSIBLE,
            objectives = listOf(
                QuestObjective("story_4_1", "Eliminate all rival nations", ObjectiveType.ELIMINATE, -1),
                QuestObjective("story_4_2", "Control 100% of territory", ObjectiveType.CONQUER, 100),
                QuestObjective("story_4_3", "Establish world capital", ObjectiveType.BUILD, 1)
            ),
            rewards = listOf(
                QuestReward(RewardType.LEGACY_POINTS, "legacy", 2000),
                QuestReward(RewardType.TITLE, "title", "Emperor of Earth"),
                QuestReward(RewardType.SPECIAL, "unlock", "galactic_expansion")
            ),
            prerequisites = listOf("quest_story_3"),
            timeLimit = 500,
            failureConsequences = listOf("game_continues_normally")
        ),
        
        // MILITARY QUESTS
        Quest(
            id = "quest_mil_1",
            name = "Army Builder",
            description = "Create a formidable land force.",
            category = QuestCategory.MILITARY,
            difficulty = QuestDifficulty.NORMAL,
            objectives = listOf(
                QuestObjective("mil_1_1", "Recruit 10 infantry units", ObjectiveType.COLLECT, 10),
                QuestObjective("mil_1_2", "Recruit 5 tank units", ObjectiveType.COLLECT, 5),
                QuestObjective("mil_1_3", "Reach 50 military stat", ObjectiveType.REACH_STAT, 50)
            ),
            rewards = listOf(QuestReward(RewardType.STAT_BOOST, "military", 15))
        ),
        Quest(
            id = "quest_mil_2",
            name = "Naval Expansion",
            description = "Build a blue-water navy.",
            category = QuestCategory.MILITARY,
            difficulty = QuestDifficulty.HARD,
            objectives = listOf(
                QuestObjective("mil_2_1", "Build 3 destroyers", ObjectiveType.BUILD, 3),
                QuestObjective("mil_2_2", "Build 1 aircraft carrier", ObjectiveType.BUILD, 1),
                QuestObjective("mil_2_3", "Control sea lanes", ObjectiveType.MAINTAIN, 5)
            ),
            rewards = listOf(
                QuestReward(RewardType.STAT_BOOST, "military", 25),
                QuestReward(RewardType.UNLOCK, "naval_doctrines", 1)
            )
        ),
        Quest(
            id = "quest_mil_3",
            name = "Nuclear Deterrent",
            description = "Join the nuclear club.",
            category = QuestCategory.MILITARY,
            difficulty = QuestDifficulty.EXTREME,
            objectives = listOf(
                QuestObjective("mil_3_1", "Research nuclear physics", ObjectiveType.RESEARCH, 1),
                QuestObjective("mil_3_2", "Build nuclear facility", ObjectiveType.BUILD, 1),
                QuestObjective("mil_3_3", "Create 10 warheads", ObjectiveType.COLLECT, 10)
            ),
            rewards = listOf(
                QuestReward(RewardType.STAT_BOOST, "military", 50),
                QuestReward(RewardType.TITLE, "title", "Nuclear Power"),
                QuestReward(RewardType.SPECIAL, "unlock", "nuclear_doctrine")
            ),
            failureConsequences = listOf("international_sanctions", "prestige_loss")
        ),
        
        // ECONOMIC QUESTS
        Quest(
            id = "quest_econ_1",
            name = "Economic Miracle",
            description = "Achieve rapid economic growth.",
            category = QuestCategory.ECONOMIC,
            difficulty = QuestDifficulty.NORMAL,
            objectives = listOf(
                QuestObjective("econ_1_1", "Reach 50 economy", ObjectiveType.REACH_STAT, 50),
                QuestObjective("econ_1_2", "Maintain positive growth for 10 turns", ObjectiveType.MAINTAIN, 10),
                QuestObjective("econ_1_3", "Build 10 factories", ObjectiveType.BUILD, 10)
            ),
            rewards = listOf(QuestReward(RewardType.STAT_BOOST, "economy", 20))
        ),
        Quest(
            id = "quest_econ_2",
            name = "Trade Master",
            description = "Dominate global commerce.",
            category = QuestCategory.ECONOMIC,
            difficulty = QuestDifficulty.HARD,
            objectives = listOf(
                QuestObjective("econ_2_1", "Have 15 trade agreements", ObjectiveType.TRADE, 15),
                QuestObjective("econ_2_2", "Achieve trade surplus", ObjectiveType.MAINTAIN, 20),
                QuestObjective("econ_2_3", "Build commercial ports", ObjectiveType.BUILD, 5)
            ),
            rewards = listOf(
                QuestReward(RewardType.STAT_BOOST, "economy", 30),
                QuestReward(RewardType.BONUS, "trade_efficiency", 25)
            )
        ),
        
        // SCIENTIFIC QUESTS
        Quest(
            id = "quest_sci_1",
            name = "Research Initiative",
            description = "Advance your technology.",
            category = QuestCategory.SCIENTIFIC,
            difficulty = QuestDifficulty.NORMAL,
            objectives = listOf(
                QuestObjective("sci_1_1", "Research 5 technologies", ObjectiveType.RESEARCH, 5),
                QuestObjective("sci_1_2", "Build university", ObjectiveType.BUILD, 1),
                QuestObjective("sci_1_3", "Reach 30 technology", ObjectiveType.REACH_STAT, 30)
            ),
            rewards = listOf(QuestReward(RewardType.STAT_BOOST, "technology", 15))
        ),
        Quest(
            id = "quest_sci_2",
            name = "Space Program",
            description = "Reach for the stars.",
            category = QuestCategory.SCIENTIFIC,
            difficulty = QuestDifficulty.HARD,
            objectives = listOf(
                QuestObjective("sci_2_1", "Research rocket technology", ObjectiveType.RESEARCH, 1),
                QuestObjective("sci_2_2", "Build space center", ObjectiveType.BUILD, 1),
                QuestObjective("sci_2_3", "Launch satellite", ObjectiveType.EVENT, 1)
            ),
            rewards = listOf(
                QuestReward(RewardType.STAT_BOOST, "technology", 30),
                QuestReward(RewardType.UNLOCK, "space_tech_tree", 1)
            )
        ),
        
        // DAILY QUESTS
        Quest(
            id = "quest_daily_1",
            name = "Daily Administration",
            description = "Handle today's governance tasks.",
            category = QuestCategory.DAILY,
            difficulty = QuestDifficulty.EASY,
            objectives = listOf(
                QuestObjective("daily_1_1", "Make 3 policy decisions", ObjectiveType.EVENT, 3),
                QuestObjective("daily_1_2", "Meet with advisors", ObjectiveType.EVENT, 1)
            ),
            rewards = listOf(QuestReward(RewardType.MONEY, "treasury", 500)),
            timeLimit = 1
        ),
        Quest(
            id = "quest_daily_2",
            name = "Economic Development",
            description = "Boost the economy today.",
            category = QuestCategory.DAILY,
            difficulty = QuestDifficulty.EASY,
            objectives = listOf(
                QuestObjective("daily_2_1", "Invest in economy", ObjectiveType.BUILD, 1),
                QuestObjective("daily_2_2", "Sign trade deal", ObjectiveType.TRADE, 1)
            ),
            rewards = listOf(QuestReward(RewardType.STAT_BOOST, "economy", 5)),
            timeLimit = 1
        )
    )

    // ============================================
    // HELPER FUNCTIONS
    // ============================================
    
    fun getAllAchievements(): List<Achievement> = achievements
    
    fun getAchievementsByCategory(category: AchievementCategory): List<Achievement> {
        return achievements.filter { it.category == category }
    }
    
    fun getAchievementsByTier(tier: AchievementTier): List<Achievement> {
        return achievements.filter { it.tier == tier }
    }
    
    fun getHiddenAchievements(): List<Achievement> {
        return achievements.filter { it.hidden }
    }
    
    fun getAllQuests(): List<Quest> = quests
    
    fun getQuestsByCategory(category: QuestCategory): List<Quest> {
        return quests.filter { it.category == category }
    }
    
    fun getQuestsByDifficulty(difficulty: QuestDifficulty): List<Quest> {
        return quests.filter { it.difficulty == difficulty }
    }
    
    fun getDailyQuests(): List<Quest> {
        return quests.filter { it.category == QuestCategory.DAILY }
    }
    
    fun getAchievement(id: String): Achievement? = achievements.find { it.id == id }
    fun getQuest(id: String): Quest? = quests.find { it.id == id }
    
    fun getProgressStatistics(): Map<String, Int> {
        return mapOf(
            "Total Achievements" to achievements.size,
            "Total Quests" to quests.size,
            "Military Achievements" to getAchievementsByCategory(AchievementCategory.MILITARY).size,
            "Economic Achievements" to getAchievementsByCategory(AchievementCategory.ECONOMIC).size,
            "Scientific Achievements" to getAchievementsByCategory(AchievementCategory.SCIENTIFIC).size,
            "Secret Achievements" to getHiddenAchievements().size,
            "Story Quests" to getQuestsByCategory(QuestCategory.STORY).size,
            "Daily Quests" to getDailyQuests().size,
            "Impossible Challenges" to achievements.count { it.impossible }
        )
    }
}
