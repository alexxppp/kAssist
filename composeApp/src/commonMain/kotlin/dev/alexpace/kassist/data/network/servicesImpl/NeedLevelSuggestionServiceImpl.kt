package dev.alexpace.kassist.data.network.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpEngine
import dev.alexpace.kassist.data.network.responses.NeedLevelSuggestionResponse
import dev.alexpace.kassist.data.utils.constants.BASE_URL_SUGGESTION
import dev.alexpace.kassist.domain.services.NeedLevelSuggestionService
import io.ktor.client.call.body
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class NeedLevelSuggestionServiceImpl: NeedLevelSuggestionService {

    private val http = createHttpClient(getHttpEngine())
    private var count = 0

    override suspend fun getSuggestion(requestText: String): NeedLevelSuggestionResponse {
        val response: HttpResponse = http.get("$BASE_URL_SUGGESTION?request_text=$requestText") {
            timeout {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 15_000
            }
        }
        return if (response.status.isSuccess()) {
            count = 0
            response.body()
        } else {
            if (count >= 5) {
                throw Exception("Error fetching suggestion: ${response.status.value}")
            }
            count++
            getSuggestion(requestText)
        }
    }
}