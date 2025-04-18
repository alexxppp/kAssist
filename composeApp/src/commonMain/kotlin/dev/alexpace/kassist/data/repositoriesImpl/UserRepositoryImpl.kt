package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.shared.User
import dev.alexpace.kassist.domain.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl: UserRepository {

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

    override suspend fun delete(user: User) {
        usersCollection
            .document(user.id)
            .delete()
    }
}