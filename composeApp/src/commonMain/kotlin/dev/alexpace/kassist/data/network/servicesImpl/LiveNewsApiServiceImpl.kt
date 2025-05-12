package dev.alexpace.kassist.data.network.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpClient
import dev.alexpace.kassist.data.network.responses.LiveNewsResponse
import dev.alexpace.kassist.data.utils.constants.BASE_URL_TIPS
import dev.alexpace.kassist.domain.models.enums.UserType
import dev.alexpace.kassist.domain.services.LiveNewsApiService
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.http.parameters

class LiveNewsApiServiceImpl : LiveNewsApiService {

    private val http = createHttpClient(getHttpClient())
    private var count = 0

    override suspend fun getByKeywords(keywords: String, userRole: UserType): LiveNewsResponse {
        val response: HttpResponse = http.get(BASE_URL_TIPS, {
            parameters {
                append("keywords", keywords)
                append("user_role", userRole.toString())
            }
        })
        return if (response.status.isSuccess()) {
            response.body()
        } else {
            if (count >= 5) {
                throw Exception("Error fetching news")
            }
            count++
            getByKeywords(keywords, userRole)
        }
    }

}