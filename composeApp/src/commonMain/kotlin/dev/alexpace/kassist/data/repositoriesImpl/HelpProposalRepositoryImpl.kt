package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.supporter.HelpProposal
import dev.alexpace.kassist.domain.repositories.HelpProposalRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class HelpProposalRepositoryImpl: HelpProposalRepository {

    private val firestore = Firebase.firestore
    private val helpProposalCollection = firestore.collection("HelpProposal")

    override fun getAll() = flow {
        helpProposalCollection.snapshots.collect { querySnapshot ->
            val helpProposals = querySnapshot
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.data<HelpProposal>()
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
                val helpProposals = querySnapshot.documents.map { it.data<HelpProposal>() }
                emit(helpProposals)
            }
    }

    override fun getByVictimId(id: String) = flow {
        helpProposalCollection
            .where { "victimId" equalTo id }
            .snapshots
            .collect { querySnapshot ->
                val helpProposals = querySnapshot.documents.map { it.data<HelpProposal>() }
                emit(helpProposals)
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