package com.sergiocasero.commit.common.repository

import com.sergiocasero.commit.common.model.Day
import com.sergiocasero.commit.common.model.DaysResponse
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.model.Track
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.datasource.local.LocalDataSource
import com.sergiocasero.commit.common.datasource.remote.RemoteDataSource

class CommonClientRepository(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : ClientRepository {
    override suspend fun getSlot(slotId: Long): Either<Error, Slot> = remote.getSlot(slotId)
    override suspend fun getDays(): Either<Error, DaysResponse> = remote.getDays()
    override suspend fun getDayTracks(dayId: Long): Either<Error, Day> = remote.getDayTracks(dayId)
    override suspend fun getTrack(trackId: Long): Either<Error, Track> = remote.getTrack(trackId)
}
