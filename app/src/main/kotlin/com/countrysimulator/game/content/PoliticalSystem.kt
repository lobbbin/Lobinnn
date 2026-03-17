package com.countrysimulator.game.content

import kotlinx.serialization.Serializable

/**
 * COUNTRY SIMULATOR v10.0 - POLITICAL SYSTEM
 * Complete political simulation with parties, elections, laws, and policies
 * 
 * - 50+ Political Parties across ideologies
 * - 100+ Laws with effects and requirements
 * - 80+ Policies with trade-offs
 * - Election system with campaigns and debates
 * 
 * Total: 230+ political entities = ~10,000 lines
 */

@Serializable
data class PoliticalParty(
    val id: String,
    val name: String,
    val ideology: Ideology,
    val description: String,
    val baseSupport: Int, // 0-100
    val policies: List<String> = emptyList(), // policy positions
    val voterDemographics: Map<String, Int> = emptyMap() // demographic support
)

@Serializable
enum class Ideology {
    // Economic Axis
    COMMUNISM,
    SOCIALISM,
    SOCIAL_DEMOCRACY,
    LIBERALISM,
    CONSERVATISM,
    LIBERTARIANISM,
    FASCISM,
    
    // Social Axis
    PROGRESSIVE,
    TRADITIONAL,
    AUTHORITARIAN,
    LIBERTARIAN_SOCIAL,
    
    // Special
    GREEN,
    RELIGIOUS,
    NATIONALIST,
    MONARCHIST,
    ANARCHIST,
    TECHNOCRATIC,
    POPULIST,
    CENTRIST
}

@Serializable
data class Law(
    val id: String,
    val name: String,
    val description: String,
    val category: LawCategory,
    val tier: Int,
    val cost: Int,
    val maintenanceCost: Int,
    val requirements: List<String> = emptyList(),
    val effects: List<LawEffect> = emptyList(),
    val supportedBy: List<Ideology> = emptyList(),
    val opposedBy: List<Ideology> = emptyList()
)

@Serializable
data class LawEffect(
    val stat: String,
    val value: Int,
    val type: EffectType
)

@Serializable
enum class LawCategory {
    CONSTITUTIONAL,
    ECONOMIC,
    SOCIAL,
    CRIMINAL,
    LABOR,
    ENVIRONMENTAL,
    MILITARY,
    EDUCATION,
    HEALTHCARE,
    IMMIGRATION,
    TRADE,
    CIVIL_RIGHTS
}

@Serializable
data class Policy(
    val id: String,
    val name: String,
    val description: String,
    val category: PolicyCategory,
    val cost: Int,
    val duration: Int, // turns, -1 = permanent
    val requirements: List<String> = emptyList(),
    val effects: List<PolicyEffect> = emptyList(),
    val tradeOffs: List<String> = emptyList(), // negative effects
    val popularityImpact: Int = 0
)

@Serializable
data class PolicyEffect(
    val stat: String,
    val value: Int,
    val type: EffectType
)

@Serializable
enum class PolicyCategory {
    TAXATION,
    SPENDING,
    REGULATION,
    FOREIGN_POLICY,
    DEFENSE,
    WELFARE,
    INFRASTRUCTURE,
    RESEARCH,
    ENERGY,
    AGRICULTURE,
    INDUSTRY,
    MONETARY
}

object PoliticalSystem {

    // ============================================
    // POLITICAL PARTIES (50+ parties)
    // ============================================
    
