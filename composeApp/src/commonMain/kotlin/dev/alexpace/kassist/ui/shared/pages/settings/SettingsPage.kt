package dev.alexpace.kassist.ui.shared.pages.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import org.koin.compose.koinInject

@Composable
fun SettingsPage() {
    val navigator = LocalNavigator.currentOrThrow

    // DI
    val userRepository = koinInject<UserRepository>()
    val helpRequestRepository = koinInject<HelpRequestRepository>()
    val helpProposalRepository = koinInject<HelpProposalRepository>()

    // ViewModel
    val viewModel = viewModel {
        SettingsPageViewModel(
            userRepository,
            helpRequestRepository,
            helpProposalRepository,
            navigator
        )
    }

    val user = viewModel.user.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val error = viewModel.error.collectAsState().value
    val helpRequests = viewModel.helpRequests.collectAsState().value
    val helpProposals = viewModel.helpProposals.collectAsState().value

    // UI
    if (isLoading) {
        Text(
            "Loading...",
            modifier = Modifier
                .fillMaxSize(),
            textAlign = TextAlign.Center
        )
    } else if (error != null) {
        Text(
            error,
            modifier = Modifier
                .fillMaxSize(),
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    } else {
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 64.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color(0xFF4A90E2), RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = user?.name ?: "User Profile",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = user?.email ?: "No email available",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF666666)
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                // Middle
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "Your Activity",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Help Requests: ${helpRequests.size}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF333333)
                        ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Help Proposals: ${helpProposals.size}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF333333)
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF333333))
                            .clickable { viewModel.logOutFromCurrentDisaster() }
                            .padding(horizontal = 32.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "Log Out from current Disaster",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }

                // Bottom
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF333333))
                            .clickable { viewModel.logOut() }
                            .padding(horizontal = 32.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "Log Out",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}