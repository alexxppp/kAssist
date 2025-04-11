package dev.alexpace.kassist.api.application.client

import dev.alexpace.kassist.BASE_URL
import dev.alexpace.kassist.api.domain.enums.AlertLevelTypes
import dev.alexpace.kassist.api.domain.models.NaturalDisaster
import dev.alexpace.kassist.http.utils.NetworkError
import dev.alexpace.kassist.http.utils.Result
import dev.alexpace.kassist.api.domain.responses.NaturalDisasterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class NaturalDisasterClient(
    private val httpClient: HttpClient
) {

    suspend fun getNaturalDisastersByAlertLevel(
        alertLevel: AlertLevelTypes
    ): Result<List<NaturalDisaster>, NetworkError> {

        val response = try {
            httpClient.get(
                urlString = BASE_URL
            )
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET);
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION);
        }

        when (response.status.value) {
            in 200..299 -> {
                val naturalDisasters =
                    extractNaturalDisasters(response.body<NaturalDisasterResponse>(), alertLevel)

                return Result.Success(naturalDisasters)
            }

            401 -> return Result.Error(NetworkError.UNAUTHORIZED)
            408 -> return Result.Error(NetworkError.REQUEST_TIMEOUT)
            409 -> return Result.Error(NetworkError.CONFLICT)
            413 -> return Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            429 -> return Result.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> return Result.Error(NetworkError.SERVER_ERROR)
            else -> return Result.Error(NetworkError.UNKNOWN)

        }
    }

    private fun extractNaturalDisasters(
        naturalDisasterResponse: NaturalDisasterResponse,
        alertLevel: AlertLevelTypes
    ): List<NaturalDisaster> {

        val naturalDisasters = arrayListOf<NaturalDisaster>()
        naturalDisasterResponse.features.forEach {
            if (it.properties.alertLevel.equals(alertLevel.name)) {
                naturalDisasters.add(it.properties)
            }
        }

        return naturalDisasters
    }
}