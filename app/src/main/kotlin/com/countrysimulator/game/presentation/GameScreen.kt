package com.countrysimulator.game.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.countrysimulator.game.domain.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySimulatorApp(viewModel: GameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var currentScreen by remember { mutableStateOf(Screen.DASHBOARD) }
    var isDarkTheme by remember { mutableStateOf(true) }

    MaterialTheme(
        colorScheme = if (isDarkTheme) {
            darkColorScheme(
                primary = Color(0xFF3F51B5),
                secondary = Color(0xFFFFC107),
                tertiary = Color(0xFF009688),
                background = Color(0xFF121212),
                surface = Color(0xFF1E1E1E),
                onPrimary = Color.White,
                onSecondary = Color.Black,
                onBackground = Color.White,
                onSurface = Color.White
            )
        } else {
            lightColorScheme(
                primary = Color(0xFF3F51B5),
                secondary = Color(0xFFFFC107),
                tertiary = Color(0xFF009688),
                background = Color(0xFFF5F5F5),
                surface = Color(0xFFFFFFFF),
                onPrimary = Color.White,
                onSecondary = Color.Black,
                onBackground = Color.Black,
                onSurface = Color.Black
            )
        }
    ) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.showNewGameDialog) {
            NewGameDialog { name, govType -> viewModel.startNewGame(name, govType) }
        } else if (uiState.gameState != null) {
            val gameState = uiState.gameState!!

            if (gameState.isGameOver && gameState.gameOverReason != null) {
                GameOverScreen(
                    reason = gameState.gameOverReason!!,
                    message = viewModel.getGameOverMessage(gameState.gameOverReason!!),
                    onRestart = { viewModel.restartGame() }
                )
            } else {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Country Simulator") },
                            actions = {
                                TextButton(onClick = { isDarkTheme = !isDarkTheme }) {
                                    Text(if (isDarkTheme) "☀️ Light" else "🌙 Dark")
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Home, "Dashboard") },
                                label = { Text("Nation") },
                                selected = currentScreen == Screen.DASHBOARD,
                                onClick = { currentScreen = Screen.DASHBOARD }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.AccountBox, "Politics") },
                                label = { Text("Politics") },
                                selected = currentScreen == Screen.POLITICS,
                                onClick = { currentScreen = Screen.POLITICS }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Star, "Military") },
                                label = { Text("Military") },
                                selected = currentScreen == Screen.MILITARY,
                                onClick = { currentScreen = Screen.MILITARY }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.List, "World") },
                                label = { Text("World") },
                                selected = currentScreen == Screen.WORLD,
                                onClick = { currentScreen = Screen.WORLD }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Build, "Actions") },
                                label = { Text("Actions") },
                                selected = currentScreen == Screen.ACTIONS,
                                onClick = { currentScreen = Screen.ACTIONS }
                            )
                            NavigationBarItem(
                                icon = { Text("🌐", fontSize = 20.sp) },
                                label = { Text("Affairs") },
                                selected = currentScreen == Screen.AFFAIRS,
                                onClick = { currentScreen = Screen.AFFAIRS }
                            )
                        }
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        when (currentScreen) {
                            Screen.DASHBOARD -> DashboardScreen(gameState, uiState.incomeThisTurn, uiState.newsHeadline, viewModel)
                            Screen.POLITICS -> PoliticsScreen(gameState, viewModel)
                            Screen.MILITARY -> MilitaryScreen(gameState, viewModel)
                            Screen.INTEL -> IntelScreen(gameState, viewModel)
                            Screen.WORLD -> WorldScreen(gameState, viewModel)
                            Screen.UN -> UnitedNationsScreen(gameState)
                            Screen.MARKET -> MarketScreen(gameState, viewModel)
                            Screen.ACTIONS -> ActionsScreen(gameState, viewModel)
                            Screen.AFFAIRS -> AffairsScreen(gameState, viewModel)
                        }

                        // Floating Sub-menu for More
                        if (currentScreen == Screen.INTEL || currentScreen == Screen.UN || currentScreen == Screen.MARKET) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                                Card(modifier = Modifier.padding(8.dp)) {
                                    Row {
                                        TextButton(onClick = { currentScreen = Screen.INTEL }) { Text("Intel") }
                                        TextButton(onClick = { currentScreen = Screen.UN }) { Text("UN") }
                                        TextButton(onClick = { currentScreen = Screen.MARKET }) { Text("Market") }
                                    }
                                }
                            }
                        }

                        if (uiState.showEventDialog && uiState.currentEvent != null) {
                            EventDialog(event = uiState.currentEvent!!) { index -> viewModel.handleEventOption(index) }
                        }

                        if (gameState.turnSummary != null) {
                            TurnSummaryDialog(summary = gameState.turnSummary!!) { viewModel.dismissTurnSummary() }
                        }
                    }
                }
            }
        }
    }
}

