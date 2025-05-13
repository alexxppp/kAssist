package dev.alexpace.kassist.ui.admin.pages.requestReview

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.repositories.AdminPendingDataRepository
import org.koin.compose.koinInject

@Composable
fun RequestReviewPage(
    helpRequest: HelpRequest,
    modifier: Modifier = Modifier
) {
    val adminPendingDataRepository = koinInject<AdminPendingDataRepository>()
    val viewModel = viewModel {
        RequestReviewPageViewModel(adminPendingDataRepository)
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
                Text(
                    text = "Address: ${state.helpRequest!!.address}",
                    fontSize = 16.sp
                )
                Text(
                    text = "Description: ${state.helpRequest!!.description ?: "N/A"}",
                    fontSize = 16.sp
                )

                // Status Dropdown
                var statusExpanded by remember { mutableStateOf(false) }
                Box {
                    OutlinedTextField(
                        value = state.selectedStatus?.name ?: "",
                        onValueChange = {},
                        label = { Text("Status") },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { statusExpanded = true },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown"
                            )
                        }
                    )
                    DropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false }
                    ) {
                        RequestStatusTypes.entries.forEach { status ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.updateStatus(status)
                                    statusExpanded = false
                                }
                            ) {
                                Text(status.name)
                            }
                        }
                    }
                }

                // Need Level Dropdown
                var needLevelExpanded by remember { mutableStateOf(false) }
                Box {
                    OutlinedTextField(
                        value = state.selectedNeedLevel?.name ?: "",
                        onValueChange = {},
                        label = { Text("Need Level") },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { needLevelExpanded = true },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown"
                            )
                        }
                    )
                    DropdownMenu(
                        expanded = needLevelExpanded,
                        onDismissRequest = { needLevelExpanded = false }
                    ) {
                        NeedLevelTypes.entries.forEach { level ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.updateNeedLevel(level)
                                    needLevelExpanded = false
                                }
                            ) {
                                Text(level.name)
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
                        onClick = { viewModel.saveChanges() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
                    ) {
                        Text("Save", color = Color.White)
                    }
                    Button(
                        onClick = { viewModel.rejectRequest() },
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