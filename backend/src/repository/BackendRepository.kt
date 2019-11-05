package com.sergiocasero.repository

import com.sergiocasero.commit.common.model.*
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.db.LocalDataSource
import com.sergiocasero.remote.RemoteDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface BackendRepository {
    suspend fun getDays(): Either<Error, ListResponse<DayItem>>

    suspend fun getTracks(): Either<Error, ListResponse<TrackItem>>

    suspend fun getDay(dayId: Long): Either<Error, Day>
    suspend fun getTrack(trackId: Long): Either<Error, Track>
}

class CommitBackendRepository(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : BackendRepository {

    init {
        GlobalScope.launch {
            val data = remote.parseData()
            local.saveData(data)
        }

    }

    override suspend fun getDays(): Either<Error, ListResponse<DayItem>> = local.getDays()

    override suspend fun getTracks(): Either<Error, ListResponse<TrackItem>> = local.getTracks()
    override suspend fun getDay(dayId: Long): Either<Error, Day> = local.getDay(dayId)

    override suspend fun getTrack(trackId: Long): Either<Error, Track> = local.getTrack(trackId)
}
