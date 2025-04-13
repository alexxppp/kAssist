package dev.alexpace.kassist.domain.models.shared

import kotlinx.serialization.Serializable

/**
 * TODO: Maybe not needed
 */
@Serializable
data class Location(
    val longitude: Long,
    val altitude: Long
)
