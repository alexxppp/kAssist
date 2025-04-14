package dev.alexpace.kassist.ui.shared.pages.home

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.ui.supporter.navigation.screens.SupporterScreen
import dev.alexpace.kassist.ui.victim.navigation.screens.VictimScreen

class HomePageViewModel : ViewModel() {
    fun navigateToVictimScreen(navigator: Navigator) {
        navigator.push(VictimScreen())
    }

    fun navigateToSupporterScreen(navigator: Navigator) {
        navigator.push(SupporterScreen())
    }
}