package com.countrysimulator.game.domain

import kotlin.random.Random

// Explicit imports for CrisisType enum values
import com.countrysimulator.game.domain.CrisisType.BANK_RUN
import com.countrysimulator.game.domain.CrisisType.STOCK_CRASH
import com.countrysimulator.game.domain.CrisisType.CURRENCY_CRISIS
import com.countrysimulator.game.domain.CrisisType.CIVIL_UNREST
import com.countrysimulator.game.domain.CrisisType.GENERAL_STRIKE
import com.countrysimulator.game.domain.CrisisType.CORRUPTION_SCANDAL
import com.countrysimulator.game.domain.CrisisType.PANDEMIC
import com.countrysimulator.game.domain.CrisisType.FAMINE
import com.countrysimulator.game.domain.CrisisType.STATE_FAILURE

/**
 * COUNTRY SIMULATOR v10.0 - GAME LOGIC
 * "The Living Nation" - Deep Emergent Gameplay System
 * 
 * This file contains all the simulation logic for:
 * - Economy (sectors, inflation, unemployment, stock market)
 * - Public Opinion (polls, media, protests)
 * - Characters (advisors, rivals with agendas)
 * - Crisis System (cascading emergencies)
 * - Micro/Macro Actions
 * - Dynamic Events
 * - Legacy System
 */
object GameLogicV10 {

    // ────────────────────────────────────────────
    // ECONOMY SIMULATION
    // ────────────────────────────────────────────

    fun processEconomy(game: GameV10): EconomyState {
        var economy = game.economy
        val country = game.country

        // 1. Process each sector
        val newSectors = economy.sectors.map { sector ->
            processSector(sector, economy, country)
        }

        // 2. Calculate economic indicators
        val newIndicators = calculateEconomicIndicators(newSectors, economy, country)

        // 3. Process stock market
        val newStockMarket = processStockMarket(economy.stockMarket, newIndicators)

        // 4. Process banking system
        val newBanking = processBanking(economy.banking, newIndicators)

        // 5. Apply economic policy effects
        economy = economy.copy(
            sectors = newSectors,
            indicators = newIndicators,
            stockMarket = newStockMarket,
            banking = newBanking
        )

        return applyPolicyEffects(economy, country)
    }

    private fun processSector(sector: EconomySector, economy: EconomyState, country: Country): EconomySector {
        var newSector = sector

        // Base growth from investment
        if (sector.investment > 0) {
            newSector = newSector.copy(
                growth = (sector.growth + sector.investment / 10).coerceIn(-100, 100),
                productivity = (sector.productivity + sector.investment / 20).coerceIn(0, 100),
                investment = 0 // Reset investment after applying
            )
        }

        // Employment follows growth
        val employmentChange = when {
            sector.growth > 20 -> 5
            sector.growth > 10 -> 2
            sector.growth < -20 -> -5
            sector.growth < -10 -> -2
            else -> 0
        }
        newSector = newSector.copy(employment = (sector.employment + employmentChange).coerceIn(0, 100))

        // Sector interdependencies
        if (sector.name == "Manufacturing") {
            val energySector = economy.sectors.find { it.name == "Energy" }
            if (energySector != null && energySector.growth < 0) {
                newSector = newSector.copy(growth = (sector.growth - 5).coerceIn(-100, 100))
            }
        }

        if (sector.name == "Technology") {
            val education = country.stats.education
            newSector = newSector.copy(
                growth = (sector.growth + education / 20).coerceIn(-100, 100)
            )
        }

        return newSector
    }

    private fun calculateEconomicIndicators(
        sectors: List<EconomySector>,
        economy: EconomyState,
        country: Country
    ): EconomicIndicators {
        val indicators = economy.indicators

        // GDP Growth from sector performance
        val avgGrowth = sectors.map { it.growth }.average()
        val gdpGrowthRaw = avgGrowth / 10 + indicators.gdpGrowth * 0.8
        val gdpGrowth = gdpGrowthRaw.coerceAtLeast(-10.0).coerceAtMost(10.0)

        // Inflation from growth and money printing
        val inflationPressure = if (gdpGrowth > 3) 0.5 else 0.0
        val inflationRaw = indicators.inflation + inflationPressure
        val inflation = inflationRaw.coerceAtLeast(0.0).coerceAtMost(50.0)

        // Unemployment from sector employment
        val avgEmployment = sectors.map { it.employment }.average()
        val unemploymentRaw = (100 - avgEmployment) / 10 + if (gdpGrowth < 0) 2 else 0
        val unemployment = unemploymentRaw.coerceAtLeast(0.0).coerceAtMost(50.0)

        // Credit rating from debt and growth
        val creditRating = when {
            indicators.debtToGdp > 200 -> 10
            indicators.debtToGdp > 100 -> 30
            indicators.debtToGdp > 50 -> 50
            else -> 70 + (if (gdpGrowth > 3) 20 else 0)
        }

        return indicators.copy(
            gdpGrowth = gdpGrowth,
            inflation = inflation,
            unemployment = unemployment.coerceIn(0.0, 50.0),
            creditRating = creditRating
        )
    }

