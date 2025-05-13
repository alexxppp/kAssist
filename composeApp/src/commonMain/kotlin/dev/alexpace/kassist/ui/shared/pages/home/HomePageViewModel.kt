package dev.alexpace.kassist.ui.shared.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import dev.alexpace.kassist.data.utils.helpers.LocationServiceProvider
import dev.alexpace.kassist.data.utils.helpers.areCoordinatesWithinRadius
import dev.alexpace.kassist.domain.models.enums.UserType
import dev.alexpace.kassist.domain.models.shared.Coordinates
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.models.shared.NaturalDisaster
import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
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
    private val naturalDisasterRepository: NaturalDisasterRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // Values
    private val userId = Firebase.auth.currentUser?.uid
        ?: throw Exception("User not authenticated")

    private val r = 500.0 // Radius in km
    private var unfilteredDisasters: List<NaturalDisaster> = emptyList()

    // State Flows
    private val _naturalDisasters = MutableStateFlow<List<NaturalDisaster>>(emptyList())
    val naturalDisasters = _naturalDisasters.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _isFilterActive = MutableStateFlow(false)
    val isFilterActive = _isFilterActive.asStateFlow()

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
            _isFilterActive.value = !_isFilterActive.value
            if (_isFilterActive.value) {
                val userCoordinates = LocationServiceProvider.getLocationService()
                    .getCurrentLocation()
                if (userCoordinates != null) {
                    println("${userCoordinates.latitude}, ${userCoordinates.longitude}")
                    _naturalDisasters.value = unfilteredDisasters.filter { disaster ->
                        areCoordinatesWithinRadius(
                            userCoordinates,
                            disaster.coordinates,
                            r
                        )
                    }
                } else {
                    _isFilterActive.value = false
                }
            } else {
                _naturalDisasters.value = unfilteredDisasters
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
                _naturalDisasters.value = filteredDisasters
                naturalDisasterRepository.addAll(filteredDisasters)
            } catch (e: Exception) {
                println("Error fetching disasters: ${e.message}")
                _naturalDisasters.value = emptyList()
                unfilteredDisasters = emptyList()
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