package dev.alexpace.kassist.data.network.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpClient
import dev.alexpace.kassist.data.network.responses.Feature
import dev.alexpace.kassist.data.utils.constants.BASE_URL_DISASTERS
import dev.alexpace.kassist.data.network.responses.Geometry
import dev.alexpace.kassist.domain.models.classes.app.NaturalDisaster
import dev.alexpace.kassist.data.network.responses.NaturalDisasterResponse
import dev.alexpace.kassist.data.network.responses.SeverityData
import dev.alexpace.kassist.domain.models.classes.map.Coordinates
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class NaturalDisasterApiServiceImpl: NaturalDisasterApiService {

    private val http = createHttpClient(getHttpClient())
    private var count = 0;

    override suspend fun getNaturalDisasters(): NaturalDisasterResponse {
        val response: HttpResponse = http.get("$BASE_URL_DISASTERS/EVENTS4APP")
        return if (response.status.isSuccess()) {

            NaturalDisasterResponse(
                features = listOf(
                    Feature(
                        geometry = Geometry(
                            coordinates = listOf(-16.2654, 28.0847) // Near Tenerife, Canary Islands, Spain
                        ),
                        properties = NaturalDisaster(
                            id = 1479773,
                            type = "EQ",
                            alertLevel = "Orange",
                            startDate = "2025-05-01T18:15:05Z",
                            endDate = "2025-05-01T18:15:05Z",
                            name = "Earthquake near Tenerife",
                            description = "Moderate earthquake detected off the coast of Tenerife, Canary Islands.",
                            htmlDescription = "Orange M 4.8 Earthquake off-shore near Tenerife at: 01 May 2025 18:15:05 UTC.",
                            icon = "https://www.gdacs.org/images/gdacs_icons/maps/Orange/EQ.png",
                            country = "Spain",
                            coordinates = Coordinates(
                                latitude = 28.233600,
                                longitude = -16.532602
                            ),
                            severityData = SeverityData(
                                severity = 4.8,
                                severitytext = "Magnitude 4.8M, Depth: 10km",
                                severityunit = "M"
                            )
                        )
                    ),
                    Feature(
                        geometry = Geometry(
                            coordinates = listOf(-16.5123, 27.9231) // Near Gran Canaria, Canary Islands, Spain
                        ),
                        properties = NaturalDisaster(
                            id = 1479774,
                            type = "EQ",
                            alertLevel = "Orange",
                            startDate = "2025-05-01T18:20:12Z",
                            endDate = "2025-05-01T18:20:12Z",
                            name = "Earthquake near Gran Canaria",
                            description = "Moderate earthquake detected off the coast of Gran Canaria, Canary Islands.",
                            htmlDescription = "Orange M 5.0 Earthquake off-shore near Gran Canaria at: 01 May 2025 18:20:12 UTC.",
                            icon = "https://www.gdacs.org/images/gdacs_icons/maps/Orange/EQ.png",
                            country = "Spain",
                            coordinates = Coordinates(
                                latitude = 28.233600,
                                longitude = -16.532602
                            ),
                            severityData = SeverityData(
                                severity = 5.0,
                                severitytext = "Magnitude 5.0M, Depth: 12km",
                                severityunit = "M"
                            )
                        )
                    )
                )
            )

        } else {
            if (count > 5) {
                throw Exception("Error fetching news")
            }
            count++
            getNaturalDisasters()
        }
    }

}

