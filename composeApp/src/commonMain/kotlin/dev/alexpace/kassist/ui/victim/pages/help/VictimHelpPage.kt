package dev.alexpace.kassist.ui.victim.pages.help

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.ui.victim.components.requests.HelpRequestForm

@Composable
fun VictimHelpPage() {
    val helpRequestRepository = HelpRequestRepositoryImpl()
    val viewModel: VictimHelpPageViewModel = viewModel {
        VictimHelpPageViewModel(helpRequestRepository)
    }

    HelpRequestForm(onSubmit = { helpRequest ->
        viewModel.submitHelpRequest(helpRequest)
    })
}