package dev.alexpace.kassist.ui.admin.pages.requestReview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.models.enums.help.NeedLevelTypes
import dev.alexpace.kassist.domain.repositories.AdminPendingDataRepository
import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.services.GeoapifyApiService
import dev.alexpace.kassist.domain.services.NeedLevelSuggestionService
import dev.alexpace.kassist.ui.admin.components.HelpItemDisplay
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
    val needLevelSuggestionService = koinInject<NeedLevelSuggestionService>()

    // ViewModel
    val viewModel = viewModel {
        RequestReviewPageViewModel(
            adminPendingDataRepository,
            userRepository,
            geoapifyApiService,
            naturalDisasterRepository,
            needLevelSuggestionService
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
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFE6F0FA), Color.White)
                )
            )
            .padding(16.dp)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF4A90E2)
            )
        } else if (state.helpRequest == null) {
            Text(
                text = "No request selected",
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Header
                Text(
                    text = "Review Help Request",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                // Request Info Section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Request Information",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = "Request ID: ${state.helpRequest!!.id}",
                        fontSize = 16.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = "Victim: ${state.helpRequest!!.victimName} (ID: ${state.helpRequest!!.victimId})",
                        fontSize = 16.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = "Disaster ID: ${helpRequest.disasterId}",
                        fontSize = 16.sp,
                        color = Color(0xFF666666)
                    )
                }

                // Divider
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFCCCCCC),
                    thickness = 1.dp
                )

                // Location Section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Location",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Address: ${state.helpRequest!!.address}",
                            fontSize = 16.sp,
                            color = Color(0xFF666666)
                        )
                        state.isAddressValid?.let { isValid ->
                            Icon(
                                imageVector = if (isValid) Icons.Filled.Check else Icons.Filled.Close,
                                contentDescription = if (isValid) "Address Valid" else "Address Invalid",
                                tint = if (isValid) Color(0xFF4CAF50) else Color(0xFFE63946),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Button(
                        onClick = { viewModel.getAddressConfidenceScore() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4A90E2))
                    ) {
                        Text(
                            text = "Validate Address",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Divider
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFCCCCCC),
                    thickness = 1.dp
                )

                // Description Section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Description",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = state.helpRequest!!.description ?: "N/A",
                        fontSize = 16.sp,
                        color = Color(0xFF666666)
                    )
                }

                // Divider
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFCCCCCC),
                    thickness = 1.dp
                )

                // Items Section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    var expanded by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Items (${state.helpRequest!!.items.size})",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = if (expanded) "Hide" else "Show",
                            fontSize = 14.sp,
                            color = Color(0xFF4A90E2),
                            modifier = Modifier.semantics { contentDescription = if (expanded) "Hide items" else "Show items" }
                        )
                    }
                    if (expanded && state.helpRequest!!.items.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.helpRequest!!.items.filterNotNull()) { item ->
                                HelpItemDisplay(item)
                            }
                        }
                    }
                }

                // Divider
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFCCCCCC),
                    thickness = 1.dp
                )

                // Status Section
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Need level",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = "Please assign the need level for this request",
                        fontSize = 16.sp,
                        color = Color(0xFFFFA500)
                    )
                    var needLevelExpanded by remember { mutableStateOf(false) }
                    Box {
                        OutlinedTextField(
                            value = state.selectedNeedLevel?.name ?: "Select Need Level",
                            onValueChange = { },
                            modifier = Modifier
                                .fillMaxWidth(),
                            readOnly = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color(0xFF333333)
                            ),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                backgroundColor = Color(0xFFF5F7FA),
                                focusedBorderColor = Color(0xFF4A90E2),
                                unfocusedBorderColor = Color(0xFFCCCCCC),
                                cursorColor = Color(0xFF4A90E2)
                            ),
                            trailingIcon = {
                                IconButton(onClick = { needLevelExpanded = !needLevelExpanded }) {
                                    Icon(
                                        imageVector = if (needLevelExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                                        contentDescription = "Toggle need level dropdown",
                                        tint = Color(0xFF4A90E2)
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
                                            color = Color(0xFF333333)
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Button(
                        onClick = { viewModel.suggestNeedLevel() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .semantics { contentDescription = "Suggest need level" },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4A90E2))
                    ) {
                        Text(
                            text = "Suggest Need Level",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    state.suggestedNeedLevel?.let { needLevel ->
                        Text(
                            text = "Suggested Need Level: $needLevel",
                            fontSize = 16.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }

                // Error Message
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = Color(0xFFE63946),
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.saveChangesAndAccept()
                            navigator.pop()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4A90E2))
                    ) {
                        Text(
                            text = "Accept",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.rejectRequest()
                            navigator.pop()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE63946))
                    ) {
                        Text(
                            text = "Reject",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}