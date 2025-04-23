package dev.alexpace.kassist.ui.victim.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import dev.alexpace.kassist.data.repositoriesImpl.HelpProposalRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.ui.victim.components.proposals.ProposalList
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.compose.koinInject

@Composable
fun VictimHomePage() {
    val helpProposalRepository = koinInject<HelpProposalRepositoryImpl>()
    val helpRequestRepository = koinInject<HelpRequestRepositoryImpl>()
    val currentUserId = Firebase.auth.currentUser!!.uid

    val viewModel: VictimHomePageViewModel = viewModel {
        VictimHomePageViewModel(
            helpProposalRepository,
            helpRequestRepository,
            currentUserId
        )
    }

    val helpProposals by viewModel.helpProposals.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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
            Text(
                text = "Your Help Proposals",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (isLoading) {
                Text("Loading...", modifier = Modifier.fillMaxSize())
            } else {
                if (helpProposals.isEmpty()) {
                    Text(
                        text = "No proposals yet.",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF666666)
                        )
                    )
                } else {
                    ProposalList(helpProposals)
                }
            }
        }
    }
}