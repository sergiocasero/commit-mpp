package com.sergiocasero.routes

import com.sergiocasero.repository.BackendRepository
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

fun Application.day(repository: BackendRepository) {
    routing {
        route("/day") {
            get { call.execute(repository.getDays()) }
            get("/{id}") { call.execute(repository.getDay(call.parameters["id"]?.toLongOrNull() ?: 0)) }
        }
    }
}
