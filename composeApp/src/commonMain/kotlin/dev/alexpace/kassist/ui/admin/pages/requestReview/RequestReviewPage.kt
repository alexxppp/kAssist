package dev.alexpace.kassist.ui.admin.pages.requestReview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.repositories.AdminPendingDataRepository
import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.services.GeoapifyApiService
import org.koin.compose.koinInject

@Composable
fun RequestReviewPage(
    helpRequest: HelpRequest,
    modifier: Modifier = Modifier
) {
    // Values
    val navigator = LocalNavigator.currentOrThrow

    // DI
    val adminPendingDataRepository = koinInject<AdminPendingDataRepository>()
    val geoapifyApiService = koinInject<GeoapifyApiService>()
    val naturalDisasterRepository = koinInject<NaturalDisasterRepository>()
    val userRepository = koinInject<UserRepository>()

    // ViewModel
    val viewModel = viewModel {
        RequestReviewPageViewModel(
            adminPendingDataRepository,
            userRepository,
            geoapifyApiService,
            naturalDisasterRepository
        )
    }
    val state by viewModel.state.collectAsState()

    // Initialize ViewModel with the passed HelpRequest
    LaunchedEffect(helpRequest) {
        viewModel.initialize(helpRequest)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.helpRequest == null) {
            Text("No request selected", modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Review Help Request",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E88E5)
                )

                // Victim Info
                Text(
                    text = "Victim: ${state.helpRequest!!.victimName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                // Address with Validation
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Address: ${state.helpRequest!!.address}",
                            fontSize = 16.sp
                        )
                        // Validation Feedback Icon
                        state.isAddressValid?.let { isValid ->
                            Icon(
                                imageVector = if (isValid) Icons.Filled.Check else Icons.Filled.Close,
                                contentDescription = if (isValid) "Address Valid" else "Address Invalid",
                                tint = if (isValid) Color.Green else Color.Red,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Button(
                        onClick = { viewModel.getAddressConfidenceScore() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
                    ) {
                        Text("Validate Address", color = Color.White)
                    }
                }
                Text(
                    text = "Description: ${state.helpRequest!!.description ?: "N/A"}",
                    fontSize = 16.sp
                )

                // Need Level Dropdown
                var needLevelExpanded by remember { mutableStateOf(false) }
                Box {
                    OutlinedTextField(
                        value = state.selectedNeedLevel?.name ?: "Select an item",
                        onValueChange = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        readOnly = true,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF333333)
                        ),
                        trailingIcon = {
                            Box(
                                modifier = Modifier
                                    .clickable { needLevelExpanded = !needLevelExpanded }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = if (needLevelExpanded) "▲" else "▼",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color(0xFF333333)
                                    )
                                )
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = needLevelExpanded,
                        onDismissRequest = { needLevelExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        NeedLevelTypes.entries.forEach { level ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.updateNeedLevel(level)
                                    needLevelExpanded = false
                                }
                            ) {
                                Text(
                                    text = level.name,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xFF333333)
                                    )
                                )
                            }
                        }
                    }
                }

                // Error Message
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.saveChangesAndAccept()
                            navigator.pop()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
                    ) {
                        Text("Accept", color = Color.White)
                    }
                    Button(
                        onClick = {
                            viewModel.rejectRequest()
                            navigator.pop()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE53935))
                    ) {
                        Text("Reject", color = Color.White)
                    }
                }
            }
        }
    }
}