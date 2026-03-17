package com.countrysimulator.game.multiplayer

import kotlinx.serialization.Serializable
import com.countrysimulator.game.domain.*

/**
 * ========================================================================
 * COUNTRY SIMULATOR - PHASE 5 FEATURES IMPLEMENTATION
 * Features 201-250: Multiplayer & Social
 * ========================================================================
 */

// ==================== FEATURE 286: ONLINE MULTIPLAYER ====================

/**
 * Online Multiplayer - Play against real players
 */
@Serializable
data class MultiplayerSession(
    val id: String,
    val name: String,
    val hostPlayerId: String,
    val players: List<MultiplayerPlayer> = emptyList(),
    val status: SessionStatus = SessionStatus.WAITING,
    val settings: MultiplayerSettings = MultiplayerSettings(),
    val turnNumber: Int = 0,
    val currentPlayerId: String? = null,
    val turnStartTime: Long = 0,
    val timeLimit: Long = 300000,  // 5 minutes default
    val maxPlayers: Int = 8
)

enum class SessionStatus {
    WAITING,       // Lobby open
    STARTING,      // About to begin
    IN_PROGRESS,   // Game running
    PAUSED,        // Temporarily paused
    FINISHED       // Game completed
}

@Serializable
data class MultiplayerPlayer(
    val id: String,
    val name: String,
    val nationId: String?,
    val nationName: String?,
    val status: PlayerStatus = PlayerStatus.NOT_READY,
    val isHost: Boolean = false,
    val isConnected: Boolean = true,
    val stats: PlayerStats = PlayerStats(),
    val lastActionTime: Long = 0
)

enum class PlayerStatus {
    NOT_READY, READY, PLAYING, WAITING, DEFEATED, VICTORIOUS, DISCONNECTED
}

@Serializable
data class PlayerStats(
    val gamesPlayed: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val rating: Int = 1000,  // ELO-style rating
    val favoriteNation: String? = null
)

@Serializable
data class MultiplayerSettings(
    val speed: GameSpeed = GameSpeed.SLOW,
    val difficulty: Difficulty = Difficulty.NORMAL,
    val isFogOfWar: Boolean = true,
    val isTeamGame: Boolean = false,
    val victoryConditions: List<VictoryType> = listOf(VictoryType.SURVIVAL),
    val isPublic: Boolean = false,
    val allowPause: Boolean = true,
    val chatEnabled: Boolean = true
)

// ==================== FEATURE 287: HOTSEAT MULTIPLAYER ====================

/**
 * Hotseat Multiplayer - Pass device between players
 */
@Serializable
data class HotseatGame(
    val players: List<HotseatPlayer> = emptyList(),
    val currentPlayerIndex: Int = 0,
    val turnHistory: List<HotseatTurn> = emptyList(),
    val passCount: Int = 0,
    val totalPassesAllowed: Int = 0
)

@Serializable
data class HotseatPlayer(
    val index: Int,
    val name: String,
    val nation: String,
    val isReady: Boolean = false,
    val color: String = "#FFFFFF"
)

@Serializable
data class HotseatTurn(
    val playerIndex: Int,
    val turnNumber: Int,
    val actions: List<String> = emptyList(),
    val duration: Long = 0  // Milliseconds
)

// ==================== FEATURE 288: ASYNCHRONOUS PLAY ====================

/**
 * Asynchronous Play - Turn-based online play
 */
@Serializable
data class AsyncGame(
    val id: String,
    val players: List<AsyncPlayer> = emptyList(),
    val currentTurn: Int = 1,
    val pendingPlayerId: String? = null,
    val turnDeadline: Long = 0,  // Unix timestamp
    val turnNotifications: List<AsyncNotification> = emptyList(),
    val moveHistory: List<AsyncMove> = emptyList()
)

@Serializable
data class AsyncPlayer(
    val id: String,
    val name: String,
    val nationName: String,
    val isActive: Boolean = true,
    val notificationPrefs: AsyncNotificationPrefs = AsyncNotificationPrefs()
)

