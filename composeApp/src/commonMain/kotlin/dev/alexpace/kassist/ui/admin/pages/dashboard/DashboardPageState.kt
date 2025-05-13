package dev.alexpace.kassist.ui.admin.pages.dashboard

import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.models.victim.HelpRequest

data class DashboardPageState (
    val helpRequests: List<HelpRequest> = emptyList(),
    val user: User? = null
)