package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.classes.app.NaturalDisaster
import dev.alexpace.kassist.domain.models.classes.map.Coordinates
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.enums.user.UserRole
import dev.alexpace.kassist.domain.models.enums.user.UserType
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

    override suspend fun populateUsers() {
        val danaDisaster = NaturalDisaster(
            id = "DANA2024",
            type = "Flood",
            alertLevel = "Red",
            startDate = "2024-10-28",
            endDate = "2024-11-05",
            name = "DANA Valencia 2024",
            description = "Severe flooding in Valencia due to DANA",
            htmlDescription = "<p>Severe flooding in Valencia due to DANA</p>",
            icon = "flood.png",
            country = "Spain",
            severityData = null,
            coordinates = Coordinates(latitude = 39.4699, longitude = -0.3763)
        )

        // Realistic names for users
        val names = listOf(
            "Clara García", "Luis Pérez", "Marta López", "Carlos Ruiz", "Sofía Martínez",
            "Jorge Sánchez", "Elena Gómez", "Pablo Fernández", "María Torres", "Javier Díaz",
            "Laura Romero", "Antonio Morales", "Carmen Navarro", "Miguel Ángel Vargas",
            "Isabel Castro", "Raúl Moreno", "Clara Esteban", "Hugo Navarro", "Lucía Vega",
            "Diego Ramírez"
        )

        // Generate random phone numbers
        val phoneNumbers = List(20) { "+346${(100000000..999999999).random()}" }

        // Assign users with roles and types
        val users = listOf(
            // Victims (8)
            Triple("user1", names[0], UserType.Victim to UserRole.Basic),
            Triple("user2", names[1], UserType.Victim to UserRole.Basic),
            Triple("user3", names[2], UserType.Victim to UserRole.Basic),
            Triple("user4", names[3], UserType.Victim to UserRole.Basic),
            Triple("user5", names[4], UserType.Victim to UserRole.Basic),
            Triple("user6", names[5], UserType.Victim to UserRole.Basic),
            Triple("user7", names[6], UserType.Victim to UserRole.Basic),
            Triple("user8", names[7], UserType.Victim to UserRole.Basic),
            // Supporters (8)
            Triple("user9", names[8], UserType.Supporter to UserRole.Basic),
            Triple("user10", names[9], UserType.Supporter to UserRole.Basic),
            Triple("user11", names[10], UserType.Supporter to UserRole.Basic),
            Triple("user12", names[11], UserType.Supporter to UserRole.Basic),
            Triple("user13", names[12], UserType.Supporter to UserRole.Basic),
            Triple("user14", names[13], UserType.Supporter to UserRole.Basic),
            Triple("user15", names[14], UserType.Supporter to UserRole.Basic),
            Triple("user16", names[15], UserType.Supporter to UserRole.Basic),
            // Admins (4)
            Triple("user17", names[16], UserType.Admin to UserRole.Admin),
            Triple("user18", names[17], UserType.Admin to UserRole.Admin),
            Triple("user19", names[18], UserType.Admin to UserRole.Admin),
            Triple("user20", names[19], UserType.Admin to UserRole.Admin)
        )

        // Use Firestore batch for atomic writes
        firestore.runTransaction {
            users.forEachIndexed { index, (id, name, typeRole) ->
                val user = User(
                    id = id,
                    isAnonymous = false,
                    email = "${name.replace(" ", ".").lowercase()}@example.com",
                    name = name,
                    phoneNumber = phoneNumbers[index],
                    naturalDisaster = danaDisaster,
                    type = typeRole.first,
                    role = typeRole.second,
                    score = 0,
                    fcmToken = null
                )

                usersCollection.document(user.id).set(user)
            }
        }
    }
}