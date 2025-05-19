package dev.alexpace.kassist.domain.models.classes.help.proposals

import dev.alexpace.kassist.domain.models.classes.help.HelpItem
import dev.alexpace.kassist.domain.models.enums.help.RequestStatusTypes
import kotlinx.serialization.Serializable

@Serializable
data class HelpProposal(
    val id: String,
    val supporterId: String,
    val supporterName: String,
    val disasterId: String,
    val victimId: String,
    val victimName: String,
    val helpRequestId: String,
    val content: String,
    val status: RequestStatusTypes,
    val requiredTime: String,
    val fulfilledItems: List<HelpItem?>? = null
)