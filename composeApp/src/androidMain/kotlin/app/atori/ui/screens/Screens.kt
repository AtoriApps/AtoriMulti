package app.atori.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.atori.resources.Res
import app.atori.resources.call
import app.atori.resources.chat_details
import app.atori.resources.emoji
import app.atori.resources.ic_call_24px
import app.atori.resources.ic_emoji_24px
import app.atori.resources.ic_input_more_24px
import app.atori.resources.ic_pinned_msgs_24px
import app.atori.resources.ic_search_24px
import app.atori.resources.ic_search_msgs_24px
import app.atori.resources.ic_send_24px
import app.atori.resources.img_avatar_demo
import app.atori.resources.input_placeholder
import app.atori.resources.more
import app.atori.resources.pinned_messages
import app.atori.resources.search_messages
import app.atori.resources.send
import app.atori.stores.AndroidDemoStateStore
import app.atori.stores.AppStateStore
import app.atori.stores.DemoStateStore
import app.atori.ui.components.AtoriIconButton
import app.atori.ui.components.AtoriMenuItem
import app.atori.ui.components.PrefabAtoriLogoIcon
import app.atori.ui.components.SimpleTextField
import app.atori.ui.pages.DemoVoiceCallPage
import app.atori.ui.pages.DemoChatDetailsPage
import app.atori.ui.pages.DemoIncomingCallPage
import app.atori.ui.pages.DemoVideoCallPage
import app.atori.ui.views.DemoChatView
import app.atori.ui.views.DemoScreensTopBar
import app.atori.ui.views.dialogs.AboutDialog
import app.atori.ui.views.dialogs.DialogBase
import app.atori.ui.views.dialogs.OneAtoriDialog
import app.atori.utils.ResUtils.imgBmp
import app.atori.utils.ResUtils.text
import app.atori.utils.ResUtils.vector

@Composable
fun DemoVoiceCallScreen() =
    DemoVoiceCallPage { AndroidDemoStateStore.isInVoiceCallScreen.value = false }

@Composable
fun DemoVideoCallScreen() =
    DemoVideoCallPage { AndroidDemoStateStore.isInVideoCallScreen.value = false }

@Composable
fun DemoIncomingCallScreen() =
    DemoIncomingCallPage(false, true, {
        AndroidDemoStateStore.isInIncomingCallScreen.value = false
    }) {
        AndroidDemoStateStore.isInIncomingCallScreen.value = false
        AndroidDemoStateStore.isInVoiceCallScreen.value = true
    }

@Composable
fun DemoMainScreen() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar() {
        var showOneAtori by remember { mutableStateOf(false) }

        var showAbout by remember { mutableStateOf(false) }

        if (showOneAtori)
            DialogBase({ showOneAtori = false }) {
                OneAtoriDialog({ showAbout = true })
            }

        if (showAbout)
            DialogBase({ showAbout = false }) {
                AboutDialog()
            }

        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                .height(64.dp)
                .padding(horizontal = 8.dp)
        ) {
            // 左侧内容
            Row(Modifier.align(Alignment.CenterStart)) {
                Box(Modifier.size(48.dp), Alignment.Center) {
                    PrefabAtoriLogoIcon()
                }
            }

            // 中间标题
            Text(
                "Atori",
                Modifier.align(Alignment.Center),
                MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )

            // 右侧内容
            Row(Modifier.align(Alignment.CenterEnd)) {
                AtoriIconButton(Res.drawable.ic_search_24px, "Search", 48) {}

                AtoriIconButton({
                    showOneAtori = true
                }, 48) {
                    Image(
                        Res.drawable.img_avatar_demo.imgBmp,
                        "One Atori",
                        Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }

    @Composable
    fun BottomBar() = NavigationBar {
        AppStateStore.navTabItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        (if (AppStateStore.currentNavTab.value == index) item.selectedIcon else item.icon).vector,
                        item.name
                    )
                },
                label = { Text(item.name) },
                selected = AppStateStore.currentNavTab.value == index,
                onClick = { AppStateStore.currentNavTab.value = index },
                alwaysShowLabel = false
            )
        }
    }

    Scaffold(topBar = { TopBar() }, bottomBar = { BottomBar() })
    { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding), Alignment.Center
        ) {
            AppStateStore.navTabItems[AppStateStore.currentNavTab.value].view()
        }
    }
}

@Composable
fun DemoChatScreen() {
    @Composable
    fun BottomBar() {
        var tempText by remember { mutableStateOf("") }

        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .windowInsetsPadding(BottomAppBarDefaults.windowInsets)
                .padding(12.dp), Arrangement.spacedBy(8.dp), Alignment.CenterVertically
        ) {

            Row(
                Modifier
                    .weight(1F)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .padding(4.dp), Arrangement.spacedBy(4.dp), Alignment.CenterVertically
            ) {
                AtoriIconButton(Res.drawable.ic_input_more_24px, Res.string.more.text)
                SimpleTextField(
                    tempText, { tempText = it },
                    Res.string.input_placeholder.text, Modifier.weight(1F)
                )
                AtoriIconButton(Res.drawable.ic_emoji_24px, Res.string.emoji.text)
            }
            AtoriIconButton(Res.drawable.ic_send_24px, Res.string.send.text, 48)
        }
    }

    var showMoreMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DemoScreensTopBar(onClickTitle = {
                AndroidDemoStateStore.isInChatInfoScreen.value = true
            }, onClickMore = {
                showMoreMenu = true
            }, showDropMenu = showMoreMenu, onDismissDropMenu = {
                showMoreMenu = false
            }, dropMenuContent = {
                AtoriMenuItem(Res.string.pinned_messages.text, Res.drawable.ic_pinned_msgs_24px)
                AtoriMenuItem(Res.string.call.text, Res.drawable.ic_call_24px) {
                    AndroidDemoStateStore.isInVoiceCallScreen.value = true
                }
                AtoriMenuItem(Res.string.search_messages.text, Res.drawable.ic_search_msgs_24px)
            }
            ) { DemoStateStore.currentChat.value = -1 }
        },
        bottomBar = { BottomBar() })
    { padding ->
        Box(Modifier.padding(padding), Alignment.Center) {
            DemoChatView()
        }
    }
}

@Composable
fun DemoChatDetailsScreen() {
    Scaffold(
        topBar = {
            DemoScreensTopBar(
                Res.string.chat_details.text, showSubtitle = false, showMoreButton = false
            ) { AndroidDemoStateStore.isInChatInfoScreen.value = false }
        })
    { padding ->
        Box(Modifier.padding(padding), Alignment.Center) {
            DemoChatDetailsPage(true, {
                AndroidDemoStateStore.isInVideoCallScreen.value = true
            }, { AndroidDemoStateStore.isInVoiceCallScreen.value = true }) {
                AndroidDemoStateStore.isInIncomingCallScreen.value = true
            }
        }
    }
}