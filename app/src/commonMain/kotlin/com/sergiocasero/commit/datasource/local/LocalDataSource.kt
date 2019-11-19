package com.sergiocasero.commit.datasource.local

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.result.Success

interface LocalDataSource {
    suspend fun saveFavSlot(slot: Slot): Either<Error, Success>
    suspend fun getFavSlots(): Either<Error, List<Slot>>
    suspend fun remoteFavSlot(slot: Slot): Either<Error, Success>
    suspend fun isSlotFav(slot: Slot): Either<Error, Boolean>

    suspend fun clear(): Either<Error, Success>
}