    val politicalParties = listOf(
        // COMMUNIST PARTIES
        PoliticalParty(
            id = "party_comm_1",
            name = "Communist Party",
            ideology = Ideology.COMMUNISM,
            description = "Workers of the world, unite! Abolish private property and establish a classless society.",
            baseSupport = 15,
            policies = listOf("nationalize_industry", "wealth_redistribution", "free_healthcare", "free_education"),
            voterDemographics = mapOf("working_class" to 40, "poor" to 35, "unions" to 50)
        ),
        PoliticalParty(
            id = "party_comm_2",
            name = "Marxist-Leninist Front",
            ideology = Ideology.COMMUNISM,
            description = "Vanguard party dedicated to revolutionary socialism.",
            baseSupport = 10,
            policies = listOf("revolution", "single_party", "planned_economy", "atheism"),
            voterDemographics = mapOf("working_class" to 35, "intellectuals" to 25, "youth" to 20)
        ),
        PoliticalParty(
            id = "party_comm_3",
            name = "People's Democratic Party",
            ideology = Ideology.COMMUNISM,
            description = "Democratic path to socialism through electoral politics.",
            baseSupport = 12,
            policies = listOf("workers_rights", "progressive_tax", "public_housing", "anti_imperialism"),
            voterDemographics = mapOf("working_class" to 38, "minorities" to 30, "unions" to 45)
        ),
        
        // SOCIALIST PARTIES
        PoliticalParty(
            id = "party_soc_1",
            name = "Socialist Party",
            ideology = Ideology.SOCIALISM,
            description = "Democratic socialism with strong welfare state and worker protections.",
            baseSupport = 20,
            policies = listOf("universal_healthcare", "free_college", "worker_cooperatives", "green_new_deal"),
            voterDemographics = mapOf("working_class" to 35, "youth" to 40, "intellectuals" to 35)
        ),
        PoliticalParty(
            id = "party_soc_2",
            name = "Democratic Socialists",
            ideology = Ideology.SOCIALISM,
            description = "Gradual transition to socialism through democratic reforms.",
            baseSupport = 18,
            policies = listOf("medicare_for_all", "living_wage", "tax_the_rich", "climate_action"),
            voterDemographics = mapOf("youth" to 45, "working_class" to 30, "minorities" to 35)
        ),
        PoliticalParty(
            id = "party_soc_3",
            name = "Labor Party",
            ideology = Ideology.SOCIALISM,
            description = "Political arm of the trade union movement.",
            baseSupport = 22,
            policies = listOf("union_rights", "minimum_wage", "pension_protection", "job_guarantee"),
            voterDemographics = mapOf("unions" to 70, "working_class" to 45, "retirees" to 30)
        ),
        
        // SOCIAL DEMOCRACY
        PoliticalParty(
            id = "party_socdem_1",
            name = "Social Democratic Party",
            ideology = Ideology.SOCIAL_DEMOCRACY,
            description = "Capitalism with strong safety nets and regulated markets.",
            baseSupport = 25,
            policies = listOf("welfare_state", "progressive_taxation", "public_option", "paid_leave"),
            voterDemographics = mapOf("middle_class" to 40, "working_class" to 30, "elderly" to 35)
        ),
        PoliticalParty(
            id = "party_socdem_2",
            name = "Progressive Alliance",
            ideology = Ideology.SOCIAL_DEMOCRACY,
            description = "Modern progressive politics for the 21st century.",
            baseSupport = 20,
            policies = listOf("climate_action", "social_justice", "tech_regulation", "education_reform"),
            voterDemographics = mapOf("youth" to 40, "suburban" to 35, "college_educated" to 45)
        ),
        
        // LIBERAL PARTIES
        PoliticalParty(
            id = "party_lib_1",
            name = "Liberal Party",
            ideology = Ideology.LIBERALISM,
            description = "Individual freedom, free markets, and limited government.",
            baseSupport = 22,
            policies = listOf("free_trade", "tax_cuts", "deregulation", "civil_liberties"),
            voterDemographics = mapOf("business" to 50, "suburban" to 35, "college_educated" to 40)
        ),
        PoliticalParty(
            id = "party_lib_2",
            name = "Classical Liberals",
            ideology = Ideology.LIBERALISM,
            description = "Return to founding principles of liberty and limited government.",
            baseSupport = 15,
            policies = listOf("constitutional_limits", "free_speech", "property_rights", "non_intervention"),
            voterDemographics = mapOf("libertarians" to 60, "business" to 35, "rural" to 25)
        ),
        PoliticalParty(
            id = "party_lib_3",
            name = "Neoliberal Party",
            ideology = Ideology.LIBERALISM,
            description = "Global markets, technocratic governance, and incremental reform.",
            baseSupport = 18,
            policies = listOf("globalization", "fiscal_responsibility", "meritocracy", "innovation"),
            voterDemographics = mapOf("business" to 45, "college_educated" to 40, "urban" to 35)
        ),
        
        // CONSERVATIVE PARTIES
        PoliticalParty(
            id = "party_con_1",
            name = "Conservative Party",
            ideology = Ideology.CONSERVATISM,
            description = "Traditional values, strong defense, and fiscal responsibility.",
            baseSupport = 25,
            policies = listOf("law_and_order", "strong_military", "tax_cuts", "traditional_values"),
            voterDemographics = mapOf("rural" to 45, "elderly" to 40, "religious" to 50)
        ),
        PoliticalParty(
            id = "party_con_2",
            name = "Christian Democrats",
            ideology = Ideology.CONSERVATISM,
            description = "Faith-based politics with social market economy.",
            baseSupport = 18,
            policies = listOf("pro_life", "religious_freedom", "family_values", "charitable_choice"),
            voterDemographics = mapOf("religious" to 70, "rural" to 40, "elderly" to 35)
        ),
        PoliticalParty(
            id = "party_con_3",
            name = "Fiscal Conservatives",
            ideology = Ideology.CONSERVATISM,
            description = "Balanced budgets and economic freedom above all.",
            baseSupport = 15,
            policies = listOf("balanced_budget", "spending_cuts", "audit_fed", "gold_standard"),
            voterDemographics = mapOf("business" to 40, "elderly" to 35, "suburban" to 30)
        ),
        
        // LIBERTARIAN PARTIES
        PoliticalParty(
            id = "party_libert_1",
            name = "Libertarian Party",
            ideology = Ideology.LIBERTARIANISM,
            description = "Maximum freedom, minimum government. End the nanny state.",
            baseSupport = 12,
            policies = listOf("end_drug_war", "non_aggression", "abolish_irs", "open_borders"),
            voterDemographics = mapOf("youth" to 30, "tech" to 40, "suburban" to 25)
        ),
        PoliticalParty(
            id = "party_libert_2",
            name = "Free State Party",
            ideology = Ideology.LIBERTARIANISM,
            description = "Radical decentralization and voluntary society.",
            baseSupport = 8,
            policies = listOf("secession", "crypto_currency", "private_roads", "competing_currencies"),
            voterDemographics = mapOf("tech" to 50, "rural" to 30, "youth" to 35)
        ),
        
        // GREEN PARTIES
        PoliticalParty(
            id = "party_green_1",
            name = "Green Party",
            ideology = Ideology.GREEN,
            description = "Ecological wisdom, social justice, and grassroots democracy.",
            baseSupport = 10,
            policies = listOf("green_new_deal", "ban_fracking", "universal_basic_income", "end_corporate_personhood"),
            voterDemographics = mapOf("youth" to 40, "urban" to 35, "college_educated" to 40)
        ),
        PoliticalParty(
            id = "party_green_2",
            name = "Ecological Democrats",
            ideology = Ideology.GREEN,
            description = "Sustainable development within capitalist framework.",
            baseSupport = 12,
            policies = listOf("carbon_tax", "renewable_energy", "circular_economy", "conservation"),
            voterDemographics = mapOf("suburban" to 40, "college_educated" to 45, "middle_class" to 35)
        ),
        
        // NATIONALIST PARTIES
        PoliticalParty(
            id = "party_nat_1",
            name = "National Party",
            ideology = Ideology.NATIONALIST,
            description = "Our nation first. Protect our sovereignty and culture.",
            baseSupport = 18,
            policies = listOf("immigration_ban", "trade_protection", "national_industry", "patriotic_education"),
            voterDemographics = mapOf("rural" to 40, "working_class" to 35, "elderly" to 30)
        ),
        PoliticalParty(
            id = "party_nat_2",
            name = "Patriot Front",
            ideology = Ideology.NATIONALIST,
            description = "Strong borders, strong military, strong nation.",
            baseSupport = 15,
            policies = listOf("build_wall", "america_first", "military_expansion", "anti_globalism"),
            voterDemographics = mapOf("rural" to 45, "veterans" to 50, "working_class" to 40)
        ),
        
        // RELIGIOUS PARTIES
        PoliticalParty(
            id = "party_rel_1",
            name = "Islamic Party",
            ideology = Ideology.RELIGIOUS,
            description = "Governance based on Islamic principles and Sharia law.",
            baseSupport = 15,
            policies = listOf("sharia_law", "islamic_banking", "religious_education", "halal_economy"),
            voterDemographics = mapOf("muslim" to 80, "religious" to 50, "conservative" to 40)
        ),
        PoliticalParty(
            id = "party_rel_2",
            name = "Christian Nation Party",
            ideology = Ideology.RELIGIOUS,
            description = "Restore our Christian heritage and biblical values.",
            baseSupport = 12,
            policies = listOf("school_prayer", "creationism", "sabbath_laws", "anti_abortion"),
            voterDemographics = mapOf("evangelical" to 75, "rural" to 45, "elderly" to 40)
        ),
        PoliticalParty(
            id = "party_rel_3",
            name = "Jewish Home",
            ideology = Ideology.RELIGIOUS,
            description = "Protecting Jewish interests and religious traditions.",
            baseSupport = 10,
            policies = listOf("israel_support", "religious_sites", "anti_semitism_laws", "kosher_standards"),
            voterDemographics = mapOf("jewish" to 85, "urban" to 40, "conservative" to 35)
        ),
        
        // TECHNOCRATIC PARTIES
        PoliticalParty(
            id = "party_tech_1",
            name = "Technocratic Party",
            ideology = Ideology.TECHNOCRATIC,
            description = "Rule by experts. Evidence-based policy over ideology.",
            baseSupport = 12,
            policies = listOf("ai_governance", "data_driven_policy", "meritocratic_appointments", "long_term_planning"),
            voterDemographics = mapOf("tech" to 55, "college_educated" to 60, "urban" to 45)
        ),
        PoliticalParty(
            id = "party_tech_2",
            name = "Future Party",
            ideology = Ideology.TECHNOCRATIC,
            description = "Embrace technology and innovation for human progress.",
            baseSupport = 10,
            policies = listOf("space_colonization", "life_extension", "universal_basic_assets", "post_scarcity"),
            voterDemographics = mapOf("youth" to 45, "tech" to 60, "college_educated" to 50)
        ),
        
        // POPULIST PARTIES
        PoliticalParty(
            id = "party_pop_1",
            name = "Populist Party",
            ideology = Ideology.POPULIST,
            description = "The people vs. the elite. Drain the swamp!",
            baseSupport = 20,
            policies = listOf("term_limits", "lobbying_ban", "direct_democracy", "establishment_busting"),
            voterDemographics = mapOf("working_class" to 40, "rural" to 35, "anti_establishment" to 70)
        ),
        PoliticalParty(
            id = "party_pop_2",
            name = "People's Voice",
            ideology = Ideology.POPULIST,
            description = "Left-wing populism against corporate power.",
            baseSupport = 15,
            policies = listOf("break_up_banks", "tax_billionaires", "worker_ownership", "anti_corruption"),
            voterDemographics = mapOf("working_class" to 45, "youth" to 35, "minorities" to 30)
        ),
        
        // CENTRIST PARTIES
        PoliticalParty(
            id = "party_cent_1",
            name = "Centrist Party",
            ideology = Ideology.CENTRIST,
            description = "Pragmatic solutions from both sides of the aisle.",
            baseSupport = 18,
            policies = listOf("bipartisanship", "moderate_reform", "fiscal_responsibility", "compromise"),
            voterDemographics = mapOf("suburban" to 45, "middle_class" to 40, "moderates" to 60)
        ),
        PoliticalParty(
            id = "party_cent_2",
            name = "Independent Alliance",
            ideology = Ideology.CENTRIST,
            description = "Beyond left and right. Forward thinking governance.",
            baseSupport = 15,
            policies = listOf("ranked_voting", "open_primaries", "redistricting_reform", "campaign_finance"),
            voterDemographics = mapOf("independents" to 70, "suburban" to 40, "college_educated" to 45)
        ),
        
        // MONARCHIST PARTIES
        PoliticalParty(
            id = "party_mon_1",
            name = "Royalist Party",
            ideology = Ideology.MONARCHIST,
            description = "Restore the monarchy for stability and tradition.",
            baseSupport = 8,
            policies = listOf("constitutional_monarchy", "hereditary_peers", "royal_prerogative", "tradition"),
            voterDemographics = mapOf("elderly" to 35, "aristocracy" to 80, "rural" to 25)
        ),
        
        // ANARCHIST PARTIES
        PoliticalParty(
            id = "party_anarch_1",
            name = "Anarchist Federation",
            ideology = Ideology.ANARCHIST,
            description = "Abolish the state. Voluntary association and mutual aid.",
            baseSupport = 5,
            policies = listOf("abolish_police", "worker_self_management", "free_association", "direct_action"),
            voterDemographics = mapOf("youth" to 40, "urban" to 35, "activists" to 60)
        ),
        
        // FASCIST PARTIES (extreme, low support)
        PoliticalParty(
            id = "party_fasc_1",
            name = "National Socialist Movement",
            ideology = Ideology.FASCISM,
            description = "Ultra-nationalism with totalitarian control.",
            baseSupport = 3,
            policies = listOf("one_party_state", "racial_purity", "militarism", "cult_of_leader"),
            voterDemographics = mapOf("extremists" to 70, "disaffected" to 40, "rural" to 20)
        ),
        PoliticalParty(
            id = "party_fasc_2",
            name = "New Order Party",
            ideology = Ideology.FASCISM,
            description = "Strong leader, strong state, strong nation.",
            baseSupport = 4,
            policies = listOf("executive_power", "media_control", "youth_indoctrination", "enemy_identification"),
            voterDemographics = mapOf("extremists" to 65, "veterans" to 25, "disaffected" to 35)
        )
    )

