package dev.alexpace.kassist.nd.service

import dev.alexpace.kassist.nd.models.NaturalDisasterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NaturalDisastersService {

    @GET("EVENTS4APP")
    suspend fun getNaturalDisasters(): NaturalDisasterResponse

}