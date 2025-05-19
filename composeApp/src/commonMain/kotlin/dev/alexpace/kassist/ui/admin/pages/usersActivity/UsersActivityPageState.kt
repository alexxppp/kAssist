package dev.alexpace.kassist.ui.admin.pages.usersActivity

import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.models.classes.user.User

data class UsersActivityPageState (
    val user: User? = null,
    val users: List<User?> = emptyList(),
    val helpProposals: List<HelpProposal?> = emptyList(),
    val helpRequests: List<HelpRequest?> = emptyList()
)