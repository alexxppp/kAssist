package dev.alexpace.kassist.domain.models.classes.chat

import dev.alexpace.kassist.domain.models.classes.app.NaturalDisaster
import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
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
    val messages: List<ChatMessage> = emptyList()
)