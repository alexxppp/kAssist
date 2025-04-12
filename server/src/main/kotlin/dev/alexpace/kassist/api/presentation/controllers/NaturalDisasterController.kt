package dev.alexpace.kassist.api.presentation.controllers

import dev.alexpace.kassist.api.infrastructure.repositories.EmergencyPlanRepository
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

/**
 * Opens endpoints. Shouldn't include much business logic.
 */
fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Ktor: Hello")
        }

        // TODO: Return list of EmergencyPlan
        get("/ep") {
            val emergencyPlanRepository = EmergencyPlanRepository()
            val emergencyPlans = emergencyPlanRepository.getAllEmergencyPlans()

            call.respondText(emergencyPlans.toString())
        }
    }
}