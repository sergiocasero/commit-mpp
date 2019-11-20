package com.sergiocasero.commit.repository

import com.sergiocasero.commit.common.model.Day
import com.sergiocasero.commit.common.model.DaysResponse
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.model.Track
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.result.Success

interface ClientRepository {
    suspend fun getSlot(slotId: Long): Either<Error, Slot>
    suspend fun getDays(): Either<Error, DaysResponse>
    suspend fun getDayTracks(dayId: Long): Either<Error, Day>
    suspend fun getTrack(trackId: Long): Either<Error, Track>

    suspend fun updateFavSlot(slot: Slot, save: Boolean): Either<Error, Success>
    suspend fun getFavSlots(): Either<Error, List<Slot>>
    suspend fun isSlotFav(slot: Slot): Either<Error, Boolean>
}
