package dev.alexpace.kassist.domain.models

import dev.alexpace.kassist.domain.models.enums.AlertLevelTypes
import dev.alexpace.kassist.domain.models.enums.EmergencyTypes

data class EmergencyPlan (
    val id: String,
    val name: String,
    val description: String,
    val alertLevel: AlertLevelTypes,
    val emergencyType: EmergencyTypes,
)