package dev.alexpace.kassist.domain.repositories

import dev.alexpace.kassist.domain.models.shared.NaturalDisaster
import kotlinx.coroutines.flow.Flow

interface NaturalDisasterRepository {

    fun getAll(): Flow<List<NaturalDisaster>>
    fun getById(id: String): Flow<NaturalDisaster?>
    suspend fun add(naturalDisaster: NaturalDisaster)
    suspend fun addAll(naturalDisasters: List<NaturalDisaster>)
    suspend fun update(naturalDisaster: NaturalDisaster)
    suspend fun delete(naturalDisaster: NaturalDisaster)
}
