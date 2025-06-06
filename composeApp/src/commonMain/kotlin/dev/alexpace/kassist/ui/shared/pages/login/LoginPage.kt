package dev.alexpace.kassist.ui.shared.pages.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
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
import dev.alexpace.kassist.domain.services.FirebaseAuthService
import dev.alexpace.kassist.ui.shared.components.app.InputField
import org.koin.compose.koinInject

@Composable
fun LoginPage() {

    val navigator = LocalNavigator.currentOrThrow

    // DI
    val authService = koinInject<FirebaseAuthService>()

    // ViewModel
    val viewModel: LoginPageViewModel = viewModel { LoginPageViewModel(authService, navigator) }

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
            // Top Section: Title
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 64.dp)
            ) {
                androidx.compose.material.Text(
                    text = "Log In",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                androidx.compose.material.Text(
                    text = "Sign in to access kAssist",
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
                AnimatedVisibility(
                    visible = state.errorMessage != null,
                    enter = slideInVertically() + fadeIn(),
                    exit = fadeOut()
                ) {
                    androidx.compose.material.Text(
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
                            if (state.isLoginButtonEnabled && !state.isLoading) Color(0xFF4A90E2)
                            else Color(0xFFD3D3D3)
                        )
                        .clickable(enabled = state.isLoginButtonEnabled && !state.isLoading) {
                            viewModel.login()
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
                        androidx.compose.material.Text(
                            text = "Log In",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                androidx.compose.material.Text(
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