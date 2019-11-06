package com.sergiocasero.commit.repository

import com.sergiocasero.commit.common.model.DayItem
import com.sergiocasero.commit.common.model.ListResponse
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error

interface ClientRepository {
    suspend fun getSlot(slotId: Long): Either<Error, Slot>
    suspend fun getDays(): Either<Error, ListResponse<DayItem>>
}
