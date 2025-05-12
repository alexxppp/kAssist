package dev.alexpace.kassist.ui.victim.pages.proposalInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import org.koin.compose.koinInject

@Composable
fun ProposalInfoPage(proposal: HelpProposal) {

    // DI
    val helpProposalRepository = koinInject<HelpProposalRepository>()
    val helpRequestRepository = koinInject<HelpRequestRepository>()
    val userRepository = koinInject<UserRepository>()
    val liveChatRepository = koinInject<LiveChatRepository>()

    // ViewModel
    val viewModel: ProposalInfoViewModel = viewModel {
        ProposalInfoViewModel(helpProposalRepository, helpRequestRepository, userRepository, liveChatRepository, proposal)
    }

    val helpRequest by viewModel.helpRequest.collectAsState()
    val supporter by viewModel.supporter.collectAsState()

    // UI
    Box(
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                helpRequest == null -> {
                    Text("Loading...")
                }
                else -> {
                    Text(
                        text = "Proposal from: ${supporter?.name ?: "Unknown"}",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Content: ${proposal.content}",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Status: ${proposal.status.name}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = when (proposal.status) {
                                RequestStatusTypes.Pending -> Color(0xFF666666)
                                RequestStatusTypes.Accepted -> Color(0xFF4CAF50)
                                RequestStatusTypes.Declined -> Color(0xFFE57373)
                            }
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Help Request: ${helpRequest!!.description}",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    if (proposal.status == RequestStatusTypes.Pending) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Button(
                                onClick = { viewModel.acceptProposal() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF4CAF50),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Accept")
                            }
                            Button(
                                onClick = { viewModel.declineProposal() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFE57373),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Decline")
                            }
                        }
                    }
                }
            }
        }
    }
}