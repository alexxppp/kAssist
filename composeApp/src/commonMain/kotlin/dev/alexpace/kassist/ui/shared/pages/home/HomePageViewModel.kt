package dev.alexpace.kassist.ui.shared.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.domain.models.enums.UserType
import dev.alexpace.kassist.domain.models.shared.naturalDisaster.NaturalDisaster
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import dev.alexpace.kassist.ui.supporter.navigation.screens.SupporterScreen
import dev.alexpace.kassist.ui.victim.navigation.screens.VictimScreen
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val naturalDisasterApiService: NaturalDisasterApiService,
    private val userRepository: UserRepository
) : ViewModel() {

    private val userId = Firebase.auth.currentUser?.uid

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
                _naturalDisasters.value = disasters.features
                    .map {
                        it.properties
                    }
                    .filter {
                        (it.alertLevel == "Orange" || it.alertLevel == "Red")
                                && it.type != "DR"
                    }
            } catch (e: Exception) {
                // Handle error (e.g., log it or update UI with error state)
                _naturalDisasters.value = emptyList()
            }
        }
    }

    private fun registerUserAs(userId: String?, userType: UserType) {
        userId?.let { uid ->
            viewModelScope.launch {
                try {
                    val user = userRepository.getById(uid).firstOrNull()
                    if (user != null) {
                        val updatedUser = user.copy(type = userType)
                        userRepository.update(updatedUser)
                    }
                } catch (e: Exception) {
                    println("Error registering user: ${e.message}")
                    e.printStackTrace()
                }
            }
        } ?: run {
            println("No authenticated user found")
        }
    }

    fun navigateToVictimScreen(navigator: Navigator) {
        registerUserAs(userId, UserType.Victim)
        navigator.push(VictimScreen())
    }

    fun navigateToSupporterScreen(navigator: Navigator) {
        registerUserAs(userId, UserType.Supporter)
        navigator.push(SupporterScreen())
    }
}