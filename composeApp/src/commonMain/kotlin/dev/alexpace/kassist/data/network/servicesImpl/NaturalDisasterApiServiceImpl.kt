package dev.alexpace.kassist.data.network.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpClient
import dev.alexpace.kassist.data.utils.constants.BASE_URL
import dev.alexpace.kassist.data.network.responses.Feature
import dev.alexpace.kassist.data.network.responses.Geometry
import dev.alexpace.kassist.domain.models.shared.NaturalDisaster
import dev.alexpace.kassist.data.network.responses.NaturalDisasterResponse
import dev.alexpace.kassist.data.network.responses.SeverityData
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class NaturalDisasterApiServiceImpl: NaturalDisasterApiService {

    private val http = createHttpClient(getHttpClient())
    private var count = 0;

    override suspend fun getNaturalDisasters(): NaturalDisasterResponse {
        val response: HttpResponse = http.get("$BASE_URL/EVENTS4APP")
        return if (response.status.isSuccess()) {

            NaturalDisasterResponse(
                features = listOf(
                    Feature(
                        geometry = Geometry(
                            coordinates = listOf(-16.0113, -43.6369)
                        ),
                        properties = NaturalDisaster(
                            id = 1479773,
                            type = "EQ",
                            alertLevel = "Orange",
                            startDate = "2025-05-01T18:15:05",
                            endDate = "2025-05-01T18:15:05",
                            name = "Earthquake in ",
                            description = "Earthquake in ",
                            htmlDescription = "Green M 4.8 Earthquake off-shore at: 01 May 2025 18:15:05.",
                            icon = "https://www.gdacs.org/images/gdacs_icons/maps/Green/EQ.png",
                            country = "",
                            severityData = SeverityData(
                                severity = 4.8,
                                severitytext = "Magnitude 4.8M, Depth:10km",
                                severityunit = "M"
                            )
                        )
                    )
                )
            )
            // response.body()
        } else {
            if (count > 5) {
                throw Exception("Error fetching news")
            }
            count++
            getNaturalDisasters()
        }
    }

}

