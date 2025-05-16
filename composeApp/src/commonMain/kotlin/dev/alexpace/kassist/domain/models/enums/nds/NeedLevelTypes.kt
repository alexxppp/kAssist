package dev.alexpace.kassist.domain.models.enums.nds

import kotlinx.serialization.Serializable

@Serializable
enum class NeedLevelTypes {
    NotAssigned,
    Low,
    Moderate,
    High,
    VeryHigh
}