    private fun processStockMarket(stockMarket: StockMarket, indicators: EconomicIndicators): StockMarket {
        // Market follows economy with volatility
        val marketChange = when {
            indicators.gdpGrowth > 3 -> Random.nextInt(10, 50)
            indicators.gdpGrowth > 0 -> Random.nextInt(-10, 30)
            indicators.gdpGrowth > -2 -> Random.nextInt(-50, 10)
            else -> Random.nextInt(-100, -20)
        }

        var newIndex = (stockMarket.index + marketChange).coerceAtLeast(100)

        // Crash mechanics
        var crashRisk = stockMarket.crashRisk
        if (stockMarket.volatility > 80) {
            crashRisk += 10
        }
        if (Random.nextInt(100) < crashRisk) {
            newIndex = (newIndex * 0.7).toInt()
            crashRisk = 0
        }

        return stockMarket.copy(
            index = newIndex,
            bullMarket = newIndex > 1000,
            crashRisk = crashRisk.coerceIn(0, 100)
        )
    }

    private fun processBanking(banking: BankingSystem, indicators: EconomicIndicators): BankingSystem {
        var newBanking = banking

        // NPLs rise in recession
        if (indicators.gdpGrowth < 0) {
            newBanking = newBanking.copy(
                nonPerformingLoans = (banking.nonPerformingLoans + 2).coerceIn(0, 100)
            )
        }

        // Stability follows NPLs
        val stability = 100 - banking.nonPerformingLoans - banking.bankRunRisk
        newBanking = newBanking.copy(stability = stability.coerceIn(0, 100))

        // Bank run risk in crisis
        if (banking.stability < 30) {
            newBanking = newBanking.copy(
                bankRunRisk = (banking.bankRunRisk + 5).coerceIn(0, 100)
            )
        }

        return newBanking
    }

    private fun applyPolicyEffects(economy: EconomyState, country: Country): EconomyState {
        var newEconomy = economy

        when (economy.policy) {
            EconomicPolicy.LAISSEZ_FAIRE -> {
                // Higher growth, higher inequality
                newEconomy = newEconomy.copy(
                    indicators = economy.indicators.copy(
                        gdpGrowth = economy.indicators.gdpGrowth + 0.5,
                        unemployment = economy.indicators.unemployment - 0.5
                    )
                )
            }
            EconomicPolicy.KEYNESIAN -> {
                // Government spending boosts growth but increases debt
                newEconomy = newEconomy.copy(
                    indicators = economy.indicators.copy(
                        gdpGrowth = economy.indicators.gdpGrowth + 0.3,
                        debtToGdp = economy.indicators.debtToGdp + 2
                    )
                )
            }
            EconomicPolicy.AUSTERITY -> {
                // Cuts debt but hurts growth
                newEconomy = newEconomy.copy(
                    indicators = economy.indicators.copy(
                        debtToGdp = economy.indicators.debtToGdp - 3,
                        gdpGrowth = economy.indicators.gdpGrowth - 0.5,
                        unemployment = economy.indicators.unemployment + 1
                    )
                )
            }
            else -> {}
        }

        return newEconomy
    }

    // ────────────────────────────────────────────
    // PUBLIC OPINION SIMULATION
    // ────────────────────────────────────────────

    fun processPublicOpinion(game: GameV10): PublicOpinion {
        var opinion = game.publicOpinion
        val country = game.country
        val economy = game.economy

        // Approval from economy
        val economicApproval = when {
            economy.indicators.gdpGrowth > 3 -> 20
            economy.indicators.gdpGrowth > 0 -> 10
            economy.indicators.gdpGrowth < -2 -> -20
            else -> 0
        }

        // Approval from happiness/stability
        val socialApproval = (country.stats.happiness - 50) / 5 + (country.stats.stability - 50) / 5

        // Media influence
        val mediaInfluence = if (game.media.pressFreedom > 70) {
            // Free press holds government accountable
            (game.media.stateMedia - 50) / 10
        } else {
            // State media boosts approval artificially
            (game.media.stateMedia - 50) / 5
        }

        var newApproval = (opinion.approvalRating + economicApproval + socialApproval + mediaInfluence)
            .coerceIn(0, 100)

        // Polarization from controversial policies
        var polarization = opinion.politicalPolarization
        if (game.macroActions.activePolicies.any { it.opposition > 60 }) {
            polarization = (polarization + 2).coerceIn(0, 100)
        }

        // Protest activity
        var protestActivity = opinion.protestActivity
        if (newApproval < 30 && polarization > 60) {
            protestActivity = (protestActivity + 5).coerceIn(0, 100)
        } else if (newApproval > 60) {
            protestActivity = (protestActivity - 3).coerceIn(0, 100)
        }

        return opinion.copy(
            approvalRating = newApproval,
            politicalPolarization = polarization,
            protestActivity = protestActivity,
            nationalMood = newApproval
        )
    }

    fun generatePoll(game: GameV10, pollster: String): Poll {
        val opinion = game.publicOpinion
        val economy = game.economy

        val margin = Random.nextInt(2, 5)
        val noise = Random.nextInt(-margin, margin)

        return Poll(
            id = "poll_${game.turn}_${pollster}",
            date = game.turn,
            approvalRating = (opinion.approvalRating + noise).coerceIn(0, 100),
            economyRating = ((100 - economy.indicators.unemployment * 2) + noise).toInt().coerceIn(0, 100),
            securityRating = (game.country.stats.military + noise).coerceIn(0, 100),
            topConcern = getTopConcern(game),
            margin = margin
        )
    }

