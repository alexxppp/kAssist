package dev.alexpace.kassist.ui.shared.pages.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexpace.kassist.data.network.responses.LiveNewsResponse
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.alexpace.kassist.domain.services.LiveNewsApiService
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull

class NewsPageViewModel(
    private val liveNewsApiService: LiveNewsApiService,
    private val userRepository: UserRepository,
) : ViewModel() {

    // Values
    private val currentUserId =
        // TODO: Handle more nicely
        Firebase.auth.currentUser?.uid ?: throw Exception("User not authenticated")

    // State Flows
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _news = MutableStateFlow<LiveNewsResponse?>(null)
    val news: StateFlow<LiveNewsResponse?> = _news.asStateFlow()

    private val _isLoadingNews = MutableStateFlow(false)
    val isLoadingNews: StateFlow<Boolean> = _isLoadingNews.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    // Init
    init {
        fetchUser()
    }

    // Functions

    /**
     * Fetches user from UserRepository
     */
    private fun fetchUser() {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    _user.value = userRepository.getById(currentUserId).firstOrNull()
                } catch (e: Exception) {
                    _user.value = null
                }
            }
        }
    }

    /**
     * Fetches news from Python Fastapi API
     * Triggered on button press
     */
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
                val newsResponse =
                    liveNewsApiService.getByKeywords(
                        currentUserDisaster.name + " disaster in " +
                                currentUserDisaster.country,
                        _user.value!!.type
                    )
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