package com.sergiocasero.commit.datasource.remote

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

class CommonRemoteDataSource : RemoteDataSource {

    companion object {
        private const val END_POINT = "https://commitconf.herokuapp.com"
    }

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    override fun getSlot(slotId: Long): Either<Error, Slot> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
