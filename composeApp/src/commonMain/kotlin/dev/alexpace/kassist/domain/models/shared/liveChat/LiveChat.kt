package dev.alexpace.kassist.domain.models.shared.liveChat

import dev.alexpace.kassist.domain.models.shared.naturalDisaster.NaturalDisaster
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import kotlinx.serialization.Serializable

@Serializable
data class LiveChat (
    val id: String,
    val victimId: String,
    val supporterId: String,
    val naturalDisaster: NaturalDisaster,
    val helpRequest: HelpRequest,
    val helpProposal: HelpProposal,
    val isActive: Boolean,
    val messages: List<ChatMessage>?
)