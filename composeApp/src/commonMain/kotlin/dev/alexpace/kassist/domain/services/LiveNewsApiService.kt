package dev.alexpace.kassist.domain.services

import dev.alexpace.kassist.data.network.responses.LiveNewsResponse
import dev.alexpace.kassist.domain.models.enums.UserType

interface LiveNewsApiService {

    suspend fun getByKeywords(keywords: String, userRole: UserType): LiveNewsResponse

}