package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl : UserRepository {

    private val firestore = Firebase.firestore
    private val usersCollection = firestore.collection("User")

    override fun getAll() = flow {
        usersCollection.snapshots.collect { querySnapshot ->
            val users = querySnapshot
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.data<User>()
                }
            emit(users)
        }
    }

    override fun getAllWithNegativeScore() = flow {
        usersCollection
            .where { "score" lessThan 0 }
            .snapshots
            .collect { querySnapshot ->
                val users = querySnapshot
                    .documents
                    .mapNotNull { documentSnapshot ->
                        try {
                            documentSnapshot.data<User>()
                        } catch (e: Exception) {
                            null
                        }
                    }
                emit(users)
            }
    }

    override fun getAllByDisaster(disasterId: String) = flow {
        usersCollection
            .where { "disasterId" equalTo disasterId }
            .snapshots
            .collect { querySnapshot ->
                val users = querySnapshot
                    .documents
                    .mapNotNull { documentSnapshot ->
                        try {
                            documentSnapshot.data<User>()
                        } catch (e: Exception) {
                            null
                        }
                    }
                emit(users)
            }
    }

    override fun getById(id: String) = flow {
        usersCollection
            .document(id)
            .snapshots
            .collect { documentSnapshot ->
                emit(documentSnapshot.data<User>())
            }
    }

    override suspend fun add(user: User) {
        usersCollection
            .document(user.id)
            .set(user.copy(id = user.id))
    }

    override suspend fun update(user: User) {
        usersCollection
            .document(user.id)
            .set(user)
    }

    override suspend fun delete(id: String) {
        usersCollection
            .document(id)
            .delete()
    }
}