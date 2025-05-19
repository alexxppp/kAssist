package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.enums.help.RequestStatusTypes
import dev.alexpace.kassist.domain.models.classes.help.proposals.HelpProposal
import dev.alexpace.kassist.domain.models.classes.help.requests.HelpRequest
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class HelpProposalRepositoryImpl : HelpProposalRepository {

    private val firestore = Firebase.firestore
    private val helpProposalCollection = firestore.collection("HelpProposal")
    private val userCollection = firestore.collection("User")

    override fun getAllByDisaster(disasterId: Int) = flow {
        helpProposalCollection
            .snapshots
            .collect { querySnapshot ->
                val helpProposals = querySnapshot
                    .documents
                    .map { documentSnapshot ->
                        documentSnapshot.data<HelpProposal>()
                    }
                    .filter {
                        it.disasterId == disasterId
                    }
                emit(helpProposals)
            }
    }

    override fun getById(id: String) = flow {
        helpProposalCollection
            .document(id)
            .snapshots
            .collect { documentSnapshot ->
                if (documentSnapshot.exists) {
                    emit(documentSnapshot.data<HelpProposal>())
                } else {
                    emit(null)
                }
            }
    }

    override fun getBySupporterId(id: String) = flow {
        helpProposalCollection
            .where { "supporterId" equalTo id }
            .snapshots
            .collect { querySnapshot ->
                val helpProposalsWithScores = querySnapshot.documents.map { documentSnapshot ->
                    val helpProposal = documentSnapshot.data<HelpProposal>()
                    val supporterScore = try {
                        val userSnapshot = userCollection.document(helpProposal.supporterId).get()
                        userSnapshot.data<User>().score
                    } catch (e: Exception) {
                        0
                    }
                    Pair(helpProposal, supporterScore)
                }

                val sortedHelpProposals = helpProposalsWithScores
                    .sortedByDescending { it.second }
                    .map { it.first }

                emit(sortedHelpProposals)
            }
    }

    override fun getByVictimId(id: String) = flow {
        helpProposalCollection
            .where { "victimId" equalTo id }
            .snapshots
            .collect { querySnapshot ->
                val helpProposalsWithScores = querySnapshot.documents.map { documentSnapshot ->
                    val helpProposal = documentSnapshot.data<HelpProposal>()
                    val supporterScore = try {
                        val userSnapshot = userCollection.document(helpProposal.supporterId).get()
                        userSnapshot.data<User>().score
                    } catch (e: Exception) {
                        0
                    }
                    Pair(helpProposal, supporterScore)
                }

                val sortedHelpProposals = helpProposalsWithScores
                    .sortedByDescending { it.second }
                    .map { it.first }

                emit(sortedHelpProposals)
            }
    }

    override fun getBySupporterIdAndStatuses(id: String, statuses: List<RequestStatusTypes>) =
        flow {
            if (statuses.isEmpty()) {
                emit(emptyList())
                return@flow
            }
            helpProposalCollection
                .where { "supporterId" equalTo id }
                .where { "status" inArray statuses.map { it.toString() } }
                .snapshots
                .collect { querySnapshot ->
                    val helpProposalsWithScores = querySnapshot.documents.map { documentSnapshot ->
                        val helpProposal = documentSnapshot.data<HelpProposal>()
                        val supporterScore = try {
                            val userSnapshot =
                                userCollection.document(helpProposal.supporterId).get()
                            userSnapshot.data<User>().score
                        } catch (e: Exception) {
                            0
                        }
                        Pair(helpProposal, supporterScore)
                    }

                    val sortedHelpProposals = helpProposalsWithScores
                        .sortedByDescending { it.second }
                        .map { it.first }

                    emit(sortedHelpProposals)
                }
        }

    override fun getByVictimIdAndStatuses(id: String, statuses: List<RequestStatusTypes>) = flow {
        if (statuses.isEmpty()) {
            emit(emptyList())
            return@flow
        }
        helpProposalCollection
            .where { "victimId" equalTo id }
            .where { "status" inArray statuses.map { it.toString() } }
            .snapshots
            .collect { querySnapshot ->
                val helpProposalsWithScores = querySnapshot.documents.map { documentSnapshot ->
                    val helpProposal = documentSnapshot.data<HelpProposal>()
                    val supporterScore = try {
                        val userSnapshot = userCollection.document(helpProposal.supporterId).get()
                        userSnapshot.data<User>().score
                    } catch (e: Exception) {
                        0
                    }
                    Pair(helpProposal, supporterScore)
                }

                val sortedHelpProposals = helpProposalsWithScores
                    .sortedByDescending { it.second }
                    .map { it.first }

                emit(sortedHelpProposals)
            }
    }

    override suspend fun update(helpProposal: HelpProposal) {
        helpProposalCollection
            .document(helpProposal.id)
            .set(helpProposal)
    }

    override suspend fun add(helpProposal: HelpProposal) {
        helpProposalCollection
            .document(helpProposal.id)
            .set(helpProposal.copy(id = helpProposal.id))
    }

    override suspend fun delete(helpProposal: HelpProposal) {
        helpProposalCollection
            .document(helpProposal.id)
            .delete()
    }
}