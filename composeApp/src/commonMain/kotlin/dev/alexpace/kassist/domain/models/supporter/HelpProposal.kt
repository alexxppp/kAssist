package dev.alexpace.kassist.domain.models.supporter

import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import kotlinx.serialization.Serializable

@Serializable
data class HelpProposal(
    val id: String,
    val supporterId: String,
    val supporterName: String,
    val victimId: String,
    val victimName: String,
    val helpRequestId: String,
    val content: String,
    val status: RequestStatusTypes,
    val requiredTime: String,
)