@Serializable
data class AsyncNotificationPrefs(
    val email: Boolean = true,
    val push: Boolean = true,
    val inApp: Boolean = true,
    val frequency: NotificationFrequency = NotificationFrequency.IMMEDIATE
)

enum class NotificationFrequency {
    IMMEDIATE, DAILY, WEEKLY, MANUAL
}

@Serializable
data class AsyncNotification(
    val type: AsyncNotificationType,
    val message: String,
    val timestamp: Long,
    val isRead: Boolean = false
)

enum class AsyncNotificationType {
    TURN_REMINDER, YOUR_TURN, OPPONENT_MOVED, GAME_ENDED, TIME_WARNING
}

@Serializable
data class AsyncMove(
    val playerId: String,
    val turnNumber: Int,
    val moveData: String,  // Serialized move
    val timestamp: Long
)

// ==================== FEATURE 289: PLAYER NATIONS ====================

/**
 * Player Nations - Persistent online nations
 */
@Serializable
data class PlayerNation(
    val ownerId: String,
    val nationName: String,
    val nationData: Country,
    val stats: PlayerNationStats = PlayerNationStats(),
    val achievements: List<String> = emptyList(),
    val badges: List<Badge> = emptyList()
)

@Serializable
data class PlayerNationStats(
    val totalGames: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val survivalDays: Int = 0,
    val totalPopulation: Long = 0,
    val totalEarnings: Long = 0
)

@Serializable
data class Badge(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val earnedDate: Long = 0
)

// ==================== FEATURE 290: ALLIANCE CREATION ====================

/**
 * Alliance Creation - Form player alliances
 */
@Serializable
data class PlayerAlliance(
    val id: String,
    val name: String,
    val leaderId: String,
    val memberIds: List<String> = emptyList(),
    val createdAt: Long = 0,
    val charter: AllianceCharter = AllianceCharter(name = "Default Charter", description = "Alliance charter"),
    val diplomacy: AllianceDiplomacy = AllianceDiplomacy(),
    val forums: List<AlliancePost> = emptyList()
)

@Serializable
data class AllianceCharter(
    val name: String,
    val description: String,
    val goals: List<String> = emptyList(),
    val rules: List<String> = emptyList(),
    val isPublic: Boolean = true
)

@Serializable
data class AllianceDiplomacy(
    val allies: List<String> = emptyList(),
    val enemies: List<String> = emptyList(),
    val neutral: List<String> = emptyList(),
    val wars: List<String> = emptyList()
)

@Serializable
data class AlliancePost(
    val id: String,
    val authorId: String,
    val content: String,
    val timestamp: Long,
    val replies: Int = 0
)

// ==================== FEATURE 291: TRADE NETWORKS ====================

/**
 * Trade Networks - Player-to-player trade
 */
@Serializable
data class PlayerTrade(
    val id: String,
    val offeredBy: String,
    val requestedBy: String,
    val offeredItems: List<TradeItem> = emptyList(),
    val requestedItems: List<TradeItem> = emptyList(),
    val status: TradeStatus = TradeStatus.PENDING,
    val createdAt: Long = 0,
    val expiresAt: Long = 0
)

@Serializable
data class TradeItem(
    val type: TradeItemType,
    val resource: String? = null,
    val amount: Int = 0,
    val value: Int = 0
)

enum class TradeItemType {
    RESOURCES, TREASURY, TECHNOLOGY, TERRITORY, TREATY, ARTIFACT
}

enum class TradeStatus {
    PENDING, ACCEPTED, REJECTED, CANCELLED, COMPLETED, EXPIRED
}

@Serializable
data class TradeNetwork(
    val trades: List<PlayerTrade> = emptyList(),
    val activeConnections: Map<String, List<String>> = emptyMap(),  // Player -> trading partners
    val tradeHistory: List<TradeHistoryEntry> = emptyList()
)

@Serializable
data class TradeHistoryEntry(
    val tradeId: String,
    val players: List<String>,
    val totalValue: Int,
    val timestamp: Long
)

// ==================== FEATURE 292: WAR DECLARATIONS ====================

/**
 * War Declarations - Player vs. player warfare
 */
