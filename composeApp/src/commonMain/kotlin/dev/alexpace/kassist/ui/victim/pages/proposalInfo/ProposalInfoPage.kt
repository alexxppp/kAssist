package dev.alexpace.kassist.ui.victim.pages.proposalInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.models.enums.help.RequestStatusTypes
import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.LiveChatRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import org.koin.compose.koinInject

@Composable
fun ProposalInfoPage(proposal: HelpProposal) {
    // Values
    val navigator = LocalNavigator.currentOrThrow

    // DI
    val helpProposalRepository = koinInject<HelpProposalRepository>()
    val helpRequestRepository = koinInject<HelpRequestRepository>()
    val userRepository = koinInject<UserRepository>()
    val liveChatRepository = koinInject<LiveChatRepository>()

    // ViewModel
    val viewModel: ProposalInfoViewModel = viewModel {
        ProposalInfoViewModel(
            navigator,
            helpProposalRepository,
            helpRequestRepository,
            userRepository,
            liveChatRepository,
            proposal
        )
    }

    val helpRequest by viewModel.helpRequest.collectAsState()
    val supporter by viewModel.supporter.collectAsState()

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF0F4F8), Color.White)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Proposal Details",
                    tint = Color(0xFF4A90E2),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Text(
                    text = "Proposal Details",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Review the help proposal",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF666666)
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .shadow(4.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                when {
                    helpRequest == null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Loading...",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF666666)
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Supporter Name
                            Text(
                                text = "Proposal from: ${supporter?.name ?: "Unknown"}",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF333333)
                                )
                            )

                            // Proposal Content
                            Text(
                                text = "Content: ${proposal.content}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF333333)
                                )
                            )

                            // Estimated Time
                            Text(
                                text = "Estimated time: ${proposal.requiredTime}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF333333)
                                )
                            )

                            // Fulfilled Items
                            if (!proposal.fulfilledItems.isNullOrEmpty()) {
                                Divider(
                                    color = Color(0xFFCCCCCC),
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                Text(
                                    text = "Offered Items:",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF333333)
                                    )
                                )
                                proposal.fulfilledItems.forEach { item ->
                                    if (item != null) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 8.dp, top = 4.dp)
                                        ) {
                                            Text(
                                                text = item.name,
                                                style = TextStyle(
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF333333)
                                                )
                                            )
                                            Text(
                                                text = "${item.neededQuantity} ${item.unit.name}",
                                                style = TextStyle(
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Color(0xFF666666)
                                                )
                                            )
                                            if (item.details != null) {
                                                Text(
                                                    text = item.details,
                                                    style = TextStyle(
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Normal,
                                                        color = Color(0xFF888888)
                                                    )
                                                )
                                            }
                                        }
                                    } else {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 8.dp, top = 4.dp)
                                        ) {
                                            Text(
                                                text = "Sorry, we could not display any item",
                                                style = TextStyle(
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF333333)
                                                )
                                            )
                                        }
                                    }
                                }
                            }

                            // Status
                            Divider(
                                color = Color(0xFFCCCCCC),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                text = "Status: ${proposal.status.name}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = when (proposal.status) {
                                        RequestStatusTypes.Pending -> Color(0xFF666666)
                                        RequestStatusTypes.Accepted -> Color(0xFF4CAF50)
                                        RequestStatusTypes.Declined -> Color(0xFFE57373)
                                        else -> Color(0xFF666666)
                                    }
                                )
                            )

                            // Help Request Description
                            Text(
                                text = "Help Request: ${helpRequest!!.description ?: "No description"}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF666666)
                                )
                            )

                            // Action Buttons
                            if (proposal.status == RequestStatusTypes.Pending) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(
                                        onClick = { viewModel.acceptProposal() },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                            .clip(RoundedCornerShape(12.dp)),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(0xFF4CAF50),
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text(
                                            text = "Accept",
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                    Button(
                                        onClick = { viewModel.declineProposal() },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                            .clip(RoundedCornerShape(12.dp)),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(0xFFE57373),
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text(
                                            text = "Decline",
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}