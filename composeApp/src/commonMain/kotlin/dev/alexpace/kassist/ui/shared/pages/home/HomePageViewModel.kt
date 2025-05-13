package dev.alexpace.kassist.ui.shared.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.data.network.servicesImpl.LocationServiceImpl
import dev.alexpace.kassist.data.utils.helpers.LocationServiceProvider
import dev.alexpace.kassist.data.utils.helpers.areCoordinatesWithinRadius
import dev.alexpace.kassist.domain.models.enums.UserType
import dev.alexpace.kassist.domain.models.shared.Coordinates
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.models.shared.NaturalDisaster
import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import dev.alexpace.kassist.ui.shared.utils.controllers.SnackbarController
import dev.alexpace.kassist.ui.supporter.navigation.screens.SupporterScreen
import dev.alexpace.kassist.ui.victim.navigation.screens.VictimScreen
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val naturalDisasterApiService: NaturalDisasterApiService,
    private val naturalDisasterRepository: NaturalDisasterRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // Values
    private val userId = Firebase.auth.currentUser?.uid
    // TODO: Handle more nicely
        ?: throw Exception("User not authenticated")

    private val r = 500.0 // Radius in km

    // State Flows
    private val _naturalDisasters = MutableStateFlow<List<NaturalDisaster>>(emptyList())
    val naturalDisasters = _naturalDisasters.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    // Init
    init {
        fetchNaturalDisasters()
        fetchUser()
    }

    // Functions
    fun filterNaturalDisastersByRadius() {
        viewModelScope.launch {
            val userCoordinates = LocationServiceProvider.getLocationService()
                .getCurrentLocation()

            if (userCoordinates == null) {
                SnackbarController.showSnackbar("Cannot access user location")
                return@launch
            }

            _naturalDisasters.collect {
                _naturalDisasters.value = it.filter { disaster ->
                    areCoordinatesWithinRadius(
                        userCoordinates,
                        disaster.coordinates,
                        r
                    )
                }
            }
        }
    }

    /**
     * Fetches natural disasters from NaturalDisasterApiService
     */
    private fun fetchNaturalDisasters() {
        viewModelScope.launch {
            try {
                val disasters = naturalDisasterApiService.getNaturalDisasters()
                val filteredDisasters = disasters.features
                    .map { feature ->
                        feature.properties.copy(
                            coordinates = Coordinates(
                                latitude = feature.geometry.coordinates[1],
                                longitude = feature.geometry.coordinates[0]
                            )
                        )
                    }
                    // Filters by type and alert level
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

    /**
     * Fetches user from UserRepository
     */
    private fun fetchUser() {
        viewModelScope.launch {
            try {
                userRepository.getById(userId).collect { user ->
                    _user.value = user
                }
            } catch (e: Exception) {
                _user.value = null
            }
        }
    }

    /**
     * Registers user as victim or supporter
     */
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