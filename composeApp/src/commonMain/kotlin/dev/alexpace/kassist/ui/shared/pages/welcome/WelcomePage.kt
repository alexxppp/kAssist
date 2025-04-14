package dev.alexpace.kassist.ui.shared.pages.welcome

import androidx.compose.runtime.*

@Composable
fun WelcomePage(
    onGetStartedClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    WelcomePageContent(onGetStartedClick, onLoginClick)
}