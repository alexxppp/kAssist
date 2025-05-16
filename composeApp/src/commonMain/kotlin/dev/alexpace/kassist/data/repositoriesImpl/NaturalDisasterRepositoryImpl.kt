package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.classes.app.NaturalDisaster
import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class NaturalDisasterRepositoryImpl : NaturalDisasterRepository {

    private val firestore = Firebase.firestore
    private val naturalDisasterCollection = firestore.collection("NaturalDisaster")

    override fun getAll() = flow {
        naturalDisasterCollection.snapshots.collect { querySnapshot ->
            val naturalDisasters = querySnapshot
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.data<NaturalDisaster>()
                }
            emit(naturalDisasters)
        }
    }

    override fun getById(id: String) = flow {
        naturalDisasterCollection
            .document(id)
            .snapshots
            .collect { documentSnapshot ->
                emit(documentSnapshot.data<NaturalDisaster>())
            }
    }

    override suspend fun add(naturalDisaster: NaturalDisaster) {
        naturalDisasterCollection
            .document(naturalDisaster.id.toString())
            .set(naturalDisaster.copy(id = naturalDisaster.id))
    }

    override suspend fun addAll(naturalDisasters: List<NaturalDisaster>) {
        naturalDisasters.forEach { naturalDisaster ->
            naturalDisasterCollection
                .document(naturalDisaster.id.toString())
                .set(naturalDisaster.copy(id = naturalDisaster.id))
        }
    }

    override suspend fun update(naturalDisaster: NaturalDisaster) {
        naturalDisasterCollection
            .document(naturalDisaster.id.toString())
            .set(naturalDisaster)
    }

    override suspend fun delete(naturalDisaster: NaturalDisaster) {
        naturalDisasterCollection
            .document(naturalDisaster.id.toString())
            .delete()
    }

}