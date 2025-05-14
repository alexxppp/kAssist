package dev.alexpace.kassist.ui.shared.pages.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import dev.alexpace.kassist.ui.shared.components.app.InputField
import org.koin.compose.koinInject

@Composable
fun RegistrationPage() {

    val navigator = LocalNavigator.currentOrThrow

    // DI
    val authService = koinInject<FirebaseAuthService>()
    val userRepository = koinInject<UserRepository>()

    // ViewModel
    val viewModel: RegistrationPageViewModel =
        viewModel { RegistrationPageViewModel(authService, userRepository, navigator) }

    val state by viewModel.state.collectAsState()

    // UI
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 64.dp)
            ) {
                Text(
                    text = "Register",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Sign up to access kAssist",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF666666)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Middle Section: Input Fields
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                InputField(
                    value = state.name,
                    onValueChange = { viewModel.updateName(it) },
                    placeholder = "Name",
                    keyboardType = KeyboardType.Text
                )
                InputField(
                    value = state.phoneNumber,
                    onValueChange = { viewModel.updatePhoneNumber(it) },
                    placeholder = "Phone number",
                    keyboardType = KeyboardType.Phone
                )
                InputField(
                    value = state.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    placeholder = "Email",
                    keyboardType = KeyboardType.Email
                )
                InputField(
                    value = state.password,
                    onValueChange = { viewModel.updatePassword(it) },
                    placeholder = "Password",
                    keyboardType = KeyboardType.Password,
                    isPassword = true
                )
                InputField(
                    value = state.confirmPassword,
                    onValueChange = { viewModel.updateConfirmPassword(it) },
                    placeholder = "Confirm password",
                    keyboardType = KeyboardType.Password,
                    isPassword = true
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Switch(
                        checked = state.isAdmin,
                        onCheckedChange = { viewModel.updateIsAdmin(it) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Register as Admin")
                }
                AnimatedVisibility(
                    visible = state.errorMessage != null,
                    enter = slideInVertically() + fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = state.errorMessage ?: "",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFE63946)
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Bottom Section: Buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (state.isRegistrationButtonEnabled && !state.isLoading) Color(0xFF4A90E2)
                            else Color(0xFFD3D3D3)
                        )
                        .clickable(enabled = state.isRegistrationButtonEnabled && !state.isLoading) {
                            viewModel.register()
                        }
                        .padding(horizontal = 32.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "Register",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Forgot Password?",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4A90E2)
                    ),
                    modifier = Modifier
                        .clickable { /* TODO: Handle forgot password */ }
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}