    private fun getTopConcern(game: GameV10): String {
        val economy = game.economy
        val country = game.country

        return when {
            economy.indicators.unemployment > 8 -> "Jobs & Economy"
            economy.indicators.inflation > 5 -> "Inflation"
            country.stats.military < 30 -> "National Security"
            country.stats.happiness < 40 -> "Healthcare"
            game.crisisManager.activeCrises.isNotEmpty() -> "Crisis Management"
            else -> "General Direction"
        }
    }

    // ────────────────────────────────────────────
    // CHARACTER SYSTEM
    // ────────────────────────────────────────────

    fun processCharacters(game: GameV10): List<Character> {
        return game.characters.map { character ->
            var newChar = character

            // Age characters
            newChar = newChar.copy(
                health = if (character.age > 70) (character.health - 2).coerceAtLeast(0) else character.health
            )

            // Process agendas
            if (character.agenda != null) {
                val agendaProgress = when (character.agenda.type) {
                    AgendaType.POWER_GRAB -> {
                        // Gains progress if loyalty is low and ambition is high
                        if (character.loyalty < 40 && character.ambition > 70) {
                            character.agenda.progress + 5
                        } else {
                            character.agenda.progress
                        }
                    }
                    AgendaType.CORRUPTION -> {
                        // Gains progress if corruption is high
                        if (character.corruption > 50) {
                            character.agenda.progress + 3
                        } else {
                            character.agenda.progress
                        }
                    }
                    AgendaType.REFORM -> {
                        // Gains progress if player passes related policies
                        if (game.macroActions.activePolicies.isNotEmpty()) {
                            character.agenda.progress + 2
                        } else {
                            character.agenda.progress
                        }
                    }
                    else -> character.agenda.progress
                }

                newChar = newChar.copy(
                    agenda = character.agenda.copy(progress = agendaProgress.coerceAtMost(100))
                )
            }

            // Check for agenda completion
            if (newChar.agenda?.progress == 100) {
                newChar = handleAgendaCompletion(newChar, game)
            }

            // Death from old age or poor health
            if (newChar.health <= 0 || (character.age > 85 && Random.nextInt(100) < 20)) {
                newChar = newChar.copy(isAlive = false)
            }

            newChar
        }
    }

    private fun handleAgendaCompletion(character: Character, game: GameV10): Character {
        return when (character.agenda?.type) {
            AgendaType.POWER_GRAB -> {
                // Character attempts to seize power
                character // Trigger coup event in main game logic
            }
            AgendaType.CORRUPTION -> {
                // Character steals money
                character // Trigger corruption scandal
            }
            AgendaType.REFORM -> {
                // Character successfully pushes reform
                character.copy(loyalty = (character.loyalty + 10).coerceAtMost(100))
            }
            AgendaType.LEGACY -> {
                // Character builds something lasting
                character.copy(popularity = (character.popularity + 20).coerceAtMost(100))
            }
            else -> character
        }
    }

    fun interactWithCharacter(game: GameV10, characterId: String, action: CharacterAction): GameV10 {
        val character = game.characters.find { it.id == characterId } ?: return game

        return when (action) {
            is CharacterAction.Bribe -> {
                if (game.country.treasury >= action.amount) {
                    game.copy(
                        country = game.country.copy(treasury = game.country.treasury - action.amount),
                        characters = game.characters.map {
                            if (it.id == characterId) {
                                it.copy(
                                    loyalty = (it.loyalty + 20).coerceAtMost(100),
                                    corruption = (it.corruption + 10).coerceAtMost(100)
                                )
                            } else it
                        }
                    )
                } else game
            }
            is CharacterAction.Promote -> {
                game.copy(
                    characters = game.characters.map {
                        if (it.id == characterId) {
                            it.copy(popularity = (it.popularity + 15).coerceAtMost(100))
                        } else it
                    }
                )
            }
            is CharacterAction.Fire -> {
                game.copy(
                    characters = game.characters.filter { it.id != characterId }
                )
            }
            is CharacterAction.AssignMission -> {
                // Assign character to special mission
                game
            }
        }
    }

    sealed class CharacterAction {
        data class Bribe(val amount: Int) : CharacterAction()
        object Promote : CharacterAction()
        object Fire : CharacterAction()
        data class AssignMission(val missionType: String) : CharacterAction()
    }

    // ────────────────────────────────────────────
    // CRISIS SYSTEM
    // ────────────────────────────────────────────

    fun processCrises(game: GameV10): CrisisManager {
        var crisisManager = game.crisisManager

        // Process active crises
        val updatedCrises = crisisManager.activeCrises.map { crisis ->
            processCrisis(crisis, game)
        }

        // Remove contained crises
        val activeCrises = updatedCrises.filter { !it.contained && it.duration < 50 }

        // Check for new crises
        val newCrisis = checkForNewCrisis(game, crisisManager)

        // Update crisis risks
        val updatedRisks = updateCrisisRisks(game, crisisManager.crisisRisk)

        return crisisManager.copy(
            activeCrises = activeCrises + listOfNotNull(newCrisis),
            crisisRisk = updatedRisks
        )
    }

