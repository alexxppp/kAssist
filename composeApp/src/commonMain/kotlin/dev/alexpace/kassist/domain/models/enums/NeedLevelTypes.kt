package dev.alexpace.kassist.domain.models.enums

import kotlinx.serialization.Serializable

@Serializable
enum class NeedLevelTypes {
    NotAssigned,
    Low,
    Moderate,
    High,
    VeryHigh
}