package dev.alexpace.kassist.ui.pages.victim.help

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.ui.components.victim.requests.HelpRequestForm
import kotlinx.coroutines.launch

@Composable
fun VictimHelpPageContent(helpRequestRepository: HelpRequestRepository) {

    val scope = rememberCoroutineScope()

    HelpRequestForm(onSubmit = { helpRequest ->
        scope.launch {
            helpRequestRepository.addHelpRequest(helpRequest)
        }
    })
}