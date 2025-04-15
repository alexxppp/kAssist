package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class HelpRequestRepositoryImpl: HelpRequestRepository {

    private val firestore = Firebase.firestore
    private val helpRequestCollection = firestore.collection("HelpRequest")

    override fun getAll() = flow {
        helpRequestCollection.snapshots.collect { querySnapshot ->
            val helpRequests = querySnapshot
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.data<HelpRequest>()
                }
            emit(helpRequests)
        }
    }

    override fun getById(id: String) = flow {
        helpRequestCollection
            .document(id)
            .snapshots
            .collect { documentSnapshot ->
                emit(documentSnapshot.data<HelpRequest>())
            }
    }

    override fun getByVictimId(id: String) = flow {
        helpRequestCollection
            .where { "victimId" equalTo id }
            .snapshots
            .collect { querySnapshot ->
                val helpRequests = querySnapshot.documents.map { it.data<HelpRequest>() }
                emit(helpRequests)
            }
    }

    override suspend fun add(helpRequest: HelpRequest) {
        helpRequestCollection
            .document(helpRequest.id)
            .set(helpRequest.copy(id = helpRequest.id))
    }

    override suspend fun delete(helpRequest: HelpRequest) {
        helpRequestCollection
            .document(helpRequest.id)
            .delete()
    }
}