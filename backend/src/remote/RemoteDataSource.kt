package com.sergiocasero.remote

import com.sergiocasero.commit.common.CommitResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.takeFrom

interface RemoteDataSource {
    suspend fun parseData(): CommitResponse
}

class KtorRemoteDataSource : RemoteDataSource {

    companion object {
        private const val END_POINT = "https://www.koliseo.com"
    }

    private val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    override suspend fun parseData(): CommitResponse = client.get {
        apiUrl("/events/commit-2019/r4p/5106829466009600/agenda")
        // accept(ContentType.Application.Json)
    }

    private fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(END_POINT)
            encodedPath = path
        }
    }
}
