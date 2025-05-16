package dev.alexpace.kassist.domain.models.classes.help.requests

import dev.alexpace.kassist.domain.models.classes.help.HelpItem
import dev.alexpace.kassist.domain.models.enums.nds.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.help.RequestStatusTypes
import kotlinx.serialization.Serializable

@Serializable
data class HelpRequest (
    val id: String,
    val victimId: String,
    val victimName: String,
    val disasterId: Int?,
    val address: String,
    val description: String?,
    val items: List<HelpItem?> = emptyList(),
    val needLevel: NeedLevelTypes,
    val status: RequestStatusTypes
)