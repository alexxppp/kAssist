package dev.alexpace.kassist.domain.models.shared

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(val latitude: Double, val longitude: Double)
