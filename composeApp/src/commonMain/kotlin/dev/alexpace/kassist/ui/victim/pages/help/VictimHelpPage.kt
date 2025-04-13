package dev.alexpace.kassist.ui.victim.pages.help

import androidx.compose.runtime.Composable
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl

@Composable
fun VictimHelpPage() {

    val helpRequestRepository = HelpRequestRepositoryImpl()

    VictimHelpPageContent(helpRequestRepository)

}
