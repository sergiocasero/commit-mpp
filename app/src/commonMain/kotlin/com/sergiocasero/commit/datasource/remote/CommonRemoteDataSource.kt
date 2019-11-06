package com.sergiocasero.commit.datasource.remote

import com.sergiocasero.commit.common.model.Day
import com.sergiocasero.commit.common.model.Days
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.takeFrom

class CommonRemoteDataSource : RemoteDataSource {

    companion object {
        private const val END_POINT = "https://commitconf.herokuapp.com"
    }

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    override suspend fun getSlot(slotId: Long): Either<Error, Slot> = execute {
        client.get<Slot> {
            apiUrl("/slot/$slotId")
        }
    }

    override suspend fun getDays(): Either<Error, Days> = execute {
        client.get<Days> {
            apiUrl("/day")
        }
    }

    override suspend fun getDayTracks(dayId: Long): Either<Error, Day> = execute {
        client.get<Day> {
            apiUrl("/day/$dayId")
        }
    }

    private suspend fun <R> execute(f: suspend () -> R): Either<Error, R> =
        try {
            Either.Right(f())
        } catch (e: Exception) {
            Either.Left(
                when (e) {
                    is ClientRequestException -> when (e.response.status) {
                        HttpStatusCode.Unauthorized -> Error.InvalidCredentials
                        HttpStatusCode.NotFound -> Error.NotFound
                        HttpStatusCode.BadRequest -> Error.NoInternet
                        else -> Error.Default
                    }
                    else -> Error.Default
                }
            )
        }

    private fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(END_POINT)
            encodedPath = path
        }
    }
}
