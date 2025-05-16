package dev.alexpace.kassist.domain.models.enums.help

import kotlinx.serialization.Serializable

@Serializable
enum class NeedLevelTypes {
    NotAssigned,
    Low,
    Moderate,
    High,
    VeryHigh
}