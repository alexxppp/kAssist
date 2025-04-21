package dev.alexpace.kassist.ui.supporter.pages.contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import org.koin.compose.koinInject

@Composable
fun SupporterContactPage(supporterId: String) {
    val liveChatRepository = koinInject<LiveChatRepository>()
    val viewModel: SupporterContactPageViewModel = viewModel {
        SupporterContactPageViewModel(liveChatRepository, supporterId)
    }
    val liveChats by viewModel.liveChats.collectAsState()

    Column {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(liveChats) { chat ->
                Text(
                    text = "Chat with ${chat.victimId}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}