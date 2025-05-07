package dev.alexpace.kassist.domain.services

import dev.alexpace.kassist.data.network.responses.NaturalDisasterResponse

interface NaturalDisasterApiService {
    suspend fun getNaturalDisasters(): NaturalDisasterResponse
}