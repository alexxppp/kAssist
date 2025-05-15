package dev.alexpace.kassist.domain.models.shared

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