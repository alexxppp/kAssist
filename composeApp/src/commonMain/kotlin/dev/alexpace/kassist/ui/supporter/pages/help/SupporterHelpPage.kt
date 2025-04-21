import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.ui.supporter.components.proposal.HelpProposalForm
import dev.alexpace.kassist.ui.supporter.components.requests.HelpRequestList
import dev.alexpace.kassist.ui.supporter.pages.help.SupporterHelpPageViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.compose.koinInject

@Composable
fun SupporterHelpPage() {
    val helpRequestRepository = koinInject<HelpRequestRepositoryImpl>()
    val helpProposalRepository = koinInject<HelpProposalRepositoryImpl>()
    val userId = Firebase.auth.currentUser?.uid ?: return // Handle null user

    val viewModel: SupporterHelpPageViewModel = viewModel {
        SupporterHelpPageViewModel(helpRequestRepository, helpProposalRepository, userId)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE6F0FA),
                        Color(0xFFFFFFFF)
                    )
                )
            )
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HelpRequestList(viewModel)
            viewModel.selectedHelpRequest.collectAsState().value?.let { request ->
                HelpProposalForm(
                    helpRequest = request,
                    onSubmit = { content ->
                        viewModel.submitHelpProposal(content, request)
                    },
                    onCancel = { viewModel.selectHelpRequest(null) }
                )
            }
        }
    }
}