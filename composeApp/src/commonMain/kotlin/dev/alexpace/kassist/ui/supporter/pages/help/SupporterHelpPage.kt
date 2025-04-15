import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
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
    val navigator = LocalNavigator.currentOrThrow
    val helpRequestRepository = koinInject<HelpRequestRepositoryImpl>()
    val helpProposalRepository = koinInject<HelpProposalRepositoryImpl>()
    val userId = Firebase.auth.currentUser?.uid ?: return // Handle null user

    val viewModel: SupporterHelpPageViewModel = viewModel {
        SupporterHelpPageViewModel(helpRequestRepository, helpProposalRepository, userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Help Requests",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Color(0xFF4A90E2),
                modifier = Modifier.fillMaxWidth()
            )
        },
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