@Serializable
data class PlayerWar(
    val id: String,
    val attackerId: String,
    val defenderId: String,
    val startTurn: Int,
    val status: WarStatus = WarStatus.ACTIVE,
    val battles: List<PlayerBattle> = emptyList(),
    val warGoals: List<WarGoal> = emptyList(),
    val warExhaustion: Map<String, Int> = emptyMap()
)

enum class WarStatus {
    ACTIVE, NEGOTIATING, TRUCE, ENDED
}

@Serializable
data class PlayerBattle(
    val id: String,
    val attackerId: String,
    val defenderId: String,
    val location: String,
    val attackerLosses: Int = 0,
    val defenderLosses: Int = 0,
    val winner: String? = null,
    val timestamp: Long
)

@Serializable
data class WarGoal(
    val type: WarGoalType,
    val value: Int,
    val isAchieved: Boolean = false
)

enum class WarGoalType {
    TERRITORY, RESOURCES, REPARATIONS, REGIME_CHANGE, SURRENDER
}

// ==================== FEATURE 293: GLOBAL EVENTS ====================

/**
 * Global Events - Server-wide events
 */
@Serializable
data class GlobalEvent(
    val id: String,
    val type: GlobalEventType,
    val name: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val rewards: List<EventReward> = emptyList(),
    val rules: List<String> = emptyList(),
    val leaderboard: List<LeaderboardEntry> = emptyList()
)

enum class GlobalEventType {
    COMPETITION, COOPERATION, LIMITED_TIME, TOURNAMENT, CHALLENGE
}

@Serializable
data class EventReward(
    val rank: Int,
    val rewardType: String,
    val value: Int,
    val description: String
)

@Serializable
data class LeaderboardEntry(
    val rank: Int,
    val playerId: String,
    val playerName: String,
    val score: Int,
    val nationName: String
)

// ==================== FEATURE 294: LEADERBOARDS ====================

/**
 * Leaderboards - Competitive rankings
 */
@Serializable
data class Leaderboard(
    val id: String,
    val type: LeaderboardType,
    val entries: List<LeaderboardEntry> = emptyList(),
    val lastUpdated: Long = 0,
    val timeRange: LeaderboardTimeRange = LeaderboardTimeRange.ALL_TIME
)

enum class LeaderboardType {
    ECONOMY, MILITARY, DIPLOMATIC, OVERALL, SURVIVAL, WINS
}

enum class LeaderboardTimeRange {
    SEASONAL, MONTHLY, WEEKLY, ALL_TIME
}

// ==================== FEATURE 295: CLAN SYSTEMS ====================

/**
 * Clan Systems - Player groups
 */
@Serializable
data class Clan(
    val id: String,
    val name: String,
    val tag: String,  // Short tag like [TFS]
    val leaderId: String,
    val officers: List<String> = emptyList(),
    val members: List<ClanMember> = emptyList(),
    val description: String = "",
    val foundedDate: Long = 0,
    val stats: ClanStats = ClanStats(),
    val diplomaticStance: ClanDiplomacy = ClanDiplomacy()
)

@Serializable
data class ClanMember(
    val playerId: String,
    val rank: ClanRank,
    val joinDate: Long,
    val contribution: Int = 0
)

enum class ClanRank {
    LEADER, OFFICER, ELITE, MEMBER, RECRUIT
}

@Serializable
data class ClanStats(
    val totalWins: Int = 0,
    val totalGames: Int = 0,
    val averageRating: Int = 0,
    val membersOnline: Int = 0
)

@Serializable
data class ClanDiplomacy(
    val allies: List<String> = emptyList(),
    val rivals: List<String> = emptyList(),
    val wars: List<String> = emptyList()
)

// ==================== FEATURE 296: TOURNAMENTS ====================

/**
 * Tournaments - Competitive events
 */
@Serializable
data class Tournament(
    val id: String,
    val name: String,
    val description: String,
    val format: TournamentFormat,
    val status: TournamentStatus,
    val startDate: Long,
    val endDate: Long,
    val maxPlayers: Int,
    val registeredPlayers: List<String> = emptyList(),
    val bracket: TournamentBracket? = null,
    val prizes: List<TournamentPrize> = emptyList()
)

