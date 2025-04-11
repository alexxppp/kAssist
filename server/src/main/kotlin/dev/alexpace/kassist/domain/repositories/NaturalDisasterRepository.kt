package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.infrastructure.responses.NaturalDisasterResponse
import retrofit2.http.GET

/**
 * Interface for Dependency Injection. Declares the
 * needed methods for the repository.
 * TODO: Apparently not necessary
 */
interface NaturalDisasterRepository {

    @GET("EVENTS4APP")
    suspend fun getAllNaturalDisasters(): NaturalDisasterResponse

}