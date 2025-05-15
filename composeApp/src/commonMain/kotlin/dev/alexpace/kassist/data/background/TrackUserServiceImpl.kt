package dev.alexpace.kassist.data.background

import dev.alexpace.kassist.data.utils.constants.DELAY_TRACK_LOCATION
import dev.alexpace.kassist.data.utils.helpers.LocationServiceProvider
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.models.shared.UserLocation
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.repositories.UsersLocationRepository
import dev.alexpace.kassist.domain.services.TrackUserService
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TrackUserServiceImpl : TrackUserService, KoinComponent {

    private val scope = CoroutineScope(Dispatchers.Default)
    private val usersLocationRepository by inject<UsersLocationRepository>()
    private val userRepository by inject<UserRepository>()

    private val currentUserId = Firebase.auth.currentUser?.uid

    private lateinit var user: User

    init {
        fetchUser()
    }

    private fun fetchUser() {
        scope.launch {
            if (currentUserId == null) {
                delay(10000)
                return@launch
            }
            userRepository.getById(currentUserId).collect { user ->
                this@TrackUserServiceImpl.user = user!!
            }
        }
    }

    fun launchTrackingUser() {

        scope.launch {
            try {
                fetchUser()

                while (true) {

                    val userCoordinates =
                        LocationServiceProvider.getLocationService().getCurrentLocation()

                    println(userCoordinates)

                    if (userCoordinates == null) continue

                    val userLocation = UserLocation(
                        id = user.id,
                        user = user,
                        latitude = userCoordinates.latitude,
                        longitude = userCoordinates.longitude,
                        timestamp = Clock.System.now().toEpochMilliseconds(),
                        isActive = true
                    )

                    if (usersLocationRepository.exists(userLocation.id)) {
                        usersLocationRepository.update(userLocation)
                    } else {
                        usersLocationRepository.add(userLocation)
                    }

                    delay(DELAY_TRACK_LOCATION)
                }
            } catch (e: Exception) {
                return@launch
            }
        }
    }

    fun stopTrackingUser() {
        scope.cancel()
    }

}