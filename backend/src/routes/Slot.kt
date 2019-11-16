package com.sergiocasero.routes

import com.sergiocasero.repository.BackendRepository
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

fun Application.slot(repository: BackendRepository) {
    routing {
        route("/slot") {
            get { call.execute(repository.getSlots()) }
            get("/{id}") { call.execute(repository.getSlot(call.parameters["id"]?.toLongOrNull() ?: 0)) }
        }
    }
}
