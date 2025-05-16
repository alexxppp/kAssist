package dev.alexpace.kassist.domain.services

import dev.alexpace.kassist.data.network.responses.NeedLevelSuggestionResponse

interface NeedLevelSuggestionService {
    suspend fun getSuggestion(requestText: String): NeedLevelSuggestionResponse
}