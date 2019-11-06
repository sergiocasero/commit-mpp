package com.sergiocasero.commit.repository

import com.sergiocasero.commit.common.model.*
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error

interface ClientRepository {
    suspend fun getSlot(slotId: Long): Either<Error, Slot>
    suspend fun getDays(): Either<Error, Days>
    suspend fun getDayTracks(dayId: Long): Either<Error, Day>
}
