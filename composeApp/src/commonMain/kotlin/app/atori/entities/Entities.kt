package app.atori.entities

import androidx.compose.runtime.Composable
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.compose.resources.DrawableResource

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fromUser: String,
    val messageBody: String,
    val timestamp: Long
)

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jid: String,
    val password: String,
)

data class DemoChatEntity(
    val avatar: DrawableResource,
    val name: String,
    val lastMessage: String,
    val timestamp: Long,
    val unreadCount: Int,
    val pinned: Boolean,
    val muted: Boolean,
    val sendState: Int
)

data class NavTabItem(
    val icon: DrawableResource,
    val selectedIcon: DrawableResource,
    val name: String,
    val view: @Composable () -> Unit
)

enum class DemoMessageType {
    FIRST_MESSAGE,
    HEADLESS,
    READ_TO_HERE,
    TIME_LINE,
    REPLY,
}

enum class DemoMessageBodyType {
    NONE,
    TEXT,
    IMAGE
}

data class DemoMessageReactionEntity(
    val emoji: String,
    val count: Int = 1,
    val useClicked: Boolean = false
)

data class DemoMessageEntity(
    // val fromNpc: Boolean = false,
    val messageBodyData: Any?,
    val timestamp: Long,
    val messageType: DemoMessageType,
    val messageBodyType: DemoMessageBodyType = DemoMessageBodyType.NONE,
    val reactions: List<DemoMessageReactionEntity> = emptyList()
)