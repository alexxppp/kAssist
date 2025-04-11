package dev.alexpace.kassist.application.services

import dev.alexpace.kassist.infrastructure.responses.NaturalDisasterResponse
import retrofit2.http.GET

/**
 * Communicates the Infrastructure layer with the Presentation layer
 * through Dependency Injection
 */
interface NaturalDisasterService {

    @GET("EVENTS4APP")
    suspend fun getAllNaturalDisasters(): NaturalDisasterResponse

}
