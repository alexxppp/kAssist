package dev.alexpace.kassist.api.infrastructure.repositories

import dev.alexpace.kassist.api.application.client.RetrofitClient
import dev.alexpace.kassist.api.application.services.NaturalDisasterService
import dev.alexpace.kassist.api.domain.core.GetEPFromND
import dev.alexpace.kassist.api.domain.models.EmergencyPlan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmergencyPlanRepository {

    /**
     * Gets all emergency plans
     */
    suspend fun getAllEmergencyPlans(): List<EmergencyPlan> {
        return withContext(Dispatchers.IO) {
            try {
                val service: NaturalDisasterService = RetrofitClient.retrofit.create(
                    NaturalDisasterService::class.java
                )

                val naturalDisasters = service.getAllNaturalDisasters().features
                println(naturalDisasters)

                GetEPFromND(naturalDisasters)
            } catch (e: Exception) {
                throw Exception("Error making API call: ${e.message}")
            }
        }
    }
}