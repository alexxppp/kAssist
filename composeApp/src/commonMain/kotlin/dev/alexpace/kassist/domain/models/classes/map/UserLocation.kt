package dev.alexpace.kassist.domain.models.classes.map

import dev.alexpace.kassist.domain.models.classes.user.User
import kotlinx.serialization.Serializable

@Serializable
data class UserLocation (
    val id: String,
    val user: User,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val isActive: Boolean
)