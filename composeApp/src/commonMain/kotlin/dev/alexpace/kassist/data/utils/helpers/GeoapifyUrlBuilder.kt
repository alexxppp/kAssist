package dev.alexpace.kassist.data.utils.helpers

import dev.alexpace.kassist.data.utils.constants.BASE_URL_GEOAPIFY
import io.ktor.http.URLBuilder

object GeoapifyUrlBuilder {
    private const val API_KEY = "0b2922ac7a484b758b65b2d4e9c39783"

    fun buildGeocodingUrl(address: String, country: String): String {
        return URLBuilder(BASE_URL_GEOAPIFY).apply {
            parameters.append("text", address)
            parameters.append("filter", "countrycode:$country")
            parameters.append("apiKey", API_KEY)
        }.buildString()
    }
}