package dev.alexpace.kassist.ui.admin.pages.dashboard

import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest

data class DashboardPageState (
    val helpRequests: List<HelpRequest> = emptyList(),
    val user: User? = null
)