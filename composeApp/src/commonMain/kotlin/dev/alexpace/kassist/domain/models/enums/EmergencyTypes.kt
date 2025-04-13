package dev.alexpace.kassist.domain.models.enums

import kotlinx.serialization.Serializable

@Serializable
enum class EmergencyTypes {
    Earthquake,
    Fire,
    Tsunami,
    Flood
}