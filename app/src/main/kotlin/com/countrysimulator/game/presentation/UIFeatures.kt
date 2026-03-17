package com.countrysimulator.game.presentation

import kotlinx.serialization.Serializable

/**
 * ========================================================================
 * COUNTRY SIMULATOR - PHASE 3 FEATURES IMPLEMENTATION
 * Features 101-150: UI/UX & Presentation
 * ========================================================================
 */

// ==================== FEATURE 241: INTERACTIVE MAPS ====================

/**
 * Interactive Maps - Zoomable, pannable world map
 */
@Serializable
data class MapState(
    val zoomLevel: Float = 1.0f,
    val panX: Float = 0f,
    val panY: Float = 0f,
    val selectedRegion: String? = null,
    val mapMode: MapMode = MapMode.POLITICAL,
    val showLabels: Boolean = true,
    val showBorders: Boolean = true,
    val showResources: Boolean = false,
    val showMilitary: Boolean = false
)

enum class MapMode {
    POLITICAL,    // Shows nation borders
    TERRAIN,      // Shows geographic features
    RESOURCE,     // Shows resource distribution
    MILITARY,     // Shows military presence
    ECONOMIC,     // Shows economic data
    CULTURAL,     // Shows cultural regions
    IDEOLOGICAL   // Shows ideological divisions
}

enum class MapRegion(
    val displayName: String,
    val continent: String,
    val centerX: Float,
    val centerY: Float
) {
    NORTH_AMERICA("North America", "Americas", 0.2f, 0.3f),
    SOUTH_AMERICA("South America", "Americas", 0.3f, 0.7f),
    EUROPE("Europe", "Eurasia", 0.5f, 0.25f),
    AFRICA("Africa", "Africa", 0.5f, 0.55f),
    ASIA("Asia", "Eurasia", 0.75f, 0.35f),
    OCEANIA("Oceania", "Pacific", 0.85f, 0.75f),
    ARCTIC("Arctic", "Polar", 0.5f, 0.05f),
    ANTARCTICA("Antarctica", "Polar", 0.5f, 0.95f)
}

// ==================== FEATURE 242: NATION COMPARISON ====================

/**
 * Nation Comparison - Side-by-side statistics
 */
@Serializable
data class NationComparison(
    val nation1Id: String,
    val nation2Id: String,
    val comparisonMetrics: List<ComparisonMetric>
)

@Serializable
data class ComparisonMetric(
    val name: String,
    val nation1Value: Int,
    val nation2Value: Int,
    val category: String
) {
    fun getLeader(): Int {  // Returns 1, 2, or 0 for tie
        return when {
            nation1Value > nation2Value -> 1
            nation2Value > nation1Value -> 2
            else -> 0
        }
    }
    
    fun getDifference(): Int = kotlin.math.abs(nation1Value - nation2Value)
}

// ==================== FEATURE 243: NEWS FEED ====================

/**
 * News Feed - In-game news ticker and articles
 */
@Serializable
data class NewsArticle(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val category: NewsCategory,
    val timestamp: Int,  // Turn number
    val relatedNationId: String? = null,
    val imageUrl: String? = null,
    val isRead: Boolean = false,
    val importance: NewsImportance = NewsImportance.NORMAL
)

enum class NewsCategory {
    POLITICS, ECONOMY, MILITARY, DIPLOMACY, SCIENCE, CULTURE, ENVIRONMENT
}

enum class NotificationPriority {
    LOW, NORMAL, HIGH, URGENT
}    SCIENCE, CULTURE, ENVIRONMENT, DISASTER, SPORTS
}

enum class NewsImportance {
    LOW, NORMAL, HIGH, BREAKING
}

@Serializable
data class NewsFeed(
    val articles: List<NewsArticle> = emptyList(),
    val unreadCount: Int = 0,
    val selectedCategory: NewsCategory? = null,
    val sortOrder: NewsSortOrder = NewsSortOrder.NEWEST
)

enum class NewsSortOrder {
    NEWEST, OLDEST, IMPORTANCE, CATEGORY
}

