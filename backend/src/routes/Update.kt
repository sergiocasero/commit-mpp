package com.sergiocasero.routes

import com.sergiocasero.repository.BackendRepository
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

fun Application.update(repository: BackendRepository) {
    routing {
        route("/update") {
            get { call.respond(repository.update()) }
        }
    }
}