package dev.alexpace.kassist.data.network.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpEngine
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

    private val http = createHttpClient(getHttpEngine())
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
                            id = "1479773",
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
                            coordinates = listOf(-15.627312, 27.938431) // Near Gran Canaria, Canary Islands, Spain
                        ),
                        properties = NaturalDisaster(
                            id = "1479774",
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
                                latitude = 27.938431,
                                longitude = -15.627312
                            ),
                            severityData = SeverityData(
                                severity = 5.0,
                                severitytext = "Magnitude 5.0M, Depth: 12km",
                                severityunit = "M"
                            )
                        )
                    ),
                    Feature(
                        geometry = Geometry(
                            coordinates = listOf(-0.3763, 39.4699) // Valencia, Spain
                        ),
                        properties = NaturalDisaster(
                            id = "DANA2024VALENCIA",
                            type = "Flood",
                            alertLevel = "Red",
                            startDate = "2024-10-28T00:00:00Z",
                            endDate = "2024-11-05T23:59:59Z",
                            name = "DANA Valencia 2024",
                            description = "Severe flooding caused by a DANA event in Valencia, Spain, October 2024.",
                            htmlDescription = "<p>Severe flooding caused by a DANA event in Valencia, Spain, October 2024.</p>",
                            icon = "https://www.gdacs.org/images/gdacs_icons/maps/Red/Flood.png",
                            country = "Spain",
                            coordinates = Coordinates(
                                latitude = 39.4699,
                                longitude = -0.3763
                            ),
                            severityData = SeverityData(
                                severity = 8.5,
                                severitytext = "Severe",
                                severityunit = "Richter equivalent"
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

