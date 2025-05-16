package dev.alexpace.kassist.domain.models.enums.help

import kotlinx.serialization.Serializable

@Serializable
enum class HelpItemType {
    Physical,
    Material,
    Medical,
    Other
}