enum class TournamentFormat {
    SINGLE_ELIMINATION, DOUBLE_ELIMINATION, ROUND_ROBIN, SWISS, FREE_FOR_ALL
}

enum class TournamentStatus {
    REGISTRATION, IN_PROGRESS, COMPLETED, CANCELLED
}

@Serializable
data class TournamentBracket(
    val rounds: List<TournamentRound> = emptyList()
)

@Serializable
data class TournamentRound(
    val roundNumber: Int,
    val matches: List<TournamentMatch> = emptyList()
)

@Serializable
data class TournamentMatch(
    val id: String,
    val player1Id: String,
    val player2Id: String,
    val winnerId: String? = null,
    val status: MatchStatus = MatchStatus.PENDING
)

enum class MatchStatus {
    PENDING, IN_PROGRESS, COMPLETED, BYE
}

@Serializable
data class TournamentPrize(
    val place: Int,
    val rewardType: String,
    val value: Int
)

// ==================== FEATURE 297: CHAT SYSTEM ====================

/**
 * Chat System - Player communication
 */
@Serializable
data class ChatMessage(
    val id: String,
    val senderId: String,
    val senderName: String,
    val content: String,
    val channelId: String,
    val timestamp: Long,
    val type: MessageType = MessageType.CHAT
)

enum class MessageType {
    CHAT, SYSTEM, ANNOUNCEMENT, WHISPER, ALERT
}

@Serializable
data class ChatChannel(
    val id: String,
    val name: String,
    val type: ChannelType,
    val members: List<String> = emptyList(),
    val isPrivate: Boolean = false,
    val unreadCount: Map<String, Int> = emptyMap()
)

enum class ChannelType {
    GLOBAL, GAME, CLAN, ALLIANCE, TOURNAMENT, PRIVATE
)

// ==================== FEATURE 298: FRIEND LISTS ====================

/**
 * Friend Lists - Add other players
 */
@Serializable
data class Friend(
    val playerId: String,
    val name: String,
    val status: FriendStatus = FriendStatus.OFFLINE,
    val lastSeen: Long = 0,
    val friendSince: Long = 0,
    val note: String = ""
)

enum class FriendStatus {
    ONLINE, IN_GAME, AWAY, OFFLINE
}

@Serializable
data class FriendList(
    val friends: List<Friend> = emptyList(),
    val blockedPlayers: List<String> = emptyList(),
    val pendingRequests: List<FriendRequest> = emptyList()
)

@Serializable
data class FriendRequest(
    val fromId: String,
    val fromName: String,
    val timestamp: Long,
    val status: RequestStatus = RequestStatus.PENDING
)

enum class RequestStatus {
    PENDING, ACCEPTED, REJECTED
}

// ==================== FEATURE 299: REPLAY SYSTEM ====================

/**
 * Replay System - Watch past games
 */
@Serializable
data class GameReplay(
    val id: String,
    val gameId: String,
    val players: List<ReplayPlayer> = emptyList(),
    val turns: List<ReplayTurn> = emptyList(),
    val winnerId: String? = null,
    val duration: Long = 0,
    val timestamp: Long = 0,
    val views: Int = 0,
    val rating: Double = 0.0
)

@Serializable
data class ReplayPlayer(
    val playerId: String,
    val nationName: String,
    final val color: String
)

@Serializable
data class ReplayTurn(
    val turnNumber: Int,
    val actions: List<ReplayAction> = emptyList(),
    val events: List<String> = emptyList(),
    val timestamp: Long = 0
)

@Serializable
data class ReplayAction(
    val type: ActionType,
    val playerId: String,
    val data: String,
    val turnNumber: Int
)

enum class ActionType {
    DECLARE_WAR, SIGN_TREATY, BUILD, RESEARCH, ELECTION, EVENT_CHOICE
}

// ==================== FEATURE 300: SPECTATOR MODE ====================

/**
 * Spectator Mode - Watch ongoing games
 */
@Serializable
data class SpectatorSession(
    val gameId: String,
    val spectators: List<String> = emptyList(),
    val isLive: Boolean = true,
    val viewingAngle: SpectatorView = SpectatorView.FREE,
    val highlights: List<String> = emptyList(),
    val chat: List<ChatMessage> = emptyList()
)

