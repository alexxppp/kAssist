package dev.alexpace.kassist.infrastructure.repositoriesImpl

import dev.alexpace.kassist.domain.repositories.NaturalDisasterRepository
import dev.alexpace.kassist.infrastructure.responses.NaturalDisasterResponse

/**
 * Repository implementation for Natural Disaster
 * TODO: Apparently not necessary
 */
class NaturalDisasterRepositoryImpl: NaturalDisasterRepository {

    override suspend fun getAllNaturalDisasters(): NaturalDisasterResponse {
        // TODO: Yet to implement
        throw NotImplementedError()
    }

}