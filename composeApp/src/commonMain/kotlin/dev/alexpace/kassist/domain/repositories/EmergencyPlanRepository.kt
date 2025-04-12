package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.EmergencyPlan
import kotlinx.coroutines.flow.Flow

interface EmergencyPlanRepository {

    fun getEmergencyPlans(): Flow<List<EmergencyPlan>>
    fun getEmergencyPlanById(id: String): Flow<EmergencyPlan?>
    suspend fun addEmergencyPlan(emergencyPlan: EmergencyPlan)
    suspend fun updateEmergencyPlan(emergencyPlan: EmergencyPlan)
    suspend fun deleteEmergencyPlan(emergencyPlan: EmergencyPlan)
}
