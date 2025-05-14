package dev.alexpace.kassist.ui.shared.pages.home

import dev.alexpace.kassist.domain.models.shared.NaturalDisaster
import dev.alexpace.kassist.domain.models.shared.User

data class HomePageState(
    val naturalDisasters: List<NaturalDisaster> = emptyList(),
    val user: User? = null,
    val isLoading: Boolean = true,
    val isFilterActive: Boolean = false,
    val isNavigating: Boolean = false,
    val isRegistering: Boolean = false
)