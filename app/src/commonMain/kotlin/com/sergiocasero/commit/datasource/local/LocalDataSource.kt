package com.sergiocasero.commit.datasource.local

import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.result.Success

expect class LocalDataSource {
    fun saveFavSlot(slot: Slot): Either<Error, Success>
    fun getFavSlots(): Either<Error, List<Slot>>
    fun remoteFavSlot(slot: Slot): Either<Error, Success>
    fun isSlotFav(slot: Slot): Either<Error, Boolean>

    fun clear(): Either<Error, Success>
}