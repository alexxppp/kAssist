package dev.alexpace.kassist.ui.shared.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import dev.alexpace.kassist.data.utils.helpers.PLATFORM
import dev.alexpace.kassist.domain.models.enums.user.UserRole
import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.repositories.UsersLocationRepository
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import dev.alexpace.kassist.ui.shared.components.nd.NaturalDisasterCard
import dev.alexpace.kassist.ui.shared.navigation.screens.SettingsScreen
import org.koin.compose.koinInject

@Composable
fun HomePage() {
    val navigator = LocalNavigator.currentOrThrow

    // DI
    val naturalDisasterApiService = koinInject<NaturalDisasterApiService>()
    val naturalDisasterRepository = koinInject<NaturalDisasterRepository>()
    val userRepository = koinInject<UserRepository>()
    val usersLocationRepository = koinInject<UsersLocationRepository>()

    // ViewModel
    val viewModel: HomePageViewModel =
        viewModel {
            HomePageViewModel(
                naturalDisasterApiService,
                naturalDisasterRepository,
                userRepository,
                usersLocationRepository
            )
        }
    val state = viewModel.state.collectAsState().value

    // UI
    if (state.user?.role == UserRole.Banned) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your account has been banned. Contact us if you think it was a mistake",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666)
                )
            )
        }
    } else {
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = Color(0xFF4A90E2),
                    strokeWidth = 4.dp
                )
            }
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
                    Row {
                        Button(
                            onClick = { viewModel.populateUserDb() },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text("Populate user Db")
                        }
                    }
                    Row {
                        Button(
                            onClick = { viewModel.populateDb() },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text("Populate userLocation Db")
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "kAssist Home",
                                style = TextStyle(
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF333333)
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Current active disasters",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF666666)
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color(0xFF333333),
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { navigator.push(SettingsScreen()) }
                        )
                    }

                    if (PLATFORM != "Desktop" || state.user?.role != UserRole.Admin) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Checkbox(
                                checked = state.isFilterActive,
                                onCheckedChange = { viewModel.toggleFilterNaturalDisastersByRadius() },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFF4A90E2),
                                    uncheckedColor = Color(0xFF666666)
                                )
                            )
                            Text(
                                text = "Show disasters within 500km",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF333333)
                                ),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    if (state.naturalDisasters.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.naturalDisasters) { disaster ->
                                NaturalDisasterCard(
                                    disaster = disaster,
                                    user = state.user,
                                    onConfirmVictim = {
                                        viewModel.navigateToVictimScreen(
                                            navigator,
                                            disaster
                                        )
                                    },
                                    onConfirmSupporter = {
                                        viewModel.navigateToSupporterScreen(
                                            navigator,
                                            disaster
                                        )
                                    },
                                    onConfirmAdmin = {
                                        viewModel.navigateToAdminScreen(
                                            navigator,
                                            disaster
                                        )
                                    }
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "No active disasters reported",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF666666)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Navigation loading overlay
                if (state.isNavigating) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = Color(0xFF4A90E2),
                            strokeWidth = 4.dp
                        )
                    }
                }
            }
        }
    }
}