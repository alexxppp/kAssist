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
import dev.alexpace.kassist.ui.shared.components.ChatCard
import dev.alexpace.kassist.ui.shared.navigation.screens.ChatScreen
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.compose.koinInject

@Composable
fun VictimContactPage() {

    val navigator =
        LocalNavigator.currentOrThrow.parent ?: throw Exception("No parent navigator found")
    val currentUserId = Firebase.auth.currentUser?.uid ?: return
    val liveChatRepository = koinInject<LiveChatRepository>()
    val victimViewModel = viewModel {
        VictimContactPageViewModel(liveChatRepository, currentUserId)
    }
    val liveChats by victimViewModel.liveChats.collectAsState()

    Column {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(liveChats) { chat ->
                ChatCard(
                    liveChat = chat,
                    onChatClick = { chatId ->
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