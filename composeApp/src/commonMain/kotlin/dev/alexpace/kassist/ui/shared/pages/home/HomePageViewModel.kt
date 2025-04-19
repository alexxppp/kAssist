package dev.alexpace.kassist.ui.shared.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.domain.models.shared.naturalDisaster.NaturalDisaster
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import dev.alexpace.kassist.ui.supporter.navigation.screens.SupporterScreen
import dev.alexpace.kassist.ui.victim.navigation.screens.VictimScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomePageViewModel(
    val naturalDisasterApiService: NaturalDisasterApiService
) : ViewModel() {

    private val _naturalDisasters = MutableStateFlow<List<NaturalDisaster>>(emptyList())
    val naturalDisasters = _naturalDisasters.asStateFlow()

    init {
        fetchNaturalDisasters()
    }

    private fun fetchNaturalDisasters() {
        viewModelScope.launch {
            try {
                val disasters = naturalDisasterApiService.getNaturalDisasters()
                println(disasters)
                _naturalDisasters.value = disasters.features.map { it.properties }
            } catch (e: Exception) {
                // Handle error (e.g., log it or update UI with error state)
                _naturalDisasters.value = emptyList()
            }
        }
    }

    fun navigateToVictimScreen(navigator: Navigator) {
        navigator.push(VictimScreen())
    }

    fun navigateToSupporterScreen(navigator: Navigator) {
        navigator.push(SupporterScreen())
    }
}