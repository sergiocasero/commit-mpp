package com.sergiocasero.repository

import com.sergiocasero.commit.common.model.*
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.db.LocalDataSource
import com.sergiocasero.remote.RemoteDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

interface BackendRepository {
    suspend fun getDays(): Either<Error, DaysResponse>
    suspend fun getTracks(): Either<Error, TracksResponse>
    suspend fun getSlots(): Either<Error, SlotsResponse>

    suspend fun getDay(dayId: Long): Either<Error, Day>
    suspend fun getTrack(trackId: Long): Either<Error, Track>
    suspend fun getSlot(slotId: Long): Either<Error, Slot>
    suspend fun update(): String
}

class CommitBackendRepository(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : BackendRepository {

    init {
        runBlocking { update() }
    }

    override suspend fun getDays(): Either<Error, DaysResponse> = local.getDays()
    override suspend fun getTracks(): Either<Error, TracksResponse> = local.getTracks()
    override suspend fun getSlots(): Either<Error, SlotsResponse> = local.getSlots()

    override suspend fun getTrack(trackId: Long): Either<Error, Track> = local.getTrack(trackId)
    override suspend fun getDay(dayId: Long): Either<Error, Day> = local.getDay(dayId)
    override suspend fun getSlot(slotId: Long): Either<Error, Slot> = local.getSlot(slotId)

    override suspend fun update(): String {
        GlobalScope.launch {
            val data = remote.parseData()
            local.saveData(data)
        }

        return "ok"
    }
}
