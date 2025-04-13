package dev.alexpace.kassist.domain.models.shared

import dev.alexpace.kassist.domain.models.enums.AlertLevelTypes
import dev.alexpace.kassist.domain.models.enums.EmergencyTypes
import kotlinx.serialization.Serializable

@Serializable
data class EmergencyPlan (
    val id: String,
    val name: String,
    val description: String,
    val alertLevel: AlertLevelTypes,
    val emergencyType: EmergencyTypes,
)