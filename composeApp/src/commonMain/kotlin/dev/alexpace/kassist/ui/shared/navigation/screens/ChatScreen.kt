package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.alexpace.kassist.domain.models.classes.chat.LiveChat
import dev.alexpace.kassist.ui.shared.pages.chat.ChatPage

class ChatScreen(
    val liveChat: LiveChat
) : Screen {

    // Content
    @Composable
    override fun Content() {
        ChatPage(liveChat)
    }
}