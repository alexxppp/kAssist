package dev.alexpace.kassist.data.network.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpClient
import dev.alexpace.kassist.data.network.responses.LiveNewsResponse
import dev.alexpace.kassist.data.utils.constants.BASE_URL_TIPS
import dev.alexpace.kassist.domain.models.enums.UserType
import dev.alexpace.kassist.domain.services.LiveNewsApiService
import io.ktor.client.call.body
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class LiveNewsApiServiceImpl : LiveNewsApiService {

    private val http = createHttpClient(getHttpClient())
    private var count = 0

    override suspend fun getByKeywords(keywords: String, userRole: UserType): LiveNewsResponse {
        println("Sending keywords: '$keywords'")
        val response: HttpResponse = http.get("$BASE_URL_TIPS?disaster=$keywords&user_role=${userRole.name}") {
            timeout {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 15_000
            }
        }
        println("Response status: ${response.status}")
        return if (response.status.isSuccess()) {
            count = 0
            response.body()
        } else {
            if (count >= 5) {
                throw Exception("Error fetching news: ${response.status.value}")
            }
            count++
            getByKeywords(keywords, userRole)
        }
    }
}