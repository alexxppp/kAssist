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
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import org.koin.compose.koinInject

@Composable
fun ProposalInfoPage(proposalId: String) {

    val helpProposalRepository = koinInject<HelpProposalRepository>()
    val helpRequestRepository = koinInject<HelpRequestRepository>()
    val viewModel: ProposalInfoViewModel = viewModel {
        ProposalInfoViewModel(helpProposalRepository, helpRequestRepository, proposalId)
    }
    val helpProposal by viewModel.helpProposal.collectAsState()
    val helpRequest by viewModel.helpRequest.collectAsState()

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
            if (helpProposal == null || helpRequest == null) {
                Text("Loading...")
            } else {
                Text(
                    text = "Proposal from: ${helpProposal!!.supporterId}",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Content: ${helpProposal!!.content}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Status: ${helpProposal!!.status.name}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = when (helpProposal!!.status) {
                            RequestStatusTypes.Pending -> Color(0xFF666666)
                            RequestStatusTypes.Accepted -> Color(0xFF4CAF50)
                            RequestStatusTypes.Declined -> Color(0xFFE57373)
                            else -> Color(0xFF333333)
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
                if (helpProposal!!.status == RequestStatusTypes.Pending) {
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
