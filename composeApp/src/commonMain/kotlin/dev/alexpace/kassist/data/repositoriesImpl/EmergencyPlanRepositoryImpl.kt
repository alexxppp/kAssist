package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.shared.EmergencyPlan
import dev.alexpace.kassist.domain.repositories.EmergencyPlanRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class EmergencyPlanRepositoryImpl : EmergencyPlanRepository {

    private val firestore = Firebase.firestore
    private val emergencyPlansCollection = firestore.collection("EmergencyPlan")

    override fun getEmergencyPlans() = flow {
        emergencyPlansCollection.snapshots.collect { querySnapshot ->
            val emergencyPlans = querySnapshot
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.data<EmergencyPlan>()
                }
            emit(emergencyPlans)
        }
    }

    override fun getEmergencyPlanById(id: String) = flow {
        emergencyPlansCollection
            .document(id)
            .snapshots
            .collect { documentSnapshot ->
                emit(documentSnapshot.data<EmergencyPlan>())
            }
    }

    override suspend fun addEmergencyPlan(emergencyPlan: EmergencyPlan) {
        emergencyPlansCollection
            .document(emergencyPlan.id)
            .set(emergencyPlan.copy(id = emergencyPlan.id))
    }

    override suspend fun updateEmergencyPlan(emergencyPlan: EmergencyPlan) {
        emergencyPlansCollection
            .document(emergencyPlan.id)
            .set(emergencyPlan)
    }

    override suspend fun deleteEmergencyPlan(emergencyPlan: EmergencyPlan) {
        emergencyPlansCollection
            .document(emergencyPlan.id)
            .delete()
    }

}