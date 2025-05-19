package dev.alexpace.kassist.ui.admin.pages.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.repositories.AdminPendingDataRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.admin.components.HelpRequestCard
import org.koin.compose.koinInject

@Composable
fun DashboardPage() {
    // Dependencies
    val adminPendingDataRepository = koinInject<AdminPendingDataRepository>()
    val userRepository = koinInject<UserRepository>()

    // ViewModel
    val viewModel = viewModel {
        DashboardPageViewModel(userRepository, adminPendingDataRepository)
    }

    // State
    val state by viewModel.dashboardPageState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(24.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF1E88E5)
                )
            }
            state.user == null -> {
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    backgroundColor = Color.White
                ) {
                    Text(
                        text = "Loading user data...",
                        modifier = Modifier.padding(24.dp),
                        fontSize = 18.sp,
                        color = Color(0xFF666666),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            state.user?.naturalDisaster == null -> {
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    backgroundColor = Color.White
                ) {
                    Text(
                        text = "No disaster assigned",
                        modifier = Modifier.padding(24.dp),
                        fontSize = 18.sp,
                        color = Color(0xFF666666),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Dashboard: ${state.user!!.naturalDisaster?.name ?: "Unknown"}",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E88E5)
                            )
                            Text(
                                text = "Admin Control Center",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF666666)
                            )
                        }
                    }

                    // Help Requests Section
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(12.dp)),
                        backgroundColor = Color.White
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Pending Help Requests",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF333333)
                            )
                            if (state.helpRequests.isEmpty()) {
                                Text(
                                    text = "No pending help requests",
                                    fontSize = 16.sp,
                                    color = Color(0xFF666666),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.heightIn(max = 600.dp).padding(bottom = 50.dp)
                                ) {
                                    items(state.helpRequests, key = { it.id }) { request ->
                                        HelpRequestCard(request)
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