    private fun processCrisis(crisis: Crisis, game: GameV10): Crisis {
        var newCrisis = crisis.copy(duration = crisis.duration + 1)

        // Natural decay
        val decay = when (crisis.severity) {
            in 0..30 -> 5
            in 31..60 -> 3
            else -> 1
        }

        newCrisis = newCrisis.copy(
            severity = (crisis.severity - decay).coerceAtLeast(0)
        )

        // Check if contained
        if (newCrisis.severity <= 10) {
            newCrisis = newCrisis.copy(contained = true)
        }

        // Apply crisis effects
        when (crisis.type) {
            CrisisType.BANK_RUN -> {
                // Banking stability decreases
            }
            CrisisType.PANDEMIC -> {
                // Population decreases
            }
            else -> {}
        }

        return newCrisis
    }

    private fun checkForNewCrisis(game: GameV10, crisisManager: CrisisManager): Crisis? {
        val risk = crisisManager.crisisRisk

        // Check each crisis type
        risk.forEach { (type, probability) ->
            if (Random.nextInt(100) < probability) {
                return createCrisis(type, game)
            }
        }

        return null
    }

    private fun createCrisis(type: CrisisType, game: GameV10): Crisis {
        val severity = when (type) {
            CrisisType.BANK_RUN, CrisisType.STOCK_CRASH -> Random.nextInt(40, 80)
            CrisisType.PANDEMIC, CrisisType.FAMINE -> Random.nextInt(50, 100)
            CrisisType.CIVIL_UNREST, CrisisType.GENERAL_STRIKE -> Random.nextInt(30, 70)
            CrisisType.CORRUPTION_SCANDAL -> Random.nextInt(20, 60)
            else -> Random.nextInt(30, 50)
        }

        return Crisis(
            id = "crisis_${type.name}_${game.turn}",
            type = type,
            severity = severity,
            peakSeverity = severity
        )
    }

    private fun updateCrisisRisks(game: GameV10, currentRisks: Map<CrisisType, Int>): Map<CrisisType, Int> {
        val risks = currentRisks.toMutableMap()

        // Economic risks from indicators
        val economy = game.economy
        risks[CrisisType.BANK_RUN] = if (economy.banking.stability < 50) 30 else 5
        risks[CrisisType.STOCK_CRASH] = if (economy.stockMarket.volatility > 70) 25 else 5
        risks[CrisisType.CURRENCY_CRISIS] = if (economy.indicators.debtToGdp > 100) 20 else 5

        // Social risks from opinion
        val opinion = game.publicOpinion
        risks[CrisisType.CIVIL_UNREST] = if (opinion.approvalRating < 30) 30 else 5
        risks[CrisisType.GENERAL_STRIKE] = if (opinion.protestActivity > 50) 25 else 5

        // Political risks
        risks[CrisisType.CORRUPTION_SCANDAL] = if (game.country.stats.corruption > 50) 20 else 5

        // Cascade risks (active crises increase other risks)
        game.crisisManager.activeCrises.forEach { crisis ->
            if (crisis.severity > 60) {
                // High severity crises increase other risks
                val cascadeBonus = 10
                risks.putAll(risks.mapValues { (_, v) -> (v + cascadeBonus).coerceIn(0, 100) })
            }
        }

        return risks
    }

    fun respondToCrisis(game: GameV10, crisisId: String, response: CrisisResponse): GameV10 {
        val crisis = game.crisisManager.activeCrises.find { it.id == crisisId } ?: return game

        val effectiveness = when (response.strategy) {
            ResponseStrategy.DENIAL -> -10 // Makes it worse
            ResponseStrategy.CONTAINMENT -> 30
            ResponseStrategy.FULL_MOBILIZATION -> 50
            ResponseStrategy.NEGOTIATION -> 25
            ResponseStrategy.AUTHORITARIAN -> 40
            ResponseStrategy.REFORM -> 45
            ResponseStrategy.EXTERNAL_AID -> 35
            ResponseStrategy.SACRIFICE -> 60
        }

        val newCrisis = crisis.copy(
            severity = (crisis.severity - effectiveness).coerceAtLeast(0),
            response = response
        )

        return game.copy(
            crisisManager = game.crisisManager.copy(
                activeCrises = game.crisisManager.activeCrises.map {
                    if (it.id == crisisId) newCrisis else it
                }
            ),
            country = game.country.copy(
                treasury = game.country.treasury - response.cost
            )
        )
    }

    // ────────────────────────────────────────────
    // MICRO ACTIONS
    // ────────────────────────────────────────────

