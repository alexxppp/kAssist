package dev.alexpace.kassist.domain.services

interface GeoapifyApiService {

    suspend fun getConfidenceScoreForAddress(address: String, countryCode: String): Boolean

}