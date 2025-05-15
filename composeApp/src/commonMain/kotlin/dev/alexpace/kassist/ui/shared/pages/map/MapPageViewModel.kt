package dev.alexpace.kassist.ui.shared.pages.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.domain.models.enums.UserType
import dev.alexpace.kassist.domain.models.shared.MapMarker
import dev.alexpace.kassist.domain.repositories.UsersLocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MapPageViewModel(
    private val usersLocationRepository: UsersLocationRepository
) : ViewModel() {

    // State Flows
    private val _markers = MutableStateFlow<List<MapMarker>>(emptyList())
    val markers = _markers.asStateFlow()

    init {
        fetchMarkers()
    }

    private fun fetchMarkers() {
        viewModelScope.launch {
            usersLocationRepository.getAll().collectLatest { userLocations ->
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
        }
    }

    fun refreshMarkers() {
        fetchMarkers()
    }
}