package dev.alexpace.kassist.ui.shared.pages.news

import dev.alexpace.kassist.data.network.responses.LiveNewsResponse
import dev.alexpace.kassist.domain.models.shared.User

data class NewsPageState (
    val user: User? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val news: LiveNewsResponse? = null
)