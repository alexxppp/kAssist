package dev.alexpace.kassist.ui.victim.pages.help

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.victim.components.requests.HelpRequestItem
import org.koin.compose.koinInject

@Composable
fun VictimHelpPage() {
    // Dependency Injection
    val helpRequestRepository = koinInject<HelpRequestRepository>()
    val userRepository = koinInject<UserRepository>()

    // ViewModel
    val viewModel: VictimHelpPageViewModel =
        viewModel { VictimHelpPageViewModel(helpRequestRepository, userRepository) }

    val user by viewModel.user.collectAsState()
    val address by viewModel.address.collectAsState()
    val description by viewModel.description.collectAsState()
    val helpItems by viewModel.helpItems.collectAsState()

    // UI
    if (user != null) {
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
                // Header
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Request Help",
                        tint = Color(0xFF4A90E2),
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                    Text(
                        text = "Request Help",
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enter details to request assistance",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF666666)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Form Container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .shadow(4.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(24.dp, bottom = 55.dp, top = 20.dp, end = 24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Address Field
                        OutlinedTextField(
                            value = address,
                            onValueChange = { viewModel.updateAddress(it) },
                            label = { Text("Address") },
                            modifier = Modifier
                                .fillMaxWidth(),
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        // Description Field
                        OutlinedTextField(
                            value = description,
                            onValueChange = { viewModel.updateDescription(it) },
                            label = { Text("Description") },
                            modifier = Modifier
                                .fillMaxWidth(),
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        // Add Item Button
                        Button(
                            onClick = { viewModel.addHelpItem() },
                            enabled = helpItems.size < 5,
                            modifier = Modifier
                                .size(48.dp)
                                .semantics { contentDescription = "Add item to request" },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF4A90E2),
                                contentColor = Color.White
                            )
                        ) {
                            Text("+", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }

                        // Help Items
                        helpItems.forEachIndexed { index, helpItem ->
                            HelpRequestItem(
                                helpItem = helpItem,
                                onUpdate = { updatedItem ->
                                    viewModel.updateHelpItem(index, updatedItem)
                                },
                                onRemove = { viewModel.removeHelpItem(index) }
                            )
                        }

                        // Submit Button
                        Button(
                            onClick = { viewModel.submitHelpRequest() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .semantics { contentDescription = "Submit help request" }
                                .padding(end = if (helpItems.isEmpty()) 0.dp else 50.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF4A90E2),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Submit Request",
                                style = TextStyle(
                                    fontSize = 18.sp,
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