package dev.alexpace.kassist.data.repositoriesImpl

import dev.alexpace.kassist.domain.models.classes.app.NaturalDisaster
import dev.alexpace.kassist.domain.models.classes.map.Coordinates
import dev.alexpace.kassist.domain.models.classes.map.UserLocation
import dev.alexpace.kassist.domain.models.classes.user.User
import dev.alexpace.kassist.domain.models.enums.user.UserRole
import dev.alexpace.kassist.domain.models.enums.user.UserType
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

    override suspend fun populateUserLocations() {
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

        // Define affected and unaffected areas
        val affectedAreas = listOf(
            Coordinates(39.4275, -0.4180), // Paiporta
            Coordinates(39.4100, -0.3990), // Massanassa
            Coordinates(39.4050, -0.4180), // Catarroja
            Coordinates(39.4140, -0.4060)  // Alfafar
        )

        val unaffectedAreas = listOf(
            Coordinates(39.4699, -0.3763), // Valencia Center
            Coordinates(39.5100, -0.4147), // Burjassot
            Coordinates(39.5021, -0.4401), // Paterna
            Coordinates(39.4750, -0.3500), // Campanar
            Coordinates(39.4667, -0.4211), // Mislata
            Coordinates(39.4572, -0.4367), // Xirivella
            Coordinates(39.5000, -0.4667)  // Manises
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
            // Victims (8) in affected areas
            Triple("user1", names[0], UserType.Victim to UserRole.Basic),
            Triple("user2", names[1], UserType.Victim to UserRole.Basic),
            Triple("user3", names[2], UserType.Victim to UserRole.Basic),
            Triple("user4", names[3], UserType.Victim to UserRole.Basic),
            Triple("user5", names[4], UserType.Victim to UserRole.Basic),
            Triple("user6", names[5], UserType.Victim to UserRole.Basic),
            Triple("user7", names[6], UserType.Victim to UserRole.Basic),
            Triple("user8", names[7], UserType.Victim to UserRole.Basic),
            // Supporters (8) in unaffected areas
            Triple("user9", names[8], UserType.Supporter to UserRole.Basic),
            Triple("user10", names[9], UserType.Supporter to UserRole.Basic),
            Triple("user11", names[10], UserType.Supporter to UserRole.Basic),
            Triple("user12", names[11], UserType.Supporter to UserRole.Basic),
            Triple("user13", names[12], UserType.Supporter to UserRole.Basic),
            Triple("user14", names[13], UserType.Supporter to UserRole.Basic),
            Triple("user15", names[14], UserType.Supporter to UserRole.Basic),
            Triple("user16", names[15], UserType.Supporter to UserRole.Basic),
            // Admins (4) in unaffected areas
            Triple("user17", names[16], UserType.Admin to UserRole.Admin),
            Triple("user18", names[17], UserType.Admin to UserRole.Admin),
            Triple("user19", names[18], UserType.Admin to UserRole.Admin),
            Triple("user20", names[19], UserType.Admin to UserRole.Admin)
        )

        // Use Firestore batch for atomic writes
        firestore.runTransaction {
            users.forEachIndexed { index, (id, name, typeRole) ->
                val coords = if (typeRole.first == UserType.Victim) {
                    affectedAreas[index % affectedAreas.size]
                } else {
                    unaffectedAreas[index % unaffectedAreas.size]
                }

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

                val userLocation = UserLocation(
                    id = id,
                    user = user,
                    latitude = coords.latitude,
                    longitude = coords.longitude,
                    timestamp = 1730209200000, // Oct 29, 2024, 12:00 PM
                    isActive = true
                )

                usersLocationCollection.document(userLocation.id).set(userLocation)
            }
        }
    }

}