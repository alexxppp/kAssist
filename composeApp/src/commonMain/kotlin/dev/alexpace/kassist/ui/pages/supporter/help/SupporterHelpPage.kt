package dev.alexpace.kassist.ui.pages.supporter.help

import androidx.compose.runtime.Composable
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl

@Composable
fun SupporterHelpPage() {

    val helpRequestRepository = HelpRequestRepositoryImpl()

    SupporterHelpPageContent(helpRequestRepository)

}