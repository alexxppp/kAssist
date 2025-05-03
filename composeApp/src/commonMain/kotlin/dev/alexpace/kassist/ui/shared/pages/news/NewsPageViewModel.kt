package dev.alexpace.kassist.ui.shared.pages.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.data.network.responses.LiveNewsResponse
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.services.LiveNewsApiService
import dev.alexpace.kassist.domain.session.SessionManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsPageViewModel(
    private val liveNewsApiService: LiveNewsApiService
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _news = MutableStateFlow<LiveNewsResponse?>(null)
    val news: StateFlow<LiveNewsResponse?> = _news.asStateFlow()

    private val _isLoadingNews = MutableStateFlow(false)
    val isLoadingNews: StateFlow<Boolean> = _isLoadingNews.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        // Collect user data from SessionManager
        viewModelScope.launch {
            SessionManager.currentUser.collect { user ->
                _user.value = user
            }
        }
    }

    fun fetchLiveNews() {
        val currentUserDisaster = _user.value?.naturalDisaster
        if (currentUserDisaster == null) {
            _error.value = "No disaster associated with user"
            _news.value = null
            return
        }

        viewModelScope.launch {
            _isLoadingNews.value = true
            try {
                // Assuming liveNewsApiService has a method to fetch news by disaster
                val newsResponse =
                    liveNewsApiService.getByKeywords(currentUserDisaster.name + " disaster in " + currentUserDisaster.country)
                _news.value = newsResponse
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error fetching news: ${e.message}"
                _news.value = null
            } finally {
                _isLoadingNews.value = false
            }
        }
    }
}