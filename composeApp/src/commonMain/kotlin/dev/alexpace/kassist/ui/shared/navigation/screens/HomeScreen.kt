package dev.alexpace.kassist.ui.shared.navigation.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.alexpace.kassist.data.repositoriesImpl.UserRepositoryImpl
import dev.alexpace.kassist.data.servicesImpl.NaturalDisasterApiServiceImpl
import dev.alexpace.kassist.ui.shared.pages.home.HomePage
import org.koin.compose.koinInject

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        // Declaring non-nullable navigator
        val navigator = LocalNavigator.currentOrThrow
        val naturalDisasterApiService = koinInject<NaturalDisasterApiServiceImpl>()
        val userRepository = koinInject<UserRepositoryImpl>()

        HomePage(navigator, naturalDisasterApiService)
    }
}