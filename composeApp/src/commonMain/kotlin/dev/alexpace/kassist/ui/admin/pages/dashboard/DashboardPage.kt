package dev.alexpace.kassist.ui.admin.pages.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.repositories.AdminPendingDataRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import org.koin.compose.koinInject
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.ui.admin.navigation.screens.RequestReviewScreen

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
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        when {
            state.user == null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.user?.naturalDisaster == null -> {
                Text(
                    text = "No disaster assigned",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header
                    Text(
                        text = "Dashboard: ${state.user!!.naturalDisaster?.name ?: "Unknown"}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E88E5),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Help Requests List
                    if (state.helpRequests.isEmpty()) {
                        Text(
                            text = "No help requests",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.helpRequests) { request ->
                                HelpRequestCard(request)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HelpRequestCard(request: HelpRequest) {
    val navigator = LocalNavigator.currentOrThrow.parent
        ?: throw Exception("Could not find parent navigator")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
            .clickable { navigator.push(RequestReviewScreen(request)) }
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = request.victimName,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = "Address: ${request.address}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Status: ${request.status.name}",
                fontSize = 14.sp,
                color = Color(0xFFFFA500)
            )
            Text(
                text = "Need Level: ${request.needLevel.name}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            if (request.description != null) {
                Text(
                    text = "Description: ${request.description}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}