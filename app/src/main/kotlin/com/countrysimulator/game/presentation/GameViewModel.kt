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

    // Core Actions - Hoisted to GameLogic
    fun investInEconomy() = updateCountry { GameLogic.investInEconomy(it) }
    fun recruitMilitary() = updateCountry { GameLogic.recruitMilitary(it) }
    fun improveInfrastructure() = updateCountry { GameLogic.improveInfrastructure(it) }
    fun investInTechnology() = updateCountry { GameLogic.investInTechnology(it) }
    fun improveHappiness() = updateCountry { GameLogic.improveHappiness(it) }
    fun investInEducation() = updateCountry { GameLogic.investInEducation(it) }
    fun investInHealthcare() = updateCountry { GameLogic.investInHealthcare(it) }
    fun improveEnvironment() = updateCountry { GameLogic.improveEnvironment(it) }
    fun fightCrime() = updateCountry { GameLogic.fightCrime(it) }
    fun buyFood() = updateCountry { GameLogic.buyFood(it) }
    fun buyEnergy() = updateCountry { GameLogic.buyEnergy(it) }
    fun buyMaterials() = updateCountry { GameLogic.buyMaterials(it) }
    fun runPropaganda() = updateCountry { GameLogic.runPropaganda(it) }

    // Diplomacy & Intel - Hoisted to GameLogic
    fun improveRelations(nationId: String) = updateCountry { GameLogic.improveRelations(it, nationId) }
    fun sendForeignAid(nationId: String) = updateCountry { GameLogic.sendForeignAid(it, nationId) }
    fun offerTrade(nationId: String) = updateCountry { GameLogic.offerTrade(it, nationId) }
    fun formAlliance(nationId: String) = updateCountry { GameLogic.formAlliance(it, nationId) }
    fun imposeSanctions(nationId: String, sanctionType: SanctionType) = updateCountry { GameLogic.imposeSanctions(it, nationId, sanctionType) }
    fun launchSpyMission(targetId: String, type: SpyMissionType) = updateCountry { 
        val aiNations = _uiState.value.gameState?.aiNations ?: emptyList()
        GameLogic.launchSpyMission(it, targetId, type, aiNations) 
    }

    // Military - Hoisted to GameLogic
    fun recruitTroops(branch: String) = updateCountry { GameLogic.recruitTroops(it, branch) }
    fun upgradeEquipment(branch: String) = updateCountry { GameLogic.upgradeEquipment(it, branch) }
    fun startNuclearProgram() = updateCountry { GameLogic.startNuclearProgram(it) }
    fun hireMercenaries() = updateCountry { GameLogic.hireMercenaries(it) }
    fun changeDoctrine(doc: MilitaryDoctrine) = updateCountry { GameLogic.changeDoctrine(it, doc) }

    // Politics - Hoisted to GameLogic
    fun toggleLaw(id: String) = updateCountry { GameLogic.toggleLaw(it, id) }
    fun bribeFaction(name: String) = updateCountry { GameLogic.bribeFaction(it, name) }
    fun hireMinister(name: String, role: MinisterRole) = updateCountry { GameLogic.hireMinister(it, name, role) }
    fun triggerElection() = updateCountry { GameLogic.triggerElection(it) }

    // ========== NEW UPGRADE CATEGORIES (30) ==========
    fun upgradeAgriculture() = updateCountry { GameLogic.upgradeAgriculture(it) }
    fun upgradeIndustry() = updateCountry { GameLogic.upgradeIndustry(it) }
    fun upgradeEnergy() = updateCountry { GameLogic.upgradeEnergy(it) }
    fun upgradeTransportation() = updateCountry { GameLogic.upgradeTransportation(it) }
    fun upgradeHousing() = updateCountry { GameLogic.upgradeHousing(it) }
    fun upgradeSocialServices() = updateCountry { GameLogic.upgradeSocialServices(it) }
    fun upgradeImmigration() = updateCountry { GameLogic.upgradeImmigration(it) }
    fun upgradeNationalSecurity() = updateCountry { GameLogic.upgradeNationalSecurity(it) }
    fun upgradeEmergencyServices() = updateCountry { GameLogic.upgradeEmergencyServices(it) }
    fun upgradeResearch() = updateCountry { GameLogic.upgradeResearch(it) }
    fun upgradeSpaceProgram() = updateCountry { GameLogic.upgradeSpaceProgram(it) }
    fun upgradeTrade() = updateCountry { GameLogic.upgradeTrade(it) }
    fun upgradeBanking() = updateCountry { GameLogic.upgradeBanking(it) }
    fun upgradeTourism() = updateCountry { GameLogic.upgradeTourism(it) }
    fun upgradeCulture() = updateCountry { GameLogic.upgradeCulture(it) }
    fun upgradeSports() = updateCountry { GameLogic.upgradeSports(it) }
    fun upgradeMedia() = updateCountry { GameLogic.upgradeMedia(it) }
    fun upgradeReligion() = updateCountry { GameLogic.upgradeReligion(it) }
    fun upgradeYouth() = updateCountry { GameLogic.upgradeYouth(it) }
    fun upgradeElderly() = updateCountry { GameLogic.upgradeElderly(it) }
    fun upgradeLabor() = updateCountry { GameLogic.upgradeLabor(it) }
    fun upgradeAntiCorruption() = updateCountry { GameLogic.upgradeAntiCorruption(it) }
    fun upgradeCybersecurity() = updateCountry { GameLogic.upgradeCybersecurity(it) }
    fun upgradeInfrastructureGeneral() = updateCountry { GameLogic.upgradeInfrastructureGeneral(it) }
    fun upgradeTelecommunications() = updateCountry { GameLogic.upgradeTelecommunications(it) }
    fun upgradeNuclear() = updateCountry { GameLogic.upgradeNuclear(it) }
    fun upgradeMining() = updateCountry { GameLogic.upgradeMining(it) }
    fun upgradeForestry() = updateCountry { GameLogic.upgradeForestry(it) }
    fun upgradeFisheries() = updateCountry { GameLogic.upgradeFisheries(it) }
    fun upgradeForeignAid() = updateCountry { GameLogic.upgradeForeignAid(it) }

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