enum class Screen { DASHBOARD, POLITICS, MILITARY, INTEL, WORLD, UN, MARKET, ACTIONS, AFFAIRS }
enum class DiplomacyAction { IMPROVE, TRADE, ALLIANCE, WAR, AID, SANCTION }

@Composable
fun DashboardScreen(gameState: GameState, incomeThisTurn: Int, newsHeadline: String?, viewModel: GameViewModel) {
    val country = gameState.country
    var showTooltip by remember { mutableStateOf<String?>(null) }

    if (showTooltip != null) {
        AlertDialog(
            onDismissRequest = { showTooltip = null },
            confirmButton = { TextButton(onClick = { showTooltip = null }) { Text("OK") } },
            title = { Text("Stat Info") },
            text = { Text(showTooltip!!) }
        )
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(country.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("Year: ${country.year} | Turn: ${country.turnCount}", fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Treasury: $${formatNumber(country.treasury)}", fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                    Text("Income: +$${formatNumber(incomeThisTurn)}", color = Color(0xFF8BC34A))
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (newsHeadline != null) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                Text(
                    text = newsHeadline,
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Text("Nation Statistics (Tap for info)", fontWeight = FontWeight.Bold)
        StatBar("Population", country.stats.population, 100000000, 0xFF4CAF50.toInt()) {
            showTooltip = "Population base. Growth depends on healthcare and economy."
        }
        StatBar("Economy", country.stats.economy, 100, 0xFFFFD700.toInt()) {
            showTooltip = "Economy drives tax revenue and resource production efficiency."
        }
        StatBar("Military", country.stats.military, 100, 0xFFF44336.toInt()) {
            showTooltip = "Military protects from invasion. Calculated from Army, Navy, Air Force branches."
        }
        StatBar("Stability", country.stats.stability, 100, 0xFF9C27B0.toInt()) {
            showTooltip = "High stability prevents civil wars and unrest. Affected by laws and factions."
        }
        StatBar("Soft Power", country.stats.softPower, 100, 0xFF009688.toInt()) {
            showTooltip = "Your global influence and cultural appeal. Increased by foreign aid and tech."
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Resources", fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ResourceItem("Food", country.resources.food, country.resources.maxFood, 0xFF4CAF50.toInt())
            ResourceItem("Energy", country.resources.energy, country.resources.maxEnergy, 0xFFFFD700.toInt())
            ResourceItem("Matls", country.resources.materials, country.resources.maxMaterials, 0xFF9C27B0.toInt())
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Quick Actions", fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ActionButtonSmall("Invest Econ ($1K)", { viewModel.investInEconomy() }, country.treasury >= 1000, Modifier.weight(1f))
            ActionButtonSmall("Infra ($1.2K)", { viewModel.improveInfrastructure() }, country.treasury >= 1200, Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ActionButtonSmall("Happiness ($800)", { viewModel.improveHappiness() }, country.treasury >= 800, Modifier.weight(1f))
            ActionButtonSmall("Propaganda ($1K)", { viewModel.runPropaganda() }, country.treasury >= 1000, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.nextTurn() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("NEXT TURN", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PoliticsScreen(gameState: GameState, viewModel: GameViewModel) {
    val country = gameState.country
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Government & Politics", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("Type: ${country.governmentType.displayName}", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Tax Policy", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                TaxRate.values().forEach { rate ->
                    FilterChip(
                        selected = country.taxRate == rate,
                        onClick = { viewModel.setTaxRate(rate) },
                        label = { Text(rate.label) }
                    )
                }
            }
            Text("Higher taxes reduce happiness but increase treasury income.", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Political Parties", fontWeight = FontWeight.Bold)
            country.politicalParties.forEach { party ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(party.name, fontWeight = FontWeight.Bold)
                            Text(party.ideology.displayName, fontSize = 12.sp, color = Color.Gray)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Popularity: ${party.popularity}%", fontWeight = FontWeight.Bold)
                            LinearProgressIndicator(progress = { party.popularity / 100f }, modifier = Modifier.width(80.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Cabinet Ministers", fontWeight = FontWeight.Bold)
            country.ministers.forEach { minister ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(minister.name, fontWeight = FontWeight.Bold)
                            Text(minister.role.displayName, fontSize = 12.sp, color = Color.Gray)
                        }
                        Text("Skill: ${minister.skill}", fontWeight = FontWeight.Bold)
                    }
                }
            }
            if (country.ministers.size < 6) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.hireMinister("New Candidate", MinisterRole.values().random()) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = country.treasury >= 3000
                ) {
                    Text("Hire New Minister ($3000)")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Political Factions", fontWeight = FontWeight.Bold)
            country.factions.forEach { faction ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(faction.name, fontWeight = FontWeight.Bold)
                            Text("Power: ${faction.power}%", fontSize = 12.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Loyalty: ${faction.loyalty}%", modifier = Modifier.width(80.dp), fontSize = 12.sp)
                            val progress by animateFloatAsState(targetValue = faction.loyalty / 100f, animationSpec = tween(500))
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier.weight(1f),
                                color = if (faction.loyalty < 30) Color.Red else Color.Green
                            )
                            Button(
                                onClick = { viewModel.bribeFaction(faction.name) },
                                enabled = country.treasury >= 2000,
                                modifier = Modifier.padding(start = 8.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Bribe ($2K)", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Active Laws", fontWeight = FontWeight.Bold)
            country.activeLaws.forEach { law ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(law.name, fontWeight = FontWeight.Bold)
                            Text(law.description, fontSize = 10.sp, color = Color.Gray)
                        }
                        Switch(checked = law.isActive, onCheckedChange = { viewModel.toggleLaw(law.id) })
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (country.governmentType != GovernmentType.DEMOCRACY) {
            item {
                Button(
                    onClick = { viewModel.triggerElection() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text("Hold Emergency Election")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun MilitaryScreen(gameState: GameState, viewModel: GameViewModel) {
    val military = gameState.country.military
    val treasury = gameState.country.treasury

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Military Command", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            Text("Doctrine: ${military.doctrine.displayName}", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Select Military Doctrine", fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                MilitaryDoctrine.values().forEach { doctrine ->
                    ActionButtonSmall(
                        doctrine.displayName,
                        { viewModel.changeDoctrine(doctrine) },
                        treasury >= 1000 && military.doctrine != doctrine,
                        Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Branches", fontWeight = FontWeight.Bold)
            MilitaryBranchCard("Army", military.army, { viewModel.recruitTroops("Army") }, { viewModel.upgradeEquipment("Army") }, treasury)
            MilitaryBranchCard("Navy", military.navy, { viewModel.recruitTroops("Navy") }, { viewModel.upgradeEquipment("Navy") }, treasury)
            MilitaryBranchCard("Air Force", military.airForce, { viewModel.recruitTroops("Air Force") }, { viewModel.upgradeEquipment("Air Force") }, treasury)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Nuclear Program", fontWeight = FontWeight.Bold)
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    if (!military.nuclearProgram.hasProgram) {
                        Button(
                            onClick = { viewModel.startNuclearProgram() },
                            enabled = treasury >= 10000,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Start Program ($10,000)")
                        }
                    } else {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Research Progress: ${military.nuclearProgram.researchProgress}%", fontSize = 12.sp)
                            Text("Warheads: ${military.nuclearProgram.warheads}", color = Color.Red, fontWeight = FontWeight.Bold)
                        }
                        val prog by animateFloatAsState(targetValue = military.nuclearProgram.researchProgress / 100f, animationSpec = tween(500))
                        LinearProgressIndicator(progress = { prog }, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Mercenaries", fontWeight = FontWeight.Bold)
            if (military.mercenaries.isEmpty()) {
                Text("No mercenaries hired.", fontSize = 12.sp, color = Color.Gray)
            }
            military.mercenaries.forEach { merc ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(merc.name)
                        Text("Power: ${merc.power}", fontWeight = FontWeight.Bold)
                        Text("Turns: ${merc.contractTurnsRemaining}", fontSize = 12.sp)
                    }
                }
            }
            Button(
                onClick = { viewModel.hireMercenaries() },
                enabled = treasury >= 1500,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Hire Mercenaries ($1500)")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Active War Theaters", fontWeight = FontWeight.Bold)
            if (military.warTheaters.isEmpty()) {
                Text("Nation is at peace.", fontSize = 12.sp, color = Color.Gray)
            }
            military.warTheaters.forEach { theater ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (theater.territoryControlled < 30) Color(0x33FF0000) else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(theater.name, fontWeight = FontWeight.Bold)
                        Text("Territory Controlled: ${theater.territoryControlled}%", fontSize = 12.sp)
                        val territory by animateFloatAsState(targetValue = theater.territoryControlled / 100f, animationSpec = tween(800))
                        LinearProgressIndicator(
                            progress = { territory },
                            modifier = Modifier.fillMaxWidth(),
                            color = if (theater.territoryControlled > 50) Color.Green else Color.Red
                        )
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Your Strength: ${theater.playerStrength}", fontSize = 10.sp)
                            Text("Enemy Strength: ${theater.enemyStrength}", fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MilitaryBranchCard(name: String, branch: MilitaryBranch, onRecruit: () -> Unit, onUpgrade: () -> Unit, treasury: Int) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(name, fontWeight = FontWeight.Bold)
                Text("Lvl ${branch.equipmentLevel}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
            }
            Text("Manpower: ${formatNumber(branch.manpower)}", fontSize = 12.sp)
            Text("Experience: ${branch.experience}%", fontSize = 12.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Recruit ($500)", onRecruit, treasury >= 500, Modifier.weight(1f))
                ActionButtonSmall("Upgrade ($2K)", onUpgrade, treasury >= 2000, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun IntelScreen(gameState: GameState, viewModel: GameViewModel) {
    val country = gameState.country
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Intelligence Agency", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("Conduct covert operations against other nations.", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Active Missions", fontWeight = FontWeight.Bold)
            if (country.activeSpyMissions.isEmpty()) {
                Text("No active missions.", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
            }
            country.activeSpyMissions.forEach { mission ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(mission.type.displayName, fontWeight = FontWeight.Bold)
                            Text("Turns: ${mission.turnsRemaining}", fontSize = 12.sp)
                        }
                        Text("Target: ${mission.targetNationName}", fontSize = 12.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        val progress by animateFloatAsState(
                            targetValue = (mission.type.duration - mission.turnsRemaining) / mission.type.duration.toFloat(),
                            animationSpec = tween(500)
                        )
                        LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Launch New Mission", fontWeight = FontWeight.Bold)
            gameState.aiNations.filter { it.isAlive }.forEach { ai ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(ai.name, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            SpyMissionType.values().forEach { type ->
                                ActionButtonSmall(type.displayName, { viewModel.launchSpyMission(ai.id, type) }, country.treasury >= type.cost, Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UnitedNationsScreen(gameState: GameState) {
    val un = gameState.country.unitedNations
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("United Nations", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("International body for global cooperation.", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("UN Status", fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Member States: ${un.memberCount}")
                Text("Your Status: Member", color = Color.Green)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Passed Resolutions", fontWeight = FontWeight.Bold)
            if (un.passedResolutions.isEmpty()) {
                Text("No passed resolutions.", fontSize = 12.sp, color = Color.Gray)
            }
            un.passedResolutions.forEach { res ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(res.type.displayName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text(res.description, fontSize = 12.sp)
                        Text("Passed in Year ${res.yearProposed}", fontSize = 10.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun WorldScreen(gameState: GameState, viewModel: GameViewModel) {
    var showConfirmWar by remember { mutableStateOf<String?>(null) }

    if (showConfirmWar != null) {
        val targetName = gameState.aiNations.find { it.id == showConfirmWar }?.name ?: "Unknown"
        AlertDialog(
            onDismissRequest = { showConfirmWar = null },
            title = { Text("Declare War on $targetName?") },
            text = { Text("This is an irreversible action! A War Theater will be created and you will suffer severe stability penalties.") },
            confirmButton = {
                Button(
                    onClick = { viewModel.declareWar(showConfirmWar!!); showConfirmWar = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Declare War")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmWar = null }) { Text("Cancel") }
            }
        )
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text("Global Powers", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(gameState.country.diplomaticRelations) { relation ->
            val aiNation = gameState.aiNations.find { it.id == relation.nationId }
            if (aiNation != null && aiNation.isAlive) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(aiNation.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(aiNation.governmentType.displayName, fontSize = 12.sp, color = Color.Gray)
                        }
                        Text(
                            "Relation: ${relation.relationScore} (${relation.status})",
                            color = if (relation.status == RelationStatus.ENEMY) Color.Red else Color.White,
                            fontSize = 12.sp
                        )
                        if (relation.isAtWar) {
                            Text("⚠ AT WAR ⚠", color = Color.Red, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                        } else {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                                ActionButtonSmall("Improve ($500)", { viewModel.improveRelations(aiNation.id) }, gameState.country.treasury >= 500, Modifier.weight(1f))
                                ActionButtonSmall("Aid ($2K)", { viewModel.sendForeignAid(aiNation.id) }, gameState.country.treasury >= 2000, Modifier.weight(1f))
                                Button(
                                    onClick = { showConfirmWar = aiNation.id },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                    modifier = Modifier.weight(1f).height(32.dp),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text("WAR", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AffairsScreen(gameState: GameState, viewModel: GameViewModel) {
    val country = gameState.country
    val treasury = country.treasury
    
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("World Affairs & Crises", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("Handle events, disasters, and global affairs", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // CRISIS MANAGEMENT SECTION
        item {
            Text("Crisis Management", fontWeight = FontWeight.Bold, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Counter Terror ($1.5K)", { viewModel.counterTerrorism() }, treasury >= 1500, Modifier.weight(1f))
                ActionButtonSmall("Stimulate Econ ($3K)", { viewModel.stimulateEconomy() }, treasury >= 3000, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Refugees Accept", { viewModel.handleRefugees(true) }, treasury >= 500, Modifier.weight(1f))
                ActionButtonSmall("Refugees Reject", { viewModel.handleRefugees(false) }, treasury >= 200, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // INTERNATIONAL RELATIONS
        item {
            Text("International Relations", fontWeight = FontWeight.Bold, color = Color(0xFF2196F3))
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Host Summit ($5K)", { viewModel.hostSummit(SummitType.ECONOMIC) }, treasury >= 5000, Modifier.weight(1f))
                ActionButtonSmall("Join NATO ($5K)", { viewModel.joinOrganization(OrgType.NATO) }, treasury >= 5000, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Join EU ($4K)", { viewModel.joinOrganization(OrgType.EU) }, treasury >= 4000, Modifier.weight(1f))
                ActionButtonSmall("Promote Culture ($1.2K)", { viewModel.promoteCulture() }, treasury >= 1200, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // SPACE & TECHNOLOGY
        item {
            Text("Space & Technology", fontWeight = FontWeight.Bold, color = Color(0xFF9C27B0))
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Space Program ($5K)", { viewModel.advanceSpaceProgram() }, treasury >= 5000, Modifier.weight(1f))
                ActionButtonSmall("Nuclear Weapons ($8K)", { viewModel.buildNuclearWeapons() }, treasury >= 8000, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Intel Agency ($2K)", { viewModel.improveIntelAgency() }, treasury >= 2000, Modifier.weight(1f))
                ActionButtonSmall("Research Patents ($2K)", { viewModel.researchPatents() }, treasury >= 2000, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // SOCIAL REFORMS
        item {
            Text("Social Reforms", fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Universal HC ($5K)", { viewModel.proposeReform(ReformType.UNIVERSAL_HEALTHCARE) }, treasury >= 5000, Modifier.weight(1f))
                ActionButtonSmall("Universal Income ($8K)", { viewModel.proposeReform(ReformType.UNIVERSAL_INCOME) }, treasury >= 8000, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Workers Rights ($3K)", { viewModel.proposeReform(ReformType.WORKERS_RIGHTS) }, treasury >= 3000, Modifier.weight(1f))
                ActionButtonSmall("Civil Rights ($4K)", { viewModel.proposeReform(ReformType.CIVIL_RIGHTS) }, treasury >= 4000, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // INFRASTRUCTURE
        item {
            Text("Infrastructure Projects", fontWeight = FontWeight.Bold, color = Color(0xFFFF9800))
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Highway ($5K)", { viewModel.startInfrastructureProject(ProjectType.HIGHWAY) }, treasury >= 5000, Modifier.weight(1f))
                ActionButtonSmall("Railway ($7K)", { viewModel.startInfrastructureProject(ProjectType.RAILWAY) }, treasury >= 7000, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Airport ($4K)", { viewModel.startInfrastructureProject(ProjectType.AIRPORT) }, treasury >= 4000, Modifier.weight(1f))
                ActionButtonSmall("Space Center ($15K)", { viewModel.startInfrastructureProject(ProjectType.SPACE_CENTER) }, treasury >= 15000, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // PROPAGANDA
        item {
            Text("Propaganda Campaigns", fontWeight = FontWeight.Bold, color = Color(0xFFE91E63))
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Nationalism ($1K)", { viewModel.runPropagandaCampaign(PropagandaType.NATIONALISM) }, treasury >= 1000, Modifier.weight(1f))
                ActionButtonSmall("Unity ($1.2K)", { viewModel.runPropagandaCampaign(PropagandaType.UNITY) }, treasury >= 1200, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Fear ($800)", { viewModel.runPropagandaCampaign(PropagandaType.FEAR) }, treasury >= 800, Modifier.weight(1f))
                ActionButtonSmall("Glory ($1.5K)", { viewModel.runPropagandaCampaign(PropagandaType.GLORY) }, treasury >= 1500, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // POLITICAL ACTIONS
        item {
            Text("Political Actions", fontWeight = FontWeight.Bold, color = Color(0xFF607D8B))
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Hold Election ($5K)", { viewModel.holdElection(ElectionType.GENERAL) }, treasury >= 5000, Modifier.weight(1f))
                ActionButtonSmall("Attempt Coup", { viewModel.attemptCoup() }, treasury >= 1000, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ActionButtonSmall("Boost Morale ($800)", { viewModel.boostMorale() }, treasury >= 800, Modifier.weight(1f))
                ActionButtonSmall("Start Revolution", { viewModel.startRevolution() }, treasury >= 2000, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(4.dp))
            ActionButtonSmall("Scandal (Corrupt) ($3K)", { viewModel.triggerScandal(ScandalType.CORRUPTION) }, treasury >= 3000, Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
        }

        // DIPLOMATIC ACTIONS
        item {
            Text("Diplomatic Actions", fontWeight = FontWeight.Bold, color = Color(0xFF00BCD4))
            Spacer(modifier = Modifier.height(8.dp))
        }

        gameState.aiNations.filter { it.isAlive }.take(4).forEach { ai ->
            item {
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(ai.name, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            ActionButtonSmall("Ambassador ($2.5K)", { viewModel.appointAmbassador(ai.id) }, treasury >= 2500, Modifier.weight(1f))
                            ActionButtonSmall("Foreign Base ($5K)", { viewModel.establishForeignBase(ai.id) }, treasury >= 5000, Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // WAR ACTIONS
        item {
            Text("War & Peace", fontWeight = FontWeight.Bold, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        val atWarRelations = country.diplomaticRelations.filter { it.isAtWar }
        if (atWarRelations.isEmpty()) {
            item {
                Text("No active wars", fontSize = 12.sp, color = Color.Gray)
            }
        } else {
            atWarRelations.forEach { rel ->
                item {
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = Color(0x33FF0000))) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("⚠ WAR: ${rel.nationName}", fontWeight = FontWeight.Bold, color = Color.Red)
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                ActionButtonSmall("Battle (Land)", { viewModel.resolveBattle(rel.nationId, BattleType.LAND) }, treasury >= 500, Modifier.weight(1f))
                                ActionButtonSmall("Peace Treaty ($2K)", { viewModel.negotiatePeace(rel.nationId) }, treasury >= 2000, Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MarketScreen(gameState: GameState, viewModel: GameViewModel) {
    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Global Market", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        MarketCard("Food", gameState.globalMarket.foodPrice, gameState.country.resources.food) { viewModel.sellResource("Food", 50) }
        MarketCard("Energy", gameState.globalMarket.energyPrice, gameState.country.resources.energy) { viewModel.sellResource("Energy", 50) }
        MarketCard("Materials", gameState.globalMarket.materialsPrice, gameState.country.resources.materials) { viewModel.sellResource("Materials", 50) }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Global Instability: ${gameState.globalMarket.globalInstability}%", color = if (gameState.globalMarket.globalInstability > 50) Color.Red else Color.Green)
        Text("High instability increases price volatility.", fontSize = 12.sp, color = Color.Gray)
    }
}

data class ActionCategory(val name: String, val icon: String, val cost: Int, val description: String)

@Composable
fun ActionsScreen(gameState: GameState, viewModel: GameViewModel) {
    val treasury = gameState.country.treasury
    val categories = listOf(
        ActionCategory("Economy", "💰", 1000, "Boost economic growth"),
        ActionCategory("Military", "⚔️", 800, "Strengthen armed forces"),
        ActionCategory("Education", "📚", 1200, "Improve schools and universities"),
        ActionCategory("Healthcare", "🏥", 1000, "Better medical care"),
        ActionCategory("Environment", "🌳", 1500, "Protect nature"),
        ActionCategory("Infrastructure", "🏗️", 1800, "Build roads and bridges"),
        ActionCategory("Technology", "🔬", 1500, "Advance research"),
        ActionCategory("Security", "🛡️", 1400, "Internal safety"),
        ActionCategory("Agriculture", "🌾", 1200, "Food production"),
        ActionCategory("Industry", "🏭", 2000, "Manufacturing sector"),
        ActionCategory("Energy", "⚡", 1800, "Power generation"),
        ActionCategory("Transportation", "🚂", 1500, "Transport networks"),
        ActionCategory("Housing", "🏠", 1300, "Urban development"),
        ActionCategory("Social Services", "❤️", 1600, "Welfare programs"),
        ActionCategory("Immigration", "🛂", 800, "Population growth"),
        ActionCategory("Emergency", "🚑", 1100, "Disaster response"),
        ActionCategory("Research", "🔬", 2200, "Scientific R&D"),
        ActionCategory("Space", "🚀", 3500, "Space exploration"),
        ActionCategory("Trade", "📦", 1000, "Commerce boost"),
        ActionCategory("Banking", "🏦", 1700, "Financial sector"),
        ActionCategory("Tourism", "✈️", 900, "Travel industry"),
        ActionCategory("Culture", "🎭", 850, "Arts and heritage"),
        ActionCategory("Sports", "⚽", 750, "Athletic programs"),
        ActionCategory("Media", "📺", 950, "Broadcasting"),
        ActionCategory("Religion", "⛪", 600, "Religious affairs"),
        ActionCategory("Youth", "👶", 700, "Youth programs"),
        ActionCategory("Elderly", "👴", 800, "Pension systems"),
        ActionCategory("Labor", "🔧", 550, "Workers rights"),
        ActionCategory("Anti-Corruption", "🔍", 1100, "Transparency"),
        ActionCategory("Cyber", "💻", 1300, "Digital security"),
        ActionCategory("Telecom", "📡", 1400, "Internet & phones"),
        ActionCategory("Nuclear", "☢️", 5000, "Civil nuclear power"),
        ActionCategory("Mining", "⛏️", 1600, "Resource extraction"),
        ActionCategory("Forestry", "🌲", 900, "Forest management"),
        ActionCategory("Fisheries", "🐟", 750, "Ocean resources"),
        ActionCategory("Foreign Aid", "🤝", 2500, "International aid")
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("National Actions", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("Invest in your nation's development", fontSize = 14.sp, color = Color.Gray)
            Text("Treasury: $${formatNumber(treasury)}", fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(categories.chunked(2)) { rowCategories ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowCategories.forEach { cat ->
                    ActionCard(cat, treasury >= cat.cost, {
                        when (cat.name) {
                            "Economy" -> viewModel.investInEconomy()
                            "Military" -> viewModel.recruitMilitary()
                            "Education" -> viewModel.investInEducation()
                            "Healthcare" -> viewModel.investInHealthcare()
                            "Environment" -> viewModel.improveEnvironment()
                            "Infrastructure" -> viewModel.upgradeInfrastructureGeneral()
                            "Technology" -> viewModel.investInTechnology()
                            "Security" -> viewModel.upgradeNationalSecurity()
                            "Agriculture" -> viewModel.upgradeAgriculture()
                            "Industry" -> viewModel.upgradeIndustry()
                            "Energy" -> viewModel.upgradeEnergy()
                            "Transportation" -> viewModel.upgradeTransportation()
                            "Housing" -> viewModel.upgradeHousing()
                            "Social Services" -> viewModel.upgradeSocialServices()
                            "Immigration" -> viewModel.upgradeImmigration()
                            "Emergency" -> viewModel.upgradeEmergencyServices()
                            "Research" -> viewModel.upgradeResearch()
                            "Space" -> viewModel.upgradeSpaceProgram()
                            "Trade" -> viewModel.upgradeTrade()
                            "Banking" -> viewModel.upgradeBanking()
                            "Tourism" -> viewModel.upgradeTourism()
                            "Culture" -> viewModel.upgradeCulture()
                            "Sports" -> viewModel.upgradeSports()
                            "Media" -> viewModel.upgradeMedia()
                            "Religion" -> viewModel.upgradeReligion()
                            "Youth" -> viewModel.upgradeYouth()
                            "Elderly" -> viewModel.upgradeElderly()
                            "Labor" -> viewModel.upgradeLabor()
                            "Anti-Corruption" -> viewModel.upgradeAntiCorruption()
                            "Cyber" -> viewModel.upgradeCybersecurity()
                            "Telecom" -> viewModel.upgradeTelecommunications()
                            "Nuclear" -> viewModel.upgradeNuclear()
                            "Mining" -> viewModel.upgradeMining()
                            "Forestry" -> viewModel.upgradeForestry()
                            "Fisheries" -> viewModel.upgradeFisheries()
                            "Foreign Aid" -> viewModel.upgradeForeignAid()
                        }
                    }, Modifier.weight(1f))
                }
                if (rowCategories.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ActionCard(category: ActionCategory, enabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(category.icon, fontSize = 24.sp)
            Text(category.name, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(category.description, fontSize = 9.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = onClick,
                enabled = enabled,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text("$${category.cost}", fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun MarketCard(name: String, price: Int, stock: Int, onSell: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(name, fontWeight = FontWeight.Bold)
                Text("Stock: $stock | Price: $$price", fontSize = 12.sp)
            }
            Button(onClick = onSell, enabled = stock >= 50) { Text("Sell 50") }
        }
    }
}

@Composable
fun TurnSummaryDialog(summary: TurnSummary, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Turn Summary", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Income: +$${summary.grossIncome}", color = Color.Green)
                Text("Expenses: -$${summary.totalExpenses}", color = Color.Red)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                LazyColumn(modifier = Modifier.heightIn(max = 150.dp)) {
                    items(summary.messages) { msg -> Text("• $msg", fontSize = 12.sp) }
                }
            }
        },
        confirmButton = { Button(onClick = onDismiss) { Text("Continue") } }
    )
}

@Composable
fun StatBar(label: String, value: Int, max: Int, color: Int, onClick: () -> Unit) {
    val progress by animateFloatAsState(targetValue = value / max.toFloat(), animationSpec = tween(1000))
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { onClick() }) {
        Text(label, modifier = Modifier.width(80.dp), fontSize = 12.sp)
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.weight(1f).height(8.dp),
            color = Color(color),
            trackColor = Color.DarkGray
        )
        Text("$value", modifier = Modifier.width(40.dp), textAlign = TextAlign.End, fontSize = 12.sp)
    }
}

@Composable
fun EventDialog(event: GameEvent, onOptionSelected: (Int) -> Unit) {
    var clicked by remember { mutableStateOf(false) } // Anti-spam fix

    AlertDialog(
        onDismissRequest = { },
        title = { Text(event.title, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text(event.description)
                Spacer(modifier = Modifier.height(16.dp))
                event.options.forEachIndexed { index, option ->
                    Button(
                        onClick = { if (!clicked) { clicked = true; onOptionSelected(index) } },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        enabled = !clicked
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(option.label)
                            Text(option.description, fontSize = 10.sp)
                        }
                    }
                }
            }
        },
        confirmButton = {}
    )
}

@Composable
fun NewGameDialog(onStartGame: (String, GovernmentType) -> Unit) {
    var name by remember { mutableStateOf("") }
    var selectedGov by remember { mutableStateOf(GovernmentType.DEMOCRACY) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Country Simulator 6.1", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Enhanced Edition with Tax & Trade", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Country Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Select Government Type", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        GovernmentType.values().forEach { gov ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().clickable { selectedGov = gov }
            ) {
                RadioButton(selected = selectedGov == gov, onClick = { selectedGov = gov })
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(gov.displayName, fontSize = 14.sp)
                    Text(gov.description, fontSize = 10.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { if (name.isNotBlank()) onStartGame(name, selectedGov) },
            enabled = name.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Game")
        }
    }
}

@Composable
fun GameOverScreen(reason: GameOverReason, message: String, onRestart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("GAME OVER", fontSize = 40.sp, color = Color.Red, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(reason.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(message, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onRestart) { Text("Play Again") }
    }
}

@Composable
fun ResourceItem(label: String, value: Int, max: Int, color: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 10.sp, color = Color.Gray)
        val progress by animateFloatAsState(targetValue = value / max.toFloat(), animationSpec = tween(500))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.width(60.dp),
            color = Color(color)
        )
        Text("$value", fontSize = 10.sp)
    }
}

@Composable
fun ActionButtonSmall(text: String, onClick: () -> Unit, enabled: Boolean, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        contentPadding = PaddingValues(4.dp)
    ) {
        Text(text, fontSize = 11.sp, textAlign = TextAlign.Center, maxLines = 1)
    }
}

fun formatNumber(number: Int): String {
    return when {
        number >= 1000000 -> String.format("%.1fM", number / 1000000.0)
        number >= 1000 -> String.format("%.1fK", number / 1000.0)
        else -> number.toString()
    }
}