    fun getAvailableMicroActions(game: GameV10): List<DailyAction> {
        val actions = mutableListOf<DailyAction>()

        // Political actions
        actions.add(DailyAction(
            id = "micro_speech",
            name = "Give Speech",
            description = "Address the nation on current issues",
            category = ActionCategory.POLITICAL,
            cost = ActionCost(actionPoints = 1, stress = 10),
            effects = listOf(Effect("approval", 5, 3))
        ))

        actions.add(DailyAction(
            id = "micro_meeting",
            name = "Party Meeting",
            description = "Meet with party leaders",
            category = ActionCategory.POLITICAL,
            cost = ActionCost(actionPoints = 1),
            effects = listOf(Effect("politicalCapital", 10, 1))
        ))

        // Economic actions
        actions.add(DailyAction(
            id = "micro_invest",
            name = "Direct Investment",
            description = "Personally oversee an investment project",
            category = ActionCategory.ECONOMIC,
            cost = ActionCost(actionPoints = 1, money = 500),
            effects = listOf(Effect("sector_growth", 10, 5, delayed = true, delayedTurns = 1))
        ))

        // Diplomatic actions
        actions.add(DailyAction(
            id = "micro_call",
            name = "Foreign Leader Call",
            description = "Call a foreign leader to discuss issues",
            category = ActionCategory.DIPLOMATIC,
            cost = ActionCost(actionPoints = 1),
            effects = listOf(Effect("relations", 5, 1))
        ))

        // Personal actions
        actions.add(DailyAction(
            id = "micro_rest",
            name = "Take Rest",
            description = "Rest and recover energy",
            category = ActionCategory.PERSONAL,
            cost = ActionCost(actionPoints = 1),
            effects = listOf(Effect("energy", 30, 1), Effect("stress", -20, 1))
        ))

        // Filter by available action points
        return actions.filter { it.cost.actionPoints <= game.microActions.actionPoints }
    }

    fun performMicroAction(game: GameV10, actionId: String): GameV10 {
        val action = getAvailableMicroActions(game).find { it.id == actionId } ?: return game

        var newGame = game

        // Pay costs
        newGame = newGame.copy(
            country = newGame.country.copy(
                treasury = newGame.country.treasury - action.cost.money
            ),
            microActions = newGame.microActions.copy(
                actionPoints = newGame.microActions.actionPoints - action.cost.actionPoints,
                stressLevel = (newGame.microActions.stressLevel + action.cost.stress).coerceIn(0, 100),
                energyLevel = (newGame.microActions.energyLevel - action.cost.energy).coerceIn(0, 100)
            )
        )

        // Apply effects (simplified - would need effect system)
        action.effects.forEach { effect ->
            when (effect.stat) {
                "approval" -> {
                    newGame = newGame.copy(
                        publicOpinion = newGame.publicOpinion.copy(
                            approvalRating = (newGame.publicOpinion.approvalRating + effect.value)
                                .coerceIn(0, 100)
                        )
                    )
                }
            }
        }

        return newGame
    }

    // ────────────────────────────────────────────
    // MACRO ACTIONS (Policies)
    // ────────────────────────────────────────────

    fun proposePolicy(game: GameV10, policy: Policy): GameV10 {
        val proposal = PolicyProposal(
            policy = policy,
            proposer = game.characters.firstOrNull()?.id ?: "unknown",
            support = calculatePolicySupport(game, policy),
            opposition = calculatePolicyOpposition(game, policy)
        )

        return game.copy(
            macroActions = game.macroActions.copy(
                proposedPolicies = game.macroActions.proposedPolicies + proposal
            )
        )
    }

    fun processPolicyProposals(game: GameV10): GameV10 {
        val remaining = mutableListOf<PolicyProposal>()
        val passed = mutableListOf<Policy>()

        game.macroActions.proposedPolicies.forEach { proposal ->
            val newProposal = proposal.copy(
                turnsInDebate = proposal.turnsInDebate + 1
            )

            if (newProposal.turnsInDebate >= newProposal.turnsToPass) {
                // Vote time
                if (newProposal.support > newProposal.opposition) {
                    passed.add(newProposal.policy)
                }
            } else {
                remaining.add(newProposal)
            }
        }

        return game.copy(
            macroActions = game.macroActions.copy(
                proposedPolicies = remaining,
                activePolicies = game.macroActions.activePolicies + passed
            )
        )
    }

    private fun calculatePolicySupport(game: GameV10, policy: Policy): Int {
        var support = 50

        // Character support
        game.characters.filter { it.isAlive }.forEach { char ->
            if (char.loyalty > 60) support += 5
        }

        // Public support based on policy type
        when (policy.category) {
            PolicyCategory.WELFARE -> {
                support += (game.publicOpinion.approvalRating - 50) / 5
            }
            PolicyCategory.DEFENSE -> {
                support += (game.country.stats.military - 50) / 5
            }
            else -> {}
        }

        return support.coerceIn(0, 100)
    }

    private fun calculatePolicyOpposition(game: GameV10, policy: Policy): Int {
        var opposition = 30

        // Opposition from affected groups
        when (policy.category) {
            PolicyCategory.TAXATION -> {
                opposition += 20 // Rich always oppose taxes
            }
            PolicyCategory.ENVIRONMENT -> {
                opposition += 15 // Industry opposes regulation
            }
            else -> {}
        }

        // Opposition from rivals
        game.characters.filter { it.role == CharacterRole.RIVAL && it.isAlive }
            .forEach { opposition += 10 }

        return opposition.coerceIn(0, 100)
    }

    // ────────────────────────────────────────────
    // DYNAMIC EVENT GENERATOR
    // ────────────────────────────────────────────