enum class SpectatorView {
    FREE, FOLLOW_PLAYER, TACTICAL, OVERVIEW
}

// ==================== MULTIPLAYER CONTROLLER ====================

/**
 * Multiplayer Controller - Handles all multiplayer operations
 */
object MultiplayerController {
    
    /**
     * Create new multiplayer session
     */
    fun createSession(hostId: String, hostName: String, settings: MultiplayerSettings): MultiplayerSession {
        val hostPlayer = MultiplayerPlayer(
            id = hostId,
            name = hostName,
            nationId = null,
            nationName = null,
            status = PlayerStatus.READY,
            isHost = true
        )
        
        return MultiplayerSession(
            id = "session_${System.currentTimeMillis()}",
            name = "${hostName}'s Game",
            hostPlayerId = hostId,
            players = listOf(hostPlayer),
            settings = settings
        )
    }
    
    /**
     * Join existing session
     */
    fun joinSession(session: MultiplayerSession, player: MultiplayerPlayer): MultiplayerSession {
        if (session.players.size >= session.maxPlayers) {
            return session
        }
        
        return session.copy(
            players = session.players + player.copy(status = PlayerStatus.NOT_READY)
        )
    }
    
    /**
     * Start game when all players ready
     */
    fun startGame(session: MultiplayerSession): MultiplayerSession {
        val allReady = session.players.all { it.status == PlayerStatus.READY }
        
        if (!allReady || session.players.size < 2) {
            return session
        }
        
        return session.copy(
            status = SessionStatus.IN_PROGRESS,
            currentPlayerId = session.players.first().id,
            turnStartTime = System.currentTimeMillis()
        )
    }
    
    /**
     * Process player turn
     */
    fun processTurn(session: MultiplayerSession, playerId: String, actions: List<String>): MultiplayerSession {
        if (session.status != SessionStatus.IN_PROGRESS) return session
        if (session.currentPlayerId != playerId) return session
        
        // Move to next player
        val currentIndex = session.players.indexOfFirst { it.id == playerId }
        val nextIndex = (currentIndex + 1) % session.players.size
        val nextPlayer = session.players[nextIndex]
        
        // Check for timeout
        val timeElapsed = System.currentTimeMillis() - session.turnStartTime
        val player = session.players.find { it.id == playerId }
        
        val updatedPlayers = session.players.map {
            if (it.id == playerId) {
                it.copy(status = PlayerStatus.WAITING)
            } else if (it.id == nextPlayer.id) {
                it.copy(status = PlayerStatus.PLAYING)
            } else it
        }
        
        return session.copy(
            players = updatedPlayers,
            currentPlayerId = nextPlayer.id,
            turnNumber = session.turnNumber + 1,
            turnStartTime = System.currentTimeMillis()
        )
    }
    
    /**
     * Create async game
     */
    fun createAsyncGame(players: List<AsyncPlayer>): AsyncGame {
        val firstPlayer = players.first()
        
        return AsyncGame(
            id = "async_${System.currentTimeMillis()}",
            players = players,
            pendingPlayerId = firstPlayer.id,
            turnDeadline = System.currentTimeMillis() + 604800000  // 7 days
        )
    }
    
    /**
     * Submit async move
     */
    fun submitAsyncMove(game: AsyncGame, playerId: String, move: String): AsyncGame {
        if (game.pendingPlayerId != playerId) return game
        
        val currentPlayer = game.players.find { it.id == playerId } ?: return game
        val nextIndex = (game.players.indexOfFirst { it.id == playerId } + 1) % game.players.size
        val nextPlayer = game.players[nextIndex]
        
        val asyncMove = AsyncMove(
            playerId = playerId,
            turnNumber = game.currentTurn,
            moveData = move,
            timestamp = System.currentTimeMillis()
        )
        
        return game.copy(
            currentTurn = game.currentTurn + 1,
            pendingPlayerId = nextPlayer.id,
            turnDeadline = System.currentTimeMillis() + 604800000,
            moveHistory = game.moveHistory + asyncMove
        )
    }
}
