package dev.alexpace.kassist.domain.services

import dev.alexpace.kassist.data.network.responses.LiveNewsResponse

interface LiveNewsApiService {

    suspend fun getByKeywords(keywords: String): LiveNewsResponse

}