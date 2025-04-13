package dev.alexpace.kassist.domain.models.supporter

import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import kotlinx.serialization.Serializable

@Serializable
data class HelpProposal(
    val id: String,
    val supporterId: String,
    val helpRequestId: String,
    val content: String,
    val status: RequestStatusTypes
)