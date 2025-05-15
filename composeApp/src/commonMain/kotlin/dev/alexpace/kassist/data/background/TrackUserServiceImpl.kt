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
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

class TrackUserServiceImpl : TrackUserService, KoinComponent {

    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private val usersLocationRepository by inject<UsersLocationRepository>()
    private val userRepository by inject<UserRepository>()
    private var user: User? = null
    private var trackingJob: Job? = null

    init {
        observeAuthState()
    }

    private fun observeAuthState() {
        scope.launch {
            Firebase.auth.authStateChanged.collectLatest { firebaseUser ->
                val currentUserId = firebaseUser?.uid
                if (currentUserId != null) {
                    println("User authenticated: $currentUserId")
                    fetchUser(currentUserId)
                } else {
                    println("No user authenticated")
                    user = null
                    stopTrackingUser() // Stop tracking if user signs out
                }
            }
        }
    }

    private fun fetchUser(userId: String) {
        scope.launch {
            try {
                userRepository.getById(userId).collectLatest { fetchedUser ->
                    fetchedUser?.let {
                        user = it
                        println("User fetched successfully: ${it.id}")
                    }
                }
            } catch (e: Exception) {
                println("Error fetching user: ${e.message}")
                delay(10_000)
                fetchUser(userId)
            }
        }
    }

    override fun launchTrackingUser() {
        if (trackingJob?.isActive == true) {
            println("Tracking already active, ignoring new request")
            return
        }

        trackingJob = scope.launch {
            try {
                while (true) {
                    val currentUser = user
                    val currentUserId = Firebase.auth.currentUser?.uid

                    if (currentUserId == null || currentUser == null) {
                        println("User not authenticated or not fetched, skipping location update")
                        delay(DELAY_TRACK_LOCATION)
                        continue
                    }

                    val userCoordinates = try {
                        LocationServiceProvider.getLocationService().getCurrentLocation()
                    } catch (e: Exception) {
                        println("Failed to get location: ${e.message}")
                        null
                    }

                    if (userCoordinates == null) {
                        println("No location data available")
                        delay(DELAY_TRACK_LOCATION)
                        continue
                    }

                    val userLocation = UserLocation(
                        id = currentUser.id,
                        user = currentUser,
                        latitude = userCoordinates.latitude,
                        longitude = userCoordinates.longitude,
                        timestamp = Clock.System.now().toEpochMilliseconds(),
                        isActive = true
                    )

                    try {
                        if (usersLocationRepository.exists(userLocation.id)) {
                            usersLocationRepository.update(userLocation)
                            println("Updated user location: ${userLocation.id}")
                        } else {
                            usersLocationRepository.add(userLocation)
                            println("Added new user location: ${userLocation.id}")
                        }
                    } catch (e: Exception) {
                        println("Error saving user location: ${e.message}")
                    }

                    delay(DELAY_TRACK_LOCATION)
                }
            } catch (e: CancellationException) {
                println("Tracking cancelled")
                throw e
            } catch (e: Exception) {
                println("Unexpected error in tracking: ${e.message}")
            }
        }
    }

    override fun stopTrackingUser() {
        trackingJob?.cancel()
        trackingJob = null
        println("User tracking stopped")
    }
}