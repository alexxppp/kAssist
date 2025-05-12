package dev.alexpace.kassist.ui.victim.pages.help

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.data.repositoriesImpl.HelpRequestRepositoryImpl
import dev.alexpace.kassist.data.repositoriesImpl.UserRepositoryImpl
import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import org.koin.compose.koinInject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun VictimHelpPage() {
    // DI
    val helpRequestRepository = koinInject<HelpRequestRepositoryImpl>()
    val userRepository = koinInject<UserRepositoryImpl>()

    // ViewModel
    val viewModel: VictimHelpPageViewModel =
        viewModel { VictimHelpPageViewModel(helpRequestRepository, userRepository) }

    val user by viewModel.user.collectAsState()
    val address by viewModel.address.collectAsState()
    val description by viewModel.description.collectAsState()
    val selectedNeedLevel by viewModel.selectedNeedLevel.collectAsState()

    // UI
    if (user != null) {
        var isDropdownExpanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Request Help",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Enter details to request assistance",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF666666)
                        )
                    )
                }

                // Form Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Address
                        OutlinedTextField(
                            value = address,
                            onValueChange = { viewModel.updateAddress(it) },
                            label = { Text("Address") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)),
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color(0xFF333333)
                            ),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF4A90E2),
                                unfocusedBorderColor = Color(0xFF666666),
                                cursorColor = Color(0xFF4A90E2)
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        // Description
                        OutlinedTextField(
                            value = description,
                            onValueChange = { viewModel.updateDescription(it) },
                            label = { Text("Description") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)),
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color(0xFF333333)
                            ),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF4A90E2),
                                unfocusedBorderColor = Color(0xFF666666),
                                cursorColor = Color(0xFF4A90E2)
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        // Need Level selection
                        Text(
                            text = "Need Level",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF333333)
                            )
                        )
                        Box {
                            OutlinedTextField(
                                value = selectedNeedLevel.name,
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
                                    Icon(
                                        Icons.Default.ArrowDropDown,
                                        contentDescription = "Expand need level dropdown",
                                        tint = Color(0xFF666666),
                                        modifier = Modifier
                                            .clickable { isDropdownExpanded = !isDropdownExpanded }
                                            .semantics { contentDescription = "Toggle need level dropdown" }
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF4A90E2),
                                    unfocusedBorderColor = Color(0xFF666666),
                                    cursorColor = Color(0xFF4A90E2)
                                )
                            )
                            DropdownMenu(
                                expanded = isDropdownExpanded,
                                onDismissRequest = { isDropdownExpanded = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                NeedLevelTypes.entries.forEach { level ->
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.updateSelectedNeedLevel(level)
                                            isDropdownExpanded = false
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
                                    if (level != NeedLevelTypes.entries.last()) {
                                        Divider(color = Color(0xFFEEEEEE))
                                    }
                                }
                            }
                        }

                        // Submit button
                        Button(
                            onClick = {
                                val newHelpRequest = HelpRequest(
                                    id = Uuid.random().toString(),
                                    victimId = user!!.id,
                                    victimName = user!!.name,
                                    address = address,
                                    description = description,
                                    needLevel = selectedNeedLevel,
                                    status = RequestStatusTypes.Pending
                                )
                                viewModel.submitHelpRequest(newHelpRequest)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .shadow(2.dp, RoundedCornerShape(8.dp))
                                .semantics { contentDescription = "Submit help request" },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF4A90E2),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Submit",
                                style = TextStyle(
                                    fontSize = 16.sp,
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
}