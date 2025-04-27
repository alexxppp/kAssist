package dev.alexpace.kassist.data.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpClient
import dev.alexpace.kassist.data.utils.constants.BASE_URL
import dev.alexpace.kassist.domain.models.shared.naturalDisaster.NaturalDisasterResponse
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class NaturalDisasterApiServiceImpl: NaturalDisasterApiService {

    private val http = createHttpClient(getHttpClient())

    override suspend fun getNaturalDisasters(): NaturalDisasterResponse {
        val response: HttpResponse = http.get("$BASE_URL/EVENTS4APP")
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            return getNaturalDisasters()
        }
    }

}