object NewsGenerator {
    fun generateNewsItem(category: NewsCategory, nationName: String): NewsArticle {
        val templates = when (category) {
            NewsCategory.POLITICS -> listOf(
                "$nationName Announces New Policy",
                "Election Results In $nationName",
                "Political Reform Proposed in $nationName"
            )
            NewsCategory.ECONOMY -> listOf(
                "Economic Growth in $nationName",
                "Trade Deal Signed by $nationName",
                "Market Fluctuations Affect $nationName"
            )
            NewsCategory.MILITARY -> listOf(
                "$nationName Conducts Military Exercise",
                "New Weapons Tested by $nationName",
                "Military Alliance Strengthened"
            )
            else -> listOf("Breaking News from $nationName")
        }
        
        val title = templates.random()
        
        return NewsArticle(
            id = "news_${System.currentTimeMillis()}",
            title = title,
            summary = "More details about $title...",
            content = "Full article content here",
            category = category,
            timestamp = 0,
            importance = NewsImportance.NORMAL
        )
    }
}

// ==================== FEATURE 244: HISTORICAL TIMELINE ====================

/**
 * Historical Timeline - Visual timeline of events
 */
@Serializable
data class HistoricalTimeline(
    val events: List<TimelineEvent> = emptyList(),
    val startYear: Int = 1800,
    val endYear: Int = 2100,
    val zoomLevel: Float = 1.0f
) {
    val yearRange: IntRange get() = startYear..endYear
}

@Serializable
data class TimelineEvent(
    val id: String,
    val year: Int,
    val title: String,
    val description: String,
    val category: TimelineCategory,
    val importance: Int,  // 1-10
    val relatedNations: List<String> = emptyList(),
    val icon: String? = null
)

enum class TimelineCategory {
    POLITICAL, ECONOMIC, MILITARY, DIPLOMATIC,
    SCIENTIFIC, CULTURAL, DISASTER, BIRTH, DEATH
}

// ==================== FEATURE 245: STATISTICS GRAPHS ====================

/**
 * Statistics Graphs - Charts showing trends
 */
@Serializable
data class StatGraph(
    val type: GraphType,
    val title: String,
    val dataPoints: List<GraphDataPoint>,
    val timeRange: Int = 50,  // Last N turns
    val showAverage: Boolean = true,
    val showTrend: Boolean = true
)

enum class GraphType {
    LINE, BAR, PIE, AREA, SCATTER
}

@Serializable
data class GraphDataPoint(
    val turn: Int,
    val value: Int,
    val label: String? = null
)

@Serializable
data class GraphConfiguration(
    val enabled: Boolean = true,
    val updateInterval: Int = 1,  // Turns between updates
    val colors: GraphColors = GraphColors(),
    val style: GraphStyle = GraphStyle.DEFAULT
)

@Serializable
data class GraphColors(
    val economy: String = "#4CAF50",
    val military: String = "#F44336",
    val happiness: String = "#2196F3",
    val stability: String = "#FF9800",
    val technology: String = "#9C27B0"
)

enum class GraphStyle {
    DEFAULT, MINIMAL, DETAILED, RETRO
}

// ==================== FEATURE 246: NOTIFICATION CENTER ====================

/**
 * Notification Center - Consolidated alerts
 */
@Serializable
data class NotificationCenter(
    val notifications: List<AlertNotification> = emptyList(),
    val maxStored: Int = 100,
    val unreadCount: Int = 0
)

@Serializable
data class AlertNotification(
    val id: String,
    val type: AlertType,
    val title: String,
    val message: String,
    val timestamp: Int,
    val isRead: Boolean = false,
    val priority: NotificationPriority = NotificationPriority.NORMAL,
    val action: NotificationAction? = null
)

enum class AlertType {
    WARNING, INFO, SUCCESS, ERROR, EVENT, ACHIEVEMENT
}

@Serializable
data class NotificationAction(
    val label: String,
    val actionType: ActionType,
    val targetId: String? = null
)

enum class ActionType {
    VIEW, DISMISS, RESPOND, GOTO
}

