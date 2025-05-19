package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.enums.help.NeedLevelTypes
import dev.alexpace.kassist.domain.models.enums.help.RequestStatusTypes
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.repositories.AdminPendingDataRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow


class AdminPendingDataRepositoryImpl : AdminPendingDataRepository {

    private val firestore = Firebase.firestore
    private val pendingHelpRequestsCollection = firestore.collection("pendingRequests")
    private val helpRequestsCollection = firestore.collection("HelpRequest")

    override fun getAllPendingHelpRequestsByDisaster(disasterId: String) = flow {
        pendingHelpRequestsCollection.snapshots.collect { querySnapshot ->
            val helpRequests = querySnapshot
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.data<HelpRequest>()
                }
                .filter {
                    it.disasterId == disasterId && it.status == RequestStatusTypes.NotAssigned
                }
            emit(helpRequests)
        }
    }

    override suspend fun acceptHelpRequest(helpRequest: HelpRequest, needLevel: NeedLevelTypes) {
        helpRequestsCollection
            .document(helpRequest.id)
            .set(
                helpRequest.copy(
                    id = helpRequest.id,
                    needLevel = needLevel,
                    status = RequestStatusTypes.Pending
                )
            )
    }

    override suspend fun rejectOrDeleteHelpRequest(helpRequestId: String) {
        pendingHelpRequestsCollection
            .document(helpRequestId)
            .delete()
    }

}