package com.sergiocasero.repository

import com.sergiocasero.commit.common.model.Day
import com.sergiocasero.commit.common.model.ListResponse
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.model.Track
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.db.LocalDataSource
import com.sergiocasero.remote.RemoteDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface BackendRepository {
    suspend fun getDays(): Either<Error, ListResponse<Day>>

    suspend fun getTracks(): Either<Error, ListResponse<Track>>
    suspend fun getDayTracks(dayId: Long): Either<Error, ListResponse<Track>>
    suspend fun getTrackSlots(trackId: Long): Either<Error, ListResponse<Slot>>
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

    override suspend fun getDays(): Either<Error, ListResponse<Day>> = local.getDays()

    override suspend fun getTracks(): Either<Error, ListResponse<Track>> = local.getTracks()
    override suspend fun getDayTracks(dayId: Long): Either<Error, ListResponse<Track>> = local.getDayTracks(dayId)

    override suspend fun getTrackSlots(trackId: Long): Either<Error, ListResponse<Slot>> = local.getTrackSlots(trackId)
}
