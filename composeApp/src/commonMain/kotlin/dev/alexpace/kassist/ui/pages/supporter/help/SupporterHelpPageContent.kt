package dev.alexpace.kassist.ui.pages.supporter.help

import androidx.compose.runtime.Composable
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.ui.components.supporter.requests.HelpRequestList

@Composable
fun SupporterHelpPageContent(helpRequestRepository: HelpRequestRepositoryImpl) {

    HelpRequestList(helpRequestRepository)

}