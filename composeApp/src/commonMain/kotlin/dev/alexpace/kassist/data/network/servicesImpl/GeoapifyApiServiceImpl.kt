package dev.alexpace.kassist.data.network.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpEngine
import dev.alexpace.kassist.data.network.responses.geo.GeoapifyApiResponse
import dev.alexpace.kassist.data.utils.helpers.GeoapifyUrlBuilder
import dev.alexpace.kassist.domain.services.GeoapifyApiService
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess

class GeoapifyApiServiceImpl : GeoapifyApiService {

    private val http = createHttpClient(getHttpEngine())
    private var count = 0

    override suspend fun getConfidenceScoreForAddress(
        address: String,
        countryCode: String
    ): Boolean {

        val geoapifyUrl = GeoapifyUrlBuilder.buildGeocodingUrl(address, countryCode)

        val response = http.get(geoapifyUrl)

        return if (response.status.isSuccess()) {
            count = 0
            val apiResponse: GeoapifyApiResponse = response.body()
            apiResponse.features.firstOrNull()?.properties?.rank?.confidence?.let { confidence ->
                confidence >= 0.7f
            } ?: false // Return false if no features or confidence is missing
        } else {
            if (count >= 5) {
                count = 0 // Reset retry count
                return false // Return false after max retries
            }
            count++
            getConfidenceScoreForAddress(address, countryCode) // Retry
        }

    }
}