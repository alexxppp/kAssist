package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class HelpProposalRepositoryImpl: HelpProposalRepository {

    private val firestore = Firebase.firestore
    private val helpProposalCollection = firestore.collection("HelpProposal")

    override fun getHelpProposals() = flow {
        helpProposalCollection.snapshots.collect { querySnapshot ->
            val helpProposals = querySnapshot
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.data<HelpProposal>()
                }
            emit(helpProposals)
        }
    }

    override fun getHelpProposalById(id: String) = flow {
        helpProposalCollection
            .document(id)
            .snapshots
            .collect { documentSnapshot ->
                emit(documentSnapshot.data<HelpProposal>())
            }
    }

    override suspend fun addHelpProposal(helpProposal: HelpProposal) {
        helpProposalCollection
            .document(helpProposal.id)
            .set(helpProposal.copy(id = helpProposal.id))
    }

    override suspend fun deleteHelpProposal(helpProposal: HelpProposal) {
        helpProposalCollection
            .document(helpProposal.id)
            .delete()
    }
}