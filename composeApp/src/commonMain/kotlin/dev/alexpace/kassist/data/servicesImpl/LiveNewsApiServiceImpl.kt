package dev.alexpace.kassist.data.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpClient
import dev.alexpace.kassist.data.network.responses.LiveNewsResponse
import dev.alexpace.kassist.domain.services.LiveNewsApiService
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class LiveNewsApiServiceImpl: LiveNewsApiService {

    private val http = createHttpClient(getHttpClient())

    override suspend fun getByKeywords(keywords: String): LiveNewsResponse {
        val response: HttpResponse = http.get("http://127.0.0.1:8000/summarize-disaster-news")
        return if (response.status.isSuccess()) {
            response.body()
        } else {
            getByKeywords(keywords)
        }
    }


}