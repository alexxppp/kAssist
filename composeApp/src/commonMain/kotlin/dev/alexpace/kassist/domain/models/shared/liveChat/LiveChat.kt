package dev.alexpace.kassist.domain.models.shared.liveChat

import dev.alexpace.kassist.domain.models.shared.EmergencyPlan
import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.models.victim.HelpRequest

data class LiveChat (
    val id: String,
    val victimId: String,
    val supporterId: String,
    val emergencyPlan: EmergencyPlan,
    val helpRequest: HelpRequest,
    val helpProposal: HelpProposal,
    val isActive: Boolean,
    val messages: List<ChatMessage>?
)