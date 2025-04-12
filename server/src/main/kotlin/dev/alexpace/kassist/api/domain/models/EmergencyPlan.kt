package dev.alexpace.kassist.api.domain.models

import dev.alexpace.kassist.api.domain.enums.AlertLevelTypes
import dev.alexpace.kassist.api.domain.enums.EmergencyTypes

data class EmergencyPlan (
    val id: Int,
    val city: String,
    val alertLevel: AlertLevelTypes,
    val emergencyType: EmergencyTypes
)