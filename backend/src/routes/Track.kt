package com.sergiocasero.routes

import com.sergiocasero.repository.BackendRepository
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

fun Application.track(repository: BackendRepository) {
    routing {
        route("/track") {
            get { call.execute(repository.getTracks()) }
            get("/{id}") { call.execute(repository.getTrack(call.parameters["id"]?.toLongOrNull() ?: 0)) }
        }
    }
}
