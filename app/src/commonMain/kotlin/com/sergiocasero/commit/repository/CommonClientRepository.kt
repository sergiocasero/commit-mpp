package com.sergiocasero.commit.repository

import com.sergiocasero.commit.common.model.Day
import com.sergiocasero.commit.common.model.DaysResponse
import com.sergiocasero.commit.common.model.Slot
import com.sergiocasero.commit.common.model.Track
import com.sergiocasero.commit.common.result.Either
import com.sergiocasero.commit.common.result.Error
import com.sergiocasero.commit.common.result.Success
import com.sergiocasero.commit.datasource.local.LocalDataSource
import com.sergiocasero.commit.datasource.remote.RemoteDataSource

class CommonClientRepository(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : ClientRepository {
    override suspend fun getSlot(slotId: Long): Either<Error, Slot> = remote.getSlot(slotId)
    override suspend fun getDays(): Either<Error, DaysResponse> = remote.getDays()
    override suspend fun getDayTracks(dayId: Long): Either<Error, Day> = remote.getDayTracks(dayId)
    override suspend fun getTrack(trackId: Long): Either<Error, Track> = remote.getTrack(trackId)

    override suspend fun getFavSlots(): Either<Error, List<Slot>> = local.getFavSlots()
    override suspend fun updateFavSlot(slot: Slot, save: Boolean): Either<Error, Success> = when(save) {
        true -> local.saveFavSlot(slot)
        false -> local.remoteFavSlot(slot)
    }

    override suspend fun isSlotFav(slot: Slot): Either<Error, Boolean> = local.isSlotFav(slot)
}