// ==================== FEATURE 247: HELP SYSTEM ====================

/**
 * Help System - Contextual guidance
 */
@Serializable
data class HelpEntry(
    val id: String,
    val title: String,
    val content: String,
    val category: HelpCategory,
    val relatedStats: List<String> = emptyList(),
    val relatedActions: List<String> = emptyList(),
    val difficulty: HelpDifficulty = HelpDifficulty.BEGINNER
)

enum class HelpCategory {
    GAMEPLAY, ECONOMY, MILITARY, DIPLOMACY,
    POLITICS, TECHNOLOGY, EVENTS, SETTINGS
}

enum class HelpDifficulty {
    BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
}

object HelpDatabase {
    val entries = listOf(
        HelpEntry(
            "help_economy", "Managing Your Economy",
            "Your economy determines how much money you earn each turn. Invest in infrastructure, maintain trade relationships, and balance taxes to maximize growth.",
            HelpCategory.ECONOMY, difficulty = HelpDifficulty.BEGINNER
        ),
        HelpEntry(
            "help_military", "Building Military Power",
            "Recruit troops, upgrade equipment, and develop military technology. Remember that military strength affects both defense and diplomatic negotiations.",
            HelpCategory.MILITARY, difficulty = HelpDifficulty.BEGINNER
        ),
        HelpEntry(
            "help_diplomacy", "Diplomatic Relations",
            "Build relationships with other nations through trade agreements, alliances, and diplomatic missions. Good relations open opportunities.",
            HelpCategory.DIPLOMACY, difficulty = HelpDifficulty.INTERMEDIATE
        ),
        HelpEntry(
            "help_technology", "Research & Technology",
            "Invest in research to unlock new capabilities. Technology affects all aspects of your nation from military to economy.",
            HelpCategory.TECHNOLOGY, difficulty = HelpDifficulty.INTERMEDIATE
        )
    )
}

// ==================== FEATURE 248: SOUND DESIGN ====================

/**
 * Sound Design - Atmospheric audio
 */
@Serializable
data class SoundSettings(
    val masterVolume: Float = 0.8f,
    val musicVolume: Float = 0.6f,
    val sfxVolume: Float = 0.8f,
    val voiceVolume: Float = 0.7f,
    val ambientVolume: Float = 0.5f,
    val enableMusic: Boolean = true,
    val enableSFX: Boolean = true,
    val enableVoice: Boolean = true,
    val enableAmbient: Boolean = true
)

enum class SoundCategory {
    MUSIC, AMBIENT, UI, EVENT, MILITARY, ECONOMIC
}

// ==================== FEATURE 249: VISUAL THEMES ====================

/**
 * Visual Themes - Customizable appearance
 */
@Serializable
data class VisualTheme(
    val name: String,
    val primaryColor: String,
    val secondaryColor: String,
    val accentColor: String,
    val backgroundColor: String,
    val textColor: String,
    val fontFamily: String = "Default",
    val isDarkMode: Boolean = false
)

object ThemeDatabase {
    val themes = listOf(
        VisualTheme(
            "Default Blue",
            "#2196F3", "#1976D2", "#64B5F6",
            "#FFFFFF", "#212121", "Default", false
        ),
        VisualTheme(
            "Dark Mode",
            "#BB86FC", "#3700B3", "#03DAC6",
            "#121212", "#E1E1E1", "Default", true
        ),
        VisualTheme(
            "Forest Green",
            "#4CAF50", "#388E3C", "#81C784",
            "#FAFAFA", "#212121", "Default", false
        ),
        VisualTheme(
            "Royal Purple",
            "#9C27B0", "#7B1FA2", "#CE93D8",
            "#FFFFFF", "#212121", "Default", false
        ),
        VisualTheme(
            "Monochrome",
            "#607D8B", "#455A64", "#90A4AE",
            "#FAFAFA", "#212121", "Default", false
        ),
        VisualTheme(
            "Warm Earth",
            "#795548", "#5D4037", "#A1887F",
            "#EFEBE9", "#3E2723", "Default", false
        )
    )
}

