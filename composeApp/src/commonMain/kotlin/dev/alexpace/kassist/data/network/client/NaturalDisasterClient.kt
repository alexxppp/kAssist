package dev.alexpace.kassist.data.network.client

import dev.alexpace.kassist.domain.models.NaturalDisaster
import dev.alexpace.kassist.domain.models.enums.AlertLevelTypes
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class NaturalDisasterClient(
    private val httpClient: HttpClient
) {
    // TODO: Not implemented yet
    suspend fun getNaturalDisasters(alertLevel: AlertLevelTypes): List<NaturalDisaster> {
        httpClient.get(
            urlString = ""
        ) {

        }
        return emptyList()
    }
}