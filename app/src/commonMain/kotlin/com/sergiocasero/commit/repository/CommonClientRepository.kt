package com.sergiocasero.commit.repository

import com.sergiocasero.commit.common.model.*
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.datasource.local.LocalDataSource
import com.sergiocasero.commit.datasource.remote.RemoteDataSource

class CommonClientRepository(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : ClientRepository {
    override suspend fun getSlot(slotId: Long): Either<Error, Slot> = remote.getSlot(slotId)
    override suspend fun getDays(): Either<Error, DaysResponse> = remote.getDays()
    override suspend fun getDayTracks(dayId: Long): Either<Error, Day> = remote.getDayTracks(dayId)
}
