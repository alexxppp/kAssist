package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.enums.RequestStatusTypes
import dev.alexpace.kassist.domain.models.victim.HelpRequest
import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.repositories.HelpRequestRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class HelpRequestRepositoryImpl : HelpRequestRepository {

    private val firestore = Firebase.firestore
    private val helpRequestCollection = firestore.collection("HelpRequest")
    private val pendingRequestsCollection = firestore.collection("pendingRequests")
    private val userCollection = firestore.collection("User")

    override fun getAll() = flow {
        helpRequestCollection.snapshots.collect { querySnapshot ->
            val helpRequestsWithScores = querySnapshot.documents.map { documentSnapshot ->
                val helpRequest = documentSnapshot.data<HelpRequest>()
                val victimScore = try {
                    val userSnapshot = userCollection.document(helpRequest.victimId).get()
                    userSnapshot.data<User>().score
                } catch (e: Exception) {
                    0
                }
                Pair(helpRequest, victimScore)
            }

            val sortedHelpRequests = helpRequestsWithScores.sortedWith { (request1, score1), (request2, score2) ->
                // Compare needLevel first (descending)
                val needLevelCompare = (request2.needLevel.ordinal)
                    .compareTo(request1.needLevel.ordinal)
                if (needLevelCompare != 0) {
                    needLevelCompare
                } else {
                    score2.compareTo(score1)
                }
            }.map { it.first }

            emit(sortedHelpRequests)
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
                val helpRequests = querySnapshot.documents
                    .map { it.data<HelpRequest>() }
                emit(helpRequests)
            }
    }

    override fun getAllByDisaster(disasterId: Int) = flow {
        helpRequestCollection.snapshots.collect { querySnapshot ->
            val helpRequestsWithScores = querySnapshot.documents
                .map { documentSnapshot ->
                    val helpRequest = documentSnapshot.data<HelpRequest>()
                    val victimScore = try {
                        val userSnapshot = userCollection.document(helpRequest.victimId).get()
                        userSnapshot.data<User>().score
                    } catch (e: Exception) {
                        0
                    }
                    Pair(helpRequest, victimScore)
                }
                .filter { it.first.disasterId == disasterId && it.first.status == RequestStatusTypes.Pending }

            val sortedHelpRequests = helpRequestsWithScores.sortedWith { (request1, score1), (request2, score2) ->
                // Compare needLevel first (descending)
                val needLevelCompare = (request2.needLevel.ordinal)
                    .compareTo(request1.needLevel.ordinal)
                if (needLevelCompare != 0) {
                    needLevelCompare
                } else {
                    score2.compareTo(score1)
                }
            }.map { it.first }

            emit(sortedHelpRequests)
        }
    }

    override suspend fun add(helpRequest: HelpRequest) {
        helpRequestCollection
            .document(helpRequest.id)
            .set(helpRequest.copy(id = helpRequest.id))
    }

    override suspend fun addPending(helpRequest: HelpRequest) {
        pendingRequestsCollection
            .document(helpRequest.id)
            .set(helpRequest.copy(id = helpRequest.id))
    }

    override suspend fun update(helpRequest: HelpRequest) {
        helpRequestCollection
            .document(helpRequest.id)
            .set(helpRequest)
    }

    override suspend fun delete(helpRequest: HelpRequest) {
        helpRequestCollection
            .document(helpRequest.id)
            .delete()
    }
}