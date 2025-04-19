package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.ui.shared.pages.welcome.WelcomePage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.firstOrNull
import org.koin.compose.koinInject

class WelcomeScreen : Screen {
    @Composable
    override fun Content() {
        // Declaring non-nullable navigator
        val navigator = LocalNavigator.currentOrThrow
        val userRepository = koinInject<UserRepository>()

        var user by remember { mutableStateOf<User?>(null) }
        var isLoading by remember { mutableStateOf(false) }

        /* TODO: Implement
        LaunchedEffect(Unit) {
            val userId = Firebase.auth.currentUser?.uid
            if (userId != null) {
                try {
                    user = userRepository.getById(userId).firstOrNull()
                    if (user == null) {
                        Firebase.auth.signOut() // Clear local session
                    }
                } catch (e: Exception) {
                    println("Error fetching user: ${e.message}")
                }
                isLoading = false
                user?.let { navigator.replaceAll(HomeScreen()) }
            } else {
                isLoading = false
            }
        }
        */

        WelcomePage(navigator, isLoading)
    }
}