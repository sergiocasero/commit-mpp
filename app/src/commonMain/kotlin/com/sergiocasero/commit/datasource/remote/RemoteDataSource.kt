package com.sergiocasero.commit.datasource.remote

import com.sergiocasero.commit.common.model.Day
import com.sergiocasero.commit.common.model.DaysResponse
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error

interface RemoteDataSource {
    suspend fun getSlot(slotId: Long): Either<Error, Slot>
    suspend fun getDays(): Either<Error, DaysResponse>
    suspend fun getDayTracks(dayId: Long): Either<Error, Day>
}