// ==================== FEATURE 250: ACCESSIBILITY OPTIONS ====================

/**
 * Accessibility Options - Colorblind modes, text size
 */
@Serializable
data class AccessibilitySettings(
    val textSize: TextSize = TextSize.MEDIUM,
    val colorblindMode: ColorblindMode = ColorblindMode.NONE,
    val highContrast: Boolean = false,
    val reduceMotion: Boolean = false,
    val screenReader: Boolean = false,
    val keyboardNavigation: Boolean = true,
    val largeTouchTargets: Boolean = false,
    val monoAudio: Boolean = false
)

enum class TextSize(val scale: Float) {
    SMALL(0.8f), MEDIUM(1.0f), LARGE(1.2f), EXTRA_LARGE(1.5f)
}

enum class ColorblindMode {
    NONE, PROTANOPIA, DEUTERANOPIA, TRITANOPIA
}

// ==================== FEATURE 251: KEYBOARD SHORTCUTS ====================

/**
 * Keyboard Shortcuts - Quick action keys
 */
@Serializable
data class KeyboardShortcuts(
    val endTurn: String = "Enter",
    val saveGame: String = "Ctrl+S",
    val loadGame: String = "Ctrl+O",
    val openMenu: String = "Escape",
    val toggleMap: String = "M",
    val openDiplomacy: String = "D",
    val openMilitary: String = "B",
    val openEconomy: String = "E",
    val openTech: String = "T",
    val notifications: String = "N",
    val speedPause: String = "Space",
    val speed1: String = "1",
    val speed2: String = "2",
    val speed3: String = "3",
    val screenshot: String = "Ctrl+P"
)

// ==================== FEATURE 253: LOADING SCREENS ====================

/**
 * Loading Screens - Educational fact displays
 */
@Serializable
data class LoadingScreen(
    val tips: List<GameTip> = emptyList(),
    val funFacts: List<FunFact> = emptyList(),
    val currentTipIndex: Int = 0
)

@Serializable
data class GameTip(
    val title: String,
    val content: String,
    val category: HelpCategory
)

@Serializable
data class FunFact(
    val fact: String,
    val category: String,
    val isHistorical: Boolean = true
)

object LoadingContent {
    val tips = listOf(
        GameTip("Economy Basics", "Keep your economy above 50% for optimal growth", HelpCategory.ECONOMY),
        GameTip("Military Strategy", "Balance military spending with social programs", HelpCategory.MILITARY),
        GameTip("Diplomacy", "Maintain good relations with at least 5 nations", HelpCategory.DIPLOMACY)
    )
    
    val funFacts = listOf(
        FunFact("The first modern democracy was Athens in 500 BCE", "History", true),
        FunFact("The United Nations was founded in 1945", "History", true),
        FunFact("The first computer was built in 1945", "Technology", true)
    )
}

// ==================== FEATURE 256: MAP CUSTOMIZATION ====================

/**
 * Map Customization - Political, terrain, resource views
 */
@Serializable
data class MapCustomization(
    val politicalStyle: MapStyle = MapStyle.SOLID,
    val terrainStyle: MapStyle = MapStyle.DETAILED,
    val labelStyle: LabelStyle = LabelStyle.STANDARD,
    val showGrid: Boolean = false,
    val gridSize: Int = 10,
    val showWeather: Boolean = false,
    val showTradeRoutes: Boolean = true,
    val showAlliances: Boolean = true
)

enum class MapStyle {
    SOLID, DETAILED, MINIMAL, CARTOON, RETRO
}

enum class LabelStyle {
    NONE, STANDARD, DETAILED, FLAG_ONLY
}

// ==================== FEATURE 257: DASHBOARD VIEW ====================

/**
 * Dashboard View - At-a-glance overview panel
 */
@Serializable
data class DashboardLayout(
    val layout: DashboardConfig = DashboardConfig(),
    val widgets: List<DashboardWidget> = emptyList(),
    val isCompact: Boolean = false
)

