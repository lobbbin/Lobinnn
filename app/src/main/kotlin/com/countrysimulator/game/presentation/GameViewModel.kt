package com.countrysimulator.game.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.countrysimulator.game.data.GameRepository
import com.countrysimulator.game.domain.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameUiState(
    val isLoading: Boolean = true,
    val hasActiveGame: Boolean = false,
    val gameState: GameState? = null,
    val showNewGameDialog: Boolean = false,
    val showEventDialog: Boolean = false,
    val currentEvent: GameEvent? = null,
    val incomeThisTurn: Int = 0,
    val newsHeadline: String? = null
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GameRepository(application)
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init { loadSavedGame() }

    private fun loadSavedGame() {
        viewModelScope.launch {
            repository.loadGame().collect { savedGameState ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasActiveGame = savedGameState != null,
                        gameState = savedGameState,
                        showNewGameDialog = savedGameState == null,
                        incomeThisTurn = savedGameState?.let { s -> GameLogic.calculateTurnIncome(s.country) } ?: 0
                    )
                }
            }
        }
    }

    fun startNewGame(name: String, governmentType: GovernmentType) {
        val (newCountry, aiNations) = GameLogic.generateInitialCountry(name, governmentType)
        val newGameState = GameState(country = newCountry, aiNations = aiNations)
        saveAndUpdateState(newGameState)
    }

    fun nextTurn() {
        val currentState = _uiState.value.gameState ?: return
        val newState = GameLogic.processTurn(currentState)
        saveAndUpdateState(newState, showEvent = newState.lastEvent != null)
    }

    fun dismissTurnSummary() {
        _uiState.update { it.copy(gameState = it.gameState?.copy(turnSummary = null)) }
    }

    fun setTaxRate(rate: TaxRate) {
        val current = _uiState.value.gameState ?: return
        saveAndUpdateState(GameLogic.setTaxRate(current, rate))
    }

    fun sellResource(resource: String, amount: Int) {
        val current = _uiState.value.gameState ?: return
        saveAndUpdateState(GameLogic.sellResource(current, resource, amount))
    }

    fun declareWar(nationId: String) {
        val current = _uiState.value.gameState ?: return
        saveAndUpdateState(GameLogic.declareWar(current, nationId))
    }

    fun handleEventOption(optionIndex: Int) {
        val currentState = _uiState.value.gameState ?: return
        val event = _uiState.value.currentEvent ?: return
        val option = event.options.getOrNull(optionIndex) ?: return

        val (newStats, newTreasury, newResources) = option.effect(
            currentState.country.stats, currentState.country.treasury, currentState.country.resources
        )

        val updatedCountry = currentState.country.copy(stats = newStats, treasury = newTreasury, resources = newResources)
        val newState = currentState.copy(country = updatedCountry)

        saveAndUpdateState(newState, showEvent = false)
    }

    private fun saveAndUpdateState(newState: GameState, showEvent: Boolean = false) {
        viewModelScope.launch { repository.saveGame(newState) }
        _uiState.update {
            it.copy(
                gameState = newState,
                showEventDialog = showEvent,
                currentEvent = newState.lastEvent,
                newsHeadline = newState.newsHeadline ?: it.newsHeadline,
                incomeThisTurn = GameLogic.calculateTurnIncome(newState.country)
            )
        }
    }

    private fun updateCountry(action: (Country) -> Country) {
        _uiState.update { current ->
            val gs = current.gameState ?: return@update current
            val newCountry = action(gs.country)
            val newState = gs.copy(country = newCountry)
            viewModelScope.launch { repository.saveGame(newState) }
            current.copy(gameState = newState, incomeThisTurn = GameLogic.calculateTurnIncome(newCountry))
        }
    }

    // Core Actions
    fun investInEconomy() = updateCountry { country ->
        if (country.treasury >= 1000) {
            country.copy(
                stats = country.stats.copy(economy = (country.stats.economy + 8).coerceAtMost(100)),
                treasury = country.treasury - 1000
            )
        } else country
    }

    fun recruitMilitary() = updateCountry { country ->
        if (country.treasury >= 800) {
            country.copy(
                stats = country.stats.copy(military = (country.stats.military + 10).coerceAtMost(100)),
                treasury = country.treasury - 800
            )
        } else country
    }

    fun improveInfrastructure() = updateCountry { country ->
        if (country.treasury >= 1200) {
            country.copy(
                stats = country.stats.copy(stability = (country.stats.stability + 8).coerceAtMost(100)),
                treasury = country.treasury - 1200
            )
        } else country
    }

    fun investInTechnology() = updateCountry { country ->
        if (country.treasury >= 1500) {
            country.copy(
                stats = country.stats.copy(technology = (country.stats.technology + 12).coerceAtMost(100)),
                treasury = country.treasury - 1500
            )
        } else country
    }

    fun improveHappiness() = updateCountry { country ->
        if (country.treasury >= 800) {
            country.copy(
                stats = country.stats.copy(happiness = (country.stats.happiness + 10).coerceAtMost(100)),
                treasury = country.treasury - 800
            )
        } else country
    }

    fun investInEducation() = updateCountry { country ->
        if (country.treasury >= 1200) {
            country.copy(
                stats = country.stats.copy(education = (country.stats.education + 10).coerceAtMost(100)),
                treasury = country.treasury - 1200
            )
        } else country
    }

    fun investInHealthcare() = updateCountry { country ->
        if (country.treasury >= 1000) {
            country.copy(
                stats = country.stats.copy(healthcare = (country.stats.healthcare + 10).coerceAtMost(100)),
                treasury = country.treasury - 1000
            )
        } else country
    }

    fun improveEnvironment() = updateCountry { country ->
        if (country.treasury >= 1500) {
            country.copy(
                stats = country.stats.copy(environment = (country.stats.environment + 10).coerceAtMost(100)),
                treasury = country.treasury - 1500
            )
        } else country
    }

    fun fightCrime() = updateCountry { country ->
        if (country.treasury >= 800) {
            country.copy(
                stats = country.stats.copy(crime = (country.stats.crime - 10).coerceAtLeast(0)),
                treasury = country.treasury - 800
            )
        } else country
    }

    fun buyFood() = updateCountry { country ->
        if (country.treasury >= 500) {
            country.copy(
                resources = country.resources.copy(food = (country.resources.food + 50).coerceAtMost(country.resources.maxFood)),
                treasury = country.treasury - 500
            )
        } else country
    }

    fun buyEnergy() = updateCountry { country ->
        if (country.treasury >= 600) {
            country.copy(
                resources = country.resources.copy(energy = (country.resources.energy + 50).coerceAtMost(country.resources.maxEnergy)),
                treasury = country.treasury - 600
            )
        } else country
    }

    fun buyMaterials() = updateCountry { country ->
        if (country.treasury >= 800) {
            country.copy(
                resources = country.resources.copy(materials = (country.resources.materials + 30).coerceAtMost(country.resources.maxMaterials)),
                treasury = country.treasury - 800
            )
        } else country
    }

    fun runPropaganda() = updateCountry { country ->
        if (country.treasury >= 1000) {
            country.copy(
                stats = country.stats.copy(
                    propaganda = (country.stats.propaganda + 15).coerceAtMost(100),
                    happiness = (country.stats.happiness + 5).coerceAtMost(100),
                    stability = (country.stats.stability + 10).coerceAtMost(100)
                ),
                treasury = country.treasury - 1000
            )
        } else country
    }

    // Diplomacy & Intel
    fun improveRelations(nationId: String) = updateCountry { country ->
        if (country.treasury >= 500) {
            val rels = country.diplomaticRelations.map {
                if (it.nationId == nationId) it.copy(relationScore = (it.relationScore + 10).coerceAtMost(100)) else it
            }
            country.copy(diplomaticRelations = rels, treasury = country.treasury - 500)
        } else country
    }

    fun sendForeignAid(nationId: String) = updateCountry { country ->
        if (country.treasury >= 2000) {
            val rels = country.diplomaticRelations.map {
                if (it.nationId == nationId) it.copy(relationScore = (it.relationScore + 15).coerceAtMost(100)) else it
            }
            country.copy(
                diplomaticRelations = rels,
                treasury = country.treasury - 2000,
                stats = country.stats.copy(softPower = (country.stats.softPower + 5).coerceAtMost(100))
            )
        } else country
    }

    fun offerTrade(nationId: String) = updateCountry { country ->
        val relation = country.diplomaticRelations.find { it.nationId == nationId } ?: return@updateCountry country
        if (relation.relationScore >= 40 && !relation.hasTradeAgreement) {
            val rels = country.diplomaticRelations.map {
                if (it.nationId == nationId) it.copy(hasTradeAgreement = true) else it
            }
            country.copy(diplomaticRelations = rels)
        } else country
    }

    fun formAlliance(nationId: String) = updateCountry { country ->
        val relation = country.diplomaticRelations.find { it.nationId == nationId } ?: return@updateCountry country
        if (relation.relationScore >= 80 && !relation.hasAlliance) {
            val rels = country.diplomaticRelations.map {
                if (it.nationId == nationId) it.copy(hasAlliance = true, status = RelationStatus.ALLY) else it
            }
            country.copy(diplomaticRelations = rels)
        } else country
    }

    fun imposeSanctions(nationId: String, sanctionType: SanctionType) = updateCountry { country ->
        if (country.treasury >= 500) {
            val rels = country.diplomaticRelations.map {
                if (it.nationId == nationId && !it.sanctions.contains(sanctionType)) {
                    it.copy(
                        sanctions = it.sanctions + sanctionType,
                        relationScore = (it.relationScore - 30).coerceAtLeast(0),
                        status = if (it.relationScore - 30 < 20) RelationStatus.ENEMY else it.status
                    )
                } else it
            }
            country.copy(diplomaticRelations = rels, treasury = country.treasury - 500)
        } else country
    }

    fun launchSpyMission(targetId: String, type: SpyMissionType) = updateCountry { country ->
        if (country.treasury >= type.cost) {
            val targetName = _uiState.value.gameState?.aiNations?.find { it.id == targetId }?.name ?: "Unknown"
            val mission = SpyMission(
                "spy_${System.currentTimeMillis()}",
                targetId,
                targetName,
                type,
                (40..80).random(),
                type.duration,
                50
            )
            country.copy(activeSpyMissions = country.activeSpyMissions + mission, treasury = country.treasury - type.cost)
        } else country
    }

    // Military Updates V6.0
    fun recruitTroops(branch: String) = updateCountry { country ->
        if (country.treasury >= 500) {
            val mil = country.military
            val updated = when (branch) {
                "Army" -> mil.copy(army = mil.army.copy(manpower = mil.army.manpower + 1000))
                "Navy" -> mil.copy(navy = mil.navy.copy(manpower = mil.navy.manpower + 500))
                else -> mil.copy(airForce = mil.airForce.copy(manpower = mil.airForce.manpower + 200))
            }
            country.copy(military = updated, treasury = country.treasury - 500)
        } else country
    }

    fun upgradeEquipment(branch: String) = updateCountry { country ->
        if (country.treasury >= 2000) {
            val mil = country.military
            val updated = when (branch) {
                "Army" -> mil.copy(army = mil.army.copy(equipmentLevel = (mil.army.equipmentLevel + 1).coerceAtMost(10)))
                "Navy" -> mil.copy(navy = mil.navy.copy(equipmentLevel = (mil.navy.equipmentLevel + 1).coerceAtMost(10)))
                else -> mil.copy(airForce = mil.airForce.copy(equipmentLevel = (mil.airForce.equipmentLevel + 1).coerceAtMost(10)))
            }
            country.copy(military = updated, treasury = country.treasury - 2000)
        } else country
    }

    fun startNuclearProgram() = updateCountry { country ->
        if (country.treasury >= 10000 && !country.military.nuclearProgram.hasProgram) {
            country.copy(
                military = country.military.copy(nuclearProgram = country.military.nuclearProgram.copy(hasProgram = true)),
                treasury = country.treasury - 10000
            )
        } else country
    }

    fun hireMercenaries() = updateCountry { country ->
        if (country.treasury >= 1500) {
            val mercGroup = MercenaryGroup(
                "Mercs ${System.currentTimeMillis() % 100}",
                (10..20).random(),
                200,
                10
            )
            country.copy(
                military = country.military.copy(mercenaries = country.military.mercenaries + mercGroup),
                treasury = country.treasury - 1500
            )
        } else country
    }

    fun changeDoctrine(doc: MilitaryDoctrine) = updateCountry { country ->
        if (country.treasury >= 1000) {
            country.copy(military = country.military.copy(doctrine = doc), treasury = country.treasury - 1000)
        } else country
    }

    // Politics
    fun toggleLaw(id: String) = updateCountry { country ->
        val law = country.activeLaws.find { it.id == id } ?: return@updateCountry country
        val cost = if (!law.isActive) law.cost else 0
        if (country.treasury >= cost || law.isActive) {
            country.copy(
                activeLaws = country.activeLaws.map { if (it.id == id) it.copy(isActive = !it.isActive) else it },
                treasury = country.treasury - cost
            )
        } else country
    }

    fun bribeFaction(name: String) = updateCountry { country ->
        if (country.treasury >= 2000) {
            country.copy(
                factions = country.factions.map { if (it.name == name) it.copy(loyalty = (it.loyalty + 20).coerceAtMost(100)) else it },
                treasury = country.treasury - 2000
            )
        } else country
    }

    fun hireMinister(name: String, role: MinisterRole) = updateCountry { country ->
        if (country.treasury >= 3000) {
            val newMinister = Minister("m_${System.currentTimeMillis()}", name, role, (40..80).random(), (0..20).random(), 100)
            country.copy(
                ministers = country.ministers.filter { it.role != role } + newMinister,
                treasury = country.treasury - 3000
            )
        } else country
    }

    fun triggerElection() = updateCountry { country ->
        if (country.election?.isActive != true) {
            country.copy(election = Election(year = country.year, isActive = true, turnsRemaining = 1))
        } else country
    }

    fun restartGame() {
        viewModelScope.launch {
            repository.clearGame()
            _uiState.update { GameUiState(isLoading = false, showNewGameDialog = true) }
        }
    }

    fun getGameOverMessage(reason: GameOverReason): String {
        return when (reason) {
            GameOverReason.BANKRUPTCY -> "Your country has gone bankrupt! The government has collapsed due to unsustainable debt."
            GameOverReason.REVOLUTION -> "The people have revolted! Your regime has been overthrown by angry citizens."
            GameOverReason.INVASION -> "Your military was too weak. Invaders have conquered your country."
            GameOverReason.TECH_FAILURE -> "Your nation fell behind technologically and became obsolete."
            GameOverReason.FAMINE -> "Widespread famine has devastated your population."
            GameOverReason.ENVIRONMENTAL_COLLAPSE -> "Environmental collapse has made your nation uninhabitable."
            GameOverReason.NUCLEAR_WINTER -> "Nuclear war has brought on a devastating winter."
            GameOverReason.CIVIL_WAR -> "Civil war has destroyed everything."
            GameOverReason.ASSASSINATION -> "You have been assassinated by political rivals!"
            GameOverReason.COUP -> "The military has seized power in a violent coup!"
        }
    }
}
