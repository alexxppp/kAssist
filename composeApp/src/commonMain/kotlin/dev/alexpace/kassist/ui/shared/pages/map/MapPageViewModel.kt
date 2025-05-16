package dev.alexpace.kassist.ui.shared.pages.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.enums.user.UserType
import dev.alexpace.kassist.domain.models.classes.map.MapMarker
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.repositories.UsersLocationRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MapPageViewModel(
    private val usersLocationRepository: UsersLocationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // Values
    private val currentUserId = Firebase.auth.currentUser?.uid
    // TODO: Handle more nicely
        ?: throw Exception("User not authenticated")

    // State Flows
    private val _markers = MutableStateFlow<List<MapMarker>>(emptyList())
    val markers = _markers.asStateFlow()

    private val _initialLat = MutableStateFlow<Double?>(null)
    val initialLat = _initialLat.asStateFlow()

    private val _initialLon = MutableStateFlow<Double?>(null)
    val initialLon = _initialLon.asStateFlow()

    init {
        fetchCurrentUserAndMarkers()
    }

    private fun fetchCurrentUserAndMarkers() {
        viewModelScope.launch {
            val currentUser = userRepository.getById(currentUserId).firstOrNull()
            if (currentUser != null) {
                currentUser.naturalDisaster?.id?.let { disasterId ->
                    // Set initial coordinates once
                    if (_initialLat.value == null) {
                        _initialLat.value = currentUser.naturalDisaster.coordinates.latitude
                        _initialLon.value = currentUser.naturalDisaster.coordinates.longitude

                        println("" + _initialLat.value + " HEREEE " + _initialLon.value)
                    }
                    usersLocationRepository.getAllByDisaster(disasterId)
                        .collectLatest { userLocations ->
                            val mapMarkers = userLocations.mapIndexed { index, userLocation ->
                                val color = when (userLocation.user.type) {
                                    UserType.Supporter -> 120f // Green
                                    UserType.Admin -> 60f // Yellow
                                    else -> null
                                }
                                MapMarker(
                                    title = userLocation.user.name,
                                    lat = userLocation.latitude,
                                    lon = userLocation.longitude,
                                    color = color
                                )
                            }
                            _markers.value = mapMarkers
                        }
                } ?: run {
                    _initialLat.value = 0.0
                    _initialLon.value = 0.0
                }
            }
        }
    }

    fun refreshMarkers() {
        fetchCurrentUserAndMarkers()
    }
}