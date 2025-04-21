package dev.alexpace.kassist.ui.supporter.pages.contact

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import org.koin.compose.koinInject

@Composable
fun SupporterContactPage() {

    val liveChatRepository = koinInject<LiveChatRepository>()

    val viewModel: SupporterContactPageViewModel = viewModel {
        SupporterContactPageViewModel(liveChatRepository)
    }

}