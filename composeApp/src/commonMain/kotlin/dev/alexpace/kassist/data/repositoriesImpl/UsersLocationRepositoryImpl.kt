package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.classes.map.UserLocation
import dev.alexpace.kassist.domain.repositories.UsersLocationRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class UsersLocationRepositoryImpl : UsersLocationRepository {

    private val firestore = Firebase.firestore
    private val usersLocationCollection = firestore.collection("UserLocation")

    override fun getAll() = flow {
        usersLocationCollection
            .snapshots
            .collect { querySnapshot ->
                val usersLocations = querySnapshot
                    .documents
                    .map { documentSnapshot ->
                        documentSnapshot.data<UserLocation>()
                    }
                emit(usersLocations)
            }
    }

    override fun getAllByDisaster(ndId: String) = flow {
        usersLocationCollection
            .snapshots
            .collect { querySnapshot ->
                val usersLocations = querySnapshot
                    .documents
                    .map { documentSnapshot ->
                        documentSnapshot.data<UserLocation>()
                    }
                    .filter {
                        it.user.naturalDisaster!!.id == ndId
                    }
                emit(usersLocations)
            }
    }

    override fun getById(id: String) = flow {
        usersLocationCollection
            .document(id)
            .snapshots
            .collect { documentSnapshot ->
                emit(documentSnapshot.data<UserLocation>())
            }
    }

    override suspend fun exists(userLocationId: String): Boolean {
        return firestore.collection("user_locations")
            .document(userLocationId)
            .get()
            .exists
    }

    override suspend fun add(userLocation: UserLocation) {
        usersLocationCollection
            .document(userLocation.user.id)
            .set(userLocation)
    }

    override suspend fun update(userLocation: UserLocation) {
        usersLocationCollection
            .document(userLocation.id)
            .set(userLocation)
    }

    override suspend fun delete(id: String) {
        usersLocationCollection
            .document(id)
            .delete()
    }


}