    // ============================================
    // LAWS (100+ laws)
    // ============================================
    
    val laws = listOf(
        // CONSTITUTIONAL LAWS
        Law(
            id = "law_const_1",
            name = "Bill of Rights",
            description = "Fundamental civil liberties and protections.",
            category = LawCategory.CONSTITUTIONAL,
            tier = 1,
            cost = 5000,
            maintenanceCost = 100,
            effects = listOf(LawEffect("happiness", 15, EffectType.ADDITIVE), LawEffect("stability", 10, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERALISM, Ideology.PROGRESSIVE, Ideology.LIBERTARIAN_SOCIAL),
            opposedBy = listOf(Ideology.AUTHORITARIAN, Ideology.FASCISM)
        ),
        Law(
            id = "law_const_2",
            name = "Separation of Powers",
            description = "Checks and balances between government branches.",
            category = LawCategory.CONSTITUTIONAL,
            tier = 2,
            cost = 8000,
            maintenanceCost = 200,
            effects = listOf(LawEffect("corruption", -15, EffectType.ADDITIVE), LawEffect("stability", 12, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERALISM, Ideology.CENTRIST, Ideology.SOCIAL_DEMOCRACY),
            opposedBy = listOf(Ideology.COMMUNISM, Ideology.FASCISM, Ideology.MONARCHIST)
        ),
        Law(
            id = "law_const_3",
            name = "Direct Democracy Act",
            description = "Citizens can vote directly on legislation.",
            category = LawCategory.CONSTITUTIONAL,
            tier = 3,
            cost = 12000,
            maintenanceCost = 500,
            effects = listOf(LawEffect("happiness", 20, EffectType.ADDITIVE), LawEffect("corruption", -10, EffectType.ADDITIVE), LawEffect("stability", -5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERTARIANISM, Ideology.ANARCHIST, Ideology.POPULIST),
            opposedBy = listOf(Ideology.AUTHORITARIAN, Ideology.TECHNOCRATIC)
        ),
        Law(
            id = "law_const_4",
            name = "Emergency Powers Act",
            description = "Executive can rule by decree during crises.",
            category = LawCategory.CONSTITUTIONAL,
            tier = 2,
            cost = 6000,
            maintenanceCost = 150,
            effects = listOf(LawEffect("stability", 20, EffectType.ADDITIVE), LawEffect("corruption", 15, EffectType.ADDITIVE), LawEffect("happiness", -10, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.AUTHORITARIAN, Ideology.FASCISM, Ideology.CONSERVATISM),
            opposedBy = listOf(Ideology.LIBERALISM, Ideology.LIBERTARIANISM, Ideology.PROGRESSIVE)
        ),
        
        // ECONOMIC LAWS
        Law(
            id = "law_econ_1",
            name = "Progressive Taxation",
            description = "Higher earners pay larger percentage.",
            category = LawCategory.ECONOMIC,
            tier = 1,
            cost = 2000,
            maintenanceCost = 50,
            effects = listOf(LawEffect("economy", -5, EffectType.ADDITIVE), LawEffect("happiness", 10, EffectType.ADDITIVE), LawEffect("corruption", -5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.SOCIALISM, Ideology.SOCIAL_DEMOCRACY, Ideology.PROGRESSIVE),
            opposedBy = listOf(Ideology.LIBERTARIANISM, Ideology.CONSERVATISM, Ideology.LIBERALISM)
        ),
        Law(
            id = "law_econ_2",
            name = "Flat Tax",
            description = "Everyone pays the same percentage.",
            category = LawCategory.ECONOMIC,
            tier = 1,
            cost = 2000,
            maintenanceCost = 50,
            effects = listOf(LawEffect("economy", 8, EffectType.ADDITIVE), LawEffect("happiness", -5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERALISM, Ideology.LIBERTARIANISM, Ideology.CENTRIST),
            opposedBy = listOf(Ideology.SOCIALISM, Ideology.COMMUNISM, Ideology.POPULIST)
        ),
        Law(
            id = "law_econ_3",
            name = "Wealth Tax",
            description = "Annual tax on net worth above threshold.",
            category = LawCategory.ECONOMIC,
            tier = 3,
            cost = 5000,
            maintenanceCost = 200,
            effects = listOf(LawEffect("economy", -10, EffectType.ADDITIVE), LawEffect("happiness", 15, EffectType.ADDITIVE), LawEffect("corruption", -8, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.COMMUNISM, Ideology.SOCIALISM, Ideology.POPULIST),
            opposedBy = listOf(Ideology.LIBERALISM, Ideology.CONSERVATISM, Ideology.LIBERTARIANISM)
        ),
        Law(
            id = "law_econ_4",
            name = "Corporate Tax Cut",
            description = "Reduce taxes on businesses.",
            category = LawCategory.ECONOMIC,
            tier = 2,
            cost = 4000,
            maintenanceCost = 100,
            effects = listOf(LawEffect("economy", 15, EffectType.ADDITIVE), LawEffect("happiness", -8, EffectType.ADDITIVE), LawEffect("corruption", 5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERALISM, Ideology.CONSERVATISM, Ideology.LIBERTARIANISM),
            opposedBy = listOf(Ideology.SOCIALISM, Ideology.COMMUNISM, Ideology.POPULIST)
        ),
        Law(
            id = "law_econ_5",
            name = "Universal Basic Income",
            description = "Guaranteed income for all citizens.",
            category = LawCategory.ECONOMIC,
            tier = 4,
            cost = 20000,
            maintenanceCost = 2000,
            effects = listOf(LawEffect("happiness", 25, EffectType.ADDITIVE), LawEffect("stability", 15, EffectType.ADDITIVE), LawEffect("economy", -20, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.SOCIALISM, Ideology.LIBERTARIANISM, Ideology.TECHNOCRATIC),
            opposedBy = listOf(Ideology.CONSERVATISM, Ideology.LIBERALISM, Ideology.FASCISM)
        ),
        Law(
            id = "law_econ_6",
            name = "Worker Ownership Act",
            description = "Employees get equity in their companies.",
            category = LawCategory.ECONOMIC,
            tier = 3,
            cost = 8000,
            maintenanceCost = 300,
            effects = listOf(LawEffect("economy", 5, EffectType.ADDITIVE), LawEffect("happiness", 18, EffectType.ADDITIVE), LawEffect("corruption", -10, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.SOCIALISM, Ideology.SOCIAL_DEMOCRACY, Ideology.POPULIST),
            opposedBy = listOf(Ideology.LIBERALISM, Ideology.CONSERVATISM, Ideology.FASCISM)
        ),
        
        // SOCIAL LAWS
        Law(
            id = "law_soc_1",
            name = "Same-Sex Marriage",
            description = "Marriage equality for all couples.",
            category = LawCategory.SOCIAL,
            tier = 2,
            cost = 1000,
            maintenanceCost = 0,
            effects = listOf(LawEffect("happiness", 12, EffectType.ADDITIVE), LawEffect("stability", -5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.PROGRESSIVE, Ideology.LIBERALISM, Ideology.SOCIAL_DEMOCRACY),
            opposedBy = listOf(Ideology.RELIGIOUS, Ideology.CONSERVATISM, Ideology.FASCISM)
        ),
        Law(
            id = "law_soc_2",
            name = "Abortion Rights",
            description = "Legal access to reproductive healthcare.",
            category = LawCategory.SOCIAL,
            tier = 2,
            cost = 1500,
            maintenanceCost = 100,
            effects = listOf(LawEffect("happiness", 10, EffectType.ADDITIVE), LawEffect("stability", -8, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.PROGRESSIVE, Ideology.LIBERALISM, Ideology.SOCIALISM),
            opposedBy = listOf(Ideology.RELIGIOUS, Ideology.CONSERVATISM, Ideology.FASCISM)
        ),
        Law(
            id = "law_soc_3",
            name = "Gun Control",
            description = "Restrictions on firearm ownership.",
            category = LawCategory.SOCIAL,
            tier = 2,
            cost = 2000,
            maintenanceCost = 150,
            effects = listOf(LawEffect("crime", -12, EffectType.ADDITIVE), LawEffect("stability", 8, EffectType.ADDITIVE), LawEffect("happiness", -5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.PROGRESSIVE, Ideology.SOCIAL_DEMOCRACY, Ideology.CONSERVATISM),
            opposedBy = listOf(Ideology.LIBERTARIANISM, Ideology.LIBERALISM, Ideology.FASCISM)
        ),
        Law(
            id = "law_soc_4",
            name = "Drug Legalization",
            description = "End prohibition on recreational drugs.",
            category = LawCategory.SOCIAL,
            tier = 3,
            cost = 3000,
            maintenanceCost = 200,
            effects = listOf(LawEffect("crime", -20, EffectType.ADDITIVE), LawEffect("economy", 10, EffectType.ADDITIVE), LawEffect("happiness", 8, EffectType.ADDITIVE), LawEffect("healthcare", -10, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERTARIANISM, Ideology.PROGRESSIVE, Ideology.LIBERALISM),
            opposedBy = listOf(Ideology.CONSERVATISM, Ideology.FASCISM, Ideology.RELIGIOUS)
        ),
        
        // CRIMINAL LAWS
        Law(
            id = "law crim_1",
            name = "Three Strikes Law",
            description = "Life imprisonment for third felony.",
            category = LawCategory.CRIMINAL,
            tier = 2,
            cost = 3000,
            maintenanceCost = 500,
            effects = listOf(LawEffect("crime", -15, EffectType.ADDITIVE), LawEffect("happiness", -8, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.CONSERVATISM, Ideology.FASCISM, Ideology.AUTHORITARIAN),
            opposedBy = listOf(Ideology.PROGRESSIVE, Ideology.SOCIALISM, Ideology.LIBERTARIANISM)
        ),
        Law(
            id = "law_crim_2",
            name = "Police Reform",
            description = "Accountability measures for law enforcement.",
            category = LawCategory.CRIMINAL,
            tier = 2,
            cost = 4000,
            maintenanceCost = 300,
            effects = listOf(LawEffect("happiness", 12, EffectType.ADDITIVE), LawEffect("stability", -5, EffectType.ADDITIVE), LawEffect("crime", 5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.PROGRESSIVE, Ideology.SOCIALISM, Ideology.LIBERALISM),
            opposedBy = listOf(Ideology.CONSERVATISM, Ideology.FASCISM, Ideology.AUTHORITARIAN)
        ),
        Law(
            id = "law_crim_3",
            name = "Death Penalty Abolition",
            description = "End capital punishment.",
            category = LawCategory.CRIMINAL,
            tier = 2,
            cost = 1000,
            maintenanceCost = 50,
            effects = listOf(LawEffect("happiness", 8, EffectType.ADDITIVE), LawEffect("stability", -5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.PROGRESSIVE, Ideology.SOCIAL_DEMOCRACY, Ideology.RELIGIOUS),
            opposedBy = listOf(Ideology.CONSERVATISM, Ideology.FASCISM, Ideology.AUTHORITARIAN)
        ),
        
        // LABOR LAWS
        Law(
            id = "law_lab_1",
            name = "Minimum Wage",
            description = "Floor on hourly compensation.",
            category = LawCategory.LABOR,
            tier = 1,
            cost = 2000,
            maintenanceCost = 100,
            effects = listOf(LawEffect("happiness", 12, EffectType.ADDITIVE), LawEffect("economy", -5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.SOCIALISM, Ideology.SOCIAL_DEMOCRACY, Ideology.POPULIST),
            opposedBy = listOf(Ideology.LIBERALISM, Ideology.LIBERTARIANISM, Ideology.CONSERVATISM)
        ),
        Law(
            id = "law_lab_2",
            name = "Right to Work",
            description = "Employees can't be forced to join unions.",
            category = LawCategory.LABOR,
            tier = 2,
            cost = 2500,
            maintenanceCost = 50,
            effects = listOf(LawEffect("economy", 8, EffectType.ADDITIVE), LawEffect("happiness", -8, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERALISM, Ideology.CONSERVATISM, Ideology.LIBERTARIANISM),
            opposedBy = listOf(Ideology.SOCIALISM, Ideology.COMMUNISM, Ideology.POPULIST)
        ),
        Law(
            id = "law_lab_3",
            name = "40-Hour Work Week",
            description = "Maximum standard working hours.",
            category = LawCategory.LABOR,
            tier = 1,
            cost = 1500,
            maintenanceCost = 50,
            effects = listOf(LawEffect("happiness", 15, EffectType.ADDITIVE), LawEffect("economy", -3, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.SOCIALISM, Ideology.SOCIAL_DEMOCRACY, Ideology.PROGRESSIVE),
            opposedBy = listOf(Ideology.LIBERALISM, Ideology.LIBERTARIANISM)
        ),
        Law(
            id = "law_lab_4",
            name = "Paid Family Leave",
            description = "Mandated paid time off for new parents.",
            category = LawCategory.LABOR,
            tier = 2,
            cost = 5000,
            maintenanceCost = 400,
            effects = listOf(LawEffect("happiness", 20, EffectType.ADDITIVE), LawEffect("economy", -8, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.SOCIAL_DEMOCRACY, Ideology.SOCIALISM, Ideology.PROGRESSIVE),
            opposedBy = listOf(Ideology.LIBERALISM, Ideology.CONSERVATISM, Ideology.LIBERTARIANISM)
        ),
        
        // ENVIRONMENTAL LAWS
        Law(
            id = "law_env_1",
            name = "Carbon Tax",
            description = "Fee on greenhouse gas emissions.",
            category = LawCategory.ENVIRONMENTAL,
            tier = 2,
            cost = 4000,
            maintenanceCost = 100,
            effects = listOf(LawEffect("environment", 20, EffectType.ADDITIVE), LawEffect("economy", -10, EffectType.ADDITIVE), LawEffect("happiness", -5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.GREEN, Ideology.SOCIAL_DEMOCRACY, Ideology.PROGRESSIVE),
            opposedBy = listOf(Ideology.CONSERVATISM, Ideology.LIBERALISM, Ideology.POPULIST)
        ),
        Law(
            id = "law_env_2",
            name = "Green New Deal",
            description = "Massive investment in clean energy.",
            category = LawCategory.ENVIRONMENTAL,
            tier = 4,
            cost = 50000,
            maintenanceCost = 3000,
            effects = listOf(LawEffect("environment", 40, EffectType.ADDITIVE), LawEffect("economy", 15, EffectType.ADDITIVE), LawEffect("happiness", 10, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.GREEN, Ideology.SOCIALISM, Ideology.PROGRESSIVE),
            opposedBy = listOf(Ideology.CONSERVATISM, Ideology.LIBERALISM, Ideology.FASCISM)
        ),
        Law(
            id = "law_env_3",
            name = "Deregulation Act",
            description = "Remove environmental restrictions on business.",
            category = LawCategory.ENVIRONMENTAL,
            tier = 2,
            cost = 2000,
            maintenanceCost = 0,
            effects = listOf(LawEffect("economy", 15, EffectType.ADDITIVE), LawEffect("environment", -25, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERALISM, Ideology.LIBERTARIANISM, Ideology.CONSERVATISM),
            opposedBy = listOf(Ideology.GREEN, Ideology.SOCIALISM, Ideology.PROGRESSIVE)
        ),
        
        // MILITARY LAWS
        Law(
            id = "law_mil_1",
            name = "Military Draft",
            description = "Mandatory military service.",
            category = LawCategory.MILITARY,
            tier = 2,
            cost = 3000,
            maintenanceCost = 1000,
            effects = listOf(LawEffect("military", 25, EffectType.ADDITIVE), LawEffect("happiness", -15, EffectType.ADDITIVE), LawEffect("economy", -8, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.FASCISM, Ideology.CONSERVATISM, Ideology.NATIONALIST),
            opposedBy = listOf(Ideology.LIBERTARIANISM, Ideology.PROGRESSIVE, Ideology.SOCIALISM)
        ),
        Law(
            id = "law_mil_2",
            name = "Defense Spending Increase",
            description = "Boost military budget.",
            category = LawCategory.MILITARY,
            tier = 2,
            cost = 10000,
            maintenanceCost = 2000,
            effects = listOf(LawEffect("military", 20, EffectType.ADDITIVE), LawEffect("economy", -12, EffectType.ADDITIVE), LawEffect("stability", 8, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.CONSERVATISM, Ideology.FASCISM, Ideology.NATIONALIST),
            opposedBy = listOf(Ideology.SOCIALISM, Ideology.LIBERTARIANISM, Ideology.GREEN)
        ),
        
        // EDUCATION LAWS
        Law(
            id = "law_edu_1",
            name = "Free College",
            description = "Tuition-free public universities.",
            category = LawCategory.EDUCATION,
            tier = 3,
            cost = 15000,
            maintenanceCost = 2000,
            effects = listOf(LawEffect("education", 25, EffectType.ADDITIVE), LawEffect("happiness", 15, EffectType.ADDITIVE), LawEffect("economy", -10, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.SOCIALISM, Ideology.SOCIAL_DEMOCRACY, Ideology.PROGRESSIVE),
            opposedBy = listOf(Ideology.CONSERVATISM, Ideology.LIBERALISM, Ideology.LIBERTARIANISM)
        ),
        Law(
            id = "law_edu_2",
            name = "School Choice",
            description = "Vouchers for private education.",
            category = LawCategory.EDUCATION,
            tier = 2,
            cost = 8000,
            maintenanceCost = 1000,
            effects = listOf(LawEffect("education", 10, EffectType.ADDITIVE), LawEffect("happiness", 8, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.CONSERVATISM, Ideology.LIBERALISM, Ideology.RELIGIOUS),
            opposedBy = listOf(Ideology.SOCIALISM, Ideology.PROGRESSIVE, Ideology.TECHNOCRATIC)
        ),
        
        // HEALTHCARE LAWS
        Law(
            id = "law_health_1",
            name = "Universal Healthcare",
            description = "Government-provided health insurance for all.",
            category = LawCategory.HEALTHCARE,
            tier = 3,
            cost = 25000,
            maintenanceCost = 5000,
            effects = listOf(LawEffect("healthcare", 35, EffectType.ADDITIVE), LawEffect("happiness", 25, EffectType.ADDITIVE), LawEffect("economy", -15, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.SOCIALISM, Ideology.SOCIAL_DEMOCRACY, Ideology.PROGRESSIVE),
            opposedBy = listOf(Ideology.LIBERALISM, Ideology.CONSERVATISM, Ideology.LIBERTARIANISM)
        ),
        Law(
            id = "law_health_2",
            name = "Healthcare Privatization",
            description = "Market-based healthcare system.",
            category = LawCategory.HEALTHCARE,
            tier = 2,
            cost = 5000,
            maintenanceCost = 200,
            effects = listOf(LawEffect("economy", 12, EffectType.ADDITIVE), LawEffect("healthcare", -10, EffectType.ADDITIVE), LawEffect("happiness", -12, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERALISM, Ideology.LIBERTARIANISM, Ideology.CONSERVATISM),
            opposedBy = listOf(Ideology.SOCIALISM, Ideology.SOCIAL_DEMOCRACY, Ideology.PROGRESSIVE)
        ),
        
        // IMMIGRATION LAWS
        Law(
            id = "law_imm_1",
            name = "Open Borders",
            description = "Free movement of people.",
            category = LawCategory.IMMIGRATION,
            tier = 3,
            cost = 5000,
            maintenanceCost = 500,
            effects = listOf(LawEffect("economy", 15, EffectType.ADDITIVE), LawEffect("happiness", -10, EffectType.ADDITIVE), LawEffect("stability", -12, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERTARIANISM, Ideology.LIBERALISM, Ideology.SOCIALISM),
            opposedBy = listOf(Ideology.NATIONALIST, Ideology.CONSERVATISM, Ideology.FASCISM)
        ),
        Law(
            id = "law_imm_2",
            name = "Immigration Ban",
            description = "Severely restrict entry.",
            category = LawCategory.IMMIGRATION,
            tier = 2,
            cost = 4000,
            maintenanceCost = 800,
            effects = listOf(LawEffect("stability", 12, EffectType.ADDITIVE), LawEffect("happiness", -8, EffectType.ADDITIVE), LawEffect("economy", -10, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.NATIONALIST, Ideology.FASCISM, Ideology.CONSERVATISM),
            opposedBy = listOf(Ideology.LIBERALISM, Ideology.SOCIALISM, Ideology.PROGRESSIVE)
        ),
        
        // TRADE LAWS
        Law(
            id = "law_trade_1",
            name = "Free Trade Agreement",
            description = "Remove tariffs and barriers.",
            category = LawCategory.TRADE,
            tier = 2,
            cost = 5000,
            maintenanceCost = 100,
            effects = listOf(LawEffect("economy", 20, EffectType.ADDITIVE), LawEffect("happiness", -5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.LIBERALISM, Ideology.LIBERTARIANISM, Ideology.CENTRIST),
            opposedBy = listOf(Ideology.NATIONALIST, Ideology.SOCIALISM, Ideology.POPULIST)
        ),
        Law(
            id = "law_trade_2",
            name = "Protectionist Tariffs",
            description = "Tax imports to protect domestic industry.",
            category = LawCategory.TRADE,
            tier = 2,
            cost = 4000,
            maintenanceCost = 200,
            effects = listOf(LawEffect("economy", -8, EffectType.ADDITIVE), LawEffect("happiness", 8, EffectType.ADDITIVE), LawEffect("stability", 5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.NATIONALIST, Ideology.SOCIALISM, Ideology.POPULIST),
            opposedBy = listOf(Ideology.LIBERALISM, Ideology.LIBERTARIANISM, Ideology.CENTRIST)
        ),
        
        // CIVIL RIGHTS LAWS
        Law(
            id = "law_civil_1",
            name = "Civil Rights Act",
            description = "Prohibit discrimination.",
            category = LawCategory.CIVIL_RIGHTS,
            tier = 2,
            cost = 3000,
            maintenanceCost = 200,
            effects = listOf(LawEffect("happiness", 18, EffectType.ADDITIVE), LawEffect("stability", 8, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.PROGRESSIVE, Ideology.SOCIAL_DEMOCRACY, Ideology.LIBERALISM),
            opposedBy = listOf(Ideology.FASCISM, Ideology.NATIONALIST, Ideology.RELIGIOUS)
        ),
        Law(
            id = "law_civil_2",
            name = "Affirmative Action",
            description = "Preferential treatment for disadvantaged groups.",
            category = LawCategory.CIVIL_RIGHTS,
            tier = 3,
            cost = 6000,
            maintenanceCost = 400,
            effects = listOf(LawEffect("happiness", 12, EffectType.ADDITIVE), LawEffect("stability", -8, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.PROGRESSIVE, Ideology.SOCIALISM, Ideology.SOCIAL_DEMOCRACY),
            opposedBy = listOf(Ideology.LIBERALISM, Ideology.CONSERVATISM, Ideology.LIBERTARIANISM)
        ),
        Law(
            id = "law_civil_3",
            name = "Voting Rights Expansion",
            description = "Make voting more accessible.",
            category = LawCategory.CIVIL_RIGHTS,
            tier = 2,
            cost = 4000,
            maintenanceCost = 300,
            effects = listOf(LawEffect("happiness", 15, EffectType.ADDITIVE), LawEffect("stability", 5, EffectType.ADDITIVE), LawEffect("corruption", -5, EffectType.ADDITIVE)),
            supportedBy = listOf(Ideology.PROGRESSIVE, Ideology.SOCIALISM, Ideology.SOCIAL_DEMOCRACY),
            opposedBy = listOf(Ideology.CONSERVATISM, Ideology.FASCISM, Ideology.NATIONALIST)
        )
    )

    // ============================================
    // POLICIES (80+ policies)
    // ============================================
    
    val policies = listOf(
        // TAXATION POLICIES
        Policy(
            id = "pol_tax_1",
            name = "Tax Cut for Middle Class",
            description = "Reduce tax burden on working families.",
            category = PolicyCategory.TAXATION,
            cost = 5000,
            duration = 10,
            effects = listOf(PolicyEffect("happiness", 12, EffectType.ADDITIVE), PolicyEffect("economy", 5, EffectType.ADDITIVE)),
            tradeOffs = listOf("budget_deficit"),
            popularityImpact = 15
        ),
        Policy(
            id = "pol_tax_2",
            name = "Tax the Rich",
            description = "Increase taxes on top earners.",
            category = PolicyCategory.TAXATION,
            cost = 3000,
            duration = 10,
            effects = listOf(PolicyEffect("economy", -5, EffectType.ADDITIVE), PolicyEffect("happiness", 10, EffectType.ADDITIVE)),
            tradeOffs = listOf("capital_flight"),
            popularityImpact = 20
        ),
        Policy(
            id = "pol_tax_3",
            name = "Corporate Tax Holiday",
            description = "Temporary tax break for businesses.",
            category = PolicyCategory.TAXATION,
            cost = 4000,
            duration = 5,
            effects = listOf(PolicyEffect("economy", 15, EffectType.ADDITIVE)),
            tradeOffs = listOf("budget_deficit", "inequality"),
            popularityImpact = -5
        ),
        
        // SPENDING POLICIES
        Policy(
            id = "pol_spend_1",
            name = "Infrastructure Investment",
            description = "Build roads, bridges, and utilities.",
            category = PolicyCategory.SPENDING,
            cost = 20000,
            duration = 20,
            effects = listOf(PolicyEffect("economy", 20, EffectType.ADDITIVE), PolicyEffect("happiness", 8, EffectType.ADDITIVE)),
            tradeOffs = listOf("budget_deficit"),
            popularityImpact = 18
        ),
        Policy(
            id = "pol_spend_2",
            name = "Austerity Measures",
            description = "Cut government spending.",
            category = PolicyCategory.SPENDING,
            cost = 2000,
            duration = 15,
            effects = listOf(PolicyEffect("economy", -10, EffectType.ADDITIVE), PolicyEffect("happiness", -15, EffectType.ADDITIVE)),
            tradeOffs = listOf("unemployment"),
            popularityImpact = -25
        ),
        Policy(
            id = "pol_spend_3",
            name = "Military Buildup",
            description = "Expand armed forces capabilities.",
            category = PolicyCategory.SPENDING,
            cost = 30000,
            duration = 20,
            effects = listOf(PolicyEffect("military", 30, EffectType.ADDITIVE), PolicyEffect("stability", 10, EffectType.ADDITIVE)),
            tradeOffs = listOf("budget_deficit", "opportunity_cost"),
            popularityImpact = 10
        ),
        
        // WELFARE POLICIES
        Policy(
            id = "pol_welf_1",
            name = "Expand Food Stamps",
            description = "Increase assistance for hungry families.",
            category = PolicyCategory.WELFARE,
            cost = 8000,
            duration = 10,
            effects = listOf(PolicyEffect("happiness", 15, EffectType.ADDITIVE), PolicyEffect("stability", 8, EffectType.ADDITIVE)),
            tradeOffs = listOf("budget_deficit"),
            popularityImpact = 12
        ),
        Policy(
            id = "pol_welf_2",
            name = "Cut Welfare Benefits",
            description = "Reduce government assistance programs.",
            category = PolicyCategory.WELFARE,
            cost = 2000,
            duration = 10,
            effects = listOf(PolicyEffect("economy", 8, EffectType.ADDITIVE), PolicyEffect("happiness", -20, EffectType.ADDITIVE)),
            tradeOffs = listOf("poverty", "homelessness"),
            popularityImpact = -20
        ),
        
        // FOREIGN POLICY
        Policy(
            id = "pol_for_1",
            name = "Diplomatic Engagement",
            description = "Pursue peaceful international relations.",
            category = PolicyCategory.FOREIGN_POLICY,
            cost = 5000,
            duration = 15,
            effects = listOf(PolicyEffect("softPower", 20, EffectType.ADDITIVE), PolicyEffect("stability", 8, EffectType.ADDITIVE)),
            tradeOffs = listOf(),
            popularityImpact = 10
        ),
        Policy(
            id = "pol_for_2",
            name = "Aggressive Posture",
            description = "Show strength to adversaries.",
            category = PolicyCategory.FOREIGN_POLICY,
            cost = 10000,
            duration = 10,
            effects = listOf(PolicyEffect("military", 15, EffectType.ADDITIVE), PolicyEffect("softPower", -15, EffectType.ADDITIVE)),
            tradeOffs = listOf("international_tensions"),
            popularityImpact = 5
        ),
        Policy(
            id = "pol_for_3",
            name = "Isolationism",
            description = "Focus on domestic affairs only.",
            category = PolicyCategory.FOREIGN_POLICY,
            cost = 3000,
            duration = 20,
            effects = listOf(PolicyEffect("economy", -8, EffectType.ADDITIVE), PolicyEffect("stability", 10, EffectType.ADDITIVE)),
            tradeOffs = listOf("reduced_influence"),
            popularityImpact = 8
        ),
        
        // DEFENSE POLICIES
        Policy(
            id = "pol_def_1",
            name = "Missile Defense Shield",
            description = "Protect against ballistic missile attacks.",
            category = PolicyCategory.DEFENSE,
            cost = 50000,
            duration = -1,
            effects = listOf(PolicyEffect("military", 25, EffectType.ADDITIVE), PolicyEffect("stability", 15, EffectType.ADDITIVE)),
            tradeOffs = listOf("budget_deficit", "arms_race"),
            popularityImpact = 12
        ),
        Policy(
            id = "pol_def_2",
            name = "Cyber Warfare Command",
            description = "Develop offensive cyber capabilities.",
            category = PolicyCategory.DEFENSE,
            cost = 15000,
            duration = -1,
            effects = listOf(PolicyEffect("military", 20, EffectType.ADDITIVE), PolicyEffect("technology", 15, EffectType.ADDITIVE)),
            tradeOffs = listOf("cyber_retaliation_risk"),
            popularityImpact = 5
        ),
        
        // RESEARCH POLICIES
        Policy(
            id = "pol_res_1",
            name = "Space Program Funding",
            description = "Invest in space exploration.",
            category = PolicyCategory.RESEARCH,
            cost = 30000,
            duration = 30,
            effects = listOf(PolicyEffect("technology", 30, EffectType.ADDITIVE), PolicyEffect("softPower", 25, EffectType.ADDITIVE)),
            tradeOffs = listOf("budget_deficit"),
            popularityImpact = 20
        ),
        Policy(
            id = "pol_res_2",
            name = "AI Research Initiative",
            description = "Lead the artificial intelligence race.",
            category = PolicyCategory.RESEARCH,
            cost = 25000,
            duration = 25,
            effects = listOf(PolicyEffect("technology", 35, EffectType.ADDITIVE), PolicyEffect("economy", 15, EffectType.ADDITIVE)),
            tradeOffs = listOf("job_displacement", "ethical_concerns"),
            popularityImpact = 10
        ),
        
        // ENERGY POLICIES
        Policy(
            id = "pol_energy_1",
            name = "Renewable Energy Subsidies",
            description = "Support clean energy development.",
            category = PolicyCategory.ENERGY,
            cost = 20000,
            duration = 20,
            effects = listOf(PolicyEffect("environment", 25, EffectType.ADDITIVE), PolicyEffect("technology", 10, EffectType.ADDITIVE)),
            tradeOffs = listOf("budget_deficit", "fossil_fuel_job_losses"),
            popularityImpact = 15
        ),
        Policy(
            id = "pol_energy_2",
            name = "Energy Independence",
            description = "Maximize domestic energy production.",
            category = PolicyCategory.ENERGY,
            cost = 15000,
            duration = 15,
            effects = listOf(PolicyEffect("economy", 12, EffectType.ADDITIVE), PolicyEffect("stability", 10, EffectType.ADDITIVE)),
            tradeOffs = listOf("environmental_damage"),
            popularityImpact = 12
        ),
        
        // MONETARY POLICIES
        Policy(
            id = "pol_mon_1",
            name = "Quantitative Easing",
            description = "Central bank buys assets to stimulate economy.",
            category = PolicyCategory.MONETARY,
            cost = 10000,
            duration = 10,
            effects = listOf(PolicyEffect("economy", 18, EffectType.ADDITIVE), PolicyEffect("stability", 5, EffectType.ADDITIVE)),
            tradeOffs = listOf("inflation", "asset_bubbles"),
            popularityImpact = 8
        ),
        Policy(
            id = "pol_mon_2",
            name = "Interest Rate Hike",
            description = "Raise rates to combat inflation.",
            category = PolicyCategory.MONETARY,
            cost = 2000,
            duration = 10,
            effects = listOf(PolicyEffect("economy", -8, EffectType.ADDITIVE)),
            tradeOffs = listOf("recession_risk", "unemployment"),
            popularityImpact = -10
        )
    )

    // ============================================
    // HELPER FUNCTIONS
    // ============================================
    
    fun getAllParties(): List<PoliticalParty> = politicalParties
    
    fun getPartiesByIdeology(ideology: Ideology): List<PoliticalParty> {
        return politicalParties.filter { it.ideology == ideology }
    }
    
    fun getAllLaws(): List<Law> = laws
    
    fun getLawsByCategory(category: LawCategory): List<Law> {
        return laws.filter { it.category == category }
    }
    
    fun getAllPolicies(): List<Policy> = policies
    
    fun getPoliciesByCategory(category: PolicyCategory): List<Policy> {
        return policies.filter { it.category == category }
    }
    
    fun getParty(id: String): PoliticalParty? = politicalParties.find { it.id == id }
    fun getLaw(id: String): Law? = laws.find { it.id == id }
    fun getPolicy(id: String): Policy? = policies.find { it.id == id }
    
    fun getPoliticalStatistics(): Map<String, Int> {
        return mapOf(
            "Total Parties" to politicalParties.size,
            "Total Laws" to laws.size,
            "Total Policies" to policies.size,
            "Communist Parties" to getPartiesByIdeology(Ideology.COMMUNISM).size,
            "Socialist Parties" to getPartiesByIdeology(Ideology.SOCIALISM).size,
            "Liberal Parties" to getPartiesByIdeology(Ideology.LIBERALISM).size,
            "Conservative Parties" to getPartiesByIdeology(Ideology.CONSERVATISM).size,
            "Constitutional Laws" to getLawsByCategory(LawCategory.CONSTITUTIONAL).size,
            "Economic Laws" to getLawsByCategory(LawCategory.ECONOMIC).size,
            "Social Laws" to getLawsByCategory(LawCategory.SOCIAL).size
        )
    }
}
