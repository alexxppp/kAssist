package dev.alexpace.kassist.data.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpClient
import dev.alexpace.kassist.domain.services.EmergencyPlanService
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class EmergencyPlanServiceImpl: EmergencyPlanService {

    private val http = createHttpClient(getHttpClient())
    private val BASE_URL = "https://www.gdacs.org/gdacsapi/api/events/geteventlist/"

    suspend fun getEmergencyPlans(): HttpResponse {
        return http.get(BASE_URL)
    }
}
