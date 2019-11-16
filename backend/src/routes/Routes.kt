package com.sergiocasero.routes

import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.db.H2LocalDataSource
import com.sergiocasero.remote.KtorRemoteDataSource
import com.sergiocasero.repository.CommitBackendRepository
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

fun Application.routes() {
    val repository = CommitBackendRepository(local = H2LocalDataSource(), remote = KtorRemoteDataSource())
    day(repository)
    track(repository)
    slot(repository)
    update(repository)
}

suspend fun <R : Any> ApplicationCall.execute(either: Either<Error, R>) {
    when (either) {
        is Either.Left -> {
            val statusCode = when (either.error) {
                Error.NoInternet -> HttpStatusCode(500, "Server error")
                Error.NotFound -> HttpStatusCode.NotFound
                Error.InvalidCredentials -> HttpStatusCode.Unauthorized
                Error.Default -> HttpStatusCode(500, "Server error")
            }
            respond(statusCode)
        }
        is Either.Right -> respond(either.success)
    }
}
