package dev.alexpace.kassist.ui.shared.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.data.utils.helpers.LocationServiceProvider
import dev.alexpace.kassist.data.utils.helpers.areCoordinatesWithinRadius
import dev.alexpace.kassist.domain.models.enums.user.UserType
import dev.alexpace.kassist.domain.models.classes.map.Coordinates
import dev.alexpace.kassist.domain.models.classes.app.NaturalDisaster
import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.repositories.UsersLocationRepository
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import dev.alexpace.kassist.ui.admin.navigation.screens.AdminScreen
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
    private val userLocationRepository: UsersLocationRepository
) : ViewModel() {

    // Values
    private val userId = Firebase.auth.currentUser?.uid
        ?: throw Exception("User not authenticated")

    private val r = 500.0 // Radius in km
    private var unfilteredDisasters: List<NaturalDisaster> = emptyList()

    // State Flow
    private val _state = MutableStateFlow(HomePageState())
    val state = _state.asStateFlow()

    // Init
    init {
        fetchNaturalDisasters()
        fetchUser()
    }

    // Functions

    /**
     * Toggles the filter on or off for natural disasters by radius
     */
    fun toggleFilterNaturalDisastersByRadius() {
        viewModelScope.launch {
            val currentState = _state.value
            val newFilterActive = !currentState.isFilterActive
            _state.value = currentState.copy(isFilterActive = newFilterActive)
            if (newFilterActive) {
                val userCoordinates = LocationServiceProvider.getLocationService()
                    .getCurrentLocation()

                if (userCoordinates != null) {
                    println("${userCoordinates.latitude}, ${userCoordinates.longitude}")
                    _state.value = _state.value.copy(
                        naturalDisasters = unfilteredDisasters.filter { disaster ->
                            areCoordinatesWithinRadius(
                                userCoordinates,
                                disaster.coordinates,
                                r
                            )
                        }
                    )
                } else {
                    _state.value = _state.value.copy(isFilterActive = false)
                }
            } else {
                _state.value = _state.value.copy(naturalDisasters = unfilteredDisasters)
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
                unfilteredDisasters = filteredDisasters
                _state.value = _state.value.copy(
                    naturalDisasters = filteredDisasters,
                    isLoading = false
                )
                naturalDisasterRepository.addAll(filteredDisasters)
            } catch (e: Exception) {
                println("Error fetching disasters: ${e.message}")
                _state.value = _state.value.copy(
                    naturalDisasters = emptyList(),
                    isLoading = false
                )
                unfilteredDisasters = emptyList()
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
                    _state.value = _state.value.copy(user = user)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(user = null)
            }
        }
    }

    /**
     * Registers user as victim, supporter, or admin
     */
    private suspend fun registerUserAs(
        userId: String?,
        userType: UserType,
        disaster: NaturalDisaster
    ) {
        userId?.let { uid ->
            try {
                val user = userRepository.getById(uid).firstOrNull()
                if (user != null) {
                    val updatedUser = user.copy(type = userType, naturalDisaster = disaster)
                    userRepository.update(updatedUser)
                    _state.value = _state.value.copy(user = updatedUser)
                }
            } catch (e: Exception) {
                println("Error registering user: ${e.message}")
            }
        } ?: println("No authenticated user found")
    }

    // Navigation functions with loading state
    fun navigateToAdminScreen(navigator: Navigator, disaster: NaturalDisaster) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isNavigating = true)
            try {
                registerUserAs(userId, UserType.Admin, disaster)
                navigator.push(AdminScreen())
            } finally {
                _state.value = _state.value.copy(isNavigating = false)
            }
        }
    }

    fun navigateToVictimScreen(navigator: Navigator, disaster: NaturalDisaster) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isNavigating = true)
            try {
                registerUserAs(userId, UserType.Victim, disaster)
                navigator.push(VictimScreen())
            } finally {
                _state.value = _state.value.copy(isNavigating = false)
            }
        }
    }

    fun navigateToSupporterScreen(navigator: Navigator, disaster: NaturalDisaster) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isNavigating = true)
            try {
                registerUserAs(userId, UserType.Supporter, disaster)
                navigator.push(SupporterScreen())
            } finally {
                _state.value = _state.value.copy(isNavigating = false)
            }
        }
    }

    /*
    fun populateDb() {
        viewModelScope.launch {
            userLocationRepository.populateUserLocations()
        }
    }

    fun populateUserDb() {
        viewModelScope.launch {
            userRepository.populateUsers()
        }
    }
    */
}