@Serializable
data class DashboardConfig(
    val showEconomy: Boolean = true,
    val showMilitary: Boolean = true,
    val showDiplomacy: Boolean = true,
    val showTechnology: Boolean = true,
    val showResources: Boolean = true,
    val showNotifications: Boolean = true,
    val showMiniMap: Boolean = true,
    val position: DashboardPosition = DashboardPosition.BOTTOM
)

enum class DashboardPosition {
    TOP, BOTTOM, LEFT, RIGHT
}

@Serializable
data class DashboardWidget(
    val id: String,
    val type: WidgetType,
    val title: String,
    val position: Int,
    val isVisible: Boolean = true,
    val configKeys: List<String> = emptyList(),
    val configValues: List<String> = emptyList()
)

enum class WidgetType {
    STAT_BAR, MINI_GRAPH, QUICK_ACTIONS, 
    NOTIFICATIONS, ALERTS, TIMELINE
}

// ==================== UI STATE & VIEW MODEL ====================

/**
 * Complete UI State for Phase 3
 */
@Serializable
data class UIState(
    // Map & Navigation
    val mapState: MapState = MapState(),
    val currentView: GameView = GameView.MAIN,
    val selectedNation: String? = null,
    
    // News & Information
    val newsFeed: NewsFeed = NewsFeed(),
    val timeline: HistoricalTimeline = HistoricalTimeline(),
    val notifications: NotificationCenter = NotificationCenter(),
    
    // Graphs & Stats
    val graphConfig: GraphConfiguration = GraphConfiguration(),
    val graphs: List<StatGraph> = emptyList(),
    val nationComparison: NationComparison? = null,
    
    // Settings
    val soundSettings: SoundSettings = SoundSettings(),
    val visualTheme: VisualTheme = ThemeDatabase.themes[0],
    val accessibility: AccessibilitySettings = AccessibilitySettings(),
    val keyboardShortcuts: KeyboardShortcuts = KeyboardShortcuts(),
    
    // Dashboard
    val dashboard: DashboardLayout = DashboardLayout(),
    val mapCustomization: MapCustomization = MapCustomization(),
    
    // Loading
    val loadingScreen: LoadingScreen = LoadingScreen(),
    
    // Help
    val helpDatabase: List<HelpEntry> = HelpDatabase.entries,
    val currentHelpTopic: String? = null
)

enum class GameView {
    MAIN, MAP, DIPLOMACY, MILITARY, ECONOMY, 
    TECHNOLOGY, POLITICS, EVENTS, SETTINGS, ACHIEVEMENTS
}

/**
 * UI Controller - Handles all UI interactions
 */
object UIController {
    
    fun updateMapView(state: UIState, mode: MapMode): UIState {
        return state.copy(
            mapState = state.mapState.copy(mapMode = mode)
        )
    }
    
    fun addNewsArticle(state: UIState, article: NewsArticle): UIState {
        val newArticles = (state.newsFeed.articles + article)
            .sortedByDescending { it.timestamp }
            .take(100)
        
        return state.copy(
            newsFeed = state.newsFeed.copy(
                articles = newArticles,
                unreadCount = state.newsFeed.unreadCount + 1
            )
        )
    }
    
    fun markNotificationRead(state: UIState, notificationId: String): UIState {
        val updated = state.notifications.notifications.map {
            if (it.id == notificationId) it.copy(isRead = true) else it
        }
        
        return state.copy(
            notifications = state.notifications.copy(
                notifications = updated,
                unreadCount = (state.notifications.unreadCount - 1).coerceAtLeast(0)
            )
        )
    }
    
    fun changeTheme(state: UIState, theme: VisualTheme): UIState {
        return state.copy(visualTheme = theme)
    }
    
    fun changeGraphStyle(state: UIState, style: GraphStyle): UIState {
        return state.copy(
            graphConfig = state.graphConfig.copy(style = style)
        )
    }
    
    fun toggleDashboard(state: UIState): UIState {
        return state.copy(
            dashboard = state.dashboard.copy(isCompact = !state.dashboard.isCompact)
        )
    }
}
