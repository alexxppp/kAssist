package dev.alexpace.kassist.domain.models.victim

import dev.alexpace.kassist.domain.models.enums.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import kotlinx.serialization.Serializable

@Serializable
data class HelpRequest (
    val id: String,
    val victimId: String,
    val victimName: String,
    val address: String,
    val description: String,
    val needLevel: NeedLevelTypes,
    val status: RequestStatusTypes

)