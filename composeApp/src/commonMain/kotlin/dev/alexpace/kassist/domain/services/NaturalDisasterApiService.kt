package dev.alexpace.kassist.domain.services

import dev.alexpace.kassist.domain.models.shared.naturalDisaster.NaturalDisasterResponse

interface NaturalDisasterApiService {
    suspend fun getNaturalDisasters(): NaturalDisasterResponse
}