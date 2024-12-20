package app.atori.stores

import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import app.atori.models.NavTabItem
import app.atori.ui.pages.DemoChatsPage
import app.atori.resources.*

object AppStateStore {
    val navTabItems = listOf(
        NavTabItem(Res.drawable.ic_chats_24px, Res.drawable.ic_chats_filled_24px, "聊天") {
            DemoChatsPage()
        },
        NavTabItem(Res.drawable.ic_contacts_24px, Res.drawable.ic_contacts_filled_24px, "联系人") {
            Text("联系人")
        },
        NavTabItem(Res.drawable.ic_explore_24px, Res.drawable.ic_explore_filled_24px, "发现") {
            Text("发现")
        }
    )

    val currentNavTab = mutableStateOf(0)
}