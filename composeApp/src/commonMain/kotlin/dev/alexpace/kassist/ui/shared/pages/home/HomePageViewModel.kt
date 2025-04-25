package dev.alexpace.kassist.ui.shared.pages.home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.domain.models.enums.UserType
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.models.shared.naturalDisaster.NaturalDisaster
import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import dev.alexpace.kassist.ui.shared.navigation.screens.WelcomeScreen
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
    private val naturalDisasterRepository: NaturalDisasterRepository,
    private val userRepository: UserRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val userId = Firebase.auth.currentUser?.uid

    private val _naturalDisasters = MutableStateFlow<List<NaturalDisaster>>(emptyList())
    val naturalDisasters = _naturalDisasters.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchNaturalDisasters()
        fetchUser()
    }

    private fun fetchNaturalDisasters() {
        viewModelScope.launch {
            try {
                val disasters = naturalDisasterApiService.getNaturalDisasters()
                val filteredDisasters = disasters.features
                    .map { it.properties }
                    .filter {
                        (it.alertLevel == "Orange" || it.alertLevel == "Red") &&
                                it.type != "DR"
                    }
                _naturalDisasters.value = filteredDisasters
                naturalDisasterRepository.addAll(filteredDisasters)
            } catch (e: Exception) {
                println("Error fetching disasters: ${e.message}")
                _naturalDisasters.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun fetchUser() {
        userId?.let { uid ->
            viewModelScope.launch {
                try {
                    _user.value = userRepository.getById(uid).firstOrNull()
                } catch (e: Exception) {
                    println(userId)
                    println("Error fetching user: ${e.message}")
                    _user.value = null
                }
            }
        }
    }

    private fun registerUserAs(userId: String?, userType: UserType, disaster: NaturalDisaster) {
        userId?.let { uid ->
            viewModelScope.launch {
                try {
                    val user = userRepository.getById(uid).firstOrNull()
                    if (user != null) {
                        val updatedUser = user.copy(type = userType, naturalDisaster = disaster)
                        userRepository.update(updatedUser)
                        _user.value = updatedUser
                    }
                } catch (e: Exception) {
                    println("Error registering user: ${e.message}")
                }
            }
        } ?: println("No authenticated user found")
    }

    fun navigateToVictimScreen(navigator: Navigator, disaster: NaturalDisaster) {
        registerUserAs(userId, UserType.Victim, disaster)
        navigator.push(VictimScreen())
    }

    fun navigateToSupporterScreen(navigator: Navigator, disaster: NaturalDisaster) {
        registerUserAs(userId, UserType.Supporter, disaster)
        navigator.push(SupporterScreen())
    }
}