    fun generateDynamicEvent(game: GameV10): GameEvent? {
        // Check event chains first
        game.eventMemory.eventChains.forEach { (chainId, progress) ->
            // Continue chain if active
        }

        // Generate event based on game state
        val context = buildEventContext(game)

        return when {
            // Economic events
            game.economy.indicators.unemployment > 10 -> generateUnemploymentEvent(context)
            game.economy.indicators.inflation > 5 -> generateInflationEvent(context)

            // Political events
            game.publicOpinion.approvalRating < 30 -> generateUnrestEvent(context)
            game.publicOpinion.politicalPolarization > 70 -> generatePolarizationEvent(context)

            // Character events
            game.characters.any { it.agenda?.progress == 100 } -> generateAgendaEvent(context, game)

            // Crisis events
            game.crisisManager.activeCrises.isNotEmpty() -> generateCrisisEvent(context, game)

            // Random events based on context
            Random.nextInt(100) < 15 -> generateRandomEvent(context)

            else -> null
        }
    }

    private fun buildEventContext(game: GameV10): EventContext {
        return EventContext(
            turn = game.turn,
            approvalRating = game.publicOpinion.approvalRating,
            economicGrowth = game.economy.indicators.gdpGrowth,
            activeCrises = game.crisisManager.activeCrises.size,
            characters = game.characters.filter { it.isAlive },
            aiNations = game.aiNations.filter { it.isAlive },
            pastEvents = game.eventMemory.pastEvents
        )
    }

    private fun generateUnemploymentEvent(context: EventContext): GameEvent {
        return GameEvent(
            id = "unemployment_crisis_${context.turn}",
            title = "Rising Unemployment",
            description = "Unemployment has reached ${context.economicGrowth.toInt()}%. Workers are demanding action.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(happiness = (it.happiness - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Job Creation Program", "Invest in public works") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5), treasury - 2000, resources)
                },
                EventOption("Tax Cuts for Business", "Let market handle it") { stats, treasury, resources ->
                    Triple(stats.copy(economy = stats.economy + 5), treasury - 1000, resources)
                },
                EventOption("Unemployment Benefits", "Support the unemployed") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = stats.stability + 5), treasury - 1500, resources)
                }
            )
        )
    }

    private fun generateInflationEvent(context: EventContext): GameEvent {
        return GameEvent(
            id = "inflation_surge_${context.turn}",
            title = "Inflation Surge",
            description = "Prices are rising rapidly. Citizens are struggling to afford basics.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(happiness = (it.happiness - 10).coerceAtLeast(0), stability = (it.stability - 5).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Raise Interest Rates", "Cool the economy") { stats, treasury, resources ->
                    Triple(stats.copy(economy = (stats.economy - 5).coerceAtLeast(0), happiness = (stats.happiness - 5).coerceAtLeast(0)), treasury, resources)
                },
                EventOption("Price Controls", "Cap prices") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 5, economy = (stats.economy - 10).coerceAtLeast(0)), treasury, resources)
                },
                EventOption("Do Nothing", "Wait it out") { stats, treasury, resources ->
                    Triple(stats, treasury, resources)
                }
            )
        )
    }

    private fun generateUnrestEvent(context: EventContext): GameEvent {
        return GameEvent(
            id = "mass_protests_${context.turn}",
            title = "Mass Protests",
            description = "Thousands are in the streets demanding your resignation!",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.MAJOR,
            effect = { it.copy(stability = (it.stability - 20).coerceAtLeast(0)) },
            options = listOf(
                EventOption("Concede to Demands", "Accept reforms") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 15, stability = stats.stability + 10), treasury - 2000, resources)
                },
                EventOption("Crack Down", "Use force") { stats, treasury, resources ->
                    Triple(stats.copy(military = stats.military + 5, happiness = (stats.happiness - 20).coerceAtLeast(0), stability = stats.stability + 5), treasury - 1000, resources)
                },
                EventOption("Call Elections", "Let people decide") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15), treasury - 500, resources)
                }
            )
        )
    }

    private fun generatePolarizationEvent(context: EventContext): GameEvent {
        return GameEvent(
            id = "political_polarization_${context.turn}",
            title = "Nation Divided",
            description = "Political polarization is at an all-time high. The country is splitting apart.",
            category = EventCategory.POLITICAL,
            severity = EventSeverity.MODERATE,
            effect = { it.copy(stability = (it.stability - 10).coerceAtLeast(0)) },
            options = listOf(
                EventOption("National Unity Campaign", "Bring people together") { stats, treasury, resources ->
                    Triple(stats.copy(stability = stats.stability + 15), treasury - 1500, resources)
                },
                EventOption("Take Sides", "Mobilize your base") { stats, treasury, resources ->
                    Triple(stats.copy(happiness = stats.happiness + 10, stability = (stats.stability - 10).coerceAtLeast(0)), treasury - 500, resources)
                }
            )
        )
    }

    private fun generateAgendaEvent(context: EventContext, game: GameV10): GameEvent {
        val character = game.characters.first { it.agenda?.progress == 100 && it.isAlive }

        return when (character.agenda?.type) {
            AgendaType.POWER_GRAB -> GameEvent(
                id = "coup_attempt_${context.turn}",
                title = "Coup Attempt!",
                description = "${character.name} is attempting to seize power!",
                category = EventCategory.POLITICAL,
                severity = EventSeverity.CATASTROPHIC,
                effect = { it.copy(stability = (it.stability - 30).coerceAtLeast(0)) },
                options = listOf(
                    EventOption("Arrest Conspirators", "Crush the coup") { stats, treasury, resources ->
                        val success = stats.military > 50
                        if (success) {
                            Triple(stats.copy(stability = stats.stability + 20, military = stats.military + 5), treasury - 1000, resources)
                        } else {
                            Triple(stats.copy(stability = 10, military = (stats.military - 20).coerceAtLeast(0)), treasury - 2000, resources)
                        }
                    },
                    EventOption("Flee Country", "Escape into exile") { stats, treasury, resources ->
                        Triple(stats.copy(stability = 5), treasury - 5000, resources)
                    }
                )
            )
            AgendaType.CORRUPTION -> GameEvent(
                id = "corruption_scandal_${context.turn}",
                title = "Corruption Scandal",
                description = "${character.name} has been caught embezzling funds!",
                category = EventCategory.POLITICAL,
                severity = EventSeverity.MODERATE,
                effect = { it.copy(stability = (it.stability - 10).coerceAtLeast(0)) },
                options = listOf(
                    EventOption("Prosecute", "Make example") { stats, treasury, resources ->
                        Triple(stats.copy(stability = stats.stability + 10), treasury + 500, resources)
                    },
                    EventOption("Cover Up", "Hide the scandal") { stats, treasury, resources ->
                        Triple(stats.copy(corruption = (stats.corruption + 10).coerceAtMost(100)), treasury - 1000, resources)
                    }
                )
            )
            else -> null
        } ?: generateRandomEvent(context)
    }

    private fun generateCrisisEvent(context: EventContext, game: GameV10): GameEvent? {
        val crisis = game.crisisManager.activeCrises.firstOrNull() ?: return null

        return GameEvent(
            id = "crisis_update_${crisis.id}",
            title = "${crisis.type.name.replace('_', ' ')} Continues",
            description = "The ${crisis.type.name.lowercase()} is ongoing. Severity: ${crisis.severity}%",
            category = EventCategory.DISASTER,
            severity = when (crisis.severity) {
                in 0..30 -> EventSeverity.MINOR
                in 31..60 -> EventSeverity.MODERATE
                in 61..80 -> EventSeverity.MAJOR
                else -> EventSeverity.CATASTROPHIC
            },
            effect = { it },
            options = listOf(
                EventOption("Continue Current Response", "Stay the course") { stats, treasury, resources ->
                    Triple(stats, treasury, resources)
                },
                EventOption("Escalate Response", "More resources") { stats, treasury, resources ->
                    Triple(stats, treasury - 1000, resources)
                }
            )
        )
    }

    private fun generateRandomEvent(context: EventContext): GameEvent {
        val randomEvents = listOf(
            "economic_boom", "natural_disaster", "scientific_breakthrough",
            "political_unrest", "trade_agreement"
        )

        return GameEvent(
            id = "random_${randomEvents.random()}_${context.turn}",
            title = "Random Event",
            description = "Something unexpected has happened.",
            category = EventCategory.ECONOMIC,
            severity = EventSeverity.MINOR,
            effect = { it },
            options = listOf(
                EventOption("Option 1", "Choose this") { stats, treasury, resources ->
                    Triple(stats, treasury + 500, resources)
                }
            )
        )
    }

    // ────────────────────────────────────────────
    // LEGACY SYSTEM
    // ────────────────────────────────────────────

    fun updateLegacy(game: GameV10, reason: GameOverReason?): GameV10 {
        var legacy = game.legacy

        // Add this playthrough to history
        val historicalLeader = HistoricalLeader(
            name = game.country.name,
            governmentType = game.country.governmentType,
            turnsInPower = game.turn,
            achievements = calculateAchievements(game),
            failures = calculateFailures(game, reason),
            legacyRating = calculateLegacyRating(game, reason),
            endedBy = reason
        )

        legacy = legacy.copy(
            totalPlaythroughs = legacy.totalPlaythroughs + 1,
            totalTurns = legacy.totalTurns + game.turn,
            historicalLeaders = legacy.historicalLeaders + historicalLeader
        )

        // Unlock achievements
        val newAchievements = checkAchievements(game, reason)
        legacy = legacy.copy(
            achievements = legacy.achievements + newAchievements
        )

        // Unlock bonuses
        val newBonuses = calculateLegacyBonuses(legacy)
        legacy = legacy.copy(
            unlockedBonuses = legacy.unlockedBonuses + newBonuses
        )

        return game.copy(legacy = legacy)
    }

    private fun calculateAchievements(game: GameV10): List<String> {
        val achievements = mutableListOf<String>()

        if (game.turn >= 50) achievements.add("Long-serving Leader")
        if (game.publicOpinion.approvalRating >= 80) achievements.add("Beloved Leader")
        if (game.economy.indicators.gdpGrowth >= 5) achievements.add("Economic Miracle")

        return achievements
    }

    private fun calculateFailures(game: GameV10, reason: GameOverReason?): List<String> {
        val failures = mutableListOf<String>()

        when (reason) {
            GameOverReason.BANKRUPTCY -> failures.add("Economic Collapse")
            GameOverReason.REVOLUTION -> failures.add("Overthrown by People")
            GameOverReason.INVASION -> failures.add("Military Defeat")
            else -> {}
        }

        return failures
    }

    private fun calculateLegacyRating(game: GameV10, reason: GameOverReason?): Int {
        var rating = 50

        // Base from stats
        rating += (game.country.stats.economy - 50) / 5
        rating += (game.publicOpinion.approvalRating - 50) / 5

        // Penalty for bad endings
        when (reason) {
            GameOverReason.BANKRUPTCY, GameOverReason.REVOLUTION -> rating -= 30
            GameOverReason.INVASION -> rating -= 20
            else -> {}
        }

        return rating.coerceIn(0, 100)
    }

    private fun checkAchievements(game: GameV10, reason: GameOverReason?): List<Achievement> {
        val achievements = mutableListOf<Achievement>()

        // Turn-based achievements
        if (game.turn >= 100) {
            achievements.add(Achievement("century", "Century Club", "Last 100 turns", true, game.turn, AchievementDifficulty.NORMAL))
        }

        // Stat-based achievements
        if (game.country.stats.economy >= 100) {
            achievements.add(Achievement("economic_power", "Economic Superpower", "Reach max economy", true, game.turn, AchievementDifficulty.HARD))
        }

        // Ending achievements
        if (reason == null) {
            achievements.add(Achievement("survivor", "Survivor", "End without game over", true, game.turn, AchievementDifficulty.EASY))
        }

        return achievements
    }

    private fun calculateLegacyBonuses(legacy: LegacyData): List<LegacyBonus> {
        val bonuses = mutableListOf<LegacyBonus>()

        if (legacy.totalPlaythroughs >= 5) {
            bonuses.add(LegacyBonus("veteran", "Veteran Player", "5+ playthroughs", "+5 starting approval", true))
        }

        if (legacy.totalTurns >= 500) {
            bonuses.add(LegacyBonus("dedicated", "Dedicated Leader", "500+ total turns", "+5% income", true))
        }

        return bonuses
    }

    // ────────────────────────────────────────────
    // MAIN TURN PROCESSOR
    // ────────────────────────────────────────────

    fun processTurnV10(game: GameV10): GameV10 {
        var newGame = game.copy(turn = game.turn + 1)

        // 1. Process Economy
        newGame = newGame.copy(economy = processEconomy(newGame))

        // 2. Process Public Opinion
        newGame = newGame.copy(publicOpinion = processPublicOpinion(newGame))

        // 3. Process Characters
        newGame = newGame.copy(characters = processCharacters(newGame))

        // 4. Process Crises
        newGame = newGame.copy(crisisManager = processCrises(newGame))

        // 5. Process Policy Proposals
        newGame = processPolicyProposals(newGame)

        // 6. Reset Micro Actions
        newGame = newGame.copy(
            microActions = newGame.microActions.copy(
                actionPoints = newGame.microActions.maxActionPoints,
                energyLevel = (newGame.microActions.energyLevel + 20).coerceAtMost(100)
            )
        )

        // 7. Generate Dynamic Event
        val event = generateDynamicEvent(newGame)
        if (event != null) {
            newGame = newGame.copy(
                lastEvent = event,
                newsHeadline = event.title,
                eventHistory = newGame.eventHistory + "${newGame.turn}: ${event.title}"
            )
        }

        // 8. Update Crisis Risks
        newGame = newGame.copy(
            crisisManager = newGame.crisisManager.copy(
                crisisRisk = updateCrisisRisks(newGame, newGame.crisisManager.crisisRisk)
            )
        )

        // 9. Check Game Over
        val gameOverReason = checkGameOverV10(newGame)
        if (gameOverReason != null) {
            newGame = newGame.copy(
                isGameOver = true,
                gameOverReason = gameOverReason
            )
            newGame = updateLegacy(newGame, gameOverReason)
        }

        return newGame
    }

    private fun checkGameOverV10(game: GameV10): GameOverReason? {
        // Original game over conditions
        val originalReason = checkGameOverOriginal(game.country)
        if (originalReason != null) return originalReason

        // New v10.0 game over conditions
        if (game.economy.banking.bankRunRisk >= 100) {
            return GameOverReason.BANKRUPTCY
        }

        if (game.publicOpinion.protestActivity >= 100) {
            return GameOverReason.REVOLUTION
        }

        if (game.crisisManager.activeCrises.any { it.type == CrisisType.STATE_FAILURE }) {
            return GameOverReason.CIVIL_WAR
        }

        return null
    }

    private fun checkGameOverOriginal(country: Country): GameOverReason? {
        return when {
            country.treasury < -5000 -> GameOverReason.BANKRUPTCY
            country.stats.happiness < 5 -> GameOverReason.REVOLUTION
            country.stats.military < 5 && Random.nextInt(10) == 0 -> GameOverReason.INVASION
            country.stats.technology < 3 -> GameOverReason.TECH_FAILURE
            country.resources.food < 5 -> GameOverReason.FAMINE
            country.stats.stability < 5 -> GameOverReason.CIVIL_WAR
            else -> null
        }
    }
}

// Event context data class
data class EventContext(
    val turn: Int,
    val approvalRating: Int,
    val economicGrowth: Double,
    val activeCrises: Int,
    val characters: List<Character>,
    val aiNations: List<AiNation>,
    val pastEvents: List<PastEvent>
)
