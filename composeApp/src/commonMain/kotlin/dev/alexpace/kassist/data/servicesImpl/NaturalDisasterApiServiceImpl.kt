package dev.alexpace.kassist.data.servicesImpl

import dev.alexpace.kassist.data.network.http.createHttpClient
import dev.alexpace.kassist.data.network.http.getHttpClient
import dev.alexpace.kassist.data.utils.constants.BASE_URL
import dev.alexpace.kassist.domain.models.shared.naturalDisaster.Feature
import dev.alexpace.kassist.domain.models.shared.naturalDisaster.Geometry
import dev.alexpace.kassist.domain.models.shared.naturalDisaster.NaturalDisaster
import dev.alexpace.kassist.domain.models.shared.naturalDisaster.NaturalDisasterResponse
import dev.alexpace.kassist.domain.models.shared.naturalDisaster.SeverityData
import dev.alexpace.kassist.domain.services.NaturalDisasterApiService
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class NaturalDisasterApiServiceImpl: NaturalDisasterApiService {

    private val http = createHttpClient(getHttpClient())

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
            getNaturalDisasters()
        }
    }

}

