package dev.alexpace.kassist.ui.victim.pages.contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.shared.components.chat.ChatCard
import dev.alexpace.kassist.ui.shared.navigation.screens.ChatScreen
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.compose.koinInject

@Composable
fun VictimContactPage() {

    // Values
    val navigator =
        LocalNavigator.currentOrThrow.parent ?: throw Exception("No parent navigator found")
    val currentUserId = Firebase.auth.currentUser?.uid
        // TODO: Handle more nicely
        ?: throw Exception("User not authenticated")

    // DI
    val liveChatRepository = koinInject<LiveChatRepository>()
    val userRepository = koinInject<UserRepository>()

    // ViewModel
    val viewModel = viewModel {
        VictimContactPageViewModel(liveChatRepository, userRepository, currentUserId)
    }
    val liveChats by viewModel.liveChats.collectAsState()
    val userNames by viewModel.userNames.collectAsState()

    // UI
    Column {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(liveChats) { chat ->
                val receiverId =
                    if (chat.victimId == currentUserId) chat.supporterId else chat.victimId
                val receiverName = userNames[receiverId] ?: "Loading..."
                ChatCard(
                    liveChat = chat,
                    receiverName = receiverName,
                    onChatClick = { _ ->
                        navigator.push(
                            ChatScreen(
                                liveChat = chat
                            )
                        )
                    }
                )
            }